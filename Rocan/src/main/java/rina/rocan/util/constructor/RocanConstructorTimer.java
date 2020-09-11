package rina.rocan.util.constructor;

/**
  *
  * @author Rina!
  *
  * Created by Rina!
  * 11/09/2020.
  *
  **/
public class RocanConstructorTimer {
	private long just_timer;

	public RocanConstructorTimer() {
		this.just_timer = -1;
	}

	public void setJustTimer(long longg) {
		this.just_timer = longg;
	}

	public void reset(long longg) {
		this.just_timer = System.currentTimeMillis() + longg;
	}

	public void reset() {
		this.just_timer = System.currentTimeMillis();
	}

	public long getJustTimer() {
		return this.just_timer;
	}

	public boolean isPassedMS(double ms) {
		return System.currentTimeMillis() - this.just_timer >= ms;
	}

	public boolean isPassedSI(double si) {
		return System.currentTimeMillis() - this.just_timer >= (si * 1000);
	}
}