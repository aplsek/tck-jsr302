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

import edu.purdue.scj.VMSupport;
import edu.purdue.scj.utils.Utils;

public class HelloWorld extends CyclicExecutive {

	public HelloWorld() {
		super(null);
	}

	public CyclicSchedule getSchedule(PeriodicEventHandler[] handlers) {
		CyclicSchedule.Frame[] frames = new CyclicSchedule.Frame[1];
		CyclicSchedule schedule = new CyclicSchedule(frames);
		frames[0] = new CyclicSchedule.Frame(new RelativeTime(200, 0), handlers);
		return schedule;
	}

	public void initialize() {
		new WordHandler(100000, "HelloWorld.\n", 0);
	}

	/**
	 * A method to query the maximum amount of memory needed by this mission.
	 * 
	 * @return the amount of memory needed
	 */
	// @Override
	public long missionMemorySize() {
		return 600000;
	}

	public void setUp() {
		Terminal.getTerminal().write("setUp.\n");
	}

	public void tearDown() {
		Terminal.getTerminal().write("teardown.\n");
	}

	public void cleanUp() {
		Terminal.getTerminal().write("cleanUp.\n");
	}

}
