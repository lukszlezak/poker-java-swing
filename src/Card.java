public class Card {
	public String znak_karty;
	public String figura_karty;
	public int wartosc_karty;
	
	Card (String znak_karty, int wartosc_karty) {
		this.znak_karty = znak_karty;
		this.wartosc_karty = wartosc_karty;
		if (wartosc_karty == 14) {
			this.figura_karty = "A";
		}
		else if (wartosc_karty == 13) {
			this.figura_karty = "K";
		}
		else if (wartosc_karty == 12) {
			this.figura_karty = "D";
		}
		else if (wartosc_karty == 11) {
			this.figura_karty = "J";
		}
		else {
			this.figura_karty = "" + wartosc_karty;
		}
	}
	
	public Card() {
		this.znak_karty = "";
		this.wartosc_karty = 0;
	}

	public String pokaz_znak() {
		return znak_karty;
	}
	
	public int pokaz_wartosc() {
		return wartosc_karty;
	}
	
	public String pokaz_figure() {
		return figura_karty;
	}
}