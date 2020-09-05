package rina.rocan.client.modules.movement;

// Minecraft.
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.gui.GuiChat;

// OpenGL.
import org.lwjgl.input.Keyboard;

// Java.
import java.util.*;

// Pomelo.
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

// Event.
import rina.rocan.event.gui.RocanEventGUI;

// Client.
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 23/08/2020.
 *
 **/
public class RocanGUIWalk extends RocanModule {
	private static KeyBinding[] KEYS = new KeyBinding[] {
		mc.gameSettings.keyBindForward, mc.gameSettings.keyBindRight,
		mc.gameSettings.keyBindBack, mc.gameSettings.keyBindLeft,
		mc.gameSettings.keyBindJump, mc.gameSettings.keyBindSprint
	};

	public RocanGUIWalk() {
		super(new String[] {"GUI Walk", "GUIWalk", "Able move while opened any GUI."}, Category.ROCAN_MOVEMENT);
	}

	@Listener
	public void stateGUI(RocanEventGUI event) {
		if (mc.player == null && mc.world == null) {
			return;
		}

		if (event.getGuiScreen() instanceof GuiChat || event.getGuiScreen() == null) {
			return;
		}

		walk();
	}

	@Override
	public void onUpdate() {
		if (mc.player == null && mc.world == null) {
			return;
		}

		if (mc.currentScreen instanceof GuiChat || mc.currentScreen == null) {
			return;
		}

		walk();
	}

	public void walk() {
		KeyBinding[] keys = KEYS;

		int keys_n   = keys.length;
		int keys_n_2 = 0;

		while (keys_n_2 < keys_n) {
			KeyBinding key_binding = keys[keys_n_2];

			if (Keyboard.isKeyDown(key_binding.getKeyCode())) {
				if (key_binding.getKeyConflictContext() != KeyConflictContext.UNIVERSAL) {
					key_binding.setKeyConflictContext(KeyConflictContext.UNIVERSAL);
				}

				KeyBinding.setKeyBindState(key_binding.getKeyCode(), true);
			} else {
				KeyBinding.setKeyBindState(key_binding.getKeyCode(), false);
			}

			++keys_n_2;
		}
	}
}