import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import javafx.scene.shape.Line; 

class OS {
	public CPU cpu;
	public IOdevice io;
	public boolean isCPUAvailable = true; // initially this had no value
	// public ProcessTable process_Table;
	public ArrayList<Process> New_Queue;
	public ArrayList<Process> Ready_Queue;
	public ArrayList<Process> Wait_Queue;
	public ArrayList<Process> Terminated_Queue;

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
			int priority = Integer.parseInt(words[2]); 
			int arrival = Integer.parseInt(words[1]); 
			String IOburst = words[3]; 
			
			ArrayList<String> burst = new ArrayList<>(); 
			burst.add(IOburst); 
		}
		
	}
	public void FCFS(Process process) {
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

			case "Ready":// Process is ready to be executed
				System.out.println("Process ready to execute...");
				if (isCPUAvailable == true) {
					state = "Running";
				}
				System.out.println("CPU is not available, please wait...");

			case "Running":// Process is now being executed
				System.out.println("Executing process...");
				CPU.BubbleSort(Ready_Queue.get(0));
				Wait_Queue.add(Ready_Queue.get(0));
				if (process.getBurstSequence() == null) {
					Wait_Queue.remove(0);
					state = "Terminated";
				}
				state = "Waiting";

			case "Waiting":// Process is waiting on I/O
				System.out.println("Waiting on I/O");
				IOdevice.BubbleSort(Wait_Queue.get(0));
				state = "Running";

			case "Terminated":// Process has finished executing
				Terminated_Queue.add(Ready_Queue.get(0));
				Ready_Queue.remove(0);
				if (Ready_Queue == null) {
					done = true;// If there are no more processes to execute its
								// finished
				}
				System.out.println("Finished executing");
				state = "Ready";
				break;
			}
		}
		System.out.println("All processes have finished executing");
	}

	public void RoundRobin(Process process) {
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

			case "Ready":// Process is ready to be executed
				System.out.println("Process ready to execute...");
				if (isCPUAvailable == true) {
					state = "Running";
				}
				System.out.println("CPU is not available, please wait...");

			case "Running":// Process is now being executed
				System.out.println("Executing process...");
				CPU.BubbleSort(Ready_Queue.get(0));
				Wait_Queue.add(Ready_Queue.get(0));
				if (process.getBurstSequence() == null) {// Need to circle queue
															// if timeslice is
															// surpasses
					Wait_Queue.remove(0);
					state = "Terminated";
				}
				state = "Waiting";

			case "Waiting":// Process is waiting on I/O
				System.out.println("Waiting on I/O");
				IOdevice.BubbleSort(Wait_Queue.get(0));
				state = "Running";

			case "Terminated":// Process has finished executing
				Terminated_Queue.add(Ready_Queue.get(0));
				Ready_Queue.remove(0);
				if (Ready_Queue == null) {
					done = true;// If there are no more processes to execute its
								// finished
				}
				System.out.println("Finished executing");
				state = "Ready";
				break;
			}
		}
		System.out.println("All processes have finished executing");
	}

	public void staticPriority(Process process) {
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

			case "Ready":// Process is ready to be executed
				System.out.println("Process ready to execute...");
				if (isCPUAvailable == true) {
					state = "Running";
				}
				System.out.println("CPU is not available, please wait...");

			case "Running":// Process is now being executed
				System.out.println("Executing process...");
				CPU.BubbleSort(Ready_Queue.get(0));
				Wait_Queue.add(Ready_Queue.get(0));
				if (process.getBurstSequence() == null) {
					Wait_Queue.remove(0);
					state = "Terminated";
				}
				state = "Waiting";

			case "Waiting":// Process is waiting on I/O
				System.out.println("Waiting on I/O");
				IOdevice.BubbleSort(Wait_Queue.get(0));
				state = "Running";

			case "Terminated":// Process has finished executing
				Terminated_Queue.add(Ready_Queue.get(0));
				Ready_Queue.remove(0);
				if (Ready_Queue == null) {
					done = true;// If there are no more processes to execute its
								// finished
				}
				System.out.println("Finished executing");
				state = "Ready";
				break;
			}
		}
		System.out.println("All processes have finished executing");
	}

	public void Circle(ArrayList<Process> Ready_Queue) {
		Process head = Ready_Queue.get(0);
		Ready_Queue.remove(0);
		Ready_Queue.add(head);
	}

}