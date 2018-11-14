package io.github.githubclient.data;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by codeczx on 2018/11/15 上午 12:11.
 * Class description:
 */
public interface ApiService {

	@GET("users/{user}/repos")
	Flowable<List<ReposEntity>> getRepos(@Path("user") String name);
}
