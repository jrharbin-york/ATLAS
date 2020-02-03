package atlascollectiveint.custom;

import atlascollectiveint.api.*;
import atlascollectiveintgenerator.CollectiveIntLog;
import atlassharedclasses.*;

import java.lang.Double;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

class ComputerCIshoreside {

	// The shoreside CI's copy of the robot locations
	private static List<String> robots = new ArrayList<String>();
	private static HashMap<String,Point> robotLocations = new LinkedHashMap<String,Point>();
	private static HashMap<Integer,Integer> detectionCounts = new LinkedHashMap<Integer,Integer>();
	private static Region fullRegion;

	private static final double SWEEP_RADIUS = 50.0;
	
    private static boolean freshDetection(int label) {
    	Integer c = detectionCounts.get(label);
        if (c == null) {
            detectionCounts.put(label,0);
            return true;
        } else {
            detectionCounts.put(label,c+1);
            return false;
        }
    }
    
    private static Map<String,Double> robotDistancesTo(Point loc) {
    	Map<String,Double> res;
    	res = robotLocations.entrySet().stream().
    			collect(Collectors.toMap(
    	            e -> e.getKey(),
    	            e -> loc.distanceTo(e.getValue())));
    	return res;
    }
    
    // The shoreside chooses a robot to use to confirm detections, 
    // excluding the detecting one obviously!
    private static Optional<String> chooseRobotNear(Point loc, String excludeRobot) {
    	// sort them all relative to distances from the location
    	Map<String,Double> dists = robotDistancesTo(loc);
    	Optional<Map.Entry<String, Double>> res = dists.entrySet().stream()
    			.filter(e -> e.getKey() != excludeRobot)
    			.sorted()
    			.findFirst();
    	// TODO: check the sort order here!
    	
    	if (res.isPresent()) {
    		return Optional.of(res.get().getKey());
    	} else {
    		// If no robots are known, it will be empty  
    		return Optional.empty();
    	}    	
    }
    
  public static void setRobotNamesAndRegion() {
	  robots.add("frank");
	  robots.add("gilda");
	  robots.add("henry");
	  robots.add("ella");
	  fullRegion = new Region(0.0,0.0,100.0,100.0);
  }
  
  public static Map<String,Region> staticRegionSplit(Region fullRegion, List<String> robots) {
	  return new HashMap<String,Region>();
	  // TODO: implement region division for the robots
  }
    
  public static void init() {
      // TODO: load the robot definition names - or should this be part of generated code?
	  // for now, hardcode it statically
	  setRobotNamesAndRegion();
	  Map<String,Region> regionAssignments = staticRegionSplit(fullRegion,robots);
	  for (Map.Entry<String, Region> e : regionAssignments.entrySet()) {
		  String robot = e.getKey();
		  Region region = e.getValue();
		  Behaviours.setRegion(robot, region);
	  }
	  
      // divide up the rect region amongst the robots
      // set their original behaviour sweeps on a stack?
	  // look in the lisp version to see how this is done
  }

  public static void SONARDetectionHook(SonarDetection detection, String robotName) {
	  // On a detection, if the detection is the first time...
	  // Send a second robot in to confirm
	  // Need to scan the positions to find the best choice

	  // TODO: Need to move Point into atlassharedclasses
	  // and change all PointCI into Point
	  Point loc = detection.detectionLocation;
	  int label = detection.objectID;

    // Count the detections at this location
    if (freshDetection(label)) {
        // choose robot to do the confirmation
        Optional<String> rName_o = chooseRobotNear(loc, robotName);
        if (rName_o.isPresent()) {
        	String rName = rName_o.get();
            Behaviours.setRegionAroundPoint(rName, loc, SWEEP_RADIUS);
            // need to send this robot back to its original action after some time...
            // TODO: use a timer here to pop from the robot action stack
            // will need to track the robot active regions on some sort of per-robot stack?
        } else {
        	CollectiveIntLog.logCI("No other robots defined to confirm the detection");
        }
    }
  }

  public static void GPS_POSITIONDetectionHook(Double x, Double y, String robotName) {
	  // Update the robot position notification
	  // TODO: Need the robotname in this hook in the code generator!
	  robotLocations.put(robotName, new Point(x,y));
  }
}