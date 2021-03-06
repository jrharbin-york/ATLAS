package exptrunner.systematic;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import org.uma.jmetal.util.errorchecking.JMetalException;

import atlasdsl.Mission;
import atlasdsl.loader.DSLLoadFailed;
import atlasdsl.loader.DSLLoader;
import atlasdsl.loader.GeneratedDSLLoader;
import exptrunner.metrics.Metrics;
import exptrunner.metrics.MetricsProcessing;
import exptrunner.runner.RunExperiment;
import faultgen.InvalidFaultFormat;

public class RepeatedRunner {

	public static void runFixedCSVExpt(Mission mission, String fixedCSVFuzzingFile, ExptParams eparams, String exptTag, boolean actuallyRun, double timeLimit) throws InterruptedException, IOException {
		while (!eparams.completed()) {
			eparams.printState();
			RunExperiment.doExperimentFromFile(mission, exptTag, fixedCSVFuzzingFile, actuallyRun, timeLimit);
			eparams.logResults("/home/jharbin/academic/atlas/atlas-middleware/expt-working/logs");
			eparams.advance();
		}
	}
	
	public static void runRepeatedFuzzing(List<Metrics> metricList, String faultFileName, String fileTag, int runCount) {
		DSLLoader loader = new GeneratedDSLLoader();
		Mission mission;

		try {
			mission = loader.loadMission();
			double runTime = mission.getEndTime();
			String fileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
			FileWriter tempLog = new FileWriter("tempLog-" + fileName + ".res");
			MetricsProcessing mp = new MetricsProcessing(metricList, tempLog);
			String resFileName = "fuzzingexpt-"+fileTag+".res";
			ExptParams ep = new RepeatSingleExpt(mp, runTime, runCount, mission, faultFileName, resFileName);
			runFixedCSVExpt(mission, faultFileName, ep, resFileName, true, runTime);
			System.out.println("Done");
		} catch (DSLLoadFailed e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvalidFaultFormat e) {
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args) throws JMetalException, FileNotFoundException, InterruptedException {
//		List<Metrics> l = new ArrayList<Metrics>();
//		l.add(Metrics.PURE_MISSED_DETECTIONS);
//		l.add(Metrics.OUTSIDE_REGION_COUNT);
//		runRepeatedFuzzing(l, "/home/atlas/academic/atlas/atlas-middleware/bash-scripts/jmetal-expts/res-keep/test-fif/null.fif", "rudderfault", 200);
//	}
}
