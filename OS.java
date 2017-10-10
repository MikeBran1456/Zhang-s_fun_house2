import java.io.BufferedReader;
import java.io.File;
import java.io.*;
import java.util.*; 

class OS {
	public CPU cpu;
	public IOdevice io;
	public boolean isCPUAvailable = true; // initially this had no value
	// public ProcessTable process_Table;
	public ArrayList<PCB> New_Queue;
	public ArrayList<PCB> Ready_Queue;
	public ArrayList<PCB> Wait_Queue;
	public ArrayList<PCB> Terminated_Queue;

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
	public void CreateProcess() throws NumberFormatException, IOException
	{
		/* 
		 * Reads the file 
		 * separates the four fields in the text document
		 * takes the IOBurst and turns it into an array
		 */
		String line; 
		BufferedReader read = new BufferedReader(new FileReader ("file"));
		while ((line = read.readLine()) != null)
		{
			String[] words = line.split("," );
			int ID = Integer.parseInt(words[0]); 
			int arrival = Integer.parseInt(words[1]); 
			int priority = Integer.parseInt(words[2]);  
			String IOburst = words[3];
			char[] IOArray = IOburst.toCharArray(); 
			int [] IOBurstArray = null; 
			for (int i=0; i<IOburst.length(); i++)
			{
				
				IOBurstArray[i] = Character.getNumericValue(IOArray[i]); 
			}
			/*TODO split IOburst into integers and fill 
			*an array and place as final argument in PCB constructor*/
			PCB myPCB = new PCB(ID, arrival, priority, IOBurstArray);
		}
		
	}
	
	public void FCFS(PCB process) {
		int timeslice = 99999;
		boolean done = false;
		CPU cpu = new CPU(timeslice);

		while (!done) {
			String state = "New";
			switch (state) {
			case "New":// Process being created
				while (New_Queue.get(0) != null) {
					Ready_Queue.add(New_Queue.get(0));// Moves all processes
														// from new queue to
														// ready queue
					New_Queue.remove(0);
				}

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
				if (process.getBurstSequence() == null) {
					Wait_Queue.remove(0);
					state = "Terminated";
					process.setProcessState("Terminated");
					
				}
				state = "Waiting";
				process.setProcessState("Waiting");

			case "Waiting":// Process is waiting on I/O
				System.out.println("Waiting on I/O");
				IOdevice.BubbleSort();
				state = "Running";
				process.setProcessState("Running");

			case "Terminated":// Process has finished executing
				Terminated_Queue.add(Ready_Queue.get(0));
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

	public void RoundRobin(PCB process) {
		int timeslice = 10;
		boolean done = false;
		CPU cpu = new CPU(timeslice);

		while (!done) {
			String state = "New";
			switch (state) {
			case "New":// Process being created
				while (New_Queue.get(0) != null) {
					Ready_Queue.add(New_Queue.get(0));// Moves all processes
														// from New_Queue to
														// Ready_Queue
					New_Queue.remove(0);
				}
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
				if (process.getBurstSequence() == null) {// Need to circle queue
															// if timeslice is
															// surpasses
					Wait_Queue.remove(0);
					state = "Terminated";
					process.setProcessState("Terminated");
				}
				state = "Waiting";
				process.setProcessState("Waiting");

			case "Waiting":// Process is waiting on I/O
				System.out.println("Waiting on I/O");
				IOdevice.BubbleSort();
				state = "Running";
				process.setProcessState("Running");

			case "Terminated":// Process has finished executing
				Terminated_Queue.add(Ready_Queue.get(0));
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

	public void staticPriority(PCB process) {
		int timeslice = 10;// This may need to change if we want to make
							// the lower priority processes preemptive
		boolean done = false;
		CPU cpu = new CPU(timeslice);

		while (!done) {
			String state = "New";
			switch (state) {
			case "New":// Process being created
				while (New_Queue.get(0) != null) {
					Ready_Queue.add(New_Queue.get(0));// Moves all processes
														// from new queue to
														// ready queue
					New_Queue.remove(0);// This needs to be sorted by priority
				}

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
				if (process.getBurstSequence() == null) {
					Wait_Queue.remove(0);
					state = "Terminated";
					process.setProcessState("Terminated");
				}
				state = "Waiting";
				process.setProcessState("Waiting");

			case "Waiting":// Process is waiting on I/O
				System.out.println("Waiting on I/O");
				IOdevice.BubbleSort();
				state = "Running";
				process.setProcessState("Running");

			case "Terminated":// Process has finished executing
				Terminated_Queue.add(Ready_Queue.get(0));
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

	public void Circle(ArrayList<PCB> Ready_Queue) {
		PCB head = Ready_Queue.get(0);
		Ready_Queue.remove(0);
		Ready_Queue.add(head);
	}
	//this calculates average for a double array
	public double avg(double[] c){
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
	public double standardDev(double[] c){
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
	public double getLatencyTime(){
		int size = Terminated_Queue.size();
		double[] latTimes = new double[size];
		for (int i = 0; i < size; i++){
			latTimes[i] = (double)Terminated_Queue.get(i).getLatency();
		}
		double latencyDev = standardDev(latTimes);
		return latencyDev;
	}
	//this method calculates, prints, and returns the standard deviation of the response times for all finished processes
	public double getResponseTime(){
		int size = Terminated_Queue.size();
		double[] resTimes = new double[size];
		for (int i = 0; i < size; i++){
			resTimes[i] = (double)Terminated_Queue.get(i).getResponseTime();
		}
		double responseDev = standardDev(resTimes);
		return responseDev;
	}

} 