package oppgave_2;
import java.util.Scanner; 
public class TowerOfHanoi {

	 /** Main method */
	  public static void main(String[] args) {
	    // Create a Scanner
	    Scanner input = new Scanner(System.in);
	    System.out.print("Legg inn antall skiver: ");
	    int n = input.nextInt();

	    // Find the solution recursively
	    System.out.println("Trekkene er som følger:");
	    flyttDisker(n, 'A', 'B', 'C');
	  }

	  /** The method for finding the solution to move n disks
	      from fromTower to toTower with auxTower */
	  public static void flyttDisker(int n, char fraTaarn, char tilTaarn, char hjelpeTaarn) {
	    if (n == 1) // Stopping condition
	      System.out.println("Flytter disk " + n + " fra " + fraTaarn + " til " + tilTaarn);
	    else {
	      flyttDisker(n - 1, fraTaarn, hjelpeTaarn, tilTaarn);
	      System.out.println("Flytter disk " + n + " til " + fraTaarn + " fra " + tilTaarn);
	      flyttDisker(n - 1, hjelpeTaarn, tilTaarn, fraTaarn);
	    }
	  }

}
