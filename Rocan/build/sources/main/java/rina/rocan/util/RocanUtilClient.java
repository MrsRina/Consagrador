package rina.rocan.util;

// Minecraft.
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.client.Minecraft;

// Java.
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class RocanUtilClient {
	public final static Minecraft mc = Minecraft.getMinecraft();

	public static void sendMessage(String message) {
		if (mc.player != null) {
			mc.player.connection.sendPacket(new CPacketChatMessage(message));
		}
	}

	public static void sendNotify(String message) {
		if (mc.player != null) {
			mc.player.sendMessage(new CustomTextComponent(message));
		}
	}

	public static void sendNotifyClient(String message) {
		sendNotify(Rocan.getGrayColor() + "Rocan Notify " + Rocan.setDefaultColor() + message);
	}

	public static void sendNotifyErrorClient(String message) {
		sendNotify(Rocan.getGrayColor() + "Rocan Notify " + Rocan.getRedColor() + message);
	}

	public static class CustomTextComponent extends TextComponentBase {
		String message_input;
		
		public CustomTextComponent(String message) {		
			Pattern p       = Pattern.compile("&[0123456789abcdefrlosmk]");
			Matcher m       = p.matcher(message);
			StringBuffer sb = new StringBuffer();
	
			while (m.find()) {
				String replacement = "\u00A7" + m.group().substring(1);
				m.appendReplacement(sb, replacement);
			}
	
			m.appendTail(sb);
			this.message_input = sb.toString();
		}
		
		public String getUnformattedComponentText() {
			return this.message_input;
		}
		
		@Override
		public ITextComponent createCopy() {
			return new CustomTextComponent(this.message_input);
		}
	}
}