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
	RocanSetting fonts       = createSetting(new String[] {"Font", "ChatSuffixFont", "Fonts modes."}, "Hephaestus", new String[] {"Hephaestus"});
	RocanSetting name_suffix = createSetting(new String[] {"Suffix", "ChatSuffixSuffix", "Custom suffix."}, "rocan");
	RocanSetting start_split = createSetting(new String[] {"Start Split", "ChatSuffixStartSplit", "| Start split to chat suffix."}, true);
	RocanSetting ignore      = createSetting(new String[] {"Ignore", "ChatSuffixIgnore", "Ignore somes characters."}, true);

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
				String suffix = "";

				if (fonts.getString().equals("Hephaestus")) {
					suffix = fontHephaestus(name_suffix.getString().toLowerCase());
				}

				original_message += (start_split.getBoolean() ? " \u23D0 " : " ") + suffix;
			}

			((CPacketChatMessage) event.getPacket()).message = original_message;
		}
	}

	public String fontHephaestus(String args) {
		String converted_string = args;

		converted_string = converted_string.replace("a", "\u1d00");
		converted_string = converted_string.replace("b", "\u0299");
		converted_string = converted_string.replace("c", "\u1d04");
		converted_string = converted_string.replace("d", "\u1d05");
		converted_string = converted_string.replace("e", "\u1d07");
		converted_string = converted_string.replace("f", "\u0493");
		converted_string = converted_string.replace("g", "\u0262");
		converted_string = converted_string.replace("h", "\u029c");
		converted_string = converted_string.replace("i", "\u026a");
		converted_string = converted_string.replace("j", "\u1d0a");
		converted_string = converted_string.replace("k", "\u1d0b");
		converted_string = converted_string.replace("l", "\u029f");
		converted_string = converted_string.replace("m", "\u1d0d");
		converted_string = converted_string.replace("n", "\u0274");
		converted_string = converted_string.replace("o", "\u1d0f");
		converted_string = converted_string.replace("p", "\u1d18");
		converted_string = converted_string.replace("q", "\u01eb");
		converted_string = converted_string.replace("r", "\u0280");
		converted_string = converted_string.replace("s", "\u0455");
		converted_string = converted_string.replace("t", "\u1d1b");
		converted_string = converted_string.replace("u", "\u1d1c");
		converted_string = converted_string.replace("v", "\u1d20");
		converted_string = converted_string.replace("w", "\u1d21");
		converted_string = converted_string.replace("x", "\u0445");
		converted_string = converted_string.replace("y", "\u028f");
		converted_string = converted_string.replace("z", "\u1d22");

		return converted_string;
	}
}