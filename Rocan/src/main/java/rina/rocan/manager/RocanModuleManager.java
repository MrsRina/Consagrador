package rina.rocan.manager;

// Minecraft.
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

// Java.
import java.util.*;

// Turok.
import rina.turok.TurokRenderHelp;
import rina.turok.TurokScreenUtil;
import rina.turok.TurokRenderGL;
import rina.turok.TurokRect;

// Events.
import rina.rocan.event.render.RocanEventRender;

// HUDS.
import rina.rocan.client.huds.*;

// Modules..
import rina.rocan.client.modules.combat.*;
import rina.rocan.client.modules.movement.*;
import rina.rocan.client.modules.render.*;
import rina.rocan.client.modules.exploit.*;
import rina.rocan.client.modules.misc.*;
import rina.rocan.client.modules.gui.*;

// System.
import rina.rocan.client.system.*;

// Client.
import rina.rocan.client.RocanModule.Category;
import rina.rocan.client.RocanModule;
import rina.rocan.client.RocanHUD;

// Util.
import rina.rocan.util.RocanUtilMinecraftHelper;
import rina.rocan.util.RocanUtilEntity;

/**
  *
  * @author Rina!
  *
  * Created by Rina!
  * 15/08/2020.
  *
  **/
public class RocanModuleManager {
	private ArrayList<RocanModule> module_list;

	private ArrayList<RocanHUD> hud_list;
	private ArrayList<RocanHUD> hud_list_left_up;
	private ArrayList<RocanHUD> hud_list_left_down;
	private ArrayList<RocanHUD> hud_list_right_up;
	private ArrayList<RocanHUD> hud_list_right_down;

	private TurokRect hud_rect_left_up;
	private TurokRect hud_rect_left_down;
	private TurokRect hud_rect_right_up;
	private TurokRect hud_rect_right_down;

	private int off_set_chat;

	public RocanModuleManager() {
		this.module_list = new ArrayList<>();

		this.hud_list            = new ArrayList<>();
		this.hud_list_left_up    = new ArrayList<>();
		this.hud_list_left_down  = new ArrayList<>();
		this.hud_list_right_up   = new ArrayList<>();
		this.hud_list_right_down = new ArrayList<>();

		this.hud_rect_left_up    = new TurokRect("LEFTUP", 0, 0, 0, 0);
		this.hud_rect_left_down  = new TurokRect("LEFTDOWN", 0, 0, 0, 0);
		this.hud_rect_right_up   = new TurokRect("RIGHTUP", 0, 0, 0, 0);
		this.hud_rect_right_down = new TurokRect("RIGHTDOWN", 0, 0, 0, 0);

		// Combat.
		addModule(new RocanOffhandUtil());

		// Movement.
		addModule(new RocanStep());
		addModule(new RocanGUIWalk());
		addModule(new RocanStrafe());
		addModule(new RocanVelocity());

		// Render.
		addModule(new RocanHUDRender());
		addModule(new RocanBlockHighlight());
		addModule(new RocanEntityTracer());
		addModule(new RocanEntityChams());
		addModule(new RocanEntityESP());
		addModule(new RocanHoleESP());

		// Exploit.
		addModule(new RocanXCarry());

		// Misc.
		addModule(new RocanFastUtil());
		addModule(new RocanChatSuffix());

		// GUI.
		addModule(new RocanGUI());
		addModule(new RocanHUDEditor());

		// Backend system.
		addModule(new RocanSystemPacket());

		// HUD.
		addHUD(new RocanWelcome());
		addHUD(new RocanModuleList());
		addHUD(new RocanCoordinates());
		addHUD(new RocanInventory());
		addHUD(new RocanVelocitySpeed());

		// Organize to alfabhet algined.
		Collections.sort(this.module_list, Comparator.comparing(RocanModule::getName));
	}

	public void addModule(RocanModule module) {
		this.module_list.add(module);
	}

	public void addHUD(RocanHUD hud) {
		this.module_list.add((RocanModule) hud);
		this.hud_list.add(hud);
	}

