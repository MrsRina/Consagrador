package rina.rocan.client;

/**
  *
  * @author Rina!
  *
  * Created by Rina!
  * 09/09/2020.
  *
  **/
public class RocanFriend {
	private String name;
	private String uuid;

	private double last_pos_x;
	private double last_pos_y;
	private double last_pos_z;

	public RocanFriend(String name, String uuid) {
		this.name = name;
		this.uuid = uuid;
	}

	public RocanFriend(String name, String uuid, double x, double y, double z) {
		this.name = name;
		this.uuid = uuid;

		this.last_pos_x = x;
		this.last_pos_y = y;
		this.last_pos_z = z;
	}

	public void setLastPosX(double x) {
		this.last_pos_x = x;
	}

	public void setLastPosY(double y) {
		this.last_pos_y = y;
	}

	public void setLastPosZ(double z) {
		this.last_pos_z = z;
	}

	public String getName() {
		return this.name;
	}

	public String getUUID() {
		return this.uuid;
	}

	public double getLastPosX() {
		return this.last_pos_x;
	}

	public double getLastPosY() {
		return this.last_pos_y;
	}

	public double getLastPosZ() {
		return this.last_pos_z;
	}
}