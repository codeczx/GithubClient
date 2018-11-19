package io.github.githubclient;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import io.github.githubclient.repo.RepoViewModel;

/**
 * Created by codeczx on 2018/11/14 下午 07:33.
 * Class description:
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

	private static volatile ViewModelFactory INSTANCE;
	private Application mApplication;

	private ViewModelFactory(Application application) {
		mApplication = application;
	}

	public static ViewModelFactory getInstance(Application application) {
		if (INSTANCE == null) {
			synchronized (ViewModelFactory.class) {
				if (INSTANCE == null) {
					INSTANCE = new ViewModelFactory(application);
				}
			}
		}
		return INSTANCE;
	}

	@Override
	public <T extends ViewModel> T create(Class<T> modelClass) {
		if (modelClass.isAssignableFrom(RepoViewModel.class)) {
			return (T) new RepoViewModel(mApplication);
		}
		throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
	}
}
