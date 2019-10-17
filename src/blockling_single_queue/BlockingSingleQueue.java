package blockling_single_queue;

public class BlockingSingleQueue {




	public static void main(String[] args) {
		
		DoubleBufferList list = new DoubleBufferList(Tools.lP, Tools.lC, 100); 
		list.check();
		Factory f = new Factory();
		Factory f2 = new Factory();
		Factory f3 = new Factory();
		f.start();
		f2.start();
		f3.start();
		Kid k = new Kid();
		
		Kid k2 = new Kid();
		Kid k3 = new Kid();
		k2.start();
		k3.start();
		k.start();
	}


}
