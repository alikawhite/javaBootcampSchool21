package ex01;class Egg implements Runnable {    private final Object lock;    private final int count;    public Egg(Object lock, int count) {        this.lock = lock;        this.count = count;    }    @Override    public void run() {        for (int i = 0; i < count; i++) {            synchronized (lock) {                System.out.println("EGG");                lock.notify();                try {                    if (i < count - 1) lock.wait();                } catch (InterruptedException e) {                    e.printStackTrace();                }            }        }    }}