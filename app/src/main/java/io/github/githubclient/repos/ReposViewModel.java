package io.github.githubclient.repos;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.github.githubclient.SingleLiveEvent;
import io.github.githubclient.data.ApiRepository;
import io.github.githubclient.data.ReposEntity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by codeczx on 2018/11/14 下午 07:39.
 * Class description:
 */
public class ReposViewModel extends AndroidViewModel {

	private MutableLiveData<List<ReposEntity>> mReposData = new MutableLiveData<>();
	private SingleLiveEvent<String> mNewSearchEvent = new SingleLiveEvent<>();

	private Disposable mDisposable;

	public ReposViewModel(Application application) {
		super(application);
	}

	public void start(){
		getRepos();
	}

	public void getRepos(){
		if(mNewSearchEvent.getValue()==null){
			return;
		}

		mDisposable = ApiRepository.getInstance().getRepos(mNewSearchEvent.getValue())
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<List<ReposEntity>>() {
					@Override
					public void accept(List<ReposEntity> reposEntityEntities) throws Exception {
						mReposData.setValue(reposEntityEntities);
					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {

					}
				});
	}

	public LiveData<List<ReposEntity>> getReposData() {
		return mReposData;
	}

	public SingleLiveEvent<String> getNewSearchEvent() {
		return mNewSearchEvent;
	}
}
