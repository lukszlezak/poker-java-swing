import java.util.Random;

public class Deck {
	private Card talia[];
	
	Deck() {
		talia = new Card [52];
		for (int x = 0; x < 52; x++) {
			talia[x] = new Card();
		}
	}
	
	public void potasuj_karty() {
		for (int x = 0 ; x < 52 ; x++) {
			talia[x].znak_karty = "";
			talia[x].wartosc_karty = 0;
		}
		
		talia[0].znak_karty = losuj_znak();
		talia[0].wartosc_karty = losuj_wartosc();
		
		for (int x = 1; x < 52; x++) {
			String znak;
			int wartosc;
			do {
				znak = losuj_znak();
				wartosc = losuj_wartosc();
			} while (czy_byla (znak, wartosc));
			talia[x].wartosc_karty = wartosc;
			talia[x].znak_karty = znak;
		}
	}
	
	private String losuj_znak() {
		Random rand = new Random();
		int xxx = rand.nextInt(4);
		if (xxx == 0) 		return "Kier";
		else if (xxx == 1)	return "Karo";
		else if (xxx == 2)	return "Trefl";
		else				return "Pik";
	}
	
	private int losuj_wartosc() {
		Random rand = new Random();
		return rand.nextInt(13) + 2;
	}
	
	private boolean czy_byla (String znak, int wartosc) {
		for (int x = 0 ; x < 52 ; x++) {
			if ((talia[x].pokaz_znak().equals(znak)) && (talia[x].pokaz_wartosc() == wartosc)) {
				return true;
			}
		}
		return false;
	}
	
	public Card zwroc_karte (int index) {
		return talia[index];
	}
}