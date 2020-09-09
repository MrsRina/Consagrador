package rina.rocan.client.modules.misc;

// Minecraft.
import net.minecraft.network.play.client.CPacketChatMessage;

// Pomelo.
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

// Event.
import rina.rocan.event.network.RocanEventPacketSend;

// Client.
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 09/09/2020.
 *
 **/
public class RocanChatSuffix extends RocanModule {
	RocanSetting ignore = createSetting(new String[] {"Ignore", "ChatSuffixIgnore", "Ignore somes characters."}, true);

	private final String ROCAN_DEV_SUFFIX = "\u25b8 \u0280\u1d0f\u1d04\u1d00\u0274";

	public RocanChatSuffix() {
		super(new String[] {"Chat Suffix", "ChatSuffix", "Every message ends with a pretty suffix."}, Category.ROCAN_MISC);
	}

	@Listener
	public void sendPacket(RocanEventPacketSend event) {
		if (event.getPacket() instanceof CPacketChatMessage) {
			String original_message = ((CPacketChatMessage) event.getPacket()).getMessage();

			boolean accept_message = true;

			if (original_message.startsWith("/")  || original_message.startsWith("\\") ||
		    	original_message.startsWith("!")  || original_message.startsWith(":")  ||
		    	original_message.startsWith(";")  || original_message.startsWith(".")  ||
		    	original_message.startsWith(",")  || original_message.startsWith("@")  ||
		    	original_message.startsWith("&")  || original_message.startsWith("*")  ||
		    	original_message.startsWith("$")  || original_message.startsWith("#")  ||
		    	original_message.startsWith("(")  || original_message.startsWith(")")  && ignore.getBoolean())  {
		    	accept_message = false;
			}

			if (accept_message) {
				original_message += " " + ROCAN_DEV_SUFFIX;
			}

			((CPacketChatMessage) event.getPacket()).message = original_message;
		}
	}
}