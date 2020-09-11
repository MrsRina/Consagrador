package rina.rocan.client.commands;

// Minecraft.
import net.minecraft.client.network.NetworkPlayerInfo;

// Java.
import java.util.*;

// Command.
import rina.rocan.client.RocanCommand;

// Friend.
import rina.rocan.client.RocanFriend;

// Util.
import rina.rocan.util.RocanUtilClient;
import rina.rocan.util.RocanUtilEntity;

// Rocan.
import rina.rocan.Rocan;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 10/09/2020.
 *
 **/
public class RocanCommandFriend extends RocanCommand {
	public RocanCommandFriend() {
		super("friend", "Friend util.");
	}

	@Override
	public boolean onRequested(String[] args) {
		String type   = "null";
		String friend = "null"; 

		if (verifyIndex(args, 1)) {
			type = args[1];
		}

		if (verifyIndex(args, 2)) {
			friend = args[2];
		}

		if (verifyIndex(args, 3)) {
			RocanUtilClient.sendNotifyErrorClient("friend add/new/rem/remove/del/delete/get name/list");

			return true;
		}

		if (type.equals("null")) {
			RocanUtilClient.sendNotifyErrorClient("friend add/new/rem/remove/del/delete/get name/list");

			return true;
		}

		if (friend.equals("null")) {
			RocanUtilClient.sendNotifyErrorClient("friend add/new/rem/remove/del/delete/get name/list");
			
			return true;
		}

		boolean isForAdd = true;

		if (type.equalsIgnoreCase("add")  ||
			type.equalsIgnoreCase("new")) {
			// Add, huh.
			isForAdd = true;
		} else if (type.equalsIgnoreCase("rem")     ||
				   type.equalsIgnoreCase("remove")  ||
				   type.equalsIgnoreCase("del")     ||
				   type.equalsIgnoreCase("delete")) {
			// Remove.
			isForAdd = false;
		} else if (type.equalsIgnoreCase("get")) {
			if (!friend.equals("list")) {
				RocanUtilClient.sendNotifyErrorClient("friend add/new/rem/remove/del/delete/get name/list");

				return true;
			}

			if (Rocan.getFriendManager().getFriendList().isEmpty()) {
				RocanUtilClient.sendNotifyClient("Empty list :(.");

				return true;
			}

			int count = 0;
			int size  = Rocan.getFriendManager().getFriendList().size();
	
			StringBuilder names = new StringBuilder();
	
			for (RocanFriend friends : Rocan.getFriendManager().getFriendList()) {
				count++;
	
				if (count >= size) {
					names.append(Rocan.getDarkGreenColor() + friends.getName() + Rocan.setDefaultColor() + ".");
				} else {
					names.append(Rocan.getDarkGreenColor() + friends.getName() + Rocan.setDefaultColor() + ", ");
				}
			}
	
			RocanUtilClient.sendNotifyClient(names.toString());

			return true;
		}

		if (isForAdd) {
			ArrayList<NetworkPlayerInfo> info_map = new ArrayList<NetworkPlayerInfo> (mc.getConnection().getPlayerInfoMap());
			NetworkPlayerInfo profile             = requested_player_from_server(info_map, friend);

			if (profile == null) {
				RocanUtilClient.sendNotifyErrorClient("The player is offline.");

				return true;
			} else {
				if (Rocan.getFriendManager().isFriend(friend)) {
					RocanUtilClient.sendNotifyClient("Already added as friend.");

					return true;
				} else {
					Rocan.getFriendManager().addFriend(friend);

					RocanUtilClient.sendNotifyClient("Added " + Rocan.getDarkGreenColor() + friend + Rocan.setDefaultColor() + " as friend.");

					return true;
				}
			}
		} else {
			RocanFriend friend_requested = Rocan.getFriendManager().getFriendByName(friend);

			if (friend_requested == null) {
				RocanUtilClient.sendNotifyErrorClient("This player does not exist.");

				return true;
			} else {
				Rocan.getFriendManager().removeFriend(friend_requested);

				RocanUtilClient.sendNotifyErrorClient("Removed " + Rocan.getDarkRedColor() + friend + Rocan.setDefaultColor() + " :(.");

				return  true;
			}
		}
	}

	public NetworkPlayerInfo requested_player_from_server(ArrayList<NetworkPlayerInfo> list, String friend) {
		return list.stream().filter(player -> player.getGameProfile().getName().equalsIgnoreCase(friend)).findFirst().orElse(null);
	}
}