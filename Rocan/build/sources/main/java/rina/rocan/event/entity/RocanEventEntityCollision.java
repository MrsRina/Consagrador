package rina.rocan.event.entity;

// Minecraft.
import net.minecraft.entity.Entity;

// Event.
import rina.rocan.event.RocanEventCancellable;

/**
  *
  * @author Rina!
  *
  * Created by Rina!
  * 23/09/2020.
  *
  **/
public class RocanEventEntityCollision extends RocanEventCancellable {
	Entity entity;

	double x;
	double y;
	double z;

	public RocanEventEntityCollision(Entity entity, double x, double y, double z) {
		super();

		this.entity = entity;

		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
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
}