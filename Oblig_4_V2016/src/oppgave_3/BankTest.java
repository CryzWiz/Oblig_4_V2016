package oppgave_3;

public class BankTest {

	//------------------------------------------------------------------------
//		Programmet skal kunne behandle flere traader med bank transaksjoner
//		ved hjelp av KontoTraader()
//		Her testes overfoering mellom Konto()'er i en Bank()
//		Ressurskontroll vha locks knyttet mot hvert konto objekt
//	 	Testutskrift for Â sjekke at summen av saldoene er uendret.
//						sum = ANTALLKONTOER * STARTBELOP
	//------------------------------------------------------------------------

//		ANTALLKONTOER	Bestemmer hvor mange kontoer som skal opprettes
	//  STARTBELOP		Startbeloepet som hver konto sin saldo settes til
		
	public static final int ANTALLKONTOER = 100;
	public static final int STARTBELOP = 10000;

	public static void main(String[] args) {
		// Opprett bank objekt
		Bank b = new Bank (ANTALLKONTOER, STARTBELOP);

		// Skriv ut oppstartstatus
	    System.out.println("TEST AV BANK MED FLERE TRAADER. " +
	    	"Antall kontoer/tråder: " + b.antKontoer());
	    
	    for (int i = 0; i < ANTALLKONTOER; i++) {
	       KontoTraader t = new KontoTraader (b, i, STARTBELOP);
	       // Sett prioritet
	       t.setPriority (Thread.NORM_PRIORITY + i % 2);
	       // Start trådene
	       t.start ();
	    }
	}

	}
