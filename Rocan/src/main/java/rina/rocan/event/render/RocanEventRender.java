package rina.rocan.event.render;

// Minecraft.
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.Vec3d;

// Event.
import rina.rocan.event.RocanEventCancellable;

/**
  * @author Rina
  *
  * Created by Rina!
  * 15/08/2020.
  *
  **/
public class RocanEventRender extends RocanEventCancellable {
	private final Tessellator tessellator;
	private final Vec3d render_pos;

	public RocanEventRender(Tessellator tessellator, Vec3d pos) {
		super();

		this.tessellator = tessellator;
		this.render_pos  = pos;
	}

	public Tessellator getTessellator() {
		return this.tessellator;
	}

	public Vec3d getRenderPos() {
		return this.render_pos;
	}

	public BufferBuilder getBuffer() {
		return this.tessellator.getBuffer();
	}

	public void setTranslatation(Vec3d pos) {
		getBuffer().setTranslation(- pos.x, - pos.y, - pos.z);
	}

	public void resetTranslatation() {
		setTranslatation(getRenderPos());
	}
}