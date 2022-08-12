import com.sun.org.apache.bcel.internal.generic.NEW;

import javax.lang.model.element.VariableElement;
import java.util.concurrent.*;

public class ThreadLocalDemo {

    private static ThreadLocal<String> localVar = new ThreadLocal<String>();

    static void print(String str) {
        //打印当前线程中本地内存中本地变量的值
        System.out.println(str + " :" + localVar.get());
        //清除本地内存中的本地变量
        localVar.remove();
    }
    public static void main(String[] args) throws InterruptedException {




        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    ThreadLocalDemo.localVar.set("local_A");
                    print("A");
                    //打印本地变量
                    System.out.println("after remove : " + localVar.get());

                }

            }
        },"A").start();


//        FutureTask futureTask = new FutureTask(()->{
//            for (int i = 0; i < 100; i++) {
//                System.out.println(i);
//
//            }
//            return 11;
//        });




//        FutureTask<String> futureTask = new FutureTask<String>(()->{
//            for (int i = 0; i < 10; i++) {
//                System.out.println("a"+i);
//            }
//            return "11";
//        });
//        new Thread(futureTask,"C").start();
//
////        Thread.sleep(1000);
//        FutureTask futureTask1 = new FutureTask(()->{
//            for (int i = 0; i < 10; i++) {
//                System.out.println("b"+i);
//            }
//            return "11";
//        });
//        new Thread(futureTask1).start();

        ExecutorService pool = Executors.newCachedThreadPool();
        Future<String> future = pool.submit(new calltest());

        try {
            System.out.println(future.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        pool.submit(new calltest());


        pool.shutdown();


        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    ThreadLocalDemo.localVar.set("local_B");
                    print("B");
                    System.out.println("after remove : " + localVar.get());
                }

            }
        },"B").start();

//        calltest calltest = new calltest();



    }

}
