package io.github.githubclient.repository;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import io.github.githubclient.AppExecutors;
import io.github.githubclient.api.ApiResource;
import io.github.githubclient.vo.Resource;

/**
 * Created by codeczx on 2018/11/18 上午 11:42.
 * Class description:
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {

	private MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();
	AppExecutors mAppExecutors;

	NetworkBoundResource(AppExecutors appExecutors) {
		mAppExecutors = appExecutors;
		result.setValue((Resource<ResultType>) Resource.loading(null));
		final LiveData<ResultType> dbSource = loadFromDb();
		result.addSource(dbSource, new Observer<ResultType>() {
			@Override
			public void onChanged(ResultType resultType) {
				result.removeSource(dbSource);
				if (shouldFetch(resultType)) {
					fetchFromNetwork(dbSource);
				} else {
					result.addSource(dbSource, new Observer<ResultType>() {
						@Override
						public void onChanged(ResultType resultType) {
							setValue(Resource.success(resultType));
						}
					});
				}
			}
		});
	}

	private void setValue(Resource<ResultType> newValue) {
		if (result.getValue() != newValue) {
			result.setValue(newValue);
		}
	}

	protected void fetchFromNetwork(final LiveData<ResultType> dbSource) {
		final LiveData<ApiResource<RequestType>> apiResponse = createCall();
		result.addSource(dbSource, new Observer<ResultType>() {
			@Override
			public void onChanged(ResultType newData) {
				setValue(Resource.loading(newData));
			}
		});
		result.addSource(apiResponse, new Observer<ApiResource<RequestType>>() {
			@Override
			public void onChanged(final ApiResource<RequestType> response) {
				result.removeSource(dbSource);
				result.removeSource(apiResponse);
				if (apiResponse != null) {
					if (response instanceof ApiResource.ApiSuccessResponse) {
						mAppExecutors.diskIO().execute(new Runnable() {
							@Override
							public void run() {
								saveCallResult(processResponse((ApiResource.ApiSuccessResponse<ResultType>) response));
								mAppExecutors.mainThread().execute(new Runnable() {
									@Override
									public void run() {
										result.addSource(loadFromDb(), new Observer<ResultType>() {
											@Override
											public void onChanged(ResultType newData) {
												setValue(Resource.success(newData));
											}
										});
									}
								});
							}
						});
					} else if (response instanceof ApiResource.ApiEmptyResponse) {
						mAppExecutors.mainThread().execute(new Runnable() {
							@Override
							public void run() {
								result.addSource(loadFromDb(), new Observer<ResultType>() {
									@Override
									public void onChanged(ResultType newData) {
										setValue(Resource.success(newData));
									}
								});
							}
						});
					} else if (response instanceof ApiResource.ApiErrorResponse) {
						onFetchFailed();
						result.addSource(dbSource, new Observer<ResultType>() {
							@Override
							public void onChanged(ResultType newData) {
								setValue(Resource.error(((ApiResource.ApiErrorResponse) response).getErrorMessage(), newData));
							}
						});
					}
				}
			}
		});
	}

	protected abstract void onFetchFailed();

	protected abstract RequestType processResponse(ApiResource.ApiSuccessResponse<ResultType> response);

	protected abstract boolean shouldFetch(ResultType resultType);

	protected abstract LiveData<ResultType> loadFromDb();

	protected abstract LiveData<ApiResource<RequestType>> createCall();

	protected abstract void saveCallResult(RequestType item);

	protected LiveData<Resource<ResultType>> asLiveData() {
		return result;
	}
}
