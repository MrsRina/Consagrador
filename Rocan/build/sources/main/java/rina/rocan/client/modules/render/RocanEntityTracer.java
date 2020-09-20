package rina.rocan.client.modules.render;

// Minecraft.
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.*;
import net.minecraft.init.Items;

// OpenGL.
import org.lwjgl.opengl.GL11;

// Java.
import java.awt.*;

// Turok.
import rina.turok.TurokRenderHelp;
import rina.turok.TurokRenderGL;

// Client.
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;

// Util.
import rina.rocan.util.RocanUtilRendererEntity2D3D;

// Event.
import rina.rocan.event.render.RocanEventRender;

// Rocan.
import rina.rocan.Rocan;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 10/09/2020.
 *
 **/
public class RocanEntityTracer extends RocanModule {
	// Stuff...
	RocanSetting range                = createSetting(new String[] {"Range", "EntityTracerRange", "Range to render."}, 200, 0, 200);
	RocanSetting range_to_stop_render = createSetting(new String[] {"Range To Stop Render", "PlayerTracerToStopRender", "Range to stop render."}, 1, 0, 10);

	// Entities.
	RocanSetting render_entity_player      = createSetting(new String[] {"Player", "EntityTracerRenderEntityPlayer", "Enable entity to render."}, false);
	RocanSetting render_entity_enemy       = createSetting(new String[] {"Enemy", "EntityTracerRenderEntityEnemy", "Enable entity to render."}, false);
	RocanSetting render_entity_friend      = createSetting(new String[] {"Friend", "EntityTracerRenderEntityFriend", "Enable entity to render."}, true);
	RocanSetting render_entity_hostile     = createSetting(new String[] {"Hostile", "EntityTracerRenderEntityHostile", "Enable entity to rende."}, false);
	RocanSetting render_entity_animals     = createSetting(new String[] {"Animals & Pigs", "EntityTracerRenderEntityAnimals", "Enable entity to render."}, false);
	RocanSetting render_entity_end_crystal = createSetting(new String[] {"End Crystal", "EntityTracerRenderEntityEndCrystal", "Enable entity to render."}, false);
	RocanSetting render_entity_drop_item   = createSetting(new String[] {"Drop Item", "EntityTracerRenderEntityDropItem", "Enable entity to render."}, false);

	// RGB effecttt.
	RocanSetting rgb_effect = createSetting(new String[] {"RGB", "EntityTracerRGB", "RGB effect to render."}, false);

	// Color.
	RocanSetting render_color_red   = createSetting(new String[] {"Render Red", "EntityTracerRed", "Render color red."}, 190, 0, 255);
	RocanSetting render_color_green = createSetting(new String[] {"Render Green", "EntityTracerGreen", "Render color green."}, 190, 0, 255);
	RocanSetting render_color_blue  = createSetting(new String[] {"Render Blue", "EntityTracerBlue", "Render color blue."}, 190, 0, 255);
	RocanSetting render_color_alpha = createSetting(new String[] {"Render Alpha", "EntityTracerAlpha", "Render color alpha."}, 255, 0, 255);

	// Line size.
	RocanSetting line_size = createSetting(new String[] {"Line Size", "EntityTracerSize", "Line size to render."}, 1.0, 1.0, 3.0);

	int r;
	int g;
	int b;

	public RocanEntityTracer() {
		super(new String[] {"Entity Tracer", "EntityTracer", "Tracer util for render."}, Category.ROCAN_RENDER);
	}

	@Override
	public void onRender(RocanEventRender event) {
		if (mc.player == null || mc.world == null) {
			return;
		}

		if (rgb_effect.getBoolean()) {
			render_color_red.setInteger(Rocan.getEventManager().getRGBEffect()[0]);
			render_color_green.setInteger(Rocan.getEventManager().getRGBEffect()[1]);
			render_color_blue.setInteger(Rocan.getEventManager().getRGBEffect()[2]);

			r = render_color_red.getInteger();
			g = render_color_green.getInteger();
			b = render_color_blue.getInteger();
		} else {
			r = render_color_red.getInteger();
			g = render_color_green.getInteger();
			b = render_color_blue.getInteger();
		}

		for (Entity entity : mc.world.loadedEntityList) {
			if (mc.player == entity) {
				continue;
			}

			if (mc.player.getDistance(entity) > range.getInteger() || mc.player.getDistance(entity) < range_to_stop_render.getInteger()) {
				continue;
			}

			if (verifyRender(entity)) {
				if (entity instanceof EntityPlayer && Rocan.getFriendManager().isFriend(entity.getName())) {
					TurokRenderHelp.renderTracer(event.getPartialTicks(), entity, Rocan.getClientHUDRed(), Rocan.getClientHUDGreen(), Rocan.getClientHUDBlue(), render_color_alpha.getInteger(), (float) line_size.getDouble());
				} else {
					TurokRenderHelp.renderTracer(event.getPartialTicks(), entity, r, g, b, render_color_alpha.getInteger(), (float) line_size.getDouble());
				}
			}
		}
	}

	public boolean verifyRender(Entity entity) {
		boolean render = false;

		if (entity instanceof EntityPlayer && entity == mc.player) {
			render =  false;
		}

		if (entity instanceof EntityLivingBase && entity instanceof EntityPlayer && (render_entity_player.getBoolean() || (render_entity_enemy.getBoolean() && Rocan.getFriendManager().isEnemy(entity.getName())) || (render_entity_friend.getBoolean() && Rocan.getFriendManager().isFriend(entity.getName())))) {
			render = true;
		}

		if (entity instanceof EntityLivingBase && entity instanceof IMob && render_entity_hostile.getBoolean()) {
			render = true;
		}

		if (entity instanceof EntityLivingBase && entity instanceof IAnimals && render_entity_animals.getBoolean()) {
			render = true;
		}

		if (entity instanceof EntityEnderCrystal && render_entity_end_crystal.getBoolean()) {
			render = true;
		}

		if (entity instanceof EntityItem && render_entity_drop_item.getBoolean()) {
			render = true;
		}

		return render;
	}
}