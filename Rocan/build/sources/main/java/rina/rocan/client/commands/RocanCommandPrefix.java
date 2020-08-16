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
		String value_0 = null;

		if (verifyErrorArg(args, 0)) {
			RocanUtilClient.sendNotifyErrorClient("The usage correct is prefix <char>");

			return true;
		}

		if (verifyErrorArg(args, 1)) {
			RocanUtilClient.sendNotifyErrorClient("The usage correct is prefix <char>");

			return true;
		}

		value_0 = args[0];

		Rocan.getCommandManager().setPrefix(value_0);

		RocanUtilClient.sendNotifyClient("The client prefix is " + Rocan.getCommandManager().getPrefix());

		return false;
	}
}