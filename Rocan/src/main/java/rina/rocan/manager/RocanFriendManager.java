package rina.rocan.manager;

// Java.
import java.util.*;

// Client.
import rina.rocan.client.RocanFriend;

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

	public RocanFriendManager(String tag_non_variable) {
		this.friend_list = new ArrayList<>();
	}

	public void addFriend(String name, String uuid) {
		this.friend_list.add(new RocanFriend(name, uuid));
	}

	public void addFriend(String name) {
		this.friend_list.add(new RocanFriend(name, "null"));
	}

	public void removeFriend(RocanFriend friend) {
		this.friend_list.remove(friend);
	}

	public ArrayList<RocanFriend> getFriendList() {
		return this.friend_list;
	}

	public RocanFriend getFriendByName(String name) {
		for (RocanFriend friends : getFriendList()) {
			if (friends.getName().equals(name)) {
				return friends;
			}
		}

		return null;
	}

	public RocanFriend getFriendByUUID(String uuid) {
		for (RocanFriend friends : getFriendList()) {
			if (friends.getUUID().equals(uuid)) {
				return friends;
			}
		}

		return null;
	}

	public boolean isFriend(String name) {
		for (RocanFriend friends : getFriendList()) {
			if (friends.getName().equals(name)) {
				return true;
			}
		}

		return false;
	}
}