package rina.rocan.event.player;

// Minecraft.
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Entity;

// Event.
import rina.rocan.event.RocanEventCancellable;

/**
  *
  * @author Rina!
  *
  * Created by Rina!
  * 23/08/2020.
  *
  **/
public class RocanEventPlayerMove extends RocanEventCancellable {
	MoverType type;

	double x;
	double y;
	double z;

	public RocanEventPlayerMove(MoverType type, double x, double y, double z) {
		super();

		this.type = type;

		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void setType(MoverType type) {
		this.type = type;
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

	public MoverType getType() {
		return this.type;
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
