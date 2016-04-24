package oppgave_2;
import java.util.Scanner; 
public class TowerOfHanoi {
	public static int antallFlyttinger = 0;
	public static int antallKall = 0;
	 /** Main method */
	  public static void main(String[] args) {
	    // Create a Scanner
	    Scanner input = new Scanner(System.in);
	    System.out.print("Legg inn antall skiver: ");
	    int n = input.nextInt();

	    // Ser ikke helt poenget med å telle antall kall og antall flyttinger, da disse vil være like. 
	    // Men har uansett lagt inn 2 tellere. En for flyttinger, og en for kall til funksjonen.
	    
	    // Find the solution recursively
	    System.out.println("Trekkene er som følger:");
	    flyttDisker(n, 'A', 'B', 'C');
	    antallKall++;
	    
	    System.out.println("Antall flyttinger er: " + antallFlyttinger);
	    System.out.println("Antall kall er: " + antallKall);
	  }

	  /** The method for finding the solution to move n disks
	      from fromTower to toTower with auxTower */
	  public static void flyttDisker(int n, char fraTaarn, char tilTaarn, char hjelpeTaarn) {
	    if (n == 1){ // Stopping condition
	      System.out.println("Flytter disk " + n + " fra " + fraTaarn + " til " + tilTaarn);
	      antallFlyttinger++;
	      antallKall++;
	  }
	    else {
	      flyttDisker(n - 1, fraTaarn, hjelpeTaarn, tilTaarn);
	      antallKall++;
	      System.out.println("Flytter disk " + n + " til " + fraTaarn + " fra " + tilTaarn);
	      flyttDisker(n - 1, hjelpeTaarn, tilTaarn, fraTaarn);
	      antallKall++;
	      antallFlyttinger++;
	    }
	  }

}
