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
public class RocanCommandHelp extends RocanCommand {
	public RocanCommandHelp() {
		super("help", "Get util command client.");
	}

	@Override
	public boolean onRequested(String[] args) {
		RocanUtilClient.sendNotifyClient("Gay");

		return true;
	}
}