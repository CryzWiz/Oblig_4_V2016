package oppgave_1;
import java.util.*;
public class SnuTekst {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Legg inn setningen/ordet du ønsker å snu:");
		String tekst = input.nextLine();
		baklengs(tekst);

	}
	public static void baklengs(String tekst){
		int n = tekst.length() - 1;
		System.out.println("Setningen du skrev inn baklengs:");
		while(n >= 0){
		System.out.print(tekst.charAt(n));
		n--;
		}
	}

}
