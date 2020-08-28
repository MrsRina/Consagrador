package rina.rocan.gui;

// Minecraft Utils.
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

// OpenGL.
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Mouse;

// Java.
import java.io.IOException;
import java.util.*;
import java.awt.*;

// Turok.
import rina.turok.TurokRenderGL;
import rina.turok.TurokRect;

// GUI.
import rina.rocan.gui.frame.RocanFrame;

// Client.
import rina.rocan.client.RocanModule;

// Util.
import rina.rocan.util.RocanUtilMinecraftHelper;

// Rocan.
import rina.rocan.Rocan;

/**
  *
  * @author Rina!
  *
  * Created by Rina!
  * 17/08/2020.
  *
  **/
public class RocanMainGUI extends GuiScreen {
	private ArrayList<RocanFrame> frame_list;

	private int mouse_x;
	private int mouse_y;

	private int screen_width;
	private int screen_height;

	private int default_position_x;

	private RocanFrame focused_frame;

	private boolean event_cancel_close_gui;

	public RocanMainGUI() {
		this.frame_list = new ArrayList<>();

		this.mouse_x = 0;
		this.mouse_y = 0;

		this.screen_width  = 0;
		this.screen_height = 0;

		this.default_position_x = 10; // 10 for default.

		this.event_cancel_close_gui = false;

		loadFrames();
	}

	public void loadFrames() {
		for (RocanModule.Category categories : RocanModule.Category.values()) {
			RocanFrame new_frame = new RocanFrame(this, categories);

			new_frame.setY(10);
			new_frame.setX(this.default_position_x);

			this.default_position_x = new_frame.getX() + new_frame.getWidth() + 1;

			this.frame_list.add(new_frame);

			this.focused_frame = new_frame;
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void onGuiClosed() {
		if (Rocan.getModuleManager().getModuleByTag("GUI").getState()) {
			Rocan.getModuleManager().getModuleByTag("GUI").setState(false);
		}
	}

	@Override
	public void keyTyped(char char_, int key) {
		this.focused_frame.keyboard(char_, key);

		if (key == Keyboard.KEY_ESCAPE && !isCancelledToCloseGUI()) {
			RocanUtilMinecraftHelper.getMinecraft().displayGuiScreen(null);
		}
	}

	@Override
	public void mouseClicked(int x, int y, int mouse) {
		for (RocanFrame frames : this.frame_list) {
			frames.refreshFocus(x, y, mouse);

			if (frames.verifyFrame(x, y)) {
				this.focused_frame = frames;
			}
		}

		this.focused_frame.click(mouse);

		if (mouse == 0) {
			if (this.focused_frame.isMousePassing() && !this.focused_frame.isFrameCancelingClick()) {
				refreshFrame();

				this.focused_frame.setMouseClick(true);

				this.focused_frame.setMoveX(x - this.focused_frame.getX());
				this.focused_frame.setMoveY(y - this.focused_frame.getY());
			}
		}
	}

	@Override
	public void mouseReleased(int x, int y, int mouse) {
		this.focused_frame.release(mouse);

		for (RocanFrame frames : this.frame_list) {
			if (mouse == 0) {
				if (frames.isMouseClick()) {
					frames.setMouseClick(false);
				}
			}
		}
	}

	@Override
	public void drawScreen(int x, int y, float partial_ticks) {
		this.drawDefaultBackground();

		// We update theme.
		Rocan.getClientGUITheme().refresh();

		ScaledResolution scaled_resolution = new ScaledResolution(RocanUtilMinecraftHelper.getMinecraft());

		TurokRenderGL.fixScreen(scaled_resolution.getScaledWidth(), scaled_resolution.getScaledHeight());

		GL11.glDisable(GL11.GL_TEXTURE_2D);

		refreshGUI(x, y, scaled_resolution);

		for (RocanFrame frames : this.frame_list) {
			frames.render();

			if (frames.verifyFrame(x, y)) {
				this.focused_frame = frames;
			}

			frames.resetFrame();
		}

		this.focused_frame.refreshFrame();

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GlStateManager.color(1, 1, 1);
	}

	public void refreshGUI(int x, int y, ScaledResolution scaled_resolution) {
		this.mouse_x = x;
		this.mouse_y = y;

		this.screen_width  = scaled_resolution.getScaledWidth();
		this.screen_height = scaled_resolution.getScaledHeight();
	}

	public void refreshFrame() {
		this.frame_list.remove(this.focused_frame);
		this.frame_list.add(this.focused_frame);
	}

	public void setCancelToCloseGUI(boolean state) {
		this.event_cancel_close_gui = state;
	}

	public int getScreenWidth() {
		return this.screen_width;
	}

	public int getScreenHeight() {
		return this.screen_height;
	}

	public int getMouseX() {
		return this.mouse_x;
	}

	public int getMouseY() {
		return this.mouse_y;
	}

	public boolean isCancelledToCloseGUI() {
		return this.event_cancel_close_gui;
	}
}