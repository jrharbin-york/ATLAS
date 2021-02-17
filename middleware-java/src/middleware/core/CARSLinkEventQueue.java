package middleware.core;

import java.util.List;
import java.util.Optional;

import atlassharedclasses.ATLASObjectMapper;
import fuzzingengine.*;
import fuzzingengine.operations.*;
import middleware.carstranslations.CARSTranslations;

public abstract class CARSLinkEventQueue<E> extends ATLASEventQueue<E> implements Runnable {
	private static final long serialVersionUID = 1L;
	protected ATLASObjectMapper atlasOMapper;
	protected ATLASCore core;
	protected FuzzingEngine fuzzingEngine;
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
	
	public void handleEvent(E event) {
		double time = core.getTime();
		boolean handleSpecifically = true;
		// Do the fuzzing specific parts of a CARS message
		// If the message is on the list to fuzz... alter it
		List<FuzzingOperation> ops = fuzzingEngine.shouldFuzzCARSEvent(event, time);
		E modifiedEvent = event;
		Optional<String> reflectBackAsName = fuzzingEngine.shouldReflectBackToCARS(event);
		
		for (FuzzingOperation op : ops) {
			modifiedEvent = fuzzingEngine.fuzzTransformEvent(modifiedEvent, op);
		}
			
		if (reflectBackAsName.isPresent()) {
			// Potentially reflect it back to the low-level CARS?
			String reflectBackName = reflectBackAsName.get();
			if (modifiedEvent instanceof CARSVariableUpdate) {
				CARSVariableUpdate varUpdate = (CARSVariableUpdate)modifiedEvent;  
				cTrans.sendBackEvent(varUpdate, reflectBackName);
			}
		}
		
		// Now the CARS specific parts of handling
		if (handleSpecifically) {
			handleEventSpecifically(modifiedEvent);
		}
	}
}
