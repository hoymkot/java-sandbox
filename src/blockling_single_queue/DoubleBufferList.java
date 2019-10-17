package blockling_single_queue;

import java.util.List;

public class DoubleBufferList {

    private List<Object> lP;
    private List<Object> lT;
    private int gap;

    /**
     * ���췽��
     *
     * @param lP
     *            ������Ŷ���Ķ���
     * @param lT
     *            ����ȡ����Ķ���
     * @param gap
     *            �����ļ��
     */
    public DoubleBufferList(List lP, List lT, int gap) {
        this.lP = lP;
        this.lT = lT;
        this.gap = gap;
    }

    public void check() {
        Runnable runner = new Runnable() {
            public void run() {
                while (true) {
                    if (lT.size() == 0) {
                        synchronized (lT) {
                            synchronized (lP) {
                                lT.addAll(lP);
                            }
                            lP.clear();
                        }
                    }
                    try {
                        Thread.sleep(gap);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runner);
        thread.start();
    }

}
