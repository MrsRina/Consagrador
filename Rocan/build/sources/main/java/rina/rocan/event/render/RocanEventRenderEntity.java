package rina.rocan.event.render;

// Minecraft.
import net.minecraft.entity.Entity;

// Event.
import rina.rocan.event.RocanEventCancellable;

/**
  *
  * @author Rina!
  *
  * Created by Rina!
  * 13/09/2020.
  *
  **/
public class RocanEventRenderEntity extends RocanEventCancellable {
	private Entity entity;

	private double x;
	private double y;
	private double z;

	private float yaw;

	private float partial_ticks;

	public RocanEventRenderEntity(EventStage stage, Entity entity, double x, double y, double z, float yaw, float partial_ticks) {
		super(stage);

		this.entity = entity;

		this.x = x;
		this.y = y;
		this.z = z;

		this.yaw = yaw;

		this.partial_ticks = partial_ticks;
	}

	public Entity getEntity() {
		return this.entity;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getZ() {
		return this.z;
	}

	public float getYaw() {
		return this.yaw;
	}

	public float getPartialTicks() {
		return this.partial_ticks;
	}
}