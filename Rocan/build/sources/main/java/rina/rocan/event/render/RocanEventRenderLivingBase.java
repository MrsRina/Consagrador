package rina.rocan.event.render;

// Minecraft.
import net.minecraft.entity.EntityLivingBase;

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
public class RocanEventRenderLivingBase extends RocanEventCancellable {
	private EntityLivingBase entity_living_base;

	private double x;
	private double y;
	private double z;

	private float yaw;

	private float partial_ticks;

	public RocanEventRenderLivingBase(EventStage stage, EntityLivingBase entity_living_base, double x, double y, double z, float yaw, float partial_ticks) {
		super(stage);

		this.entity_living_base = entity_living_base;

		this.x = x;
		this.y = y;
		this.z = z;

		this.yaw = yaw;

		this.partial_ticks = partial_ticks;
	}

	public EntityLivingBase getEntityLivingBase() {
		return this.entity_living_base;
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