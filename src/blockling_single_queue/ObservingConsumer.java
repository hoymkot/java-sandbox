package blockling_single_queue;

import java.util.concurrent.LinkedBlockingQueue;

public class ObservingConsumer implements Runnable {
    
    private LinkedBlockingQueue<String> queue;
    private Producer producer;
    
    public ObservingConsumer(LinkedBlockingQueue<String> queue, Producer producer) {
        this.queue = queue;
        this.producer = producer;
    }

    @Override
    public void run() {
        
        // As long as the producer is running,
        // we want to check for elements.
        while (producer.isRunning()) {
            System.out.println("OC\tElements right now: " + queue);
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("OC Completed.");
        System.out.println("Final elements in the queue: " + queue);
    }
}