	public void onUpdateModuleList() {
		for (RocanModule modules : getModuleList()) {
			if (modules.getState()) {
				modules.onUpdate();
			}
		}
	}

	public void onRenderModuleList() {
		for (RocanModule modules : getModuleList()) {
			if (modules.getState()) {
				modules.onRender();
			}
		}
	}

	public void onRenderModuleList(RenderWorldLastEvent event) {
		Minecraft.getMinecraft().profiler.startSection("rocan");

		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		GlStateManager.disableDepth();
		GlStateManager.glLineWidth(1f);

		RocanEventRender event_render = new RocanEventRender(event.getPartialTicks());

		for (RocanModule modules : getModuleList()) {
			if (modules.getState()) {
				modules.onRender(event_render);
			}
		}

		GlStateManager.glLineWidth(1f);
		GlStateManager.shadeModel(GL11.GL_FLAT);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
		GlStateManager.enableDepth();
		GlStateManager.enableCull();

		TurokRenderGL.release3D();

		Minecraft.getMinecraft().profiler.endSection();
	}

	public void clearHUDDockList() {
		this.hud_list_left_up.clear();
		this.hud_list_left_down.clear();
		this.hud_list_right_up.clear();
		this.hud_list_right_down.clear();
	}

	// HUD seems not a module, but extend module, so I get the list HUD in module manager.
	public void syncHUD() {
		for (RocanHUD huds : getHUDList()) {
			if (((RocanModule) huds).getState() && huds.getDocking() == RocanHUD.Docking.LEFT_UP && huds.isJoinedToDockRect() && !isOnListLeftUp(huds)) {
				this.hud_list_left_up.add(huds);
			} else if (((RocanModule) huds).getState() && huds.getDocking() != RocanHUD.Docking.LEFT_UP && isOnListLeftUp(huds)) {
				this.hud_list_left_up.remove(huds);
			} else if (((RocanModule) huds).getState() && huds.getDocking() == RocanHUD.Docking.LEFT_UP && !huds.isJoinedToDockRect() && isOnListLeftUp(huds)) {
				this.hud_list_left_up.remove(huds);
			} else if (!((RocanModule) huds).getState() && (huds.getDocking() != RocanHUD.Docking.LEFT_UP || huds.getDocking() == RocanHUD.Docking.LEFT_UP) && isOnListLeftUp(huds)) {
				this.hud_list_left_up.remove(huds);
			}

			if (((RocanModule) huds).getState() && huds.getDocking() == RocanHUD.Docking.LEFT_DOWN && huds.isJoinedToDockRect() && !isOnListLeftDown(huds)) {
				this.hud_list_left_down.add(huds);
			} else if (((RocanModule) huds).getState() && huds.getDocking() != RocanHUD.Docking.LEFT_DOWN && isOnListLeftDown(huds)) {
				this.hud_list_left_down.remove(huds);
			} else if (((RocanModule) huds).getState() && huds.getDocking() == RocanHUD.Docking.LEFT_DOWN && !huds.isJoinedToDockRect() && isOnListLeftDown(huds)) {
				this.hud_list_left_down.remove(huds);
			} else if (!((RocanModule) huds).getState() && (huds.getDocking() != RocanHUD.Docking.LEFT_DOWN || huds.getDocking() == RocanHUD.Docking.LEFT_DOWN) && isOnListLeftDown(huds)) {
				this.hud_list_left_down.remove(huds);
			}

			if (((RocanModule) huds).getState() && huds.getDocking() == RocanHUD.Docking.RIGHT_UP && huds.isJoinedToDockRect() && !isOnListRightUp(huds)) {
				this.hud_list_right_up.add(huds);
			} else if (((RocanModule) huds).getState() && huds.getDocking() != RocanHUD.Docking.RIGHT_UP && isOnListRightUp(huds)) {
				this.hud_list_right_up.remove(huds);
			} else if (((RocanModule) huds).getState() && huds.getDocking() == RocanHUD.Docking.RIGHT_UP && !huds.isJoinedToDockRect() && isOnListRightUp(huds)) {
				this.hud_list_right_up.remove(huds);
			} else if (!((RocanModule) huds).getState() && (huds.getDocking() != RocanHUD.Docking.RIGHT_UP || huds.getDocking() == RocanHUD.Docking.RIGHT_UP) && isOnListRightUp(huds)) {
				this.hud_list_right_up.remove(huds);
			}

			if (((RocanModule) huds).getState() && huds.getDocking() == RocanHUD.Docking.RIGHT_DOWN && huds.isJoinedToDockRect() && !isOnListRightDown(huds)) {
				this.hud_list_right_down.add(huds);
			} else if (((RocanModule) huds).getState() && huds.getDocking() != RocanHUD.Docking.RIGHT_DOWN && isOnListRightDown(huds)) {
				this.hud_list_right_down.remove(huds);
			} else if (((RocanModule) huds).getState() && huds.getDocking() == RocanHUD.Docking.RIGHT_DOWN && !huds.isJoinedToDockRect() && isOnListRightDown(huds)) {
				this.hud_list_right_down.remove(huds);
			} else if (!((RocanModule) huds).getState() && (huds.getDocking() != RocanHUD.Docking.RIGHT_DOWN || huds.getDocking() == RocanHUD.Docking.RIGHT_DOWN) && isOnListRightDown(huds)) {
				this.hud_list_right_down.remove(huds);
			}
		}

		ScaledResolution scl_minecraft_screen = new ScaledResolution(RocanUtilMinecraftHelper.getMinecraft());

		int scr_width  = scl_minecraft_screen.getScaledWidth();
		int scr_height = scl_minecraft_screen.getScaledHeight();

		if (RocanUtilMinecraftHelper.getMinecraft().world != null && RocanUtilMinecraftHelper.getMinecraft().ingameGUI.getChatGUI().getChatOpen()) {
			off_set_chat = 14;
		} else {
			off_set_chat = 0;
		}

		int left_up_save_y = 1;

		for (RocanHUD huds : getHUDListLeftUp()) {
			huds.setX(1);
			huds.setY(left_up_save_y);

			left_up_save_y = huds.getY() + huds.getHeight() + 1;
		}

		this.hud_rect_left_up.setX(1);
		this.hud_rect_left_up.setY(1);
		this.hud_rect_left_up.setWidth(10);
		this.hud_rect_left_up.setHeight(left_up_save_y);

		int left_down_save_y = scr_height - 1 - off_set_chat;

		for (RocanHUD huds : getHUDListLeftDown()) {
			huds.setX(1);
			huds.setY(left_down_save_y - huds.getHeight());

			left_down_save_y = huds.getY() - 1;
		}

		this.hud_rect_left_down.setX(1);
		this.hud_rect_left_down.setY(left_down_save_y);
		this.hud_rect_left_down.setWidth(10);
		this.hud_rect_left_down.setHeight(scr_height - left_down_save_y);

		int right_up_save_y = 1;

		for (RocanHUD huds : getHUDListRightUp()) {
			huds.setX(scr_width - huds.getWidth() - 1);
			huds.setY(right_up_save_y);
		
			right_up_save_y = huds.getY() + huds.getHeight() + 1;
		}

		this.hud_rect_right_up.setX(scr_width - 11);
		this.hud_rect_right_up.setY(1);
		this.hud_rect_right_up.setWidth(10);
		this.hud_rect_right_up.setHeight(right_up_save_y);

		int right_down_save_y = scr_height - 1 - off_set_chat;

		for (RocanHUD huds : getHUDListRightDown()) {
			huds.setX(scr_width - huds.getWidth() - 1);
			huds.setY(right_down_save_y - huds.getHeight());

			right_down_save_y = huds.getY() - 1;			
		}

		this.hud_rect_right_down.setX(scr_width - 11);
		this.hud_rect_right_down.setY(right_down_save_y);
		this.hud_rect_right_down.setWidth(10);
		this.hud_rect_right_down.setHeight(scr_height - right_down_save_y);
	}

