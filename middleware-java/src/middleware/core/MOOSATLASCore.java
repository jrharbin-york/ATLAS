package middleware.core;

import activemq.portmapping.PortMappings;
import atlasdsl.Mission;
import carsspecific.moos.carsqueue.MOOSEventQueue;
import carsspecific.moos.carsqueue.PShareEventQueue;
import carsspecific.moos.translations.MOOSTranslations;
import middleware.carstranslations.CARSTranslations;

public class MOOSATLASCore extends ATLASCore {
	private int MOOS_QUEUE_CAPACITY = 2000;
	
	// This is used when testing out the pShare event queue forwarding approach over UDP. 
	// Since we are not doing this anymore, it is disabled
	private final boolean USE_PSHARE_EVENT_QUEUE = false;
	
	private PShareEventQueue pShareTest;

	public MOOSATLASCore(Mission mission) {
		super(mission);
		carsOutput = (CARSTranslations) new MOOSTranslations();
		outputToCI = new ActiveMQProducer(PortMappings.portForCI("shoreside"), ActiveMQProducer.QueueOrTopic.TOPIC);
		outputToCI.run();
		carsIncoming = new MOOSEventQueue(this, mission, MOOS_QUEUE_CAPACITY);
		queues.add(carsIncoming);
		
		// TODO: check if the fuzzing engine is active before creating the queue
		if (fuzzEngine != null) {
			if (USE_PSHARE_EVENT_QUEUE) {
				pShareTest = new PShareEventQueue(this, mission, MOOS_QUEUE_CAPACITY);
				queues.add(pShareTest);
			}
		}
	}

	public void runMiddleware() {
		super.runMiddleware();
	}
}