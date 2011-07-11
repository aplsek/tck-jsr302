import javax.realtime.AbsoluteTime;
import javax.realtime.Clock;
import javax.realtime.ImmortalMemory;
import javax.realtime.MemoryArea;
import javax.realtime.RealtimeThread;
import javax.realtime.RelativeTime;
import javax.safetycritical.CyclicExecutive;
import javax.safetycritical.CyclicSchedule;
import javax.safetycritical.MissionManager;
import javax.safetycritical.PeriodicEventHandler;
import javax.safetycritical.Safelet;
import javax.safetycritical.StorageParameters;
import javax.safetycritical.Terminal;
import javax.safetycritical.Mission;

public class WordHandler extends PeriodicEventHandler {

	private int count_;

	public WordHandler(long psize, String name, int count) {

		super(null, null, new StorageParameters(psize, 1000L, 1000L), name);

		count_ = count;
	}

	/**
	 * 
	 * Testing Enter Private Memory
	 * 
	 */
	public void handleAsyncEvent() {
		Terminal.getTerminal().write(getName());

		if (count_-- == 0)
			Mission.getCurrentMission().requestSequenceTermination();
	}

	public void cleanUp() {
	}

	public StorageParameters getThreadConfigurationParameters() {
		return null;
	}
}
