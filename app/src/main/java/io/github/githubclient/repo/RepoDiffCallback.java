package io.github.githubclient.repo;

import androidx.recyclerview.widget.DiffUtil;
import io.github.githubclient.vo.Repo;

/**
 * Created by codeczx on 2018/11/14 下午 11:56.
 * Class description:
 */
public class RepoDiffCallback extends DiffUtil.ItemCallback<Repo> {
	@Override
	public boolean areItemsTheSame(Repo oldItem, Repo newItem) {
		return oldItem.getFullName().equals(newItem.getFullName());
	}

	@Override
	public boolean areContentsTheSame(Repo oldItem, Repo newItem) {
		return oldItem.getFullName().equals(newItem.getFullName());
	}
}
