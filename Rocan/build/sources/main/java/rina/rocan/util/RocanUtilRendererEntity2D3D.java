package rina.rocan.util;

// Minecraft.
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;

// OpenGL.
import org.lwjgl.opengl.GL11;

// Turok.
import rina.turok.TurokRenderGL;

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
public class RocanUtilRendererEntity2D3D {
	public static void prepare2D(Entity entity) {
		boolean is_third_person_view = RocanUtilMinecraftHelper.getMinecraft().getRenderManager().options.thirdPersonView == 2;

		float view_yaw = RocanUtilMinecraftHelper.getMinecraft().getRenderManager().playerViewY;

		TurokRenderGL.prepare3D(1.0f);

		GlStateManager.pushMatrix();

		Vec3d pos = RocanUtilEntity.getInterpolatedPos(entity, RocanUtilMinecraftHelper.getMinecraft().getRenderPartialTicks());

		GlStateManager.translate(pos.x - RocanUtilMinecraftHelper.getMinecraft().getRenderManager().renderPosX, pos.y - RocanUtilMinecraftHelper.getMinecraft().getRenderManager().renderPosY, pos.z - RocanUtilMinecraftHelper.getMinecraft().getRenderManager().renderPosZ);
		GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
		GlStateManager.rotate(-view_yaw, 0.0f, 1.0f, 0.0f);
		GlStateManager.rotate((float) (is_third_person_view ? -1 : 1), 1.0f, 0.0f, 0.0f);

		// U can make stuffs.
	}

	public static void release2D() {
		TurokRenderGL.release3D();
		GlStateManager.popMatrix();

		GL11.glColor4f(1, 1, 1, 1);
	}
}