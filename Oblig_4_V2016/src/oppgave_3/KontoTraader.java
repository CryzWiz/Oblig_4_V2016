package oppgave_3;

public class KontoTraader extends Thread  {

	private Bank bank;
	private int fraKonto, tilKonto;
	private int belop, maksBelop;
	

	public KontoTraader (Bank b, int fraKonto, int maksBelop) {
		bank = b;
		this.fraKonto = fraKonto;
		this.maksBelop = maksBelop;
	}


	public void run () {
		try {
			while (!interrupted ()) {
				// Random utvalg av tilkonto
				tilKonto = (int)(bank.antKontoer() * Math.random ());
				// Bel¯p som skal overf¯res vha bank metoden
				belop = (int)(maksBelop * Math.random ());						
				
				bank.overfoer (fraKonto, tilKonto, belop);
				// Endre verdi for Â endre farten pÂ programmet
				sleep(2);
			}	
		}
		catch (InterruptedException e) {}
	}
}
