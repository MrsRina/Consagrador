package rina.rocan.util;

// Minecraft.
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

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
  * 17/08/2020.
  *
  **/
public class RocanUtilEntity {
	public static Vec3d getLastTickPos(Entity entity, double x, double y, double z) {
		return new Vec3d(
			(entity.posX - entity.lastTickPosX) * x,
			(entity.posY - entity.lastTickPosY) * y,
			(entity.posZ - entity.lastTickPosZ) * z);
	}

	public static Vec3d getInterpolatedPos(Entity entity, double ticks) {
		return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(getLastTickPos(entity, ticks, ticks, ticks));
	}

	public static EntityPlayerSP getEntityPlayerSPOnChunkByName(String name) {
		for (Entity entity : RocanUtilMinecraftHelper.getMinecraft().world.loadedEntityList) {
			if (!(entity instanceof EntityLivingBase && entity instanceof EntityPlayerSP)) {
				continue;
			}

			EntityPlayerSP entity_player_SP = (EntityPlayerSP) entity;  

			return entity_player_SP;
		}

		return null;
	}

	public static double getInterpolated(double now, double then) {
		return then + (now - then) * RocanUtilMinecraftHelper.getMinecraft().getRenderPartialTicks();
	}

	public static double[] getInterpolated(Entity entity) {
		double x = getInterpolated(entity.posX, entity.lastTickPosX) - RocanUtilMinecraftHelper.getMinecraft().getRenderManager().renderPosX;
		double y = getInterpolated(entity.posY, entity.lastTickPosY) - RocanUtilMinecraftHelper.getMinecraft().getRenderManager().renderPosY;
		double z = getInterpolated(entity.posZ, entity.lastTickPosZ) - RocanUtilMinecraftHelper.getMinecraft().getRenderManager().renderPosZ;

		return new double[] {
			x, y, z
		};
	}
}