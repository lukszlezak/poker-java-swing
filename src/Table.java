import java.util.Random;   

public class Table {
	private static GUI gui;
	static Human gracz[];
	private static Deck talia;
	private static Sets set;
	private static int ostatnia_karta = 0;
	static int ilosc_graczy;
	private static int aktualny_gracz = 0;
	private static int wybor;
	private static int najwyzszy_uklad;
	private static int ilosc_najwiekszego_ukladu = 0;
	private static int gracz_z_najwiekszym_ukladem[];
	private static boolean czy_remis = false;
	private static String uklady_graczy[];
	private static String ogloszenie_ukladow;
	
	private static int startowe_zetony, wpisowe;
	static int stawka;
	private static int kto_zaczyna_licytacje;
	private static int ktory_licytuje;
	private static int ilu_gra;
	private static int ilu_nie_fold;
	private static int ilu_licytuje;
	private static int ktora_partia = 1;
	private static int zwiekszenie_stawki = 0;
	private static int ilu_wykonalo_ruch = 0, ilu_wykonalo_ruch_podczas_all_in, ilosc_ruchow_podczas_all_in;
	static int[] zetony_w_grze;
	private static boolean czy_bylo_all_in = false, czy_wszyscy_wykonali_ruch = false, czy_stawki_wyrownane = false, czy_byl_bet = false;
	private static String ranking;
	
	private Table (int ilosc_graczy) {
		gracz = new Human [ilosc_graczy];
		for (int x = 0; x < (ilosc_graczy); x++) { 
			gracz[x] = new Human();
		}
		talia = new Deck();
		set = new Sets();
		gracz_z_najwiekszym_ukladem = new int [ilosc_graczy];
		zetony_w_grze = new int [ilosc_graczy];
		ilu_gra = ilosc_graczy;
		uklady_graczy = new String [ilosc_graczy];
	}
	
	public Table() {}
	
	private static void wyswietl_karty (int ktorego_gracza) {
		for (int k = 0 ; k < 5 ; k++) {
			gui.but_karta[k].setText (gracz [ktorego_gracza].karty_gracza[k].pokaz_znak() + " " + gracz [ktorego_gracza].karty_gracza[k].pokaz_figure());
		}
	}
	
	private static void oczekiwanie_na_button() {
		do {
			try {
				Thread.sleep (200);
			} catch (InterruptedException e) {}
		} while (!gui.czy_kliknal);
		gui.czy_kliknal = false;
	}

	private static void rozdaj_karty() {
		for (int g = 0 ; g < ilosc_graczy ; g++) { 
			if (gracz[g].czy_gra) {
				for (int nr_karty = 0 ; nr_karty < 5 ; nr_karty++) { 
					gracz[g].odbierz_karte (talia.zwroc_karte (ostatnia_karta).pokaz_znak(), talia.zwroc_karte (ostatnia_karta).pokaz_wartosc(), nr_karty);
					ostatnia_karta++;
				}
			}
		}
	}
	
