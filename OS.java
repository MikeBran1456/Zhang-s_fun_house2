import java.io.BufferedReader;
import java.io.File;
import java.io.*;
import java.util.*; 

class OS {
	public CPU cpu;
	public IOdevice io;
	public static boolean isCPUAvailable = true; // initially this had no value
	// public ProcessTable process_Table;
	public static ArrayList<PCB> New_Queue;
	public static ArrayList<PCB> Ready_Queue;
	public static ArrayList<PCB> Wait_Queue;
	public static ArrayList<PCB> Terminated_Queue;

	// Read the txt input file, for each line, create a process and record its
	// arrival
	// time
	// Put each process in New_Q queue initially then put them in Ready_Q
	// Always check whether the CPU is idle or not; if yes, use your scheduler
	// algorithm to select a process from the Ready_Queue for CPU execution\
	// According to the return value of CPU execute(), put the process into the
	// corresponding queue.
	// Record the time of every operation for computing your latency and
	// response
	public static PCB CreateProcess(String file) throws NumberFormatException, IOException
	{
		/* 
		 * Reads the file 
		 * separates the four fields in the text document
		 * takes the IOBurst and turns it into an int array
		 */
		String line; 
		int ID= 0 ; 
		int arrival=0;
		int priority=0;
		int [] IOBurstArray = new int[10];
		PCB myPCB = new PCB(ID,arrival, priority,IOBurstArray); 
		BufferedReader read = new BufferedReader(new FileReader (file));
		
		while ((line = read.readLine()) != null)
		{	
			String[] words = line.split("," );
			ID = Integer.parseInt(words[0]); 
			arrival = Integer.parseInt(words[1]); 
			priority = Integer.parseInt(words[2]); 
			String IOburst = words[3];
			char[] IOArray = IOburst.toCharArray();  
			for (int i=0; i<IOburst.length(); i++)
			{
				
				IOBurstArray[i] = Character.getNumericValue(IOArray[i]); 
				
			}
			
			
			/*
			 * Test to make sure that the line parsing did what it is supposed to do
			 *
			System.out.println("the ID is: " + ID);
			System.out.println("The arrival is :" + arrival);
			System.out.println("the Priority is : " + priority);
			System.out.println("The IOBurst is : "+ Arrays.toString(IOBurstArray));
			*/
		}
		if ((line = read.readLine()) != null)
		{
			New_Queue.add(myPCB); 
		}
		return myPCB; 
	}
	
	public static void FCFS(PCB process) {
		int timeslice = 99999;
		boolean done = false;
		boolean firstIOCheck = true;
		CPU cpu = new CPU(timeslice);

		while (!done) {
			String state = "New";
			switch (state) {
			case "New":// Process being created
				System.out.println("Creating process...");
				state = "Ready";
				process.setProcessState("Ready");

			case "Ready":// Process is ready to be executed
				System.out.println("Process ready to execute...");
				if (isCPUAvailable == true) {
					state = "Running";
					process.setProcessState("Running");
				}
				System.out.println("CPU is not available, please wait...");

			case "Running":// Process is now being executed
				System.out.println("Executing process...");
				isCPUAvailable = false;
				CPU.BubbleSort();
				Wait_Queue.add(Ready_Queue.get(0));
				if (process.getNextInstruction() == process.getBurstSequence().length){
					Wait_Queue.remove(0);
					state = "Terminated";
					process.setProcessState("Terminated");
					
				}
				state = "Waiting";
				process.setProcessState("Waiting");
				process.advanceInstruction();
				
			case "Waiting":// Process is waiting on I/O
				System.out.println("Waiting on I/O");
				IOdevice.BubbleSort();
				if(firstIOCheck == true){
					process.setProcessIOComplete();
					firstIOCheck = false;
				}
				state = "Running";
				process.setProcessState("Running");
				process.advanceInstruction();

			case "Terminated":// Process has finished executing
				Terminated_Queue.add(Ready_Queue.get(0));
				process.setProcessFinished();//Sets the finish time
				Ready_Queue.remove(0);
				if (Ready_Queue == null) {
					done = true;// If there are no more processes to execute its
								// finished
				}
				System.out.println("Finished executing");
				state = "Ready";
				process.setProcessState("Ready");
				break;
			}
		}
		System.out.println("All processes have finished executing");
	}

