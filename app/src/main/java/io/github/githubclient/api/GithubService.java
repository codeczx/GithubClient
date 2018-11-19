package io.github.githubclient.api;

import java.util.List;

import io.github.githubclient.vo.Repo;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by codeczx on 2018/11/15 上午 12:11.
 * Class description:
 */
public interface GithubService {

	@GET("users/{user}/repos")
	Flowable<List<Repo>> getRepo(@Path("user") String name);
}
