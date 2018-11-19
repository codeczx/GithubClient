package io.github.githubclient.repo;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import io.github.githubclient.AppExecutors;
import io.github.githubclient.SingleLiveEvent;
import io.github.githubclient.repository.RepoRepository;
import io.github.githubclient.vo.Repo;
import io.github.githubclient.vo.Resource;
import io.reactivex.disposables.Disposable;

/**
 * Created by codeczx on 2018/11/14 下午 07:39.
 * Class description:
 */
public class RepoViewModel extends AndroidViewModel {

	private RepoRepository mRepoRepository;

	private SingleLiveEvent<String> mNewSearchEvent = new SingleLiveEvent<>();

	private MutableLiveData<String> query = new MutableLiveData<>();
	private LiveData<Resource<List<Repo>>> results = Transformations.switchMap(query,
			new Function<String, LiveData<Resource<List<Repo>>>>() {
				@Override
				public LiveData<Resource<List<Repo>>> apply(String input) {
					return mRepoRepository.loadRepos(input);
				}
			});

	public RepoViewModel(Application application) {
		super(application);
		mRepoRepository = RepoRepository.getInstance(application);
	}

	public LiveData<Resource<List<Repo>>> getResults() {
		return results;
	}

	public SingleLiveEvent<String> getNewSearchEvent() {
		return mNewSearchEvent;
	}

	public void setQuery(String s) {
		query.setValue(s);
	}
}
