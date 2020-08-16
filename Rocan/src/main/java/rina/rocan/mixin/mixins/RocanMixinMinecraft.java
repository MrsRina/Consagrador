package rina.rocan.mixin.mixins;

// Minecraft.
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.crash.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;

// OpenGL.
import org.lwjgl.opengl.Display;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

// Mixin.
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

// Rocan.
import rina.rocan.Rocan;

/**
  * @author Rina
  *
  * Created by Rina!
  * 08/04/20.
  *
  **/
@Mixin(value = Minecraft.class)
public class RocanMixinMinecraft {
//	@Inject(method = "displayGuiScreen", at = @At("HEAD"))
//	private void displayGuiScreen(GuiScreen guiScreenIn, CallbackInfo info) {
//		BopeEventGUIScreen guiscreen = new BopeEventGUIScreen(guiScreenIn);
//
//		Bope.ZERO_ALPINE_EVENT_BUS.post(guiscreen);
//	}

//	@Inject(method = "shutdown", at = @At("HEAD"))
//	private void shutdown(CallbackInfo info) {}
//
//	@Redirect(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayCrashReport(Lnet/minecraft/crash/CrashReport;)V"))
//	private void crash(Minecraft minecraft, CrashReport crash) {}
}