/**
 *  This file is part of oSCJ.
 *
 *   oSCJ is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   oSCJ is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with oSCJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *   Copyright 2009, 2010 
 *   @authors  Lei Zhao, Ales Plsek
 */

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

import edu.purdue.scj.BackingStoreID;
import edu.purdue.scj.VMSupport;
import edu.purdue.scj.utils.Utils;

public class TestMemory extends CyclicExecutive {

	public TestMemory() {
		super(null);
	}

	private static void writeln(String msg) {
		Terminal.getTerminal().writeln(msg);
	}

	public CyclicSchedule getSchedule(PeriodicEventHandler[] handlers) {
		CyclicSchedule.Frame[] frames = new CyclicSchedule.Frame[1];
		CyclicSchedule schedule = new CyclicSchedule(frames);
		frames[0] = new CyclicSchedule.Frame(new RelativeTime(200, 0),
				handlers);
		return schedule;
	}

	public void initialize() {
		new Handler(20000, "TestMemory.\n", 1);

		System.out.println(" total = " + Runtime.getRuntime().totalMemory());
		System.out.println(" free = " + Runtime.getRuntime().freeMemory());
		System.out.print("Initializing..");
		printMem(ImmortalMemory.instance());
		printMem(RealtimeThread.getCurrentMemoryArea());

		System.out.println("Memory area is " + RealtimeThread.getCurrentMemoryArea());
	}

	public long missionMemorySize() {
		return 500000;
	}

	public void setUp() {
		Terminal.getTerminal().write("setUp.\n");
	}

	public void tearDown() {
		Terminal.getTerminal().write("tearDown.\n");
	}

	public void cleanUp() {
		Terminal.getTerminal().write("cleanUp.\n");
	}

	public class Handler extends PeriodicEventHandler {

		private int count_;

		private Handler(long psize, String name, int count) {
			super(null, null, new StorageParameters(psize,0,0), name);
			count_ = count;
		}

		public void handleAsyncEvent() {
			Terminal.getTerminal().write(getName());

			if (count_-- == 0)
				getCurrentMission()
						.requestSequenceTermination();
		}

		public void cleanUp() {
		}

		public StorageParameters getThreadConfigurationParameters() {
			return null;
		}
	}

	public final void printMem(MemoryArea area) {
		System.out.print(" area = ");
		System.out.println(area.toString());
		System.out.print("   size = ");
		System.out.println(Long.toString(area.size()));
		System.out.print("   used = ");
		System.out.println(Long.toString(area.memoryConsumed()));
		System.out.print("   left = ");
		System.out.println(Long.toString(area.memoryRemaining()));
	}
}
