class Foo {

    public int numOfCurrentFunction; 

    public Foo() {
        numOfCurrentFunction = 1;
    }

    public void first(Runnable printFirst) throws InterruptedException {
        synchronized(this) {
            numOfCurrentFunction++;
            notifyAll();
        }
        printFirst.run();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        synchronized(this) {
            while (numOfCurrentFunction != 2) {
                wait();
            }
            numOfCurrentFunction++;
            notify();
        }
        printSecond.run();
    }

    public void third(Runnable printThird) throws InterruptedException {
        synchronized(this) {
            while (numOfCurrentFunction != 3) {
                wait();
            }
        }
        printThird.run();
    }
}