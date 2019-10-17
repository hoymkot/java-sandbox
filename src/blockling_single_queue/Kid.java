package blockling_single_queue;

public class Kid extends Thread {
	long time1 = System.currentTimeMillis();
	int count = 0;

	public void run() {
		while (true) {

//			synchronized (Tools.lA) {
//				if (Tools.lA.size() != 0)
//					Tools.lA.remove(0);
//			}
			Tools.lT.poll();
			
			
//			synchronized (Tools.lC) {
//			if (Tools.lC.size() != 0)
//				Tools.lC.remove(0);
//		}
			count++;
			
			
			if (count == 10000000) {
				javax.swing.JOptionPane.showMessageDialog(null, "”√ ±º‰: " + (System.currentTimeMillis() - time1));
				System.exit(0);
			}

		}
	}
}
