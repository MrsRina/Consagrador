package rina.rocan.mixin.mixins;

// Minecraft.
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

// Mixin.
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

// OpenGL.
import org.lwjgl.opengl.EXTPackedDepthStencil;
import org.lwjgl.opengl.EXTFramebufferObject;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL11;

// Java.
import java.awt.*;

// Util.
import rina.rocan.util.RocanUtilMinecraftHelper;

// Event.
import rina.rocan.event.render.RocanEventRenderLivingBase;

// Rocan.
import rina.rocan.Rocan;

/**
  * @author Rina
  *
  * Created by Rina!
  * 13/09/2020.
  *
  **/
@Mixin(value = RenderLivingBase.class, priority = 999)
public abstract class RocanMixinRenderLivingBase <T extends EntityLivingBase> extends RocanMixinRender<T> {
	Minecraft mc = Minecraft.getMinecraft();

	@Shadow
	protected ModelBase mainModel;

	@Inject(method = "doRender", at = @At("HEAD"))
	private void onRender(T entity, double x, double y, double z, float yaw, float partial_ticks, CallbackInfo callback) {
		RocanEventRenderLivingBase event = new RocanEventRenderLivingBase(RocanEventRenderLivingBase.EventStage.PRE, entity, x, y, z, yaw, partial_ticks);
	
		Rocan.getPomeloEventManager().dispatchEvent(event);
	}

	@Inject(method = "doRender", at = @At("RETURN"))
	private void onLastRender(T entity, double x, double y, double z, float yaw, float partial_ticks, CallbackInfo callback) {
		RocanEventRenderLivingBase event = new RocanEventRenderLivingBase(RocanEventRenderLivingBase.EventStage.POST, entity, x, y, z, yaw, partial_ticks);
	
		Rocan.getPomeloEventManager().dispatchEvent(event);
	}

	// Rina.
	public boolean verifyRender(EntityLivingBase entity) {
		if (entity instanceof EntityPlayer && Rocan.getFriendManager().isFriend(entity.getName()) && Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPRenderEntityFriend").getBoolean()) {
			return true;
		}

		if (entity instanceof EntityPlayer && Rocan.getFriendManager().isEnemy(entity.getName()) && Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPRenderEntityEnemy").getBoolean()) {
			return true;
		}

		if (entity instanceof EntityPlayer && Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPRenderEntityPlayer").getBoolean()) {
			return true;
		}

		if (entity instanceof IMob && Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPRenderEntityHostile").getBoolean()) {
			return true;
		}

		if (entity instanceof IAnimals && Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPRenderEntityAnimals").getBoolean()) {
			return true;
		}

		return false;
	}

	// Copyright ï¿½ 2016 | Hexeption & TheCyberBrick | Innocent All rights reserved.
	@Overwrite
	protected void renderModel(T entitylivingbaseIn, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float scaleFactor) {
		boolean flag  = !entitylivingbaseIn.isInvisible();
		boolean flag1 = !flag && !entitylivingbaseIn.isInvisibleToPlayer(mc.player);

		if (flag || flag1) {
			if (!bindEntityTexture(entitylivingbaseIn)) {
				return;
			}
		
			if (flag1) {
				GlStateManager.pushMatrix();
				GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
				GlStateManager.depthMask(false);
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(770, 771);
				GlStateManager.alphaFunc(516, 0.003921569F);
			}

			if (Rocan.getModuleManager().getModuleByTag("EntityESP").getState() && mc.player.getDistance(entitylivingbaseIn) < Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPRange").getInteger() && mc.player.getDistance(entitylivingbaseIn) > Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPRangeStopRender").getInteger()) {
				if (verifyRender(entitylivingbaseIn)) {
					Color n;

					if (entitylivingbaseIn instanceof EntityPlayer && Rocan.getFriendManager().isFriend(entitylivingbaseIn.getName())) {
						n = new Color(Rocan.getClientHUDRed(), Rocan.getClientHUDGreen(), Rocan.getClientHUDBlue());
					} else {
						n = new Color(Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPRenderRed").getInteger(), Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPRenderGreen").getInteger(), Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPRenderBlue").getInteger());
					}

					setColor(n);

					mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);

					renderOne((float) Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPWidthLine").getDouble());
								
					mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);

					renderTwo();

					mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);

					renderThree();
					renderFour();
					setColor(n);

					mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);

					renderFive();
					setColor(Color.WHITE);
				}
			}

			this.mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);

			if (flag1) {
				GlStateManager.disableBlend();
				GlStateManager.alphaFunc(516, 0.1F);
				GlStateManager.popMatrix();
				GlStateManager.depthMask(true);
			}
		}
	}

	public void renderOne(float width) {
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
	
	public void renderTwo() {
		GL11.glStencilFunc(GL11.GL_NEVER, 0, 0xF);
		GL11.glStencilOp(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
	}
	
	public void renderThree() {
		GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xF);
		GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
	}
	
	public void renderFour() {
		setColor(new Color(255, 255, 255));
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_POLYGON_OFFSET_LINE);
		GL11.glPolygonOffset(1.0F, -2000000F);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
	}
	
	public void renderFive() {
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

	public void setColor(Color c) {
		GL11.glColor4d(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
	}

	public void checkSetupFBO() {
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
	public void setupFBO(Framebuffer fbo) {
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