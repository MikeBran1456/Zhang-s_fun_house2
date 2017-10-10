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
	public PCB (int processId, int arrivalTime, int priority, int[] code){
		this.ProcessID = processId;
		this.ArrivalTime = arrivalTime;
		this.Priority = priority;
		this.Code = code;
		this.State = "New";
		this.PositionOfNextInstructionToExecute = 0;
		this.ProcessCreated = System.currentTimeMillis();
	}
	//helpful return methods (can add more later)
	public String getProcessState(){
		return this.State;
	}
	
	public void setProcessState(String state){
		this.State = state;
	}
	
	//time calcs
	public void setProcessFinished(){
		this.ProcessFinished = System.currentTimeMillis();
	}
	
	//time calcs
	public void setProcessIOComplete(){
		this.ProcessIOComplete = System.currentTimeMillis();
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
	
	public int[] getBurstSequence(){
		return this.Code;
	}
	
	//go to next instruction
	public void advanceInstruction(){
		this.PositionOfNextInstructionToExecute++;
	}
	
	//TODO make more methods for acquiring data
}
