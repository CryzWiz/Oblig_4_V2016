package oppgave_3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;




public class Bank {
	//antall kontoer i banken og startsaldoen til hver konto.
	private static final int ANTALL_KONTOER = 20;
	private static final double START_SALDO = 10000.00;
	//Har valgt litt andre tall enn UnsynchBankTest.
	//Antall kontoer og startsaldo er det samme, men antall reps og tallet som "beløpet" deles på 
	//er satt lavere. Dette for å fremprovosere testingen fortere. Tar så langt tid om trådene skal
	//jobbe gjennom tusenvis av overføringer når man får utskrift for hver enkelt. Tenker da på data
	//du må gå gjennom når du skal sjekke utskriftene for å lete etter feil.
	//Men prinsippet er det samme, og overføringene funker slik jeg fortod oppgaven.
	
	public static void main(String[] args) {
		Beholdning bank = new Beholdning(ANTALL_KONTOER, START_SALDO);
		ExecutorService executor = Executors.newCachedThreadPool();
	    //executor.execute(new nyTransaksjon());
	    
		for(int i = 0; i < ANTALL_KONTOER; i++){
			TransaksjonsTraad transaksjon = new TransaksjonsTraad(bank, i, START_SALDO);
			transaksjon.setPriority (Thread.NORM_PRIORITY + i % 2);
			transaksjon.start ();
		}
		Beholdning.BankSaldo();
		
	
	
	}

}

class Beholdning {
	//Endre NTEST for å endre antall kjøringer før det kjøres en test mot bankes saldo.
	public static final int NTEST = 100;
	static double[] konto;
	private long transaksjoner;
	
	public Beholdning(int k, double s){
		konto = new double[k];
		for(int i = 0; i < antallKontoer(); i++){
			konto[i] = s;
		}
		transaksjoner = 0;
	}
	
	public double KontoSaldo(int i){
		return konto[i];
	}
	
	
	public static void BankSaldo(){
		double saldo = 0;
		for(int b = 0; b < antallKontoer(); b++){
			saldo += konto[b];
		}
		System.out.println("Total beholdning for banken er: " + saldo);
	}
	
	public static int antallKontoer() {
		return konto.length;
	}
	
	private static Lock lock = new ReentrantLock();
	private static Condition nyTransaksjon = lock.newCondition();
	
	public void Transaksjon(int fra, int til, double belop){
		try{
			lock.lock(); // Acquire the lock
			while(KontoSaldo(fra) < belop ){
				System.out.println("Overføring fra konto " + fra + ", til konto " + til + 
						" er ikke utført pga lav dekning.\nOverføringen blir utført når dekning er tilstede" +
						"\nBeløpet det gjelder er kr: " + belop + "\n\n");
				nyTransaksjon.await();
			}
			konto[fra] -= belop;
			konto[til] += belop;
			transaksjoner++;
			System.out.println("Overføring fra konto " + fra + ", til konto " + til + " er utført."
					+ "\nBeløp overført er: " + belop + "\tNy saldo er: " + konto[til] + "\n");
			if (transaksjoner % NTEST == 0) test ();
			nyTransaksjon.signalAll();
		}
		catch (InterruptedException ex) {
		        ex.printStackTrace();
			}
		finally {
	        lock.unlock(); // Release the lock
	    	}
	}
	
	 public void test (){  
	    int sum = 0;

	    for (int i = 0; i < antallKontoer(); i++)
	       sum += konto[i];

	    System.out.println ("\n\nANTALL TRANSAKSJONER:" + transaksjoner
	       + " SALDO I BANKEN: " + sum + "\n\n");
	 }
	
}

class TransaksjonsTraad extends Thread{
	private Beholdning bank;
	private int fraKonto;
	private double maxBelop;
	
	//Endre disse to for å sette et lavere/høyere beløp som skal settes inn/tas ut.
	private int REPS = 10;
	private int divider = 10;
	private int sleep = 1;
	public TransaksjonsTraad(Beholdning bank, int fraKonto, double maxBelop){
		this.bank = bank;
		this.fraKonto = fraKonto;
		this.maxBelop = maxBelop;
	}
	
	public void run (){
		try
	    {  
	       while (!interrupted ())
	       {  
	          for (int i = 0; i < REPS; i++)
	          {
	             int tilKonto = (int)(Beholdning.antallKontoer() * Math.random ());
	             int amount = (int)(maxBelop * Math.random () / divider);
	             bank.Transaksjon (fraKonto, tilKonto, amount);
	             sleep (sleep);
	          }
	       }
	    }
	    catch (InterruptedException e) {}
	 }
}
	
	
