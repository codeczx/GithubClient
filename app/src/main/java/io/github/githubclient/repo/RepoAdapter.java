package io.github.githubclient.repo;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import io.github.githubclient.databinding.ItemRepoBinding;
import io.github.githubclient.vo.Repo;

/**
 * Created by codeczx on 2018/11/14 下午 11:38.
 * Class description:
 */
public class RepoAdapter extends ListAdapter<Repo, RepoAdapter.ReposViewHolder> {

	protected RepoAdapter(DiffUtil.ItemCallback<Repo> diffCallback) {
		super(diffCallback);
	}

	@Override
	public ReposViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ReposViewHolder(ItemRepoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
	}

	@Override
	public void onBindViewHolder(ReposViewHolder holder, int position) {
		holder.bind(getItem(position));
	}

	public static class ReposViewHolder extends RecyclerView.ViewHolder {

		private ItemRepoBinding mItemReposBinding;

		public ReposViewHolder(ItemRepoBinding itemReposBinding) {
			super(itemReposBinding.getRoot());
			mItemReposBinding = itemReposBinding;
		}

		public void bind(Repo repo) {
			mItemReposBinding.setRepo(repo);
		}
	}
}
