package rina.turok;

// Minecraft.
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.*;
import net.minecraft.client.Minecraft;

// Java.
import java.awt.Color;
import java.util.*;

// OpenGL.
import static org.lwjgl.opengl.GL11.*;

/**
 * @author Rina
 *
 * Created by Rina.
 * 08/09/2020.
 *
 **/
public class TurokRenderHelp {
	private static final Minecraft mc = Minecraft.getMinecraft();

	public static void prepare() {
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.disableDepth();
		GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
		GlStateManager.disableTexture2D();
		GlStateManager.depthMask(false);
		glEnable(GL_LINE_SMOOTH);
		glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
		glLineWidth(1.5f);
	}

	public static void release() {
		glDisable(GL_LINE_SMOOTH);
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

	public static void render3DSolid(ICamera camera, BlockPos blockpos, int r, int g, int b, int a) {
		render3DSolid(camera, blockpos.x, blockpos.y, blockpos.z, 1, 1, 1, r, g, b, a);
	}

	public static void render3DSolid(ICamera camera, int x, int y, int z, int r, int g, int b, int a) {
		render3DSolid(camera, x, y, z, 1, 1, 1, r, g, b, a);
	}

	public static void render3DSolid(ICamera camera, int x, int y, int z, int offset_x, int offset_y, int offset_z, int r, int g, int b, int a) {
		Color color = new Color(r, g, b, a);

		final AxisAlignedBB bb = new AxisAlignedBB(
			// The start render position.
			x - mc.getRenderManager().viewerPosX,
			y - mc.getRenderManager().viewerPosY, 
			z - mc.getRenderManager().viewerPosZ,

			// We have offset for render.
			x + offset_x - mc.getRenderManager().viewerPosX,
			y + offset_y - mc.getRenderManager().viewerPosY,
			z + offset_z - mc.getRenderManager().viewerPosZ
		);

		camera.setPosition(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().posY, mc.getRenderViewEntity().posZ);
	
		if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(
			// Start position.
			bb.minX + mc.getRenderManager().viewerPosX,
			bb.minY + mc.getRenderManager().viewerPosY, 
			bb.minZ + mc.getRenderManager().viewerPosZ,

			// offset.
			bb.maxX + mc.getRenderManager().viewerPosX, 
			bb.maxY + mc.getRenderManager().viewerPosY,
			bb.maxZ + mc.getRenderManager().viewerPosZ))) {
			// Render.
			// Prepare.
			prepare();

			// Render global.
			RenderGlobal.renderFilledBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);

			// Release.
			release();
		}
	}

	public static void render3DOutline(ICamera camera, BlockPos blockpos, int r, int g, int b, int a) {
		render3DOutline(camera, blockpos.x, blockpos.y, blockpos.z, 1, 1, 1, r, g, b, a);
	}

	public static void render3DOutline(ICamera camera, int x, int y, int z, int r, int g, int b, int a) {
		render3DOutline(camera, x, y, z, 1, 1, 1, r, g, b, a);
	}

	public static void render3DOutline(ICamera camera, int x, int y, int z, int offset_x, int offset_y, int offset_z, int r, int g, int b, int a) {
		Color color = new Color(r, g, b, a);

		final AxisAlignedBB bb = new AxisAlignedBB(
			// The start render position.
			x - mc.getRenderManager().viewerPosX,
			y - mc.getRenderManager().viewerPosY, 
			z - mc.getRenderManager().viewerPosZ,

			// We have offset for render.
			x + offset_x - mc.getRenderManager().viewerPosX,
			y + offset_y - mc.getRenderManager().viewerPosY,
			z + offset_z - mc.getRenderManager().viewerPosZ
		);

		camera.setPosition(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().posY, mc.getRenderViewEntity().posZ);
	
		if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(
			// Start position.
			bb.minX + mc.getRenderManager().viewerPosX,
			bb.minY + mc.getRenderManager().viewerPosY, 
			bb.minZ + mc.getRenderManager().viewerPosZ,

			// offset.
			bb.maxX + mc.getRenderManager().viewerPosX, 
			bb.maxY + mc.getRenderManager().viewerPosY,
			bb.maxZ + mc.getRenderManager().viewerPosZ))) {
			// Render.
			// Prepare.
			prepare();

			// Render global.
			RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);

			// Release.
			release();
		}
	}
}