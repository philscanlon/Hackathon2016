package com.solacelabs.chat;

public class ActivityMonitor {
	private long lastPingTime = 0;

	private int skippedPings = 0;
	// states
	userState state;

	public ActivityMonitor() {
		// when created the state will be ACTIVE
		state = userState.ACTIVE;
	}
	
	public enum userState {
		DISCONNECTED,ACTIVE,IDLE,BUSY
	}
	public long getLastPingTime() {
		return lastPingTime;
	}

	public int getSkippedPings() {
		return skippedPings;
	}

	public userState getState() {
		return state;
	}

	public void setLastPingTime(long lastPingTime) {
		this.lastPingTime = lastPingTime;
	}

	public void setSkippedPings(int skippedPings) {
		this.skippedPings = skippedPings;
	}

	public void setState(userState state) {
		this.state = state;
	}

}
