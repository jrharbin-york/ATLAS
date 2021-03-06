package carsspecific.moos.moosmapping;

public class ComputerCommunity extends MOOSCommunity {
	public ComputerCommunity(MOOSSimulation sim, String computerName, int pSharePortBase, int pShareOrigPort) {
		super(sim,computerName);
		addProcess(new PMarineViewerProcess(this));
		addProcess(new PHostinfoProcess(this));
		addProcess(new UFldShoreBrokerProcess(this));
		
		PShareProcess pshare = new PShareProcess(this, pSharePortBase, pShareOrigPort, dbPortOffset);
		pshare.addShoresideInput();
		addProcess(pshare);
		
		addProcess(new UFldNodeCommsProcess(this));
	}
}