	public static void RoundRobin(PCB process) {
		int timeslice = 10;
		boolean done = false;
		boolean firstIOCheck = true;
		CPU cpu = new CPU(timeslice);

		while (!done) {
			String state = "New";
			switch (state) {
			case "New":// Process being created
				
				System.out.println("Creating process...");
				state = "Ready";
				process.setProcessState("Ready");

			case "Ready":// Process is ready to be executed
				System.out.println("Process ready to execute...");
				if (isCPUAvailable == true) {
					state = "Running";
					process.setProcessState("Running");
				}
				System.out.println("CPU is not available, please wait...");

			case "Running":// Process is now being executed
				int n = 0;
				System.out.println("Executing process...");
				//if timeslice < process.getNextInstruction
				if(timeslice < process.getBurstSequence()[process.getNextInstruction()]){
					process.getBurstSequence()[process.getNextInstruction()] = process.getBurstSequence()[process.getNextInstruction()] - timeslice;
					Circle(Ready_Queue);
					state = "New";
				}
				Wait_Queue.add(Ready_Queue.get(0));
				if (process.getNextInstruction() == process.getBurstSequence().length) {// Need to circle qu
					Wait_Queue.remove(0);
					state = "Terminated";
					process.setProcessState("Terminated");
				}
				state = "Waiting";
				process.setProcessState("Waiting");
				process.advanceInstruction();

			case "Waiting":// Process is waiting on I/O
				System.out.println("Waiting on I/O");
				IOdevice.BubbleSort();
				if(firstIOCheck == true){
					process.setProcessIOComplete();
					firstIOCheck = false;
				}
				state = "Running";
				process.setProcessState("Running");
				process.advanceInstruction();

			case "Terminated":// Process has finished executing
				Terminated_Queue.add(Ready_Queue.get(0));
				process.setProcessFinished();
				Ready_Queue.remove(0);
				if (Ready_Queue == null) {
					done = true;// If there are no more processes to execute its
								// finished
				}
				System.out.println("Finished executing");
				state = "Ready";
				process.setProcessState("Ready");
				break;
			}
		}
		System.out.println("All processes have finished executing");
	}

	public static void staticPriority(PCB process) {
		int timeslice = 10;// This may need to change if we want to make
							// the lower priority processes preemptive
		boolean done = false;
		boolean firstIOCheck = true;
		CPU cpu = new CPU(timeslice);

		while (!done) {
			String state = "New";
			switch (state) {
			case "New":// Process being created
				System.out.println("Creating process...");
				state = "Ready";
				process.setProcessState("Ready");

			case "Ready":// Process is ready to be executed
				System.out.println("Process ready to execute...");
				if (isCPUAvailable == true) {
					state = "Running";
					process.setProcessState("Running");
				}
				System.out.println("CPU is not available, please wait...");

			case "Running":// Process is now being executed
				System.out.println("Executing process...");
				CPU.BubbleSort();
				Wait_Queue.add(Ready_Queue.get(0));
				if (process.getNextInstruction() == process.getBurstSequence().length) {
					Wait_Queue.remove(0);
					state = "Terminated";
					process.setProcessState("Terminated");
				}
				state = "Waiting";
				process.setProcessState("Waiting");
				process.advanceInstruction();

			case "Waiting":// Process is waiting on I/O
				System.out.println("Waiting on I/O");
				IOdevice.BubbleSort();
				if(firstIOCheck == true){
					process.setProcessIOComplete();
					firstIOCheck = false;
				}
				state = "Running";
				process.setProcessState("Running");
				process.advanceInstruction();

			case "Terminated":// Process has finished executing
				Terminated_Queue.add(Ready_Queue.get(0));
				process.setProcessFinished();
				Ready_Queue.remove(0);
				if (Ready_Queue == null) {
					done = true;// If there are no more processes to execute its
								// finished
				}
				System.out.println("Finished executing");
				state = "Ready";
				process.setProcessState("Ready");
				break;
			}
		}
		System.out.println("All processes have finished executing");
	}

