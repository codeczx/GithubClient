package io.github.githubclient.repository;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.github.githubclient.AppExecutors;
import io.github.githubclient.api.ApiRepository;
import io.github.githubclient.api.ApiResource;
import io.github.githubclient.db.GithubDb;
import io.github.githubclient.db.RepoDao;
import io.github.githubclient.vo.Repo;
import io.github.githubclient.vo.Resource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by codeczx on 2018/11/18 下午 07:36.
 * Class description:
 */
public class RepoRepository {

	private static volatile RepoRepository INSTANCE;

	private AppExecutors mAppExecutors;
	private RepoDao mRepoDao;
	private GithubDb mGithubDb;

	private RepoRepository(Application application) {
		mAppExecutors = AppExecutors.getInstance();
		mRepoDao = GithubDb.getInstance(application).repoDao();
	}

	public static RepoRepository getInstance(Application application) {
		if (INSTANCE == null) {
			synchronized (RepoRepository.class) {
				if (INSTANCE == null) {
					INSTANCE = new RepoRepository(application);
				}
			}
		}
		return INSTANCE;
	}

	//TODO:完善处理
	public LiveData<Resource<List<Repo>>> loadRepos(final String owner) {
		return new NetworkBoundResource<List<Repo>, List<Repo>>(mAppExecutors) {
			@Override
			protected void onFetchFailed() {

			}

			@Override
			protected List<Repo> processResponse(ApiResource.ApiSuccessResponse<List<Repo>> response) {
				return response.getBody();
			}

			@Override
			protected boolean shouldFetch(List<Repo> repos) {
				return true;
			}

			@Override
			protected LiveData<List<Repo>> loadFromDb() {
				return mRepoDao.loadRepositories(owner);
			}

			@Override
			protected LiveData<ApiResource<List<Repo>>> createCall() {
				final MutableLiveData<ApiResource<List<Repo>>> liveData = new MutableLiveData<>();
				Disposable disposable = ApiRepository.getInstance().getRepo(owner)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new Consumer<List<Repo>>() {
							@Override
							public void accept(List<Repo> repos) throws Exception {
								liveData.postValue(ApiResource.create(Response.success(repos)));
							}
						});
				return liveData;
			}

			@Override
			protected void saveCallResult(List<Repo> item) {
				mRepoDao.insertRepo(item);
			}
		}.asLiveData();
	}

}