	private static void licytacja (boolean czy_pierwsza_licytacja) {
		gui.but_schowaj.setEnabled (true);
		gui.but_wyswietl.setEnabled (true);
		ilu_licytuje = ilu_gra;
		ilu_wykonalo_ruch = 0;
		czy_wszyscy_wykonali_ruch = false;
		czy_stawki_wyrownane = false;
		for (int g = 0 ; g < ilosc_graczy ; g++) {
			gracz[g].wykonal_ruch = false;
		}
		
		if (czy_pierwsza_licytacja) {
			if (ktora_partia == 1) {
				Random rand = new Random();
				kto_zaczyna_licytacje = rand.nextInt (ilosc_graczy);
			}
			else {
				if ((kto_zaczyna_licytacje + 1) == ilosc_graczy) {
					kto_zaczyna_licytacje = 0;
				}
				else {
					kto_zaczyna_licytacje++;
				}
			}
		}
		ktory_licytuje = kto_zaczyna_licytacje;
		
		do {
			if ((gracz [ktory_licytuje].czy_gra) && (!(gracz [ktory_licytuje].czy_fold))) {
				
				// czy moze CHECK
				if (!czy_bylo_all_in) {
					if (!czy_byl_bet) {
						gui.but_check.setEnabled (true);
					}
					else if (czy_byl_bet && zetony_w_grze [ktory_licytuje] == stawka) {
						gui.but_check.setEnabled (true);
					}
					else {
						gui.but_check.setEnabled (false);
					}
				}
				else {
					gui.but_check.setEnabled (false);
				}
				
				// czy moze BET
				if (!czy_bylo_all_in && !czy_byl_bet && (gracz [ktory_licytuje].ilosc_zetonow > 0)) {
					gui.but_bet.setEnabled (true);
				}
				else {
					gui.but_bet.setEnabled (false);
				}
				
				// czy moze RAISE
				if ( !czy_bylo_all_in && czy_byl_bet && ((stawka - zetony_w_grze[ktory_licytuje]) < gracz[ktory_licytuje].ilosc_zetonow) ) {
					gui.but_raise.setEnabled (true);
				}
				else {
					gui.but_raise.setEnabled (false);
				}
				
				// czy moze CALL
				if ( !czy_bylo_all_in && czy_byl_bet ) {
					if (stawka != zetony_w_grze[ktory_licytuje] && ((stawka - zetony_w_grze[ktory_licytuje]) <= gracz[ktory_licytuje].ilosc_zetonow) ) {
						gui.but_call.setEnabled (true);
					}
					else {
						gui.but_call.setEnabled (false);
					}
				}
				else {
					gui.but_call.setEnabled (false);
				} 
				
				// i reszta
				gui.but_fold.setEnabled (true);
				gui.but_all_in.setEnabled (true);
				
				gui.lab_instrukcje.setText ("Graczu " + gracz [ktory_licytuje].nazwa_gracza + ". Aktualnie w lapie masz " + gracz [ktory_licytuje].ilosc_zetonow + " zetonow. Licytuj sie!");
				oczekiwanie_na_button();
				wybor = gui.klik_gracza_int;
				
				switch (wybor) {
					case 1:
						for (int k = 0 ; k < 5 ; k++) {
							gui.but_karta[k].setText ("Karta " + (k+1));
							gui.but_karta[k].setEnabled (false);
						}
						gui.wyswietl_komunikat ("Check", "Gracz " + gracz[ktory_licytuje].nazwa_gracza + " postanowil poczekac :)");
						for (int k = 0 ; k < 5 ; k++) {
							gui.but_karta[k].setText ("Karta " + (k+1));
						}
						break;
					
					case 2:
						gui.lab_instrukcje.setText ("Postanowiles wniesc stawke wieksza niz wpisowe (" + wpisowe + "). O ile chcesz zwiekszyc stawke? - ");
						gui.ilosc_zetonow_gracza = gracz[ktory_licytuje].ilosc_zetonow;
						oczekiwanie_na_button();
						if (wybor == -666) {
							gui.wyswietl_komunikat ("Rezygnacja", "Gracz " + gracz[ktory_licytuje].nazwa_gracza + " zrezygnowal z calej gry! Podziekujmy jemu za gre :) Niech spada.");
							gracz [ktory_licytuje].czy_fold = true;
							gracz [ktory_licytuje].czy_gra = false;
							ilu_gra--;
							for (int k = 0 ; k < 5 ; k++) {
								gui.but_karta[k].setText ("Karta " + (k+1));
							}
							gui.pole_zwiekszanie_stawki.setEnabled (false);
							gui.but_zwiekszanie_stawki.setEnabled (false);
							break;
						}
						else if (wybor == -9) {
							wyswietl_karty (ktory_licytuje);
							break;
						}
						else {
							zwiekszenie_stawki = gui.klik_gracza_int;
							stawka += zwiekszenie_stawki;
							gracz [ktory_licytuje].ilosc_zetonow -= zwiekszenie_stawki;
							zetony_w_grze [ktory_licytuje] += zwiekszenie_stawki;
							gui.wyswietl_komunikat ("Bet", "Gracz " + gracz[ktory_licytuje].nazwa_gracza + " zwiekszyl stawke o " + gui.klik_gracza_int + ".");
							gui.uaktualnij_stawke();
							gui.uaktualnij_zetony_na_stole();
							czy_byl_bet = true;
							for (int k = 0 ; k < 5 ; k++) {
								gui.but_karta[k].setText ("Karta " + (k+1));
							}
							break;
						}
					
					case 3:
						gui.lab_instrukcje.setText ("Postanowiles zwiekszyc stawke. O ile chcesz ja zwiekszyc? - ");
						gui.ilosc_zetonow_gracza = gracz[ktory_licytuje].ilosc_zetonow;
						oczekiwanie_na_button();
						if (wybor == -666) {
							gui.wyswietl_komunikat ("Rezygnacja", "Gracz " + gracz[ktory_licytuje].nazwa_gracza + " zrezygnowal z calej gry! Podziekujmy jemu za gre :) Niech spada.");
							gracz [ktory_licytuje].czy_fold = true;
							gracz [ktory_licytuje].czy_gra = false;
							ilu_gra--;
							for (int k = 0 ; k < 5 ; k++) {
								gui.but_karta[k].setText ("Karta " + (k+1));
							}
							gui.pole_zwiekszanie_stawki.setEnabled (false);
							gui.but_zwiekszanie_stawki.setEnabled (false);
							break;
						}
						else if (wybor == -9) {
							wyswietl_karty (ktory_licytuje);
							break;
						}
						else {
							zwiekszenie_stawki = gui.klik_gracza_int;
							stawka += zwiekszenie_stawki;
							zwiekszenie_stawki = stawka - zetony_w_grze [ktory_licytuje];
							zetony_w_grze [ktory_licytuje] += zwiekszenie_stawki;
							gracz [ktory_licytuje].ilosc_zetonow -= zwiekszenie_stawki;
							gui.wyswietl_komunikat ("Raise", "Gracz " + gracz[ktory_licytuje].nazwa_gracza + " zwiekszyl stawke.");
							gui.uaktualnij_stawke();
							gui.uaktualnij_zetony_na_stole();
							for (int k = 0 ; k < 5 ; k++) {
								gui.but_karta[k].setText ("Karta " + (k+1));
							}
							break;
						}
					
					case 4:
						for (int k = 0 ; k < 5 ; k++) {
							gui.but_karta[k].setText ("Karta " + (k+1));
							gui.but_karta[k].setEnabled (false);
						}
						zwiekszenie_stawki = stawka - zetony_w_grze [ktory_licytuje];
						gracz [ktory_licytuje].ilosc_zetonow -= zwiekszenie_stawki;
						zetony_w_grze [ktory_licytuje] += zwiekszenie_stawki;
						gui.wyswietl_komunikat ("Call", "Gracz " + gracz[ktory_licytuje].nazwa_gracza + " wyrownal swoje zetony do aktualnej stawki.");
						gui.uaktualnij_zetony_na_stole();
						for (int k = 0 ; k < 5 ; k++) {
							gui.but_karta[k].setText ("Karta " + (k+1));
						}
						break;
					
					case 5:
						for (int k = 0 ; k < 5 ; k++) {
							gui.but_karta[k].setText ("Karta " + (k+1));
							gui.but_karta[k].setEnabled (false);
						}
						gui.wyswietl_komunikat ("Check", "Gracz " + gracz[ktory_licytuje].nazwa_gracza + " postanowil zrezygnowac z tej parti.");
						gracz [ktory_licytuje].czy_fold = true;
						for (int k = 0 ; k < 5 ; k++) {
							gui.but_karta[k].setText ("Karta " + (k+1));
						}
						if (czy_bylo_all_in) {
							ilu_wykonalo_ruch_podczas_all_in++;
						}
						ilu_nie_fold--;
						break;
					
					case 6:
						gui.wyswietl_komunikat ("Check", "Gracz " + gracz[ktory_licytuje].nazwa_gracza + " postanowil zagrac vabank!");
						if (!czy_bylo_all_in) {
							ilu_wykonalo_ruch_podczas_all_in = 1;
							ilosc_ruchow_podczas_all_in = 0;
							for (int g = 0 ; g < ilosc_graczy ; g++) {
								if (gracz[g].czy_gra && !gracz[g].czy_fold) {
									ilosc_ruchow_podczas_all_in++;
								}
							}
							czy_bylo_all_in = true;
						}
						else {
							ilu_wykonalo_ruch_podczas_all_in++;
						}
						zetony_w_grze [ktory_licytuje] += gracz [ktory_licytuje].ilosc_zetonow;
						gracz [ktory_licytuje].ilosc_zetonow = 0;
						gui.lab_stawka.setText ("Aktualna stawka - ALL-IN");
						gui.uaktualnij_zetony_na_stole();
						for (int k = 0 ; k < 5 ; k++) {
							gui.but_karta[k].setText ("Karta " + (k+1));
						}
						break;
						
					case -9:
						wyswietl_karty (ktory_licytuje);
						break;
					
					case -666:
						gui.wyswietl_komunikat ("Rezygnacja", "Gracz " + gracz[ktory_licytuje].nazwa_gracza + " zrezygnowal z calej gry! Podziekujmy jemu za gre :) Niech spada.");
						gracz [ktory_licytuje].czy_fold = true;
						gracz [ktory_licytuje].czy_gra = false;
						ilu_gra--;
						for (int k = 0 ; k < 5 ; k++) {
							gui.but_karta[k].setText ("Karta " + (k+1));
						}
						break;
				}
			}
			
			if (ilu_gra < 2) {
				break;
			}
			if (ilu_nie_fold < 2) {
				break;
			}
			
			if (czy_bylo_all_in && wybor != -9) {
				if (ilu_wykonalo_ruch_podczas_all_in == ilosc_ruchow_podczas_all_in) {
					break;
				}
				
				if ((ktory_licytuje + 1) == ilosc_graczy) {
					ktory_licytuje = 0;
				}
				else {
					ktory_licytuje++;
				}
			}
			else {
				if ((!czy_wszyscy_wykonali_ruch) && (wybor != -9)) {
					gracz [ktory_licytuje].wykonal_ruch = true;
					ilu_wykonalo_ruch++;
					if (ilu_wykonalo_ruch == ilu_licytuje) {
						czy_wszyscy_wykonali_ruch = true;
					}
				}
				
				if (czy_wszyscy_wykonali_ruch) {
					czy_stawki_wyrownane = true;
					for (int g = 0 ; g < ilosc_graczy ; g++) {
						if ((gracz[g].czy_fold == false) && (zetony_w_grze[g] != stawka)) {
							czy_stawki_wyrownane = false;
						}
					}
				}
				
				if ((czy_wszyscy_wykonali_ruch == false || czy_stawki_wyrownane == false) && (wybor != -9)) {
					if ((ktory_licytuje + 1) == ilosc_graczy) {
						ktory_licytuje = 0;
					}
					else {
						ktory_licytuje++;
					}
				}
			}
			
		} while (!(czy_wszyscy_wykonali_ruch && czy_stawki_wyrownane));
		
		gui.but_check.setEnabled (false);
		gui.but_bet.setEnabled (false);
		gui.but_raise.setEnabled (false);
		gui.but_call.setEnabled (false);
		gui.but_fold.setEnabled (false);
		gui.but_all_in.setEnabled (false);
	}
	
