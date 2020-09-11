package rina.rocan.client.commands;

// Minecraft.
import net.minecraft.client.network.NetworkPlayerInfo;

// Java.
import java.util.*;

// Command.
import rina.rocan.client.RocanCommand;

// Enemy.
import rina.rocan.client.RocanEnemy;

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
public class RocanCommandEnemy extends RocanCommand {
	public RocanCommandEnemy() {
		super("enemy", "Enemies util.");
	}

	@Override
	public boolean onRequested(String[] args) {
		String type  = "null";
		String enemy = "null"; 

		if (verifyIndex(args, 1)) {
			type = args[1];
		}

		if (verifyIndex(args, 2)) {
			enemy = args[2];
		}

		if (verifyIndex(args, 3)) {
			RocanUtilClient.sendNotifyErrorClient("enemy add/new/rem/remove/del/delete/get name/list");

			return true;
		}

		if (type.equals("null")) {
			RocanUtilClient.sendNotifyErrorClient("enemy add/new/rem/remove/del/delete/get name/list");

			return true;
		}

		if (enemy.equals("null")) {
			RocanUtilClient.sendNotifyErrorClient("enemy add/new/rem/remove/del/delete/get name/list");
			
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
			if (!enemy.equals("list")) {
				RocanUtilClient.sendNotifyErrorClient("enemy add/new/rem/remove/del/delete/get name/list");

				return true;
			}

			if (Rocan.getFriendManager().getEnemyList().isEmpty()) {
				RocanUtilClient.sendNotifyClient("Empty list :).");

				return true;
			}

			int count = 0;
			int size  = Rocan.getFriendManager().getEnemyList().size();
	
			StringBuilder names = new StringBuilder();
	
			for (RocanEnemy enemies : Rocan.getFriendManager().getEnemyList()) {
				count++;
	
				if (count >= size) {
					names.append(Rocan.getDarkGreenColor() + enemies.getName() + Rocan.setDefaultColor() + ".");
				} else {
					names.append(Rocan.getDarkGreenColor() + enemies.getName() + Rocan.setDefaultColor() + ", ");
				}
			}
	
			RocanUtilClient.sendNotifyClient(names.toString());

			return true;
		}

		if (isForAdd) {
			ArrayList<NetworkPlayerInfo> info_map = new ArrayList<NetworkPlayerInfo> (mc.getConnection().getPlayerInfoMap());
			NetworkPlayerInfo profile             = requested_player_from_server(info_map, enemy);

			if (profile == null) {
				RocanUtilClient.sendNotifyErrorClient("The player is offline.");

				return true;
			} else {
				if (Rocan.getFriendManager().isEnemy(enemy)) {
					RocanUtilClient.sendNotifyClient("Already added as enemy :).");

					return true;
				} else {
					Rocan.getFriendManager().addEnemy(enemy);

					RocanUtilClient.sendNotifyClient("Added " + Rocan.getDarkGreenColor() + enemy + Rocan.setDefaultColor() + " as enemy :).");

					return true;
				}
			}
		} else {
			RocanEnemy enemy_requested = Rocan.getFriendManager().getEnemyByName(enemy);

			if (enemy_requested == null) {
				RocanUtilClient.sendNotifyErrorClient("This player does not exist.");

				return true;
			} else {
				Rocan.getFriendManager().removeEnemy(enemy_requested);

				RocanUtilClient.sendNotifyErrorClient("Removed " + Rocan.getDarkRedColor() + enemy + Rocan.setDefaultColor() + " :(.");

				return  true;
			}
		}
	}

	public NetworkPlayerInfo requested_player_from_server(ArrayList<NetworkPlayerInfo> list, String enemy) {
		return list.stream().filter(player -> player.getGameProfile().getName().equalsIgnoreCase(enemy)).findFirst().orElse(null);
	}
}