package rina.rocan.client.modules.render;

// Minecraft.
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.renderer.OpenGlHelper;
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
import net.minecraft.init.Items;

// OpenGL.
import org.lwjgl.opengl.GL11;

// Java.
import java.awt.*;

// Pomelo.
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

// Turok.
import rina.turok.TurokRenderHelp;

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
 * 07/09/2020.
 *
 **/
public class RocanEntityESP extends RocanModule {
	// Settings range, minimum, modes...
	RocanSetting range                = createSetting(new String[] {"Range", "EntityESPRange", "Area range to able render."}, 200, 0, 200);
	RocanSetting range_to_stop_render = createSetting(new String[] {"Range To Stop Render", "EntityESPRangeToStopRender", "Offset to stop render."}, 6, 0, 10);

	// Entities.
	RocanSetting render_entity_player      = createSetting(new String[] {"Player", "EntityESPRenderEntityPlayer", "Enable entity to render."}, false);
	RocanSetting render_entity_enemy       = createSetting(new String[] {"Enemy", "EntityESPRenderEntityEnemy", "Enable entity to render."}, false);
	RocanSetting render_entity_friend      = createSetting(new String[] {"Friend", "EntityESPRenderEntityFriend", "Enable entity to render."}, true);
	RocanSetting render_entity_hostile     = createSetting(new String[] {"Hostile", "EntityESPRenderEntityHostile", "Enable entity to rende."}, false);
	RocanSetting render_entity_animals     = createSetting(new String[] {"Animals & Pigs", "EntityESPRenderEntityAnimals", "Enable entity to render."}, false);
	RocanSetting render_entity_end_crystal = createSetting(new String[] {"End Crystal", "EntityESPRenderEntityEndCrystal", "Enable entity to render."}, false);
	RocanSetting render_entity_drop_item   = createSetting(new String[] {"Drop Item", "EntityESPRenderEntityDropItem", "Enable entity to render."}, false);

	// RGB retarddded effect.
	RocanSetting rgb_effect = createSetting(new String[] {"RGB", "PlayerESPRGB", "RGB effect to render."}, false);

	// Render stuff to ESP.
	RocanSetting render_color_red   = createSetting(new String[] {"Render Red", "EntityPlayerESPRenderRed", "Entity render color red."}, 190, 0, 255);
	RocanSetting render_color_green = createSetting(new String[] {"Render Green", "EntityPlayerESPrenderGreen", "Entity render color green."}, 190, 0, 255);
	RocanSetting render_color_blue  = createSetting(new String[] {"Render Blue", "EntityPlayerESPrenderBlue", "Entity render color blue."}, 190, 0, 255);

	// Line.
	RocanSetting width_line = createSetting(new String[] {"Width Line", "EntityESPWidthLine", "Width line to ESP."}, 2.0d, 1.0d, 5.0d);

	public RocanEntityESP() {
		super(new String[] {"Entity ESP", "EntityESP", "Make you see entities on area."}, Category.ROCAN_RENDER);
	}

	/*
	 * RocanEventRender make RGB effect smooth.
	 */
	@Override
	public void onRender(RocanEventRender event) {
		if (rgb_effect.getBoolean()) {	
			render_color_red.setInteger(Rocan.getEventManager().getRGBEffect()[0]);
			render_color_green.setInteger(Rocan.getEventManager().getRGBEffect()[1]);
			render_color_blue.setInteger(Rocan.getEventManager().getRGBEffect()[2]);
		}
	}
}