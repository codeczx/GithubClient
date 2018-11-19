package io.github.githubclient.api;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by codeczx on 2018/11/18 下午 12:44.
 * Class description:
 */
public abstract class ApiResource<T> {

	public static <T> ApiErrorResponse<T> create(Throwable error) {
		return new ApiErrorResponse<T>(error != null ? error.getMessage() : "unknown error");
	}

	public static <T> ApiResource<T> create(Response<T> response) {
		if (response.isSuccessful()) {
			T body = response.body();
			if (body == null || response.code() == 204) {
				return new ApiEmptyResponse<>();
			} else {
				return new ApiSuccessResponse(body);
			}
		} else {
			String errorMsg = null;
			try {
				String msg = response.errorBody().string();
				errorMsg = msg == null || msg.isEmpty() ? response.message() : msg;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new ApiErrorResponse<>(errorMsg);
		}
	}

	/**
	 * Created by codeczx on 2018/11/18 下午 07:09.
	 * Class description:
	 */
	public static class ApiErrorResponse<T> extends ApiResource<T> {

		private String errorMessage;

		public ApiErrorResponse(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		public String getErrorMessage() {
			return errorMessage;
		}
	}

	/**
	 * Created by codeczx on 2018/11/18 下午 07:08.
	 * Class description:
	 */
	public static class ApiEmptyResponse<T> extends ApiResource<T> {
		ApiEmptyResponse() {
		}
	}

	/**
	 * Created by codeczx on 2018/11/18 下午 07:09.
	 * Class description:
	 */
	public static class ApiSuccessResponse<T> extends ApiResource<T> {

		private T body;

		ApiSuccessResponse(T body) {
			this.body = body;
		}

		public T getBody() {
			return body;
		}
	}
}