	private static void wymiana_kart() {
		gui.but_schowaj.setEnabled (false);
		ktory_licytuje = kto_zaczyna_licytacje;
		do {
			if ((gracz [ktory_licytuje].czy_gra) && (!(gracz [ktory_licytuje].czy_fold))) {
				gui.wyswietl_komunikat ("Wymiana kart", "\nGraczu " + gracz[ktory_licytuje].nazwa_gracza + ". Mozesz teraz wymienic 0-4 karty.");
				gui.lab_instrukcje.setText (gracz[ktory_licytuje].nazwa_gracza + " - wpierw odslon karty, potem wymien tyle kart ile chcesz (max 4), na koncu zakoncz wymiane");
				gui.but_sprawdz.setEnabled (true);
				gui.but_wyswietl.setEnabled (true);
				
				oczekiwanie_na_button();
				if (gui.klik_gracza_int == -9) { 																// wyswietlenie kart
					wyswietl_karty (ktory_licytuje);
					gui.but_wyswietl.setEnabled (false);
					for (int k = 0 ; k < 5 ; k++) {
						gui.but_karta[k].setEnabled (true);
					}
					
					oczekiwanie_na_button();
					if (gui.klik_gracza_int == 10) {												// zakonczenie wymiany
						for (int k = 0 ; k < 5 ; k++) {
							gui.but_karta[k].setText ("Karta " + (k+1));
							gui.but_karta[k].setEnabled (false);
						}
						if ((ktory_licytuje + 1) == ilosc_graczy) {
							ktory_licytuje = 0;
							aktualny_gracz++;
						}
						else {
							ktory_licytuje++;
							aktualny_gracz++;
						}
					}
					else if (gui.klik_gracza_int == -666) {
						gui.wyswietl_komunikat ("Rezygnacja", "Gracz " + gracz[ktory_licytuje].nazwa_gracza + " zrezygnowal z calej gry! Podziekujmy jemu za gre :) Niech spada.");
						gracz [ktory_licytuje].czy_fold = true;
						gracz [ktory_licytuje].czy_gra = false;
						ilu_gra--;
						for (int k = 0 ; k < 5 ; k++) {
							gui.but_karta[k].setText ("Karta " + (k+1));
							gui.but_karta[k].setEnabled (false);
						}
						if ((ktory_licytuje + 1) == ilosc_graczy) {
							ktory_licytuje = 0;
							aktualny_gracz++;
						}
						else {
							ktory_licytuje++;
							aktualny_gracz++;
						}
					}
					else {																			// wymiana 1 karty
						gracz [ktory_licytuje].odbierz_karte (talia.zwroc_karte (ostatnia_karta).pokaz_znak(), talia.zwroc_karte (ostatnia_karta).pokaz_wartosc(), (gui.klik_gracza_int));
						ostatnia_karta++;
						
						oczekiwanie_na_button();
						if (gui.klik_gracza_int == 10) {												// zakonczenie wymiany kart
							for (int k = 0 ; k < 5 ; k++) {
								gui.but_karta[k].setText ("Karta " + (k+1));
								gui.but_karta[k].setEnabled (false);
							}
							if ((ktory_licytuje + 1) == ilosc_graczy) {
								ktory_licytuje = 0;
								aktualny_gracz++;
							}
							else {
								ktory_licytuje++;
								aktualny_gracz++;
							}
						}
						else if (gui.klik_gracza_int == -666) {
							gui.wyswietl_komunikat ("Rezygnacja", "Gracz " + gracz[ktory_licytuje].nazwa_gracza + " zrezygnowal z calej gry! Podziekujmy jemu za gre :) Niech spada.");
							gracz [ktory_licytuje].czy_fold = true;
							gracz [ktory_licytuje].czy_gra = false;
							ilu_gra--;
							for (int k = 0 ; k < 5 ; k++) {
								gui.but_karta[k].setText ("Karta " + (k+1));
								gui.but_karta[k].setEnabled (false);
							}
							if ((ktory_licytuje + 1) == ilosc_graczy) {
								ktory_licytuje = 0;
								aktualny_gracz++;
							}
							else {
								ktory_licytuje++;
								aktualny_gracz++;
							}
						}
						else {																		// wymiana 2 karty
							gracz [ktory_licytuje].odbierz_karte (talia.zwroc_karte (ostatnia_karta).pokaz_znak(), talia.zwroc_karte (ostatnia_karta).pokaz_wartosc(), (gui.klik_gracza_int));
							ostatnia_karta++;
							
							oczekiwanie_na_button();
							if (gui.klik_gracza_int == 10) {												// zakonczenie wymiany kart
								for (int k = 0 ; k < 5 ; k++) {
									gui.but_karta[k].setText ("Karta " + (k+1));
									gui.but_karta[k].setEnabled (false);
								}
								if ((ktory_licytuje + 1) == ilosc_graczy) {
									ktory_licytuje = 0;
									aktualny_gracz++;
								}
								else {
									ktory_licytuje++;
									aktualny_gracz++;
								}
							}
							else if (gui.klik_gracza_int == -666) {
								gui.wyswietl_komunikat ("Rezygnacja", "Gracz " + gracz[ktory_licytuje].nazwa_gracza + " zrezygnowal z calej gry! Podziekujmy jemu za gre :) Niech spada.");
								gracz [ktory_licytuje].czy_fold = true;
								gracz [ktory_licytuje].czy_gra = false;
								ilu_gra--;
								for (int k = 0 ; k < 5 ; k++) {
									gui.but_karta[k].setText ("Karta " + (k+1));
									gui.but_karta[k].setEnabled (false);
								}
								if ((ktory_licytuje + 1) == ilosc_graczy) {
									ktory_licytuje = 0;
									aktualny_gracz++;
								}
								else {
									ktory_licytuje++;
									aktualny_gracz++;
								}
							}
							else {																		// wymiana 3 karty
								gracz [ktory_licytuje].odbierz_karte (talia.zwroc_karte (ostatnia_karta).pokaz_znak(), talia.zwroc_karte (ostatnia_karta).pokaz_wartosc(), (gui.klik_gracza_int));
								ostatnia_karta++;
								
								oczekiwanie_na_button();
								if (gui.klik_gracza_int == 10) {												// zakonczenie wymiany kart
									for (int k = 0 ; k < 5 ; k++) {
										gui.but_karta[k].setText ("Karta " + (k+1));
										gui.but_karta[k].setEnabled (false);
									}
									if ((ktory_licytuje + 1) == ilosc_graczy) {
										ktory_licytuje = 0;
										aktualny_gracz++;
									}
									else {
										ktory_licytuje++;
										aktualny_gracz++;
									}
								}
								else if (gui.klik_gracza_int == -666) {
									gui.wyswietl_komunikat ("Rezygnacja", "Gracz " + gracz[ktory_licytuje].nazwa_gracza + " zrezygnowal z calej gry! Podziekujmy jemu za gre :) Niech spada.");
									gracz [ktory_licytuje].czy_fold = true;
									gracz [ktory_licytuje].czy_gra = false;
									ilu_gra--;
									for (int k = 0 ; k < 5 ; k++) {
										gui.but_karta[k].setText ("Karta " + (k+1));
										gui.but_karta[k].setEnabled (false);
									}
									if ((ktory_licytuje + 1) == ilosc_graczy) {
										ktory_licytuje = 0;
										aktualny_gracz++;
									}
									else {
										ktory_licytuje++;
										aktualny_gracz++;
									}
								}
								else {																		// wymiana 4 karty i ostatniej mozliwie karty
									gracz [ktory_licytuje].odbierz_karte (talia.zwroc_karte (ostatnia_karta).pokaz_znak(), talia.zwroc_karte (ostatnia_karta).pokaz_wartosc(), (gui.klik_gracza_int));
									ostatnia_karta++;
									for (int k = 0 ; k < 5 ; k++) {
										gui.but_karta[k].setEnabled (false);
									}
						
									oczekiwanie_na_button();
									if (gui.klik_gracza_int == -666) {
										gui.wyswietl_komunikat ("Rezygnacja", "Gracz " + gracz[ktory_licytuje].nazwa_gracza + " zrezygnowal z calej gry! Podziekujmy jemu za gre :) Niech spada.");
										gracz [ktory_licytuje].czy_fold = true;
										gracz [ktory_licytuje].czy_gra = false;
										ilu_gra--;
										for (int k = 0 ; k < 5 ; k++) {
											gui.but_karta[k].setText ("Karta " + (k+1));
											gui.but_karta[k].setEnabled (false);
										}
										if ((ktory_licytuje + 1) == ilosc_graczy) {
											ktory_licytuje = 0;
											aktualny_gracz++;
										}
										else {
											ktory_licytuje++;
											aktualny_gracz++;
										}
									}
									else if (gui.klik_gracza_int == 10) {
										for (int k = 0 ; k < 5 ; k++) {
											gui.but_karta[k].setText ("Karta " + (k+1));
											gui.but_karta[k].setEnabled (false);
										}
										if ((ktory_licytuje + 1) == ilosc_graczy) {
											ktory_licytuje = 0;
											aktualny_gracz++;
										}
										else {
											ktory_licytuje++;
											aktualny_gracz++;
										}
									}
								}
							}
						}
					}
				}
				else if (gui.klik_gracza_int == -666) {
					gui.wyswietl_komunikat ("Rezygnacja", "Gracz " + gracz[ktory_licytuje].nazwa_gracza + " zrezygnowal z calej gry! Podziekujmy jemu za gre :) Niech spada.");
					gracz [ktory_licytuje].czy_fold = true;
					gracz [ktory_licytuje].czy_gra = false;
					ilu_gra--;
					for (int k = 0 ; k < 5 ; k++) {
						gui.but_karta[k].setText ("Karta " + (k+1));
						gui.but_karta[k].setEnabled (false);
					}
					if ((ktory_licytuje + 1) == ilosc_graczy) {
						ktory_licytuje = 0;
						aktualny_gracz++;
					}
					else {
						ktory_licytuje++;
						aktualny_gracz++;
					}
				}
				else if (gui.klik_gracza_int == 10) {											// zakonczenie wymiany kart
					if ((ktory_licytuje + 1) == ilosc_graczy) {
						ktory_licytuje = 0;
						aktualny_gracz++;
					}
					else {
						ktory_licytuje++;
						aktualny_gracz++;
					}
				}
			}
			else {
				if ((ktory_licytuje + 1) == ilosc_graczy) {
					ktory_licytuje = 0;
					aktualny_gracz++;
				}
				else {
					ktory_licytuje++;
					aktualny_gracz++;
				}
			}
			
			if (ilu_gra < 2) {
				break;
			}
		} while (aktualny_gracz < ilosc_graczy);
		
		for (int k = 0 ; k < 5 ; k++) {
			gui.but_karta[k].setEnabled (false);
		}
		gui.but_sprawdz.setEnabled (false);
	}
	
