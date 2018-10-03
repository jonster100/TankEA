package tankea.pso;

public class Pso {
	int no;

	public void input(int personalBest, int localBest, int globalBest, int currentNo) {
		no = currentNo;
		int pso = ((personalBest - currentNo) + (localBest - currentNo) + (globalBest - currentNo));
		no += pso;
	}
	
	public void input(int personalBest,int globalBest, int currentNo) {
		no = currentNo;
		int pso = ((personalBest - currentNo) + (globalBest - currentNo));
		no += pso;
	}

	public int output() {
		return no;
	}
}
