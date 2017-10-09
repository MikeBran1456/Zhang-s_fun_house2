import java.util.ArrayList;

class IOdevice {
	boolean BusyOrNot;
	
	public IOdevice(ArrayList<Process> Wait_Queue) {
	}

	// Always pick one process from Wait_Queue to exeute
	// public String execute(int IO_burst) { dont know what this does
	//BusyOrNot=true;
	// Call Bubble Sort() for IO_burst times and then return ;
	// }

	public static void BubbleSort() {
		double[] data = new double[1000];
		for (int i = 0; i < 1000; i++){
			data[i] = Math.random();
		}
		//Perform bubble sort
		int n = data.length;
        double temp = 0.0;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (data[j-1] < data[j]) {
                    temp = data[j - 1];
                    data[j - 1] = data[j];
                    data[j] = temp;
                }
            }
        }
	}
}