	// po sortowaniu karty sa ustawione wartosciami od najmniejszej do najwiekszej - rosnaco
	private static void sortuj_karty() {
		Card temp;
		for (int x = 0 ; x < ilosc_graczy ; x++) {
			if (gracz[x].czy_gra && !gracz[x].czy_fold) {
				for (int y = 0 ; y < 4 ; y++) {
					for (int z = (y+1) ; z < 5 ; z++) {
						if (gracz[x].karty_gracza[y].pokaz_wartosc() > gracz[x].karty_gracza[z].pokaz_wartosc()) {
							temp = new Card (gracz[x].karty_gracza[y].pokaz_znak() , gracz[x].karty_gracza[y].pokaz_wartosc());
							gracz[x].karty_gracza[y].znak_karty = gracz[x].karty_gracza[z].pokaz_znak();
							gracz[x].karty_gracza[y].wartosc_karty = gracz[x].karty_gracza[z].pokaz_wartosc();
							gracz[x].karty_gracza[z].znak_karty = temp.pokaz_znak();
							gracz[x].karty_gracza[z].wartosc_karty = temp.pokaz_wartosc();
						}
					}
				}
			}
		}
	}
	
	private static void przypisz_uklady() {
		for (int g = 0 ; g < ilosc_graczy ; g++) {
			if ((gracz[g].czy_gra) && (!(gracz[g].czy_fold))) {
				if (set.czy_poker (gracz[g].zwroc_karty())) {
					gracz[g].ranking_ukladow = 1;
					uklady_graczy[g] = "Poker";
				}
				else if (set.czy_kareta (gracz[g].zwroc_karty())) {
					gracz[g].ranking_ukladow = 2;
					uklady_graczy[g] = "Kareta";
				}
				else if (set.czy_full (gracz[g].zwroc_karty())) {
					gracz[g].ranking_ukladow = 3;
					uklady_graczy[g] = "Full";
				}
				else if (set.czy_kolor (gracz[g].zwroc_karty())) {
					gracz[g].ranking_ukladow = 4;
					uklady_graczy[g] = "Kolor";
				}
				else if (set.czy_strit (gracz[g].zwroc_karty())) {
					gracz[g].ranking_ukladow = 5;
					uklady_graczy[g] = "Strit";
				}
				else if (set.czy_trojka (gracz[g].zwroc_karty())) {
					gracz[g].ranking_ukladow = 6;
					uklady_graczy[g] = "Trojka";
				}
				else if (set.czy_2_pary (gracz[g].zwroc_karty())) {
					gracz[g].ranking_ukladow = 7;
					uklady_graczy[g] = "Dwie Pary";
				}
				else if (set.czy_para (gracz[g].zwroc_karty())) {
					gracz[g].ranking_ukladow = 8;
					uklady_graczy[g] = "Jedna Para";
				}
				else {
					gracz[g].ranking_ukladow = 9;
					uklady_graczy[g] = "Najwyzsza Karta";
				}
			}
			else {
				gracz[g].ranking_ukladow = 0;
			}
		}
	}
	
