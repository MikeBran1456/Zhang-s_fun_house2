
/*
 * This class is the image of a particular process
 * It is incomplete as of now.
 */

class ProcessImage {
	//instance of PCB for data of a process
	PCB Pcb_data;
	//priority integer for later (not sure yet)
	int Priority;
	//variables for time calculation later
	long ProcessCreated;
	long ProcessFinished;
	long ProcessIOComplete;
	long Latency;
	long Response;
	//constructor
	public ProcessImage(int id, int arrivalTime, int priority, int[] IOSeq) {
		this.Pcb_data = new PCB(id, arrivalTime, "New", IOSeq);
		this.Priority = priority;
		this.ProcessCreated = System.currentTimeMillis();
	}
	//time calcs
	public void setProcessFinished(long time){
		this.ProcessFinished = time;
	}
	//time calcs
	public void setProcessIOComplete(long time){
		this.ProcessIOComplete = time;
	}
	//time calcs
	public int getLatency(){
		int TotalLatency = 0;
		TotalLatency = (int)(this.ProcessCreated-this.ProcessFinished);
		return TotalLatency;
	}
	//time calcs
	public int getResponseTime(){
		int TotalResponseTime = 0;
		TotalResponseTime = (int)(this.ProcessCreated - this.ProcessIOComplete);
		return TotalResponseTime;
	}
	//TODO possibly add methods to calculate various features.
}
