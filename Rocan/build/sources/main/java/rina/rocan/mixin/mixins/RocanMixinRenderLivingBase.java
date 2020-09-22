package rina.rocan.mixin.mixins;

// Minecraft.
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
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

// Event.
import rina.rocan.event.render.RocanEventRenderEntity;

// Java.
import java.awt.*;

// Util.
import rina.rocan.util.RocanUtilRendererEntity2D3D;
import rina.rocan.util.RocanUtilMinecraftHelper;

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
	private void doRender(T entity, double x, double y, double z, float yaw, float partial_ticks, CallbackInfo callback) {
		RocanEventRenderEntity event = new RocanEventRenderEntity(RocanEventRenderEntity.EventStage.PRE, entity, x, y, z, yaw, partial_ticks);

		Rocan.getPomeloEventManager().dispatchEvent(event);
	}

	@Inject(method = "doRender", at = @At("RETURN"))
	private void doLastRender(T entity, double x, double y, double z, float yaw, float partial_ticks, CallbackInfo callback) {
		RocanEventRenderEntity event = new RocanEventRenderEntity(RocanEventRenderEntity.EventStage.POST, entity, x, y, z, yaw, partial_ticks);

		Rocan.getPomeloEventManager().dispatchEvent(event);
	}

	public boolean verifyRender(EntityLivingBase entity) {
		boolean render = false;

		if (entity instanceof EntityPlayer && ((Rocan.getFriendManager().isFriend(entity.getName()) && Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPRenderEntityFriend").getBoolean()) || (Rocan.getFriendManager().isEnemy(entity.getName()) && Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPRenderEntityEnemy").getBoolean()) || Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPRenderEntityPlayer").getBoolean())) {
			render = true;
		}

		if (entity instanceof IMob && Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPRenderEntityHostile").getBoolean()) {
			render = true;
		}

		if (entity instanceof IAnimals && Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPRenderEntityAnimals").getBoolean()) {
			render = true;
		}

		return render;
	}

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

			if (Rocan.getModuleManager().getModuleByTag("EntityESP").getState() && mc.player.getDistance(entitylivingbaseIn) < Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPRange").getInteger() && mc.player.getDistance(entitylivingbaseIn) > Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPRangeToStopRender").getInteger()) {
				Color n = new Color(190, 190, 190);

				if (entitylivingbaseIn instanceof EntityPlayer && Rocan.getFriendManager().isFriend(entitylivingbaseIn.getName())) {
					n = new Color(Rocan.getClientHUDRed(), Rocan.getClientHUDGreen(), Rocan.getClientHUDBlue());
				} //selse {
				//	n = new Color(Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPRenderRed").getInteger(), Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPRenderGreen").getInteger(), Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPRenderBlue").getInteger());
				//}

				if (verifyRender(entitylivingbaseIn)) {
					mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);

					RocanUtilRendererEntity2D3D.renderOne((float) Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPWidthLine").getDouble(), n);

					mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);

					GL11.glLineWidth((float) Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPWidthLine").getDouble());

					RocanUtilRendererEntity2D3D.renderTwo();

					mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);

					GL11.glLineWidth((float) Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPWidthLine").getDouble());

					RocanUtilRendererEntity2D3D.renderThree();
					RocanUtilRendererEntity2D3D.renderFour(n);

					mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);

					GL11.glLineWidth((float) Rocan.getSettingManager().getSettingByModuleAndTag("EntityESP", "EntityESPWidthLine").getDouble());

					RocanUtilRendererEntity2D3D.renderFive();
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
}