package io.github.githubclient.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import io.github.githubclient.vo.Repo;

/**
 * Created by codeczx on 2018/11/16 下午 04:44.
 * Class description:
 */
@Database(entities = { Repo.class }, version = 1, exportSchema = false)
public abstract class GithubDb extends RoomDatabase {

	private static volatile GithubDb INSTANCE;

	public abstract RepoDao repoDao();

	public static GithubDb getInstance(final Context context) {
		if (INSTANCE == null) {
			synchronized (GithubDb.class) {
				if (INSTANCE == null) {
					INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
							GithubDb.class, "app_database")
							.build();
				}
			}
		}
		return INSTANCE;
	}
}
