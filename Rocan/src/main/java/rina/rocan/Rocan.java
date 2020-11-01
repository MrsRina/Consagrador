package rina.rocan;

// Minecraft.
import com.sun.jna.platform.unix.X11;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

// Mojang.
import com.mojang.realmsclient.gui.ChatFormatting;

// Event Manager.
import org.lwjgl.opengl.Display;
import team.stiff.pomelo.impl.annotated.AnnotatedEventManager;
import team.stiff.pomelo.EventManager;

// GUI.
import rina.rocan.gui.RocanMainGUI;

// Constructor.
import rina.rocan.util.constructor.RocanConstructorTimer;

// Rocan Managers.
import rina.rocan.manager.RocanCommandManager;
import rina.rocan.manager.RocanSettingManager;
import rina.rocan.manager.RocanModuleManager;
import rina.rocan.manager.RocanEventManager;
import rina.rocan.manager.RocanFileManager;
import rina.rocan.manager.RocanFriendManager;
import rina.rocan.manager.RocanTimerManager;
import rina.rocan.manager.RocanChatManager;

// Turok.
import rina.turok.TurokStructureColor;

/**
  *
  * @author Rina!
  *
  * Created by Rina!
  * 15/08/2020.
  *
  **/
@Mod(modid = "rocan", name = Rocan.ROCAN_NAME, version = Rocan.ROCAN_VERSION)
public class Rocan {
	// Details of the client.
	public final static String ROCAN_VERSION = "0.8";
	public final static String ROCAN_NAME = "Rocan";

	@Mod.Instance
	private static Rocan INSTANCE;

	// Managers.
	private RocanModuleManager module_manager;
	private RocanSettingManager setting_manager;
	private RocanCommandManager command_manager;
	private RocanEventManager event_manager;
	private RocanFileManager file_manager;
	private RocanFriendManager friend_manager;
	private RocanTimerManager timer_manager;
	private RocanChatManager chat_manager;

	// EventBus.
	private EventManager pomelo_event_manager;

	// THEME GUI.
	public static TurokStructureColor gui_theme;

	// GUI.
	public static RocanMainGUI rocan_gui;

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		setting_manager = new RocanSettingManager();
		module_manager = new RocanModuleManager();
		command_manager = new RocanCommandManager(".");
		event_manager = new RocanEventManager();
		pomelo_event_manager = new AnnotatedEventManager();
		file_manager = new RocanFileManager();
		friend_manager = new RocanFriendManager("Friends rocan!!!");
		timer_manager = new RocanTimerManager();
		chat_manager = new RocanChatManager();

		// Register events.
		MinecraftForge.EVENT_BUS.register(command_manager);
		MinecraftForge.EVENT_BUS.register(event_manager);

		// Load theme.
		gui_theme = new TurokStructureColor();

		// Create gui.
		rocan_gui = new RocanMainGUI();

		// load client.
		/*
		file_manager.loadClient();
		file_manager.reloadModules();

		 */

		Display.setTitle(ROCAN_NAME + " - " + ROCAN_VERSION);
	}

	public static Rocan getInstance() {
		return INSTANCE;
	}

	public static String getClientName() {
		return getInstance().ROCAN_NAME;
	}

	public static String getClientVersion() {
		return getInstance().ROCAN_VERSION;
	}

	public static TurokStructureColor getClientGUITheme() {
		return getInstance().gui_theme;
	} 

	public static RocanMainGUI getClientGUI() {
		return getInstance().rocan_gui;
	}

	public static int getClientHUDRed() {
		return getInstance().setting_manager.getSettingByModuleAndTag("HUD", "HUDStringRed").getInteger();
	}

	public static int getClientHUDGreen() {
		return getInstance().setting_manager.getSettingByModuleAndTag("HUD", "HUDStringGreen").getInteger();
	}

	public static int getClientHUDBlue() {
		return getInstance().setting_manager.getSettingByModuleAndTag("HUD", "HUDStringBlue").getInteger();
	}

	public static RocanSettingManager getSettingManager() {
		return getInstance().setting_manager;
	}

	public static RocanCommandManager getCommandManager() {
		return getInstance().command_manager;
	}

	public static RocanModuleManager getModuleManager() {
		return getInstance().module_manager;
	}

	public static RocanEventManager getEventManager() {
		return getInstance().event_manager;
	}

	public static RocanFileManager getFileManager() {

		return null;
//		return getInstance().file_manager;
	}

	public static RocanFriendManager getFriendManager() {
		return getInstance().friend_manager;
	}

	public static RocanTimerManager getTimerManager() {
		return getInstance().timer_manager;
	}

	public static RocanChatManager getChatManager() {
		return getInstance().chat_manager;
	}

	public static EventManager getPomeloEventManager() {
		return getInstance().pomelo_event_manager;
	}

	// Timer util.
	public static RocanConstructorTimer getTimer() {
		return getInstance().timer_manager.getTimer();
	}

	// Colors.
	public static ChatFormatting setDefaultColor() {
		return ChatFormatting.RESET;
	}

	public static ChatFormatting getBlackColor() {
		return ChatFormatting.BLACK;
	}

	public static ChatFormatting getRedColor() {
		return ChatFormatting.RED;
	}

	public static ChatFormatting getAquaColor() {
		return ChatFormatting.AQUA;
	}

	public static ChatFormatting getBlueColor() {
		return ChatFormatting.BLUE;
	}

	public static ChatFormatting getGoldColor() {
		return ChatFormatting.GOLD;
	}

	public static ChatFormatting getGrayColor() {
		return ChatFormatting.GRAY;
	}

	public static ChatFormatting getWhiteColor() {
		return ChatFormatting.WHITE;
	}

	public static ChatFormatting getGreenColor() {
		return ChatFormatting.GREEN;
	}

	public static ChatFormatting getYellowColor() {
		return ChatFormatting.YELLOW;
	}

	public static ChatFormatting getDarkRedColor() {
		return ChatFormatting.DARK_RED;
	}

	public static ChatFormatting getDarkAquaColor() {
		return ChatFormatting.DARK_AQUA;
	}

	public static ChatFormatting getDarkBlueColor() {
		return ChatFormatting.DARK_BLUE;
	}

	public static ChatFormatting getDarkGrayColor() {
		return ChatFormatting.DARK_GRAY;
	}

	public static ChatFormatting getDarkGreenColor() {
		return ChatFormatting.DARK_GREEN;
	}

	public static ChatFormatting getDarkPurpleColor() {
		return ChatFormatting.DARK_PURPLE;
	}

	public static ChatFormatting getLightPurpleColor() {
		return ChatFormatting.LIGHT_PURPLE;
	}
}