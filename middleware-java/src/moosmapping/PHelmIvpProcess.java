package moosmapping;

import atlassharedclasses.Point;

public class PHelmIvpProcess extends MOOSProcess {
	
	private double startSpeed = 1.0;
	// The number of divisions into which valid speeds is divided - e.g. 26 permits granularity of 0.05 over the range 0 -> 5 m/s 
	private int NUM_SPEED_DIVISIONS = 26;
	
	private boolean verboseHelm = true;
	
	public PHelmIvpProcess(MOOSCommunity parent, Point startPos, String vehicleName, double startSpeed, double maxSpeed) {
		super("pHelmIvP", parent);
		this.startSpeed = startSpeed;
			
		// Links the Helm process to its behaviour file
		String parentBHVFile = parent.getBehaviourFileName();
		resetProperty("AppTick", 4);
		resetProperty("CommsTick", 4);
		setProperty("Verbose", verboseHelm);
		setProperty("Behaviors", parentBHVFile);
		setProperty("Domain", "course:0:359:360");
		
		setupBehaviours(vehicleName, startPos);
		setupBehaviourVars();
		
		// The domain has to be set using the maximum speed here
		Double minSpeed = 0.0;
		Double maxSpeed_boxed = maxSpeed;
		Integer numSpeedPoints = NUM_SPEED_DIVISIONS;
		setProperty("Domain", "speed:" + minSpeed.toString() + ":" + maxSpeed_boxed.toString() + ":" + numSpeedPoints.toString());
	}
	
	private void setupBehaviourVars() {

		moosBehaviourInitVals.put("DEPLOY", true);
		moosBehaviourInitVals.put("RETURN", false);
		moosBehaviourInitVals.put("STATION_KEEP", false);
		moosBehaviourInitVals.put("LOITER", true);
		// TODO: this should be conditionally set if an avoidance goal is present
		moosBehaviourInitVals.put("AVOID", true);
				
		MOOSSetModeDetails msd1 = new MOOSSetModeDetails("ACTIVE", "INACTIVE");
		msd1.setProperty("DEPLOY", true);
		setModeProperties.put("ACTIVE", msd1);
		
		MOOSSetModeDetails msd2 = new MOOSSetModeDetails("STATION-KEEPING");
		msd2.setProperty("MODE", "ACTIVE");
		msd2.setProperty("STATION_KEEP", true);
		setModeProperties.put("STATION-KEEPING", msd2);
		
		MOOSSetModeDetails msd3 = new MOOSSetModeDetails("RETURNING");
		msd3.setProperty("MODE", "ACTIVE");
		msd3.setProperty("RETURN", true);
		setModeProperties.put("RETURNING", msd3);
		
		MOOSSetModeDetails msd4 = new MOOSSetModeDetails("LOITERING");
		msd4.setProperty("MODE", "ACTIVE");
		msd4.setProperty("LOITER", true);
		setModeProperties.put("LOITERING", msd4);
	}

	
	private void setupBehaviours(String vehicleName, Point startPos) {
		String startPosAsString = startPos.toString();
		
		double loiterSpeed = startSpeed;
		double loiterRadius = 5.0;
		double loiterNMRadius = 10.0;
		
		double waypointSpeed = startSpeed;
		double waypointRadius = 3.0;
		double waypointNMRadius = 15.0;
		
		addBehaviour(new HelmBehaviourLoiter(this, vehicleName, startPosAsString, loiterSpeed, loiterRadius, loiterNMRadius));
		addBehaviour(new HelmBehaviourAvoidance(this, vehicleName));
		addBehaviour(new HelmBehaviourStationKeep(this));
		addBehaviour(new HelmBehaviourWaypoint(this, vehicleName, startPos, waypointSpeed, waypointRadius, waypointNMRadius));
	}
}
