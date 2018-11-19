package io.github.githubclient.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.github.githubclient.vo.Repo;

/**
 * Created by codeczx on 2018/11/16 下午 04:41.
 * Class description:
 */
@Dao
public interface RepoDao {
	@Query("SELECT * FROM repo WHERE owner_login = :owner")
	LiveData<List<Repo>> loadRepositories(String owner);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insertRepo(List<Repo> repos);
}
