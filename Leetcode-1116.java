class ZeroEvenOdd {
    	private int n;
		private AtomicInteger currentNum;
		private final Semaphore zeroSemaphore;
		private final Semaphore evenSemaphore;
		private final Semaphore oddSemaphore;

		public ZeroEvenOdd(int n) {
			this.n = n;
			this.currentNum = new AtomicInteger(0);
			this.zeroSemaphore = new Semaphore(1);
			this.evenSemaphore = new Semaphore(0);
			this.oddSemaphore = new Semaphore(0);
		}

		public void zero(IntConsumer printNumber) throws InterruptedException {
			while (currentNum.intValue() < n) {
				zeroSemaphore.acquire();

				if (currentNum.intValue() < n) {
					printNumber.accept(0);
				}

				if (currentNum.intValue() % 2 == 0) {
					oddSemaphore.release();
				} else {
					evenSemaphore.release();
				}
			}
		}


		public void even(IntConsumer printNumber) throws InterruptedException {
			while (currentNum.intValue() < n) {
				evenSemaphore.acquire();

				if (currentNum.intValue() < n) {
					printNumber.accept(currentNum.incrementAndGet());
					zeroSemaphore.release();
				}
			}
		}

		public void odd(IntConsumer printNumber) throws InterruptedException {
			while (currentNum.intValue() < n) {
				oddSemaphore.acquire();

				if (currentNum.intValue() < n) {
					printNumber.accept(currentNum.incrementAndGet());
					zeroSemaphore.release();
				}
			}
		}
}