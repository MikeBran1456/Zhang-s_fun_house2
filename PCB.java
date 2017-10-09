class PCB {
	//parts of a process
	int ProcessID;
	int ArrivalTime;
	String State;
	int[] Code;
	//position in burst sequence
	int PositionOfNextInstructionToExecute;
	//priority integer
	int Priority;
	//variables for time calculation later
	long ProcessCreated;
	long ProcessFinished;
	long ProcessIOComplete;
	long Latency;
	long Response;
	//Constructor
	public PCB (int processId, int arrivalTime, int[] code, int nextInstruction){
		this.ProcessID = processId;
		this.ArrivalTime = arrivalTime;
		this.State = "New";
		this.Code = code;
		this.PositionOfNextInstructionToExecute = nextInstruction;
	}
	//helpful return methods (can add more later)
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
	
	//next instruction
	public int getNextInstruction(){
		return this.PositionOfNextInstructionToExecute;
	}
	
	public int[] getBurstSeq(){
		return this.Code;
	}
	
	//go to next instruction
	public void advanceInstruction(){
		this.PositionOfNextInstructionToExecute++;
	}
	
	//TODO make more methods for acquiring data
}
