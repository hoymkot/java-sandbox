package blockling_single_queue;


public class Factory extends Thread {
	public void run() {
		while (true) {

			Toy t = new Toy();

			t.setName("Íæ¾ß");
//			synchronized (Tools.lA) {
//				Tools.lA.add(t);
//			}
			Tools.lT.offer(t);
			
//			synchronized (Tools.lP) {
//				Tools.lP.add(t);
//			}
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
