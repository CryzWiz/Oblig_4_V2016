package oppgave_1;
import java.util.*;
public class SnuTekst {
	private static int teller = 0;
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Legg inn setningen/ordet du ønsker å snu:");
		String tekst = input.nextLine();
		int siste = tekst.length() - 1;
		System.out.println("Setningen du skrev inn baklengs:");
		baklengs(tekst, siste);
		System.out.printf("\nSetningen/ordet inneholder %d tegn.", teller);

	}
	public static void baklengs(String tekst, int siste){
		if(siste >= 0){
		System.out.print(tekst.charAt(siste));
		teller++;
		baklengs(tekst, siste - 1);
		}
	}

}
