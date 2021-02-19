package middleware.core;

import java.util.List;
import java.util.Optional;

import atlassharedclasses.ATLASObjectMapper;
import fuzzingengine.*;
import fuzzingengine.operations.*;
import middleware.carstranslations.CARSTranslations;

public abstract class CARSLinkEventQueue<E> extends ATLASEventQueue<E> implements Runnable {
	private static final long serialVersionUID = 1L;
	private static final boolean CHECK_FOR_QUEUE_EVENTS_BEFORE_ANY = true;
	protected ATLASObjectMapper atlasOMapper;
	protected ATLASCore core;
	protected FuzzingEngine<E> fuzzingEngine;
	protected CARSTranslations cTrans;
	
	public CARSLinkEventQueue(ATLASCore core, int capacity, char progressChar) {
		super(core,capacity,progressChar);
		this.core = core;
		progressChar = '.';
		this.progressChar = progressChar;
		this.fuzzingEngine = core.getFuzzingEngine();
		this.cTrans = core.getCARSTranslationOutput();
	}
	
	public abstract void handleEventSpecifically(E event);
	
	public void checkForQueuedEvents() {
		double time = core.getTime();
		List<E> delayedEvents = fuzzingEngine.pollEventsReady(time);
		for (E e : delayedEvents) {
			handleEventSpecifically(e);
		}
	}
	
	public void handleEvent(E event) {
		if (CHECK_FOR_QUEUE_EVENTS_BEFORE_ANY) {
			checkForQueuedEvents();
		}
		
		double time = core.getTime();
		
		// TODO: Before handling custom events, check for pending events that are now due!

		// Do the fuzzing specific parts of a CARS message
		// If the message is on the list to fuzz... alter it
		List<FuzzingOperation> ops = fuzzingEngine.shouldFuzzCARSEvent(event, time);
		Optional<E> modifiedEvent_o = Optional.of(event);
		Optional<String> reflectBackAsName = fuzzingEngine.shouldReflectBackToCARS(event);
		
		// Need to determine if ANY event in the chain requires enquining
		// For now, just assume false
		boolean shouldEnqueue = false;
		double enqueueTime = 0.0;
		
		// Make the transformations on the event here
		for (FuzzingOperation op : ops) {
			if (modifiedEvent_o.isPresent()) {
				modifiedEvent_o = fuzzingEngine.fuzzTransformEvent(modifiedEvent_o, op);
				shouldEnqueue = shouldEnqueue || op.shouldEnqueue();
				enqueueTime += op.enqueueTime();
			}
		}
		
		// How to handle the case where it should be enqueued?
			
		// The event may have been destroyed or deleted by the chain of fuzzing operations. If 
		// so, it will be ignored.
		if (modifiedEvent_o.isPresent()) {
			E modifiedEvent = modifiedEvent_o.get();
			if (reflectBackAsName.isPresent()) {
				// Potentially reflect it back to the CARS
				String reflectBackName = reflectBackAsName.get();
				if (modifiedEvent instanceof CARSVariableUpdate) {
					CARSVariableUpdate varUpdate = (CARSVariableUpdate)modifiedEvent;  
					cTrans.sendBackEvent(varUpdate, reflectBackName);
				}
			}
			
			// How to handle precise future delays?
			if (shouldEnqueue) {
				double futureTime = time + enqueueTime;
				fuzzingEngine.addToQueue(modifiedEvent, futureTime);
			} else {
				handleEventSpecifically(modifiedEvent);
			}
		}
	}
}
