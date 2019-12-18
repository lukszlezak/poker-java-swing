public class Sets {
	public boolean czy_poker (Card [] karty) {
		if (czy_kolor(karty) && czy_strit(karty)) { 
			return true;
		}
		else { 
			return false;
		}
	}
	
	public boolean czy_kareta (Card [] karty) {
		for (int x = 0 ; x < 2 ; x++) { 
			if ((karty[x].pokaz_wartosc() == karty[x+1].pokaz_wartosc()) && 
					(karty[x].pokaz_wartosc() == karty[x+2].pokaz_wartosc()) &&
					(karty[x].pokaz_wartosc() == karty[x+3].pokaz_wartosc())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean czy_full (Card [] karty) {
		int pomocnicza = 0;
		for (int x = 0 ; x < 4 ; x++) { 
			if (karty[x].pokaz_wartosc() == karty[x+1].pokaz_wartosc()) { 
				pomocnicza++;
			}
		}
		if (pomocnicza == 3) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean czy_kolor (Card [] karty) {
		int pomocnicza = 0;
		for (int x = 0 ; x < 4 ; x++) { 
			if (karty[x].pokaz_znak().equals (karty[x+1].pokaz_znak())) { 
				pomocnicza++;
			}
		}
		if (pomocnicza == 4) { 
			return true;
		}
		else { 
			return false;
		}
	}
	
	public boolean czy_strit (Card [] karty) {
		int pomocnicza = 0;
		for (int x = 0 ; x < 4 ; x++) { 
			if ((karty[x].pokaz_wartosc() + 1) == karty[x+1].pokaz_wartosc()) {
				pomocnicza++;
			}
		}
		if (pomocnicza == 4) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean czy_trojka (Card [] karty) {
		for (int x = 0 ; x < 3 ; x++) {
			if ((karty[x].pokaz_wartosc() == karty[x+1].pokaz_wartosc()) && (karty[x].pokaz_wartosc() == karty[x+2].pokaz_wartosc())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean czy_2_pary (Card [] karty) {
		int pomocnicza = 0;
		for (int x = 0 ; x < 4 ; x++) { 
			if (karty[x].pokaz_wartosc() == karty[x+1].pokaz_wartosc()) { 
				pomocnicza++;
			}
		}
		if (pomocnicza == 2) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean czy_para (Card [] karty) {
		int pomocnicza = 0;
		for (int x = 0 ; x < 4 ; x++) {
			if (karty[x].pokaz_wartosc() == karty[x+1].pokaz_wartosc()) { 
				pomocnicza++;
			}
		}
		if (pomocnicza == 1) {
			return true;
		}
		else {
			return false;
		}
	}
}