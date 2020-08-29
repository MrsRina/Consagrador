package rina.rocan.manager;

// Minecraft.
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

// Java.
import java.util.*;

// Turok.
import rina.turok.TurokRenderHelp;

// Events.
import rina.rocan.event.render.RocanEventRender;

// Folders.
import rina.rocan.client.modules.combat.*;
import rina.rocan.client.modules.movement.*;
import rina.rocan.client.modules.render.*;
import rina.rocan.client.modules.exploit.*;
import rina.rocan.client.modules.misc.*;
import rina.rocan.client.modules.gui.*;

// Client.
import rina.rocan.client.RocanModule.Category;
import rina.rocan.client.RocanModule;
import rina.rocan.client.RocanHUD;

// Util.
import rina.rocan.util.RocanUtilEntity;
import rina.rocan.util.RocanUtilMinecraftHelper;

/**
  *
  * @author Rina!
  *
  * Created by Rina!
  * 15/08/2020.
  *
  **/
public class RocanModuleManager {
	ArrayList<RocanModule> module_list;
	ArrayList<RocanHUD> hud_list;

	public RocanModuleManager() {
		this.module_list = new ArrayList<>();
		this.hud_list    = new ArrayList<>();

		// Combat.
		addModule(new RocanOffhandUtil());

		// Movement.
		addModule(new RocanSpeed());
		addModule(new RocanGUIWalk());
		addModule(new RocanStep());

		// Render.
		addModule(new RocanBlockHighlight());

		// Exploit.
		addModule(new RocanXCarry());

		// Misc.
		addModule(new RocanFastUtil());

		// GUI.
		addModule(new RocanGUI());
		addModule(new RocanMasterHUD());

		// HUD.
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
		RocanUtilMinecraftHelper.getMinecraft().profiler.startSection("rocan");
		RocanUtilMinecraftHelper.getMinecraft().profiler.startSection("setup");

		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		GlStateManager.disableDepth();

		GlStateManager.glLineWidth(1.5f);

		Vec3d pos = RocanUtilEntity.getInterpolatedPos(RocanUtilMinecraftHelper.getMinecraft().player, event.getPartialTicks());

		RocanEventRender event_render = new RocanEventRender(TurokRenderHelp.INSTANCE, pos);

		event_render.resetTranslatation();

		RocanUtilMinecraftHelper.getMinecraft().profiler.endSection();

		for (RocanModule modules : getModuleList()) {
			if (modules.getState()) {
				RocanUtilMinecraftHelper.getMinecraft().profiler.startSection(modules.getTag());

				modules.onRender(event_render);

				RocanUtilMinecraftHelper.getMinecraft().profiler.endSection();
			}
		}

		RocanUtilMinecraftHelper.getMinecraft().profiler.startSection("release");

		GlStateManager.glLineWidth(1.5f);

		GlStateManager.shadeModel(GL11.GL_FLAT);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
		GlStateManager.enableDepth();
		GlStateManager.enableCull();

		TurokRenderHelp.releaseGL();

		RocanUtilMinecraftHelper.getMinecraft().profiler.endSection();
		RocanUtilMinecraftHelper.getMinecraft().profiler.endSection();
	}

	public ArrayList<RocanModule> getModuleList() {
		return this.module_list;
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
}