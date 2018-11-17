package io.github.githubclient.repos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.List;

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
	public void onResume() {
		super.onResume();
		mReposViewModel.start();
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

		mReposFragBinding.etName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(actionId == EditorInfo.IME_ACTION_SEARCH){
					mReposViewModel.getNewSearchEvent().setValue(v.getText().toString());
					hideSoftInput();
				}
				return false;
			}
		});

		mReposViewModel.getReposData().observe(getViewLifecycleOwner(), new Observer<List<ReposEntity>>() {
			@Override
			public void onChanged(List<ReposEntity> reposEntityEntities) {
				mReposAdapter.submitList(reposEntityEntities);
			}
		});

		mReposViewModel.getNewSearchEvent().observe(this, new Observer<String>() {
			@Override
			public void onChanged(String s) {
				mReposViewModel.getRepos();
			}
		});
	}

	private void hideSoftInput() {
		InputMethodManager inputMethodManager = (InputMethodManager) getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		assert inputMethodManager != null;
		inputMethodManager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
	}

	private static ReposViewModel obtainViewModel(Activity activity) {
		ViewModelFactory viewModelFactory = ViewModelFactory.getInstance(activity.getApplication());
		return viewModelFactory.create(ReposViewModel.class);
	}
}
