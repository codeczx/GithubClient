package io.github.githubclient.repos;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import io.github.githubclient.data.ReposEntity;
import io.github.githubclient.databinding.ItemReposBinding;

/**
 * Created by codeczx on 2018/11/14 下午 11:38.
 * Class description:
 */
public class ReposAdapter extends ListAdapter<ReposEntity, ReposAdapter.ReposViewHolder> {

	protected ReposAdapter(DiffUtil.ItemCallback<ReposEntity> diffCallback) {
		super(diffCallback);
	}

	@Override
	public ReposViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ReposViewHolder(ItemReposBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
	}

	@Override
	public void onBindViewHolder(ReposViewHolder holder, int position) {
		holder.bind(getItem(position));
	}

	public static class ReposViewHolder extends RecyclerView.ViewHolder {

		private ItemReposBinding mItemReposBinding;

		public ReposViewHolder(ItemReposBinding itemReposBinding) {
			super(itemReposBinding.getRoot());
			mItemReposBinding = itemReposBinding;
		}

		public void bind(ReposEntity reposEntity) {
			mItemReposBinding.setReposEntity(reposEntity);
		}
	}
}
