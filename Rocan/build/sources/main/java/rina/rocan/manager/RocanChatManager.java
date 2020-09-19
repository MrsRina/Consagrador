package rina.rocan.manager;

// Minecraft.
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;

// Java.
import java.util.*;

// Constructor.
import rina.rocan.util.constructor.RocanConstructorTimer;

// Util.
import rina.rocan.util.RocanUtilMinecraftHelper;
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
public class RocanChatManager {
	private boolean isInitialized;

	private final Minecraft mc = RocanUtilMinecraftHelper.getMinecraft();

	private ArrayList<String> message_queue_list;

	private RocanConstructorTimer timer = new RocanConstructorTimer();

	private int delay;
	private int maximum_item_per_queue;

	public RocanChatManager() {
		this.isInitialized = true;

		this.message_queue_list = new ArrayList<>();

		this.delay = 0;
		this.maximum_item_per_queue = 9;
	}

	public void addMessageInQueue(String message) {
		if (this.isInitialized && this.message_queue_list.contains(message) == false) {
			this.message_queue_list.add(message);
		}
	}

	public void disable() {
		if (this.isInitialized && mc.player != null) {
			mc.gameSettings.chatVisibility = EntityPlayer.EnumChatVisibility.HIDDEN;
		}
	}

	public void disableChat() {
		if (this.isInitialized && mc.player != null) {
			mc.gameSettings.chatVisibility = EntityPlayer.EnumChatVisibility.SYSTEM;
		}
	}

	public void enable() {
		if (this.isInitialized && mc.player != null) {
			mc.gameSettings.chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
		}
	}

	public void sendMessage(String message) {
		RocanUtilClient.sendMessage(message);
	}

	public void listenChatNotifier() {
		this.delay = 2;

		// We clamp to a limit.
		if (this.message_queue_list.size() >= this.maximum_item_per_queue) {
			this.message_queue_list.clear();
		}

		for (String messages : new ArrayList<String>(this.message_queue_list)) {
			if (timer.isPassedSI(this.delay) && !this.message_queue_list.isEmpty()) {
				sendMessage(messages);

				this.message_queue_list.remove(messages);

				timer.reset();
			}
		}
	}

	public ArrayList<String> getMessageQueueList() {
		return this.message_queue_list;
	}

	public String getMessageQueueByMessage(String message) {
		for (String messages : this.message_queue_list ) {
			if (messages.equals(message)) {
				return messages;
			}
		}

		return null;
	}
}