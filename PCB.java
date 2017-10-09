class PCB {
	//parts of a process
	int ProcessID;
	int ArrivalTime;
	String State;
	int[] Code;
	//not sure what this one does
	int PositionOfNextInstructionToExecute;
	PCB Pcb_data;
	//priority integer
	int Priority;
	//variables for time calculation later
	long ProcessCreated;
	long ProcessFinished;
	long ProcessIOComplete;
	long Latency;
	long Response;
	//contructor
	public PCB (int processId, int arrivalTime, String state, int[] code){
		this.ProcessID = processId;
		this.ArrivalTime = arrivalTime;
		this.State = state;
		this.Code = code;
	}
	//helpful return method (can add more later)
	public String getProcessState(){
		return this.State;
	}
	
	public void setProcessState(String state){
		this.State = state;
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
	//TODO make more methods for acquiring data
}
