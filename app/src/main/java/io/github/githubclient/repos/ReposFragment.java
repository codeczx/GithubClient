package io.github.githubclient.repos;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;
import android.text.style.ReplacementSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import io.github.githubclient.ViewModelFactory;
import io.github.githubclient.data.ReposEntity;
import io.github.githubclient.databinding.FragReposBinding;

/**
 * Created by codeczx on 2018/11/14 下午 07:48.
 * Class description:
 */
public class ReposFragment extends Fragment {

	private FragReposBinding mReposFragBinding;
	private ReposViewModel mReposViewModel;
	private ReposAdapter mReposAdapter;

	public static ReposFragment newInstance() {
		return new ReposFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mReposViewModel = obtainViewModel(getActivity());
		mReposFragBinding = FragReposBinding.inflate(inflater, container, false);
		mReposFragBinding.setViewModel(mReposViewModel);
		return mReposFragBinding.getRoot();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mReposAdapter = new ReposAdapter(new ReposDiffCallback());
		mReposFragBinding.rvRepos.setAdapter(mReposAdapter);
		subscribeUi();
	}

	private void subscribeUi() {
		mReposViewModel.getReposData().observe(getViewLifecycleOwner(), new Observer<List<ReposEntity>>() {
			@Override
			public void onChanged(List<ReposEntity> reposEntities) {
				mReposAdapter.submitList(reposEntities);
			}
		});
	}

	private static ReposViewModel obtainViewModel(Activity activity) {
		ViewModelFactory viewModelFactory = ViewModelFactory.getInstance(activity.getApplication());
		return viewModelFactory.create(ReposViewModel.class);
	}
}
