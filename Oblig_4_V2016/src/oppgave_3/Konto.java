package oppgave_3;

import java.util.concurrent.locks.*;


public class Konto implements IKonto {

	private int saldo;
	private Lock lock;
	private Condition deposit;

	public Konto (int belop) {
		saldo = belop;
		lock = new ReentrantLock();
		deposit = lock.newCondition();  
	}


	public int getSaldo() {
		lock.lock();
		int newSaldo = saldo;
		lock.unlock();
		return newSaldo;
	}

	public void innskudd(int belop) {
		lock.lock();
		saldo = saldo + belop;
		//	Sender notifyAll
		deposit.signalAll();
		lock.unlock();
	}


	public  void uttak(int belop) {
		//	Vent, inntil saldoen er stor nok til dette uttaket
		lock.lock();
		try {
			while(saldo - belop < 0) 
				deposit.await();
			saldo = saldo - belop;
		}
		
		catch (InterruptedException e) {
			
		}
		finally {
			lock.unlock();
		}
		
		
	}
	public Lock getLock() {
		return lock;
	}
}
//****************************************************************************
