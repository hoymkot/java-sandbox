import java.util.concurrent.Semaphore;


/*
 * The following is a simulation of the classic "Dining Philosophers Problem". 
 * Note that output needs to be sorted by "current milliseconds" (the first 
 * field) and steps ( second field) due delays in standard output printing,
 * Please also note that multiple events may happen at the same milliseconds   
 * 
 */
public class DiningPhilosopher implements Runnable {

	static int NUM = 5; // number of philosophers 
	static Object mutex = new Object(); 

	DiningPhilosopher(int i) {
		this.i = i;
	}

	Semaphore permit;
	int i; // index of this philosopher
	int step = 0 ; // steps taken by this philosopher
	public enum STATE {
		HUNGRY, THINKING, EATING
	}	
	STATE state;

	public static void main(String[] args) {
		DiningPhilosopher[] lst = new DiningPhilosopher[NUM];
		for (Integer i = 0; i < NUM; i++) {
			DiningPhilosopher philo = new DiningPhilosopher(i);
			lst[i] = philo;
			philo.permit = new Semaphore(1);
		}

		lst[0].left = lst[NUM - 1];
		lst[0].right = lst[1];
		lst[NUM - 1].left = lst[NUM - 2];
		lst[NUM - 1].right = lst[0];

		for (Integer i = 1; i < NUM - 1; i++) {
			lst[i].left = lst[i - 1];
			lst[i].right = lst[i + 1];

		}
		for (Integer i = 0; i < NUM; i++) {
			Thread th = new Thread(lst[i], i.toString());
			th.start();
		}

	}

	
	long getWait() {
		return (long) (Math.random() * 1000);
	}

	public void think() {

		try {
			
			long wait = this.getWait();
			System.out.println( System.currentTimeMillis() + " " + step + " Philo " + this.i + " starts thinking (" + wait + ") ");
			step++;
			Thread.sleep(wait);
			System.out.println(System.currentTimeMillis() + " " +step + " Philo " + this.i + " stops thinking (" + wait + ") ");
			step++;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	DiningPhilosopher left;
	DiningPhilosopher right;

	void takeForks() {

		long current = System.currentTimeMillis(); 
		System.out.println(System.currentTimeMillis() + " " + step +" Philo " + i + " is ready for forks" );
		step ++;
		synchronized (mutex) {
			state = STATE.HUNGRY;
			test(this);
		}
		try {			
			this.permit.acquire();
			System.out.println(System.currentTimeMillis() + " " + step + " Philo " + i + " takes forks ( " + (System.currentTimeMillis() - current) + " )" );
			step ++;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void eat() {
		long wait = this.getWait();
		System.out.println(System.currentTimeMillis() + " " + step + " Philo " + i + " starts eating (" + wait + ")");
		step++;
		try {
			Thread.sleep(wait);
			System.out.println(System.currentTimeMillis() + " " + step + " Philo " + i + " stops eating now (" + wait + ")");
			step++;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	void putForks() {
		synchronized (mutex) {
			this.state = STATE.THINKING;
			System.out.println(System.currentTimeMillis() + " " + step + " Philo " + i + " puts down forks ");
			step++;
			test(this.left);
			test(this.right);
			
		}
	}

	static void test(DiningPhilosopher target) {
		if (target.state == STATE.HUNGRY && target.left.state != STATE.EATING && target.right.state != STATE.EATING) {
			target.state = STATE.EATING;
			target.permit.release();
			System.out.println(System.currentTimeMillis() + " " + target.step + " Philo " + target.i + " has forks now " );
			target.step ++ ;
		} else if (target.state == STATE.HUNGRY ){
			System.out.println(System.currentTimeMillis() + " " + target.step + " Philo " + target.i + " has no forks now " );
			target.step ++ ;
		} 
		// no check when philosopher is not hungry

	}

	@Override
	public void run() {
		try {
			this.permit.acquire(); // permit is only granted in test(philo)
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while (true) {
			this.think();
			this.takeForks();
			this.eat();
			this.putForks();
		}
	}
	

}
