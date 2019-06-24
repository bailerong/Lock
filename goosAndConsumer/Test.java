package goosAndConsumer;
//商品类
class Goods{
    //生产商品名称
    private String goodsName;
    //商品库存
    private int count;
    //生产方式
    public synchronized  void set(String goodsName)throws InterruptedException{
        if(this.count>0){
            System.out.println("还有方便面，等一下");
            wait();
        }
        this.goodsName=goodsName;
        this.count=count+1;
        Thread.sleep(1000);
        System.out.println("生产"+toString());
        notify();
    }
    public synchronized void get()throws InterruptedException{
        if(this.count==0){
            System.out.println("方便面卖完；请等一下");
            wait();
        }
        this.count=this.count-1;
        Thread.sleep(1000);
        System.out.println("消费"+toString());
        notify();
    }
    @Override
    public String toString() {
        return "Goods{" +
                "goodsName='" + goodsName + '\'' +
                ", 库存为" + count +
                '}';
    }
}
class Producer implements Runnable{
    private Goods goods;
    public Producer(Goods goods){
        this.goods=goods;

    }
    public void run(){
        try{
            this.goods.set("方便面");
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
class Consumer implements Runnable{
    private Goods goods;
    public Consumer(Goods goods){
        this.goods=goods;
    }
    @Override
    public void run() {
        try {
            this.goods.get();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
public class Test {
    public static void main(String[] args)throws InterruptedException {
        Goods goods=new Goods();
        Thread produceThread=new Thread(new Producer(goods),"生产者线程");
        Thread consumerThread=new Thread(new Consumer(goods),"消费者线程");
        produceThread.start();
        Thread.sleep(1000);
        consumerThread.start();
    }
}
