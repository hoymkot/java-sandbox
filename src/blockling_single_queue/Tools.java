package blockling_single_queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Tools {
    public static List<Toy>lA = new ArrayList<Toy>(10000);

//	public static ArrayBlockingQueue<Toy> lT = new ArrayBlockingQueue<Toy>(10000);
	public static LinkedBlockingQueue<Toy> lT = new LinkedBlockingQueue<Toy>(10000);
	public static ArrayList<Toy> lP = new ArrayList<Toy>(10000);
	public static ArrayList<Toy> lC = new ArrayList<Toy>(10000);
}