	private static void porownaj_uklady() {
		for (int uklad = 1 ; uklad < 10 ; uklad++) {
			for (int g = 0 ; g < ilosc_graczy ; g++) {
				if (gracz[g].ranking_ukladow == uklad) {
					gracz_z_najwiekszym_ukladem [ilosc_najwiekszego_ukladu] = g;
					ilosc_najwiekszego_ukladu++;
					najwyzszy_uklad = uklad;
				}
			}
			if (ilosc_najwiekszego_ukladu > 0) {
				break;
			}
		}
	}
	
	private static void porownaj_szczegolowo_uklady() {
		if ( (najwyzszy_uklad == 1) || (najwyzszy_uklad == 5) ) {
			int temp_wartosc = 0, temp_gracz = 0;
			czy_remis = false;
			
			for (int g = 0 ; g < ilosc_najwiekszego_ukladu ; g++) {
				if ( gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[4].pokaz_wartosc() > temp_wartosc ) {
					temp_wartosc = gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[4].pokaz_wartosc();
					temp_gracz = gracz_z_najwiekszym_ukladem[g];
					czy_remis = false;
				}
				else if ( gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[4].pokaz_wartosc() == temp_wartosc ) 
					czy_remis = true;
			}
			gracz_z_najwiekszym_ukladem[0] = temp_gracz;
		}
		
		else if (najwyzszy_uklad == 2) {
			int temp_wartosc = 0, temp_gracz = 0;
			czy_remis = false;
			
			for ( int g = 0 ; g < ilosc_najwiekszego_ukladu ; g++ ) {
				for (int x = 0 ; x < 2 ; x++) { 
					if ( (gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x].pokaz_wartosc() == gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x+1].pokaz_wartosc()) && 
							(gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x].pokaz_wartosc() == gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x+2].pokaz_wartosc()) &&
							(gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x].pokaz_wartosc() == gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x+3].pokaz_wartosc()) &&
							(gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x].pokaz_wartosc() > temp_wartosc) ) {
						temp_wartosc = gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x].pokaz_wartosc();
						temp_gracz = gracz_z_najwiekszym_ukladem[g];
						czy_remis = false;
						break;
					}
					else if ( (gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x].pokaz_wartosc() == gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x+1].pokaz_wartosc()) && 
							(gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x].pokaz_wartosc() == gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x+2].pokaz_wartosc()) &&
							(gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x].pokaz_wartosc() == gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x+3].pokaz_wartosc()) &&
							(gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x].pokaz_wartosc() == temp_wartosc) ) {
						czy_remis = true;
						break;
					}
				}
			}
			gracz_z_najwiekszym_ukladem[0] = temp_gracz;
		}
		
		else if ( (najwyzszy_uklad == 3) || (najwyzszy_uklad == 6) ) {
			int temp_wartosc = 0, temp_gracz = 0;
			czy_remis = false;
			
			for ( int g = 0 ; g < ilosc_najwiekszego_ukladu ; g++ ) {
				for ( int x = 0 ; x < 3 ; x++ ) {
					if ( (gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x].pokaz_wartosc() == gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x+1].pokaz_wartosc()) &&
							(gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x].pokaz_wartosc() == gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x+2].pokaz_wartosc()) &&
							(gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x].pokaz_wartosc() > temp_wartosc) ) {
						temp_wartosc = gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x].pokaz_wartosc();
						temp_gracz = gracz_z_najwiekszym_ukladem[g];
						czy_remis = false;
						break;
					}
					else if ( (gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x].pokaz_wartosc() == gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x+1].pokaz_wartosc()) &&
							(gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x].pokaz_wartosc() == gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x+2].pokaz_wartosc()) &&
							(gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[x].pokaz_wartosc() == temp_wartosc) ) {
						czy_remis = true;
						break;
					}
				}
			}
			gracz_z_najwiekszym_ukladem[0] = temp_gracz;
		}
		
		else if ( (najwyzszy_uklad == 4) || (najwyzszy_uklad == 9) ) {
			int temp_wartosc = 0, temp_gracz = 0;
			czy_remis = false;
			
			for ( int k = 4 ; k >= 0 ; k-- ) {
				for (int g = 0 ; g < ilosc_najwiekszego_ukladu ; g++) {
					if ( gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc() > temp_wartosc ) {
						temp_wartosc = gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc();
						temp_gracz = gracz_z_najwiekszym_ukladem[g];
						czy_remis = false;
					}
					else if ( gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc() == temp_wartosc ) 
						czy_remis = true;
				}
				
				if (czy_remis == false) {
					break;
				}
			}
			gracz_z_najwiekszym_ukladem[0] = temp_gracz;
		}
		
		else if (najwyzszy_uklad == 7) {
			int temp_wartosc = 0, temp_gracz = 0;
			czy_remis = false;
			
			for ( int g = 0 ; g < ilosc_najwiekszego_ukladu ; g++ ) {
				for ( int k = 4 ; k >= 3 ; k-- ) {
					if ( (gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc() == gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k-1].pokaz_wartosc()) &&
							(gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc() > temp_wartosc) ) {
						temp_wartosc = gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc();
						temp_gracz = gracz_z_najwiekszym_ukladem[g];
						czy_remis = false;
					}
					else if ( (gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc() == gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k-1].pokaz_wartosc()) &&
							(gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc() == temp_wartosc) ) {
						czy_remis = true;
					}
				}
			}
			
			if (czy_remis == true) {
				temp_wartosc = 0;
				for ( int g = 0 ; g < ilosc_najwiekszego_ukladu ; g++ ) {
					for ( int k = 2 ; k >= 1 ; k-- ) {
						if ( (gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc() == gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k-1].pokaz_wartosc()) &&
								(gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc() > temp_wartosc) ) {
							temp_wartosc = gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc();
							temp_gracz = gracz_z_najwiekszym_ukladem[g];
							czy_remis = false;
						}
						else if ( (gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc() == gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k-1].pokaz_wartosc()) &&
								(gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc() == temp_wartosc) ) {
							czy_remis = true;
						}
					}
				}
			}
			
			if (czy_remis == true) {
				for ( int k = 4 ; k >= 0 ; k-- ) {
					temp_wartosc = 0;
					for (int g = 0 ; g < ilosc_najwiekszego_ukladu ; g++) {
						if ( gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc() > temp_wartosc ) {
							temp_wartosc = gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc();
							temp_gracz = gracz_z_najwiekszym_ukladem[g];
							czy_remis = false;
						}
						else if ( gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc() == temp_wartosc ) 
							czy_remis = true;
					}
					
					if (czy_remis == false) {
						break;
					}
				}
			}
			gracz_z_najwiekszym_ukladem[0] = temp_gracz;
		}
		
		else if (najwyzszy_uklad == 8) {
			int temp_wartosc = 0, temp_gracz = 0;
			czy_remis = false;
			
			for ( int g = 0 ; g < ilosc_najwiekszego_ukladu ; g++ ) {
				for ( int k = 4 ; k >= 1 ; k-- ) {
					if ( (gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc() == gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k-1].pokaz_wartosc()) &&
							(gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc() > temp_wartosc) ) {
						temp_wartosc = gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc();
						temp_gracz = gracz_z_najwiekszym_ukladem[g];
						czy_remis = false;
					}
					else if ( (gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc() == gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k-1].pokaz_wartosc()) &&
							(gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc() == temp_wartosc) ) {
						czy_remis = true;
					}
				}
			}
			
			if (czy_remis == true) {
				for ( int k = 4 ; k >= 0 ; k-- ) {
					temp_wartosc = 0;
					for (int g = 0 ; g < ilosc_najwiekszego_ukladu ; g++) {
						if ( gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc() > temp_wartosc ) {
							temp_wartosc = gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc();
							temp_gracz = gracz_z_najwiekszym_ukladem[g];
							czy_remis = false;
						}
						else if ( gracz [gracz_z_najwiekszym_ukladem[g]].karty_gracza[k].pokaz_wartosc() == temp_wartosc ) 
							czy_remis = true;
					}
					
					if (czy_remis == false) {
						break;
					}
				}
			}
			gracz_z_najwiekszym_ukladem[0] = temp_gracz;
		}
	}
	
	// ---------------------        MAIN        -----------------------
	
	public static void main (String args[]) {
		gui = new GUI();
		gui.wyswietl_komunikat ("Witajcie!", "Wpierw zapoznajcie sie z oknem, pozniej wybierzcie liczbe graczy");
		oczekiwanie_na_button();
		ilosc_graczy = gui.klik_gracza_int;
		new Table (ilosc_graczy);
		for (int g = 0 ; g < ilosc_graczy ; g++) {
			gui.lab_instrukcje.setText ("Graczu nr " + (g+1) + " - podaj swoja nazwe");
			oczekiwanie_na_button();
			gracz[g].nazwa_gracza = gui.klik_gracza_string;
		}
		gui.but_zapisz_nick.setEnabled (false);
		gui.pole_nick.setEnabled (false);
		gui.pole_zetony.setEnabled (true);
		gui.pole_wpisowe.setEnabled (true);
		gui.but_zapisz_zetony.setEnabled (true);
		gui.lab_instrukcje.setText ("Ustalmy poczatkowa liczbe zetonow kazdego gracza i wysokosc wpisowego");
		oczekiwanie_na_button();
		startowe_zetony = gui.klik_gracza_int;
		for (int g = 0 ; g < ilosc_graczy ; g++) {
			gracz[g].ilosc_zetonow = startowe_zetony;
		}
		wpisowe = gui.klik_gracza_int2;
		
		// parametry gry ustalone... teraz zaczynamy gierke :)
		
		do {
			
			ilu_nie_fold = 0;
			for (int g = 0 ; g < ilosc_graczy ; g++) {
				if (gracz[g].czy_gra) {
					ilu_nie_fold++;
				}
			}
			stawka = wpisowe;
			zwiekszenie_stawki = 0;
			ostatnia_karta = 0;
			aktualny_gracz = 0;
			ilosc_najwiekszego_ukladu = 0;
			czy_remis = false;
			czy_bylo_all_in = false;
			czy_byl_bet = false;
			
			gui.wyswietl_komunikat ("Rozpoczecie parti", "Rozpoczynamy partie nr " + ktora_partia);
			for (int g = 0 ; g < ilosc_graczy ; g++) {
				zetony_w_grze[g] = 0;
				gracz[g].czy_fold = false;
				
				if (gracz[g].czy_gra) {
					gracz[g].ilosc_zetonow -= wpisowe;
					zetony_w_grze[g] = wpisowe;
				}
			}
			gui.uaktualnij_stawke();
			gui.uaktualnij_zetony_na_stole();
			gui.uaktualnij_ranking();
			talia.potasuj_karty();
			rozdaj_karty();
			
			gui.wyswietl_komunikat ("Pierwsza licytacja", "Wpisowe pobrane, karty potasowane i rozdane. Przeprowadzimy pierwsza licytacje.");
			licytacja (true);
			if (ilu_gra < 2) {
				for (int g = 0 ; g < ilosc_graczy ; g++) {
					if (gracz[g].czy_gra) {
						gracz_z_najwiekszym_ukladem[0] = g;
						break;
					}
				}
				for (int g = 0 ; g < ilosc_graczy ; g++) {
					gracz [gracz_z_najwiekszym_ukladem[0]].ilosc_zetonow += zetony_w_grze[g];
				}
				break;
			}
			if (ilu_nie_fold < 2) {
				for (int g = 0 ; g < ilosc_graczy ; g++) {
					if (gracz[g].czy_gra) {
						gracz_z_najwiekszym_ukladem[0] = g;
						break;
					}
				}
				gui.wyswietl_komunikat ("Zwyciestwo!", "Wygrywa gracz " + gracz [gracz_z_najwiekszym_ukladem[0]].nazwa_gracza + "! Zabieraj swoje zetony!");
				gracz [gracz_z_najwiekszym_ukladem[0]].liczba_zwyciestw++;
				if (!czy_bylo_all_in) {
					for (int g = 0 ; g < ilosc_graczy ; g++) {
						gracz [gracz_z_najwiekszym_ukladem[0]].ilosc_zetonow += zetony_w_grze[g];
					}
				}
				else {
					for (int g = 0 ; g < ilosc_graczy ; g++) {
						if (zetony_w_grze[g] < zetony_w_grze [gracz_z_najwiekszym_ukladem[0]]) {
							gracz [gracz_z_najwiekszym_ukladem[0]].ilosc_zetonow += zetony_w_grze[g];
						}
						else {
							gracz [gracz_z_najwiekszym_ukladem[0]].ilosc_zetonow += zetony_w_grze [gracz_z_najwiekszym_ukladem[0]];
						}
					}
				}
				gui.uaktualnij_ranking();
				ranking = "";
				for (int g = 0 ; g < ilosc_graczy ; g++) {
					if (gracz[g].czy_gra) {
						ranking += "Gracz " + gracz[g].nazwa_gracza + " ma " + gracz[g].ilosc_zetonow + " zetonow i dotychczasowo wygral " + gracz[g].liczba_zwyciestw + " razy\n";
					}
				}
				gui.wyswietl_komunikat ("Aktualny ranking po kazdej parti.", ranking);
				
				gui.wyswietl_komunikat ("Sprawdzanie zetonow przed nastepna partia", "Przed nastepna partia sprawdzmy, czy gracze maja wystarczajaca ilosc zetonow na wpisowe.");
				for (int g = 0 ; g < ilosc_graczy ; g++) {
					if (gracz[g].czy_gra) {
						if (gracz[g].ilosc_zetonow >= wpisowe) {
							gui.wyswietl_komunikat ("Kontynuacja gry", "Gracz " + gracz[g].nazwa_gracza + " gra dalej.");
						}
						else {
							gui.wyswietl_komunikat ("Koniec gry", "Graczu " + gracz[g].nazwa_gracza + ". Masz juz za malo zetonow na nastepna partie. Koniec gry.");
							gracz[g].czy_gra = false;
							gracz[g].czy_fold = true;
							ilu_gra--;
						}
					}
				}
				continue;
			}
			
			gui.wyswietl_komunikat ("Koniec pierwszej licytacji", "Pierwsza licytacja w koncu zakonczona. Teraz mozecie wymienic karty.");
			wymiana_kart();
			if (ilu_gra < 2) {
				for (int g = 0 ; g < ilosc_graczy ; g++) {
					if (gracz[g].czy_gra) {
						gracz_z_najwiekszym_ukladem[0] = g;
						break;
					}
				}
				for (int g = 0 ; g < ilosc_graczy ; g++) {
					gracz [gracz_z_najwiekszym_ukladem[0]].ilosc_zetonow += zetony_w_grze[g];
				}
				break;
			}
			
			if (!czy_bylo_all_in) {
				gui.wyswietl_komunikat ("Koniec wymiany kart", "Wymieniliscie karty. Nie bylo zagrania All-in, wiec teraz przeprowadzimy druga licytacje.");
				licytacja (false);
				if (ilu_nie_fold < 2) {
					for (int g = 0 ; g < ilosc_graczy ; g++) {
						if (gracz[g].czy_gra) {
							gracz_z_najwiekszym_ukladem[0] = g;
							break;
						}
					}
					gui.wyswietl_komunikat ("Zwyciestwo!", "Wygrywa gracz " + gracz [gracz_z_najwiekszym_ukladem[0]].nazwa_gracza + "! Zabieraj swoje zetony!");
					gracz [gracz_z_najwiekszym_ukladem[0]].liczba_zwyciestw++;
					if (!czy_bylo_all_in) {
						for (int g = 0 ; g < ilosc_graczy ; g++) {
							gracz [gracz_z_najwiekszym_ukladem[0]].ilosc_zetonow += zetony_w_grze[g];
						}
					}
					else {
						for (int g = 0 ; g < ilosc_graczy ; g++) {
							if (zetony_w_grze[g] < zetony_w_grze [gracz_z_najwiekszym_ukladem[0]]) {
								gracz [gracz_z_najwiekszym_ukladem[0]].ilosc_zetonow += zetony_w_grze[g];
							}
							else {
								gracz [gracz_z_najwiekszym_ukladem[0]].ilosc_zetonow += zetony_w_grze [gracz_z_najwiekszym_ukladem[0]];
							}
						}
					}
					gui.uaktualnij_ranking();
					ranking = "";
					for (int g = 0 ; g < ilosc_graczy ; g++) {
						if (gracz[g].czy_gra) {
							ranking += "Gracz " + gracz[g].nazwa_gracza + " ma " + gracz[g].ilosc_zetonow + " zetonow i dotychczasowo wygral " + gracz[g].liczba_zwyciestw + " razy\n";
						}
					}
					gui.wyswietl_komunikat ("Aktualny ranking po kazdej parti.", ranking);
					
					gui.wyswietl_komunikat ("Sprawdzanie zetonow przed nastepna partia", "Przed nastepna partia sprawdzmy, czy gracze maja wystarczajaca ilosc zetonow na wpisowe.");
					for (int g = 0 ; g < ilosc_graczy ; g++) {
						if (gracz[g].czy_gra) {
							if (gracz[g].ilosc_zetonow >= wpisowe) {
								gui.wyswietl_komunikat ("Kontynuacja gry", "Gracz " + gracz[g].nazwa_gracza + " gra dalej.");
							}
							else {
								gui.wyswietl_komunikat ("Koniec gry", "Graczu " + gracz[g].nazwa_gracza + ". Masz juz za malo zetonow na nastepna partie. Koniec gry.");
								gracz[g].czy_gra = false;
								gracz[g].czy_fold = true;
								ilu_gra--;
							}
						}
					}
					continue;
				}
				if (ilu_gra < 2) {
					for (int g = 0 ; g < ilosc_graczy ; g++) {
						if (gracz[g].czy_gra) {
							gracz_z_najwiekszym_ukladem[0] = g;
							break;
						}
					}
					for (int g = 0 ; g < ilosc_graczy ; g++) {
						gracz [gracz_z_najwiekszym_ukladem[0]].ilosc_zetonow += zetony_w_grze[g];
					}
					break;
				}
			}
			else {
				gui.wyswietl_komunikat ("Koniec wymiany kart", "Jako, ze w pierwszej licytacji wystapilo zagranie all-in, wiec nie przeprowadzimy drugiej licytacji.");
			}
			
			gui.wyswietl_komunikat ("Koniec licytacji i wymian kart", "Zakonczylismy wszelkie licytacje i wymiane kart. Sprawdzmy teraz kto jakie ma karty i oglosmy wyniki!");
			sortuj_karty();
			przypisz_uklady();
			porownaj_uklady();
			ogloszenie_ukladow = "";
			for (int g = 0 ; g < ilosc_graczy ; g++) {
				if (gracz[g].czy_gra && !gracz[g].czy_fold) {
					ogloszenie_ukladow += ("Gracz " + gracz[g].nazwa_gracza + " ma nastepujacy uklad kart - " + uklady_graczy[g] + "\n");
				}
			}
			gui.wyswietl_komunikat ("Uklady graczy", ogloszenie_ukladow);
			if (ilosc_najwiekszego_ukladu == 1) {
				gui.wyswietl_komunikat ("Zwyciestwo!", "Wygrywa gracz " + gracz [gracz_z_najwiekszym_ukladem[0]].nazwa_gracza + "! Zabieraj swoje zetony!");
				gracz [gracz_z_najwiekszym_ukladem[0]].liczba_zwyciestw++;
				if (!czy_bylo_all_in) {
					for (int g = 0 ; g < ilosc_graczy ; g++) {
						gracz [gracz_z_najwiekszym_ukladem[0]].ilosc_zetonow += zetony_w_grze[g];
					}
				}
				else {
					for (int g = 0 ; g < ilosc_graczy ; g++) {
						if (zetony_w_grze[g] < zetony_w_grze [gracz_z_najwiekszym_ukladem[0]]) {
							gracz [gracz_z_najwiekszym_ukladem[0]].ilosc_zetonow += zetony_w_grze[g];
						}
						else {
							gracz [gracz_z_najwiekszym_ukladem[0]].ilosc_zetonow += zetony_w_grze [gracz_z_najwiekszym_ukladem[0]];
						}
					}
				}
				gui.uaktualnij_stawke();
				gui.uaktualnij_zetony_na_stole();
				gui.uaktualnij_ranking();
			}
			else if (ilosc_najwiekszego_ukladu > 1) {
				porownaj_szczegolowo_uklady();
				if (czy_remis == true) {
					gui.wyswietl_komunikat ("Remis", "W tej rozgrywce zaden gracz nie mial wygrywajacego ukladu kart - remis");
				}
				else {
					gui.wyswietl_komunikat ("Zwyciestwo!", "Wygrywa gracz " + gracz [gracz_z_najwiekszym_ukladem[0]].nazwa_gracza + "! Zabieraj swoje zetony!");
					gracz [gracz_z_najwiekszym_ukladem[0]].liczba_zwyciestw++;
					if (!czy_bylo_all_in) {
						for (int g = 0 ; g < ilosc_graczy ; g++) {
							gracz [gracz_z_najwiekszym_ukladem[0]].ilosc_zetonow += zetony_w_grze[g];
						}
					}
					else {
						for (int g = 0 ; g < ilosc_graczy ; g++) {
							if (zetony_w_grze[g] < zetony_w_grze [gracz_z_najwiekszym_ukladem[0]]) {
								gracz [gracz_z_najwiekszym_ukladem[0]].ilosc_zetonow += zetony_w_grze[g];
							}
							else {
								gracz [gracz_z_najwiekszym_ukladem[0]].ilosc_zetonow += zetony_w_grze [gracz_z_najwiekszym_ukladem[0]];
							}
						}
					}
				}
				gui.uaktualnij_stawke();
				gui.uaktualnij_zetony_na_stole();
				gui.uaktualnij_ranking();
			}
			
			ranking = "";
			for (int g = 0 ; g < ilosc_graczy ; g++) {
				if (gracz[g].czy_gra) {
					ranking += "Gracz " + gracz[g].nazwa_gracza + " ma " + gracz[g].ilosc_zetonow + " zetonow i dotychczasowo wygral " + gracz[g].liczba_zwyciestw + " razy\n";
				}
			}
			gui.wyswietl_komunikat ("Aktualny ranking po kazdej parti.", ranking);
			
			gui.wyswietl_komunikat ("Sprawdzanie zetonow przed nastepna partia", "Przed nastepna partia sprawdzmy, czy gracze maja wystarczajaca ilosc zetonow na wpisowe.");
			for (int g = 0 ; g < ilosc_graczy ; g++) {
				if (gracz[g].czy_gra) {
					if (gracz[g].ilosc_zetonow >= wpisowe) {
						gui.wyswietl_komunikat ("Kontynuacja gry", "Gracz " + gracz[g].nazwa_gracza + " gra dalej.");
					}
					else {
						gui.wyswietl_komunikat ("Koniec gry", "Graczu " + gracz[g].nazwa_gracza + ". Masz juz za malo zetonow na nastepna partie. Koniec gry.");
						gracz[g].czy_gra = false;
						gracz[g].czy_fold = true;
						ilu_gra--;
					}
				}
			}
			
			if (ilu_gra == 1) {
				for (int g = 0 ; g < ilosc_graczy ; g++) {
					if (gracz[g].czy_gra) {
						gracz_z_najwiekszym_ukladem[0] = g;
						break;
					}
				}
				break;
			}
			else if (ilu_gra == 0) {
				break;
			}
			
			ktora_partia++;
		} while (ilu_gra > 1);
		
		if (ilu_gra == 1) {
			gui.wyswietl_komunikat ("Koniec calej gry", 
					"Gra zakonczona! Ostatecznie wygral gracz " + gracz [gracz_z_najwiekszym_ukladem[0]].nazwa_gracza + "! " + 
					"Zabiera ze stolu " + gracz [gracz_z_najwiekszym_ukladem[0]].ilosc_zetonow + " zetonow." +
					"Wygral " + gracz [gracz_z_najwiekszym_ukladem[0]].liczba_zwyciestw + " razy. \nDo nastepnej gry, czesc i czolem!");
		}
		else if (ilu_gra == 0) {
			gui.wyswietl_komunikat ("Koniec calej gry", "Gra zakonczona! Dziwna sytuacja, gdyz nikt nie wygral." +
					"Sytuacja zdarzajaca sie raz na rok ale sie zdarza. Koniec gry.");
		}
	}
}