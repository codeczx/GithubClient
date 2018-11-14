package io.github.githubclient.repos;

import androidx.recyclerview.widget.DiffUtil;
import io.github.githubclient.data.ReposEntity;

/**
 * Created by codeczx on 2018/11/14 下午 11:56.
 * Class description:
 */
public class ReposDiffCallback extends DiffUtil.ItemCallback<ReposEntity> {
	@Override
	public boolean areItemsTheSame(ReposEntity oldItem, ReposEntity newItem) {
		return oldItem.getName().equals(newItem.getName());
	}

	@Override
	public boolean areContentsTheSame(ReposEntity oldItem, ReposEntity newItem) {
		return oldItem.getName().equals(newItem.getName());
	}
}
