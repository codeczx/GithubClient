package io.github.githubclient;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import io.github.githubclient.repos.ReposFragment;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setUpFragment();
	}

	private void setUpFragment() {
		ReposFragment reposFragment = ReposFragment.newInstance();
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.fl_content, reposFragment);
		fragmentTransaction.commit();
	}
}
