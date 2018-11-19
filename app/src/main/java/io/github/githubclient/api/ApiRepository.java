package io.github.githubclient.api;

import java.util.List;

import io.github.githubclient.vo.Repo;
import io.reactivex.Flowable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by codeczx on 2018/11/15 上午 12:14.
 * Class description:
 */
public class ApiRepository {

	private static final String BASE_URL = "https://api.github.com";
	private static volatile ApiRepository INSTANCE;
	private OkHttpClient mOkHttpClient = getOkHttpClient();

	private ApiRepository() {
	}

	public static ApiRepository getInstance() {
		if (INSTANCE == null) {
			synchronized (ApiRepository.class) {
				if (INSTANCE == null) {
					INSTANCE = new ApiRepository();
				}
			}
		}
		return INSTANCE;
	}

	private OkHttpClient getOkHttpClient() {
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
		return new OkHttpClient.Builder()
				.addInterceptor(interceptor)
				.build();
	}

	private GithubService getRepoApi() {
		Retrofit retrofit = new Retrofit.Builder()
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())
				.baseUrl(BASE_URL)
				.client(mOkHttpClient)
				.build();
		return retrofit.create(GithubService.class);
	}

	public Flowable<List<Repo>> getRepo(String name) {
		return getRepoApi().getRepo(name);
	}
}
