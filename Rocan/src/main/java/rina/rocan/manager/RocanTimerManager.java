package rina.rocan.manager;

// Constructor.
import rina.rocan.util.constructor.RocanConstructorTimer;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 15/08/2020.
 *
 **/
public class RocanTimerManager {
	RocanConstructorTimer actual_timer;

	public RocanTimerManager() {
		this.actual_timer = new RocanConstructorTimer();
	}

	public RocanConstructorTimer getTimer() {
		return this.actual_timer;
	}
}