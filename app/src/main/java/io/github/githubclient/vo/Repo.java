package io.github.githubclient.vo;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;

/**
 * Created by codeczx on 2018/11/16 下午 06:55.
 * Class description:
 */
@Entity
public class Repo {
	@PrimaryKey
	private int id;

	@ColumnInfo(name = "name")
	private String name;

	@ColumnInfo(name = "full_name")
	@SerializedName("full_name")
	private String fullName;

	@ColumnInfo(name = "stargazers_count")
	@SerializedName("stargazers_count")
	private int stargazersCount;

	@Embedded(prefix = "owner_")
	private Owner owner;

	public Repo(int id, String name, String fullName, int stargazersCount) {
		this.id = id;
		this.name = name;
		this.fullName = fullName;
		this.stargazersCount = stargazersCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getStargazersCount() {
		return stargazersCount;
	}

	public void setStargazersCount(int stargazersCount) {
		this.stargazersCount = stargazersCount;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public static class Owner {

		public Owner(String login, String avatarUrl) {
			this.login = login;
			this.avatarUrl = avatarUrl;
		}

		private String login;

		@ColumnInfo(name = "avatar_url")
		@SerializedName("avatar_url")
		private String avatarUrl;

		public String getLogin() {
			return login;
		}

		public void setLogin(String login) {
			this.login = login;
		}

		public String getAvatarUrl() {
			return avatarUrl;
		}

		public void setAvatarUrl(String avatarUrl) {
			this.avatarUrl = avatarUrl;
		}
	}
}
