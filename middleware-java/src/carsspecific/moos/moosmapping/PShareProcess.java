package carsspecific.moos.moosmapping;

public class PShareProcess extends MOOSProcess {

	// TODO: this currently carries the assumption that the shoreside is the
	// first generated process, on port offset zero, this should be looked up via a 
	// mapping in the MOOS community
	public PShareProcess(MOOSCommunity parent, int pSharePortBase, int pShareOrigBase, int portOffset) {
		super("pShare", parent);
		int inputPort = pShareOrigBase + portOffset;
		setProperty("input","route =  localhost:" + Integer.toString(inputPort));
		setProperty("output","src_name=Y, dest_name=B, route=localhost:" + Integer.toString(pSharePortBase));
	}
	
	public void addShoresideInput() {
		setProperty("input","route = localhost:9200");
	}
}