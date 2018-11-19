package io.github.githubclient.repo;

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
import io.github.githubclient.databinding.FragRepoBinding;
import io.github.githubclient.vo.Repo;
import io.github.githubclient.vo.Resource;

/**
 * Created by codeczx on 2018/11/14 下午 07:48.
 * Class description:
 */
public class RepoFragment extends Fragment {

	private FragRepoBinding mRepoFragBinding;
	private RepoViewModel mRepoViewModel;
	private RepoAdapter mRepoAdapter;

	public static RepoFragment newInstance() {
		return new RepoFragment();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRepoViewModel = obtainViewModel(getActivity());
		mRepoFragBinding = FragRepoBinding.inflate(inflater, container, false);
		mRepoFragBinding.setViewModel(mRepoViewModel);
		return mRepoFragBinding.getRoot();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mRepoAdapter = new RepoAdapter(new RepoDiffCallback());
		mRepoFragBinding.rvRepo.setAdapter(mRepoAdapter);
		subscribeUi();
	}

	private void subscribeUi() {
		//TODO:处理loading情况
		mRepoFragBinding.etName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					mRepoViewModel.getNewSearchEvent().setValue(v.getText().toString());
					hideSoftInput();
				}
				return false;
			}
		});

		mRepoViewModel.getResults().observe(this, new Observer<Resource<List<Repo>>>() {
			@Override
			public void onChanged(Resource<List<Repo>> listResource) {
				mRepoAdapter.submitList(listResource.data);
			}
		});

		mRepoViewModel.getNewSearchEvent().observe(this, new Observer<String>() {
			@Override
			public void onChanged(String s) {
				mRepoViewModel.setQuery(s);
			}
		});
	}

	private void hideSoftInput() {
		InputMethodManager inputMethodManager = (InputMethodManager) getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		assert inputMethodManager != null;
		inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	private static RepoViewModel obtainViewModel(Activity activity) {
		ViewModelFactory viewModelFactory = ViewModelFactory.getInstance(activity.getApplication());
		return viewModelFactory.create(RepoViewModel.class);
	}
}
