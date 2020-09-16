package rina.rocan.util;

// Minecraft.
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;

// Java.
import java.awt.*;

// OpenGL.
import org.lwjgl.opengl.EXTPackedDepthStencil;
import org.lwjgl.opengl.EXTFramebufferObject;
import static org.lwjgl.opengl.GL11.*;
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

	public static void renderOne(float width) {
		checkSetupFBO();
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glLineWidth(width);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
		GL11.glClearStencil(0xF);
		GL11.glStencilFunc(GL11.GL_NEVER, 1, 0xF);
		GL11.glStencilOp(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
	}
	
	public static void renderTwo() {
		GL11.glStencilFunc(GL11.GL_NEVER, 0, 0xF);
		GL11.glStencilOp(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
	}
	
	public static void renderThree() {
		GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xF);
		GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
	}
	
	public static void renderFour(Color color) {
		setColor(color);
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_POLYGON_OFFSET_LINE);
		GL11.glPolygonOffset(1.0F, -2000000F);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
	}
	
	public static void renderFive() {
		GL11.glPolygonOffset(1.0F, 2000000F);
		GL11.glDisable(GL11.GL_POLYGON_OFFSET_LINE);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_STENCIL_TEST);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_DONT_CARE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glPopAttrib();
	}

	public static void setColor(Color c) {
		GL11.glColor4d(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
	}

	public static void checkSetupFBO() {
		// Gets the FBO of Minecraft
		Framebuffer fbo = Minecraft.getMinecraft().getFramebuffer();

		// Check if FBO isn't null
		if (fbo != null) {
			// Checks if screen has been resized or new FBO has been created
			if (fbo.depthBuffer > -1) {
				// Sets up the FBO with depth and stencil extensions (24/8 bit)
				setupFBO(fbo);
				// Reset the ID to prevent multiple FBO's
				fbo.depthBuffer = -1;
			}
		}
	}
	
	/**
	 * Sets up the FBO with depth and stencil
	 *
	 * @param fbo Framebuffer
	 */
	public static void setupFBO(Framebuffer fbo) {
		// Deletes old render buffer extensions such as depth
		// Args: Render Buffer ID
		EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
		// Generates a new render buffer ID for the depth and stencil extension
		int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
		// Binds new render buffer by ID
		// Args: Target (GL_RENDERBUFFER_EXT), ID
		EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencil_depth_buffer_ID);
		// Adds the depth and stencil extension
		// Args: Target (GL_RENDERBUFFER_EXT), Extension (GL_DEPTH_STENCIL_EXT),
		// Width, Height
		EXTFramebufferObject.glRenderbufferStorageEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, EXTPackedDepthStencil.GL_DEPTH_STENCIL_EXT, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		// Adds the stencil attachment
		// Args: Target (GL_FRAMEBUFFER_EXT), Attachment
		// (GL_STENCIL_ATTACHMENT_EXT), Target (GL_RENDERBUFFER_EXT), ID
		EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_STENCIL_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencil_depth_buffer_ID);
		// Adds the depth attachment
		// Args: Target (GL_FRAMEBUFFER_EXT), Attachment
		// (GL_DEPTH_ATTACHMENT_EXT), Target (GL_RENDERBUFFER_EXT), ID
		EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencil_depth_buffer_ID);
	}
}