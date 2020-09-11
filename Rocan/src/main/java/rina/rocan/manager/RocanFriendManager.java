package rina.rocan.manager;

// Java.
import java.util.*;

// Client.
import rina.rocan.client.RocanFriend;
import rina.rocan.client.RocanEnemy;

/**
  *
  * @author Rina!
  *
  * Created by Rina!
  * 15/08/2020.
  *
  **/
public class RocanFriendManager {
	private ArrayList<RocanFriend> friend_list;
	private ArrayList<RocanEnemy> enemy_list;

	public RocanFriendManager(String tag_non_variable) {
		this.friend_list = new ArrayList<>();
		this.enemy_list  = new ArrayList<>();
	}

	public void addFriend(String name) {
		if (getEnemyByName(name) != null) {
			removeEnemy(getEnemyByName(name));
		}

		this.friend_list.add(new RocanFriend(name));
	}

	public void addEnemy(String name) {
		if (getFriendByName(name) != null) {
			removeFriend(getFriendByName(name));
		}

		this.enemy_list.add(new RocanEnemy(name));
	}

	public void removeFriend(RocanFriend friend) {
		this.friend_list.remove(friend);
	}

	public void removeEnemy(RocanEnemy enemy) {
		this.enemy_list.remove(enemy);
	}

	public ArrayList<RocanFriend> getFriendList() {
		return this.friend_list;
	}

	public ArrayList<RocanEnemy> getEnemyList() {
		return this.enemy_list;
	}

	public RocanFriend getFriendByName(String name) {
		for (RocanFriend friends : getFriendList()) {
			if (friends.getName().equals(name)) {
				return friends;
			}
		}

		return null;
	}

	public RocanEnemy getEnemyByName(String name) {
		for (RocanEnemy enemies : getEnemyList()) {
			if (enemies.getName().equals(name)) {
				return enemies;
			}
		}

		return null;
	}

	public boolean isFriend(String name) {
		if (getFriendByName(name) != null) {
			return true;
		}

		return false;
	}

	public boolean isEnemy(String name) {
		if (getEnemyByName(name) != null) {
			return true;
		}

		return false;
	}
}