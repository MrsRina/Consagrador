package rina.rocan.client.commands;

// Command.
import rina.rocan.client.RocanCommand;

// Util.
import rina.rocan.util.RocanUtilClient;

// Rocan.
import rina.rocan.Rocan;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 15/08/2020.
 *
 **/
public class RocanCommandPrefix extends RocanCommand {
	public RocanCommandPrefix() {
		super("prefix", "Change prefix client.");
	}

	@Override
	public boolean onRequested(String[] args) {
		String value_0 = "null";

		if (verifyIndex(args, 1)) {
			value_0 = args[1];
		}

		if (verifyIndex(args, 2)) {
			RocanUtilClient.sendNotifyErrorClient("The usage correct is prefix <char>");

			return true;
		}

		if (value_0.equals("null")) {
			RocanUtilClient.sendNotifyErrorClient("The usage correct is prefix <char>");

			return true;
		}

		Rocan.getCommandManager().setPrefix(value_0);

		RocanUtilClient.sendNotifyClient("The client prefix is " + Rocan.getCommandManager().getPrefix());

		return true;
	}
}