	public void keyTypedHUD(char char_, int key) {
		for (RocanHUD huds : getHUDList()) {
			if (((RocanModule) huds).getState()) {
				huds.keyboard(char_, key);
			}
		}
	}

	public void mouseClickedHUD(int x, int y, int mouse) {
		for (RocanHUD huds : getHUDList()) {
			if (((RocanModule) huds).getState()) {
				huds.click(x, y, mouse);
			}
		}
	}

	public void mouseReleasedHUD(int x, int y, int mouse) {
		for (RocanHUD huds : getHUDList()) {
			if (((RocanModule) huds).getState()) {
				huds.release(x, y, mouse);
			}
		}
	}

	public void renderScreenHUD(int x, int y, float partial_ticks) {
		for (RocanHUD huds : getHUDList()) {
			if (((RocanModule) huds).getState()) {
				huds.render(x, y, partial_ticks);
			}
		}
	}

	public void addHUDListLeftUp(RocanHUD hud) {
		this.hud_list_left_up.add(hud);
	}

	public void addHUDListLeftDown(RocanHUD hud) {
		this.hud_list_left_down.add(hud);
	}

	public void addHUDListRightUp(RocanHUD hud) {
		this.hud_list_right_up.add(hud);
	}

	public void addHUDListRightDown(RocanHUD hud) {
		this.hud_list_right_down.add(hud);
	}

