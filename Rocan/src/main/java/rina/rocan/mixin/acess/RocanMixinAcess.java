package rina.rocan.mixin.acess;

// Minecraft.
import net.minecraftforge.fml.common.asm.transformers.AccessTransformer;

// Java.
import java.io.IOException;

/**
  * @author Rina
  *
  * Created by Rina!
  * 16/08/2020.
  *
  **/
public class RocanMixinAcess extends AccessTransformer {
	public RocanMixinAcess() throws IOException {
		super("rocan_at.cfg");
	}
}