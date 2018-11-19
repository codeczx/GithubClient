package io.github.githubclient;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by codeczx on 2018/11/18 上午 11:43.
 * Class description:
 */
public class AppExecutors {
	private static volatile AppExecutors INSTANCE;

	private Executor diskIO;
	private Executor networkIO;
	private Executor mainThread;

	public static AppExecutors getInstance() {
		if (INSTANCE == null) {
			synchronized (AppExecutors.class) {
				if (INSTANCE == null) {
					INSTANCE = new AppExecutors();
				}
			}
		}
		return INSTANCE;
	}

	private AppExecutors() {
		diskIO = Executors.newSingleThreadExecutor();
		networkIO = Executors.newFixedThreadPool(3);
		mainThread = new MainThreadExecutor();
	}

	public Executor diskIO() {
		return diskIO;
	}

	public Executor networkIO() {
		return networkIO;
	}

	public Executor mainThread() {
		return mainThread;
	}

	private class MainThreadExecutor implements Executor {

		private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

		@Override
		public void execute(Runnable command) {
			mainThreadHandler.post(command);
		}
	}
}
