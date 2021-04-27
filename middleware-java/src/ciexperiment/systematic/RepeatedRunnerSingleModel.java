package ciexperiment.systematic;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;

import atlasdsl.Mission;
import atlasdsl.loader.DSLLoadFailed;
import atlasdsl.loader.DSLLoader;
import atlasdsl.loader.GeneratedDSLLoader;
import exptrunner.metrics.Metrics;
import exptrunner.metrics.MetricsProcessing;
import exptrunner.metrics.MetricsProcessing.MetricStateKeys;
import ciexperiment.runner.RunExperiment;
import faultgen.InvalidFaultFormat;

public class RepeatedRunnerSingleModel {
	private static final String EMF_OUTPUT_PATH = "/home/atlas/atlas/atlas-middleware/middleware-java/src/atlasdsl/loader/GeneratedDSLLoader.java";
	
	public static ModelsTransformer modelTransformer = new ModelsTransformer();
	public static ModelEGLExecutor modelExecutor = new ModelEGLExecutor();

	public static void runCIRepeatedModel(ExptParams eparams, String exptTag, boolean actuallyRun, double timeLimit, List<String> ciOptions) throws InterruptedException, IOException {
		// The core logic for the loop
		while (!eparams.completed()) {
			eparams.printState();
			// Modify the mission from the parameters - and load the modified mission file here
			try {
				Optional<String> nextFile_o = eparams.getNextFileName();
				if (nextFile_o.isPresent()) {
					String file = nextFile_o.get();
					
					for (String ciOption : ciOptions) {
						System.out.println("Running experiments for model file " + file + " and CI class " + ciOption);
						// Mission loader - recompile it
						modelExecutor.executeEGL(file, EMF_OUTPUT_PATH);
						System.out.println("Recompiling loader");
						RunExperiment.compileLoader();
						
						// The newly recompiled loader is not used by this process, only by subprocesses
						// So they see all the changes to the mission
						Thread.sleep(3000);
						System.out.println("Loader recompilation done");
						RunExperiment.doExperimentFromFile(exptTag, actuallyRun, timeLimit, ciOption);
						eparams.logResults("/home/jharbin/academic/atlas/atlas-middleware/expt-working/logs", file, ciOption);
					}
					
					eparams.advance();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EolModelLoadingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EglRuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			Thread.sleep(1000);
		}
	}


	public static void runCIExperimentSingleModel(String sourceModelFile, List<Metrics> metricList, String fileTag, List<String> ciOptions, int count) {
		DSLLoader loader = new GeneratedDSLLoader();

		try {
			Mission baseMission = loader.loadMission();
			double runTime = baseMission.getEndTime();
			String fileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
			FileWriter tempLog = new FileWriter("tempLog-" + fileName + ".res");
			MetricsProcessing mp = new MetricsProcessing(metricList, tempLog);
			mp.setMetricState(MetricStateKeys.MISSION_END_TIME, baseMission.getEndTime());
			String resFileName = "ciexpt-"+fileTag+".res";
			System.out.println("Starting experiment set - repeated run for " + sourceModelFile);
			ExptParams ep = new RunSameModel(mp, runTime, sourceModelFile, resFileName, count);
			runCIRepeatedModel(ep, resFileName, true, runTime, ciOptions);
			
			System.out.println("Done");
		} catch (DSLLoadFailed e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void expt_caseStudy1() {
		List<Metrics> l = new ArrayList<Metrics>();
		l.add(Metrics.PURE_MISSED_DETECTIONS);
		//l.add(Metrics.DETECTION_COMPLETION_TIME);
		l.add(Metrics.WORST_CASE_WAYPOINT_COMPLETION_FROM_CI);
		String sourceModelFile = "experiment-models/casestudy1/mission-basis.model";
		String standardCI = "atlascollectiveint.expt.casestudy1.ComputerCIshoreside_standard"; 
		String advancedCI = "atlascollectiveint.expt.casestudy1.ComputerCIshoreside_advanced"; 
		
		ArrayList<String> ciOptions = new ArrayList<String>();
		ciOptions.add(standardCI);
		ciOptions.add(advancedCI);
		runCIExperimentSingleModel(sourceModelFile, l, "casestudy1", ciOptions, 1);
	}
	
	public static void expt_test() {
		List<Metrics> l = new ArrayList<Metrics>();
		l.add(Metrics.OBSTACLE_AVOIDANCE_METRIC);
		l.add(Metrics.AVOIDANCE_METRIC);
		l.add(Metrics.TOTAL_ENERGY_AT_END);
		l.add(Metrics.MEAN_ENERGY_AT_END);
		l.add(Metrics.TOTAL_FINAL_DISTANCE_AT_END);
		l.add(Metrics.MEAN_FINAL_DISTANCE_AT_END);
		l.add(Metrics.TOTAL_WAYPOINT_SWITCH_COUNT);

		String standardCI = "atlascollectiveint.expt.casestudy2.ComputerCIshoreside_standard";
		String energyTrackingCI = "atlascollectiveint.expt.casestudy2.ComputerCIshoreside_energytracking";
		
		ArrayList<String> ciOptions = new ArrayList<String>();
		ciOptions.add(standardCI);
		ciOptions.add(energyTrackingCI);
		// Standard is threshold of 750 mAh for return
		runCIExperimentSingleModel("experiment-models/casestudy2/mission-basis.model", l, "casestudy2-threshold750", ciOptions, 1);
		runCIExperimentSingleModel("experiment-models/casestudy2/mission-basis-threshold500.model", l, "casestudy2-threshold500", ciOptions, 1);
		runCIExperimentSingleModel("experiment-models/casestudy2/mission-basis-threshold250.model", l, "casestudy2-threshold250", ciOptions, 1);
		
	}
	
	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		// TODO: be sure to run the genmission run configuration for the particular mission
		// otherwise the runtime will not be correct
		//expt_caseStudy1();
		expt_test();
	}
}