	public ArrayList<RocanModule> getModuleList() {
		return this.module_list;
	}

	public ArrayList<RocanHUD> getHUDList() {
		return this.hud_list;
	}

	public ArrayList<RocanHUD> getHUDListLeftUp() {
		return this.hud_list_left_up;
	}

	public ArrayList<RocanHUD> getHUDListLeftDown() {
		return this.hud_list_left_down;
	}

	public ArrayList<RocanHUD> getHUDListRightUp() {
		return this.hud_list_right_up;
	}

	public ArrayList<RocanHUD> getHUDListRightDown() {
		return this.hud_list_right_down;
	}

	public ArrayList<RocanModule> getModuleListByCategory(Category category) {
		ArrayList<RocanModule> category_module_list = new ArrayList<>();

		for (RocanModule modules : getModuleList()) {
			if (modules.getCategory() == category) {
				category_module_list.add(modules);
			}
		}

		return category_module_list;
	}

	public RocanModule getModuleByTag(String tag) {
		for (RocanModule modules : getModuleList()) {
			if (modules.getTag().equalsIgnoreCase(tag)) {
				return modules;
			}
		}

		return null;
	}

	public TurokRect getHUDRectLeftUp() {
		return this.hud_rect_left_up;
	}

	public TurokRect getHUDRectLeftDown() {
		return this.hud_rect_left_down;
	}

	public TurokRect getHUDRectRightUp() {
		return this.hud_rect_right_up;
	}

	public TurokRect getHUDRectRightDown() {
		return this.hud_rect_right_down;
	}

	public boolean isOnListLeftUp(RocanHUD hud) {
		for (RocanHUD huds : this.hud_list_left_up) {
			if (huds.getTag().equals(hud.getTag())) {
				return true;
			}
		}

		return false;
	}

	public boolean isOnListLeftDown(RocanHUD hud) {
		for (RocanHUD huds : this.hud_list_left_down) {
			if (huds.getTag().equals(hud.getTag())) {
				return true;
			}
		}

		return false;
	}

	public boolean isOnListRightUp(RocanHUD hud) {
		for (RocanHUD huds : this.hud_list_right_up) {
			if (huds.getTag().equals(hud.getTag())) {
				return true;
			}
		}

		return false;
	}

	public boolean isOnListRightDown(RocanHUD hud) {
		for (RocanHUD huds : this.hud_list_right_down) {
			if (huds.getTag().equals(hud.getTag())) {
				return true;
			}
		}

		return false;
	}
}