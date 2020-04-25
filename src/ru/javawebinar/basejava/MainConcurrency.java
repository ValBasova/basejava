package ru.javawebinar.basejava;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private int counter;
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) throws InterruptedException {
//        System.out.println(Thread.currentThread().getName());
//
//        Thread thread0 = new Thread() {
//            @Override
//            public void run() {
//                System.out.println(getName() + ", " + getState());
//                throw new IllegalStateException();
//            }
//        };
//        thread0.start();
//
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
//            }
//
//            private void inc() {
//                synchronized (this) {
////                    counter++;
//                }
//            }
//
//        }).start();
//
//        System.out.println(thread0.getState());
//
//        final MainConcurrency mainConcurrency = new MainConcurrency();
//        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
//
//        for (int i = 0; i < THREADS_NUMBER; i++) {
//            Thread thread = new Thread(() -> {
//                for (int j = 0; j < 100; j++) {
//                    mainConcurrency.inc();
//                }
//            });
//            thread.start();
//            threads.add(thread);
//        }
//
//        threads.forEach(t -> {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        System.out.println(mainConcurrency.counter);

        new Thread(new Runnable() {

            @Override
            public void run() {
                synchronized(LOCK1) {
                    try {
                        LOCK1.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Lock1 - thread0");
                    synchronized (LOCK2) {
                        System.out.println("lock2 - thread0");
                        System.out.println(Thread.currentThread().getName());
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized(LOCK2) {
                    try {
                        LOCK2.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("lock2 - thread1");
                    synchronized (LOCK1) {
                        System.out.println("lock1 - thread1");
                        System.out.println(Thread.currentThread().getName());
                    }
                }
            }
        }).start();
    }

//    private synchronized void inc() {
//        synchronized (this) {
//        synchronized (MainConcurrency.class) {
//        counter++;
//                wait();
//                readFile
//                ...
//        }
//    }
}