	public static void Circle(ArrayList<PCB> Ready_Queue) {
		PCB head = Ready_Queue.get(0);
		Ready_Queue.remove(0);
		Ready_Queue.add(head);
	}
	//this calculates average for a double array
		public static double avg(double[] c){
			//create variables for averaging
			double total = 0;
			int members = c.length;
			double avg;
			//calculate Average of input variable
			for (int i = 0; i < members; i++){
				total = total + c[i];
			}
			avg = total/members;
			//return average
			System.out.println("Average of run times: " + avg + " ms");
			return avg;
		}
		//this calculates standard deviation for a double array
		public static double standardDev(double[] c){
			//create all variables needed for calculating standard Deviation
			double standardDev = 0.0;
			int arrayLength = c.length;
			double valuesAvg = avg(c);
			double number = 0.0;
			double sampleVariance = 0.0;
			//pull apart formula into summation, division, and square root
			for (int i = 0; i < arrayLength; i++){
				number = number +  (c[i] - valuesAvg)*(c[i] - valuesAvg);
			}
			sampleVariance = number/(arrayLength-1);
			standardDev = Math.sqrt(sampleVariance);
			//return standard deviation
			System.out.println("Standard Deviation of run times: " + standardDev + " ms");
			return standardDev;
		}
		//this method calculates, prints, and returns the standard deviation of the latency times for all finished processes
		public static double getLatencyTime(){
			int size = Terminated_Queue.size();
			double[] latTimes = new double[size];
			for (int i = 0; i < size; i++){
				latTimes[i] = (double)Terminated_Queue.get(i).getLatency();
			}
			double latencyDev = standardDev(latTimes);
			return latencyDev;
		}
		//this method calculates, prints, and returns the standard deviation of the response times for all finished processes
		public static double getResponseTime(){
			int size = Terminated_Queue.size();
			double[] resTimes = new double[size];
			for (int i = 0; i < size; i++){
				resTimes[i] = (double)Terminated_Queue.get(i).getResponseTime();
			}
			double responseDev = standardDev(resTimes);
			return responseDev;
		}
		public static void fillReady(ArrayList<PCB> New_Queue){
			while (New_Queue.get(0) != null) {
				Ready_Queue.add(New_Queue.get(0));// Moves all processes
													// from New_Queue to
													// Ready_Queue
				New_Queue.remove(0);
			}
		}
		public static void priorityFillReady(ArrayList<PCB> New_Queue){
			boolean sorted = false;
			int n = New_Queue.size();
	        PCB temp = null;
	        while (!sorted){
	    	sorted = true;
		        for (int i = 0; i < n; i++) {
		            for (int j = 1; j < (n - i); j++) {
		                if (New_Queue.get(j-1).getPriority() < New_Queue.get(j).getPriority()) {
		                	sorted = false;
		                    temp = New_Queue.get(j - 1);
		                    New_Queue.set(j - 1, New_Queue.get(j));
		                    New_Queue.set(j,temp);
		                }
		            }
		        }
	        }
		}
		/*
		 *\\\\\\\\\\\\\\\
		 *\\MAIN METHOD\\
		 * \\\\\\\\\\\\\\\
		*/
		 public static void main(String[] args)throws IOException{
			 Scanner myScanner = new Scanner(System.in);
			 System.out.println("Enter the filename: ");
			 String filename = myScanner.nextLine();
			 CreateProcess(filename);
			 System.out.println("Which scheduling algorithm do you want to use: ");
			 System.out.println("1.) First Come First Serve \n2.) Round-Robin \n3.) Static Priority");
			 if(myScanner.nextInt() == 1){
				 fillReady(New_Queue);
				 for(int i = 0; i < Ready_Queue.size(); i++){
					FCFS(Ready_Queue.get(i)); 
				 }
				 //post terminate calculations
				 getLatencyTime();
				 getResponseTime();
				 
			 }
			 else if(myScanner.nextInt() == 2){
				 priorityFillReady(New_Queue);
				 for(int i = 0; i < Ready_Queue.size(); i++){
					FCFS(Ready_Queue.get(i));
				 }
				 //post terminate calculations
				 getLatencyTime();
				 getResponseTime();
			 }
			 else if(myScanner.nextInt() == 3){
				 fillReady(New_Queue);
				 for(int i = 0; i < Ready_Queue.size(); i++){
					FCFS(Ready_Queue.get(i)); 
				 }
				 //post terminate calculations
				 getLatencyTime();
				 getResponseTime();
			 }
			 else{
				 System.out.println("Invalid Selection: ");
			 }
		 }
}