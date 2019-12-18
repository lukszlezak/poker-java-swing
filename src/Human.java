public class Human {
	public Card karty_gracza[];
	public int ranking_ukladow;

	public String nazwa_gracza;
	public int ilosc_zetonow, liczba_zwyciestw = 0;
	public boolean czy_fold = false, czy_gra = true, wykonal_ruch = false;
	
	public Human() {
		karty_gracza = new Card[5];
	}
	
	public void odbierz_karte (String znak, int wartosc, int nr_karty) {
		karty_gracza [nr_karty] = new Card (znak, wartosc);
	}
	
	public void wyswietl_karty() {
		for (int x = 0 ; x < 5 ; x++) {
			System.out.println ("Karta " + (x+1) + " - " + karty_gracza[x].pokaz_znak() + " " + karty_gracza[x].pokaz_wartosc());
		}
	}
	
	public Card [] zwroc_karty() {
		return karty_gracza;
	}
}