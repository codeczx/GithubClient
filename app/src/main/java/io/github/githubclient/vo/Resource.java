package io.github.githubclient.vo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by codeczx on 2018/11/18 上午 11:53.
 * Class description:
 */
public class Resource<T> {
	@NonNull
	public final Status status;
	@Nullable
	public final T data;
	@Nullable
	public final String message;

	private Resource(@NonNull Status status, @Nullable T data,
					 @Nullable String message) {
		this.status = status;
		this.data = data;
		this.message = message;
	}

	public static <T> Resource<T> success(@NonNull T data) {
		return new Resource<>(Status.SUCCESS, data, null);
	}

	public static <T> Resource<T> error(String msg, @Nullable T data) {
		return new Resource<>(Status.ERROR, data, msg);
	}

	public static <T> Resource<T> loading(@Nullable T data) {
		return new Resource<>(Status.LOADING, data, null);
	}

	public enum Status {SUCCESS, ERROR, LOADING}
}