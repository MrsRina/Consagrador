package rina.rocan.mixin;

// Minecraft.
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

// Javax.
import javax.annotation.Nullable;

// Mixin utils.
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

// Acess.
import rina.rocan.mixin.acess.RocanMixinAcess;

// Java.
import java.util.Map;

/**
  * @author Rina
  *
  * Created by Rina!
  * 15/08/2020.
  *
  **/
@IFMLLoadingPlugin.MCVersion("1.12.2")
public class RocanMixinLoader implements IFMLLoadingPlugin {
	public RocanMixinLoader() {
		MixinBootstrap.init();

		Mixins.addConfiguration("mixins.rocan.json");

		// MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
	}

	@Override
	public String[] getASMTransformerClass() {
		return new String[0];
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Nullable
	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {}

	@Override
	public String getAccessTransformerClass() {
		return RocanMixinAcess.class.getName();
	}
}