package middleware.core;

import activemq.portmapping.PortMappings;
import atlasdsl.Mission;

public class MOOSATLASCore extends ATLASCore {
	
	private int MOOS_QUEUE_CAPACITY = 100;

	public MOOSATLASCore(Mission mission) {
		super(mission);
		outputToCI = new ActiveMQProducer(PortMappings.portForCI("shoreside"), ActiveMQProducer.QueueOrTopic.TOPIC);
		outputToCI.run();
		carsIncoming = new MOOSEventQueue(this, mission, MOOS_QUEUE_CAPACITY);
		queues.add(carsIncoming);
	}
	
	public void runMiddleware() {
		super.runMiddleware();

	}
}