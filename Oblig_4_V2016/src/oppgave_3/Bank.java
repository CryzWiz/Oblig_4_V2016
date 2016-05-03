package oppgave_3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {

private Konto[] kontoer;
//	Antall transaksjoner utfoert
private long transaksjoner = 0;
private Lock lock;

//------------------------------------------------------------------------
//	Variabler for test
private final int NTEST = 10000;	// Antall transaksjoner foer test() kjoeres
private final int TOTEST = 100000;	// Antall transaksjoner foer toString() kjoeres
private int avvik = 0;			// Teller antall ggr tilsynelatende feil summering
private int startbelop = 0;		// Tar vare pÂ startbeloepet til kontoene
//------------------------------------------------------------------------


public Bank (int antall, int startbelop) {
	kontoer = new Konto[antall];
	for (int i = 0; i < kontoer.length; i++)
		kontoer[i]= new Konto(startbelop);
	transaksjoner = 0;
	this.startbelop = startbelop;
	lock = new ReentrantLock();

}

public void overfoer (int fra, int til, int belop) {

	lock.lock();
		transaksjoner ++;
		// Kjoer toString() metode hver TOTEST gang
		if (transaksjoner % TOTEST == 0) {
			System.out.println(test2());
		}
		// Kjoer test() metode hver NTEST gang
		//else {	
			//if (transaksjoner % NTEST == 0) test();
		//}	
	lock.unlock();
	kontoer[fra].uttak(belop);
	kontoer[til].innskudd(belop);
}	

//------------------------------------------------------------------------
//	TEST
//	BEREGNING		Summerer opp konto saldoene
//	UTSKRIFT		Skriver ut antall transaksjoner og sum saldoer
//					Skriver ut antall tilsynelatende "avvik" sÂ langt
// Avvik vil  forekomme i utskriften, er ikke reelt
//------------------------------------------------------------------------
private void test () {
	int sum = 0;
	for (int i = 0; i < kontoer.length; i++)
		sum += kontoer[i].getSaldo();
	if (sum != (kontoer.length * startbelop)) avvik++;
	System.out.println ("Transaksjoner: " + transaksjoner
			+ "  Sum: " + sum + "  Avvik: " + avvik);
}

//------------------------------------------------------------------------
//	ANTALL KONTOER
//	RETURNERER		Returnerer antall kontoer
//------------------------------------------------------------------------
public int antKontoer () {
	return kontoer.length;
}
public long transaksjoner () {
	return transaksjoner;
}
//------------------------------------------------------------------------
//	TOSTRING
//	RETURNERER		Kontoutskrift av alle kontoene.
//------------------------------------------------------------------------
public String test2 () {
	String tekst = "";
	int sum = 0, saldo=0;
	Lock locks[] = new Lock[kontoer.length];

	//lock all accounts
	for (int i = 0; i < kontoer.length; i++) {
		locks[i] = kontoer[i].getLock();
		locks[i].lock();
	} 
	//summarize
	for (int i = 0; i < kontoer.length; i++) {
		saldo = kontoer[i].getSaldo();
		sum = sum + saldo; 
		//tekst = tekst + ("\nKontonr " + i +
			//	" - Saldo: " + saldo);
	}
	tekst = tekst + "Transaksjoner: " + transaksjoner
			+ "  Sum: " + sum + "\n";
	
	//unlock all accounts
	for (int i = 0; i < kontoer.length; i++) {
		locks[i].unlock();
	} 

	return tekst;
}

}
//****************************************************************************

	
	
