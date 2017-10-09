class CPU {
	public boolean BusyOrNot;
	public int PC; // Your CPU only has one register PC
	public int timeslice;

	public CPU(int settimeslice) {
		timeslice = settimeslice;
		BusyOrNot = false;
	}
	/*
	 * public Pair<int PC, String state> execute(Process P){ BusyOrNot=true; /*
	 * read the CPU burst number, say #, from the position
	 * PositionOfNextInstructionToExecute of P. Repeat calling Bubble Sort() for
	 * # times and then continue. If the code runs out, return
	 * (PositionOfNextInstructionToExecute, â€œterminatedâ€�), then OS put it
	 * back to the terminated queue. If the slice of time (restricted number of
	 * calling Bubble sort() for a process each time) runs out, return
	 * (PositionOfNextInstructionToExecute+1, â€œreadyâ€�), then OS put it back
	 * to the ready queue. Otherwise, return
	 * (PositionOfNextInstructionToExecute+1, â€œwaitâ€�) (namely, P has an I/O
	 * request and then OS remove it from the ready queue and sent it to I/O
	 * queue) }
	 */

	public static void  BubbleSort() {
		boolean sorted = false;
		double[] data = new double[1000];
		for (int i = 0; i < 1000; i++){
			data[i] = Math.random();
		}
		//Perform bubble sort
		int n = data.length;
        double temp = 0.0;
        while (!sorted){
    	sorted = true;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (data[j-1] < data[j]) {
                	sorted = false;
                    temp = data[j - 1];
                    data[j - 1] = data[j];
                    data[j] = temp;
                }
            }
        }
        }
	}

	public Boolean CPUisBusy() {
		return BusyOrNot;
	}
}