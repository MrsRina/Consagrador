package rina.rocan.event;

/**
  *
  * @author Rina!
  *
  * Created by Rina!
  * 15/08/2020.
  *
  **/
public class RocanEventCancellable extends RocanEventStageable {
	private boolean canceled;

	public RocanEventCancellable() {}

	public RocanEventCancellable(EventStage stage) {
		super(stage);
	}

	public RocanEventCancellable(EventStage stage, boolean canceled) {
		super(stage);

		this.canceled = canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public void cancel() {
		this.canceled = true;
	}

	public boolean isCancelled() {
		return this.canceled;
	}
}