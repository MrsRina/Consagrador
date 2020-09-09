package rina.rocan.event.render;

// Minecraft.
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.Vec3d;

// Event.
import rina.rocan.event.RocanEventCancellable;

/**
  *
  * @author Rina!
  *
  * Created by Rina!
  * 17/08/2020.
  *
  **/
public class RocanEventRender extends RocanEventCancellable {
	private float partial_ticks;

	public RocanEventRender(float partial_ticks) {
		super();

		this.partial_ticks = partial_ticks;
	}

	public void setPartialTicks(float partial_ticks) {
		this.partial_ticks = partial_ticks;
	}

	public float getPartialTicks() {
		return this.partial_ticks;
	}
}