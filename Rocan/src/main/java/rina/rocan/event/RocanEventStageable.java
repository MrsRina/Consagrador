package rina.rocan.event;

/**
  * @author Rina
  *
  * Created by Rina!
  * 08/04/20.
  *
  **/
public class RocanEventStageable {
	private EventStage stage;

	public RocanEventStageable() {}

	public RocanEventStageable(EventStage stage) {
		this.stage = stage;
	}

	public void setStage(EventStage stage) {
		this.stage = stage;
	}

	public EventStage getStage() {
		return this.stage;
	}

	public enum EventStage {
		PRE, POST;
	}
}