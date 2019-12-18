import javax.swing.*;
import java.awt.event.*;

public class GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = -8119117790653572604L;
	
	private Table stol;
	public int klik_gracza_int, klik_gracza_int2, ilosc_zetonow_gracza;
	public String klik_gracza_string;
	public boolean czy_kliknal = false;
	JLabel lab_instrukcje, lab_ilu, lab_nick, lab_zetony, lab_wpisowe, lab_stawka, lab_zetony_na_stole, lab_ranking_zwyciestw, lab_opcje;
	JLabel lab_kreski1, lab_kreski2, lab_kreski3, lab_zwiekszanie_stawki;
	JButton but_dwoch, but_trzech, but_czterech, but_zapisz_nick, but_zapisz_zetony, but_zwiekszanie_stawki, but_check, but_bet, but_raise, but_call, but_fold, but_all_in;
	JButton but_wyswietl, but_sprawdz, but_schowaj, but_karta[], but_rezygnacja;
	JTextField pole_nick, pole_zetony, pole_wpisowe, pole_zwiekszanie_stawki;
	
	
	GUI () {
		super ("Poker Five Card Draw with Action - by Daniel & £ukasz");
		setBounds (300, 75, 600, 625);
    	setLayout (null);
		
		stol = new Table();
		
    	lab_instrukcje = new JLabel ("Teraz ustalcie liczbe graczy", JLabel.CENTER);
    	lab_instrukcje.setBounds (0, 5, 600, 25);
    	add (lab_instrukcje);
    	
    	lab_kreski1 = new JLabel ("_________________________________________________________________________________", JLabel.CENTER);
    	lab_kreski1.setBounds (0, 20, 595, 25);
    	add (lab_kreski1);
    	
    	lab_ilu = new JLabel ("Ilu graczy bedzie gralo?");
    	lab_ilu.setBounds (25, 50, 150, 25);
    	add (lab_ilu);
    	
    	but_dwoch = new JButton ("2");
    	but_dwoch.setBounds (200, 50, 45, 25);
    	but_dwoch.addActionListener (this);
    	add (but_dwoch);
    	
    	but_trzech = new JButton ("3");
    	but_trzech.setBounds (250, 50, 45, 25);
    	but_trzech.addActionListener (this);
    	add (but_trzech);
    	
    	but_czterech = new JButton ("4");
    	but_czterech.setBounds (300, 50, 45, 25);
    	but_czterech.addActionListener (this);
    	add (but_czterech);
    	
    	lab_nick = new JLabel ("W tym miejscu beda wpisywane nazwy graczy");
    	lab_nick.setBounds (25, 100, 275, 25);
    	add (lab_nick);
    	
    	pole_nick = new JTextField ();
    	pole_nick.setBounds (300, 100, 110, 25);
    	pole_nick.setEnabled (false);
    	add (pole_nick);
    	
    	but_zapisz_nick = new JButton ("Zapisz nazwe gracza");
    	but_zapisz_nick.setBounds (415, 100, 160, 25);
    	but_zapisz_nick.addActionListener (this);
    	but_zapisz_nick.setEnabled (false);
    	add (but_zapisz_nick);
    	
    	lab_zetony = new JLabel ("Tutaj wpisz startowa ilosc zetonow graczy");
    	lab_zetony.setBounds (25, 150, 250, 25);
    	add (lab_zetony);
    	
    	pole_zetony = new JTextField ();
    	pole_zetony.setBounds (275, 150, 50, 25);
    	pole_zetony.setEnabled (false);
    	add (pole_zetony);
    	
    	lab_wpisowe = new JLabel ("Tutaj wpisz wpisowe");
    	lab_wpisowe.setBounds (325, 150, 125, 25);
    	add (lab_wpisowe);
    	
    	pole_wpisowe = new JTextField ();
    	pole_wpisowe.setBounds (450, 150, 50, 25);
    	pole_wpisowe.setEnabled (false);
    	add (pole_wpisowe);
    	
    	but_zapisz_zetony = new JButton ("Zapisz");
    	but_zapisz_zetony.setBounds (500, 150, 75, 25);
    	but_zapisz_zetony.addActionListener (this);
    	but_zapisz_zetony.setEnabled (false);
    	add (but_zapisz_zetony);
    	
    	lab_kreski2 = new JLabel ("_________________________________________________________________________________", JLabel.CENTER);
    	lab_kreski2.setBounds (0, 175, 595, 25);
    	add (lab_kreski2);
    	
    	lab_stawka = new JLabel ("Tutaj bedzie wyswietlana aktualna stawka w parti", JLabel.CENTER);
    	lab_stawka.setBounds (0, 200, 600, 25);
    	add (lab_stawka);
    	
    	lab_zetony_na_stole = new JLabel ("Tutaj beda wyswietlone zetony graczy polozone na stole", JLabel.CENTER);
    	lab_zetony_na_stole.setBounds (0, 225, 600, 25);
    	add (lab_zetony_na_stole);
    	
    	lab_ranking_zwyciestw = new JLabel ("Tutaj bedzie wyswietlany ranking zwyciestw graczy", JLabel.CENTER);
    	lab_ranking_zwyciestw.setBounds (0, 250, 600, 25);
    	add (lab_ranking_zwyciestw);
    	
    	lab_kreski3 = new JLabel ("_________________________________________________________________________________", JLabel.CENTER);
    	lab_kreski3.setBounds (0, 275, 595, 25);
    	add (lab_kreski3);
    	
    	lab_opcje = new JLabel ("Opcje licytacji i wymiany kart", JLabel.CENTER);
    	lab_opcje.setBounds (0, 300, 600, 25);
    	add (lab_opcje);
    	
    	but_check = new JButton ("Check");
    	but_check.setBounds (25, 350, 75, 25);
    	but_check.addActionListener (this);
    	but_check.setEnabled (false);
    	add (but_check);
    	
    	but_bet = new JButton ("Bet");
    	but_bet.setBounds (110, 350, 75, 25);
    	but_bet.addActionListener (this);
    	but_bet.setEnabled (false);
    	add (but_bet);
    	
    	but_raise = new JButton ("Raise");
    	but_raise.setBounds (195, 350, 75, 25);
    	but_raise.addActionListener (this);
    	but_raise.setEnabled (false);
    	add (but_raise);
    	
    	but_call = new JButton ("Call");
    	but_call.setBounds (280, 350, 75, 25);
    	but_call.addActionListener (this);
    	but_call.setEnabled (false);
    	add (but_call);
    	
    	but_fold = new JButton ("Fold");
    	but_fold.setBounds (365, 350, 75, 25);
    	but_fold.addActionListener (this);
    	but_fold.setEnabled (false);
    	add (but_fold);
    	
    	but_all_in = new JButton ("All-in");
    	but_all_in.setBounds (450, 350, 75, 25);
    	but_all_in.addActionListener (this);
    	but_all_in.setEnabled (false);
    	add (but_all_in);
    	
    	lab_zwiekszanie_stawki = new JLabel ("O ile zwiekszyc stawke?");
    	lab_zwiekszanie_stawki.setBounds (25, 385, 150, 25);
    	add (lab_zwiekszanie_stawki);
    	
    	pole_zwiekszanie_stawki = new JTextField ();
    	pole_zwiekszanie_stawki.setBounds (200, 385, 75, 25);
    	pole_zwiekszanie_stawki.setEnabled (false);
    	add (pole_zwiekszanie_stawki);
    	
    	but_zwiekszanie_stawki = new JButton ("Zatwierdz zwiekszenie stawki");
    	but_zwiekszanie_stawki.setBounds (300, 385, 225, 25);
    	but_zwiekszanie_stawki.addActionListener (this);
    	but_zwiekszanie_stawki.setEnabled (false);
    	add (but_zwiekszanie_stawki);
    	
    	but_wyswietl = new JButton ("Wyswietl karty");
    	but_wyswietl.setBounds (25, 425, 125, 25);
    	but_wyswietl.addActionListener (this);
    	but_wyswietl.setEnabled (false);
    	add (but_wyswietl);
    	
    	but_sprawdz = new JButton ("Koniec wymiany kart");
    	but_sprawdz.setBounds (200, 425, 200, 25);
    	but_sprawdz.addActionListener (this);
    	but_sprawdz.setEnabled (false);
    	add (but_sprawdz);
    	
    	but_schowaj = new JButton ("Schowaj karty");
    	but_schowaj.setBounds (450, 425, 125, 25);
    	but_schowaj.addActionListener (this);
    	but_schowaj.setEnabled (false);
    	add (but_schowaj);
    	
    	but_karta = new JButton[5];
    	for (int k = 0, pos_x = 25 ; k < 5 ; k++, pos_x += 110) {
    		but_karta[k] = new JButton ("Karta " + (k+1));
    		but_karta[k].setBounds (pos_x, 475, 85, 50);
    		but_karta[k].addActionListener (this);
    		but_karta[k].setEnabled (false);
    		add (but_karta[k]);
    	}
    	
    	but_rezygnacja = new JButton ("Rezygnacja z gry");
    	but_rezygnacja.setBounds (200, 550, 200, 25);
    	but_rezygnacja.addActionListener (this);
    	but_rezygnacja.setEnabled (false);
    	add (but_rezygnacja);
    	
    	setVisible (true);
    	setResizable (false);
    	setDefaultCloseOperation (javax.swing.WindowConstants.EXIT_ON_CLOSE);
	}
	
	
	
	public void actionPerformed (ActionEvent ae) {
		Object source = ae.getSource();
		
		if (source == but_dwoch) {
			
			but_dwoch.setEnabled (false);
			but_trzech.setEnabled (false);
			but_czterech.setEnabled (false);
			pole_nick.setEnabled (true);
			but_zapisz_nick.setEnabled (true);
			klik_gracza_int = 2;
			czy_kliknal = true;
		}
		if (source == but_trzech) {
			
			but_dwoch.setEnabled (false);
			but_trzech.setEnabled (false);
			but_czterech.setEnabled (false);
			pole_nick.setEnabled (true);
			but_zapisz_nick.setEnabled (true);
			klik_gracza_int = 3;
			czy_kliknal = true;
		}
		if (source == but_czterech) {
			
			but_dwoch.setEnabled (false);
			but_trzech.setEnabled (false);
			but_czterech.setEnabled (false);
			pole_nick.setEnabled (true);
			but_zapisz_nick.setEnabled (true);
			klik_gracza_int = 4;
			czy_kliknal = true;
		}
		
		if (source == but_zapisz_nick) {
			if (pole_nick.getText().equals ("")) {
				JOptionPane.showMessageDialog (null, "Nic nie wpisales. Wpisuj! :)", "Error!", JOptionPane.ERROR_MESSAGE);
			}
			else {
				klik_gracza_string = pole_nick.getText();
				pole_nick.setText ("");
				czy_kliknal = true;
			}
		}
		
		if (source == but_zapisz_zetony) {
			try {
				if ( (Integer.parseInt (pole_zetony.getText()) < 1) || (Integer.parseInt (pole_wpisowe.getText()) < 0) ) {
					JOptionPane.showMessageDialog (null, "Ilosc zetonow i wpisowego musi byc liczba dodatnia, ponadto ilosc zetonow musi byc wieksza od 0 rzecz jasna :)", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				else if ( Integer.parseInt (pole_zetony.getText()) <= Integer.parseInt (pole_wpisowe.getText()) ) {
					JOptionPane.showMessageDialog (null, "Ilosc zetonow musi byc wieksza niz wpisowe na kazda partie.", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				else {
					klik_gracza_int = Integer.parseInt (pole_zetony.getText());
					klik_gracza_int2 = Integer.parseInt (pole_wpisowe.getText());
					but_zapisz_zetony.setEnabled (false);
					pole_zetony.setEnabled (false);
					pole_wpisowe.setEnabled (false);
					but_check.setEnabled (true);
					but_bet.setEnabled (true);
					but_fold.setEnabled (true);
					but_all_in.setEnabled (true);
					but_wyswietl.setEnabled (true);
					but_schowaj.setEnabled (true);
					but_rezygnacja.setEnabled (true);
					czy_kliknal = true;
				}
			}
			catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog (null, "Liczba zetonow i wpisowego musi byc oczywiscie liczba calkowita :)", "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if (source == but_check) {
			klik_gracza_int = 1;
			czy_kliknal = true;
		}
		
		if (source == but_bet) {
			klik_gracza_int = 2;
			but_check.setEnabled (false);
			but_bet.setEnabled (false);
			but_fold.setEnabled (false);
			but_all_in.setEnabled (false);
			pole_zwiekszanie_stawki.setEnabled (true);
			but_zwiekszanie_stawki.setEnabled (true);
			czy_kliknal = true;
		}
		
		if (source == but_raise) {
			klik_gracza_int = 3;
			but_check.setEnabled (false);
			but_bet.setEnabled (false);
			but_fold.setEnabled (false);
			but_all_in.setEnabled (false);
			pole_zwiekszanie_stawki.setEnabled (true);
			but_zwiekszanie_stawki.setEnabled (true);
			czy_kliknal = true;
		}
		
		if (source == but_call) {
			klik_gracza_int = 4;
			czy_kliknal = true;
		}
		
		if (source == but_fold) {
			klik_gracza_int = 5;
			czy_kliknal = true;
		}
		
		if (source == but_all_in) {
			klik_gracza_int = 6;
			czy_kliknal = true;
		}
		
		if (source == but_zwiekszanie_stawki) {
			try {
				if ( Integer.parseInt (pole_zwiekszanie_stawki.getText()) == 0 ) {
					JOptionPane.showMessageDialog (null, "Nie zartuj ;) Nie mozna zwiekszyc stawki o 0 zetonow. Wpisz liczbe calkowita wieksza od 0.", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				else if ( Integer.parseInt (pole_zwiekszanie_stawki.getText()) < 0 ) {
					JOptionPane.showMessageDialog (null, "Musisz wpisac liczbe calkowita dodatnia.", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				else if ( Integer.parseInt (pole_zwiekszanie_stawki.getText()) > ilosc_zetonow_gracza ) {
					JOptionPane.showMessageDialog (null, "Nie masz tylu zetonow przeciez! Dostepnych zetonow masz teraz: " + ilosc_zetonow_gracza, "Error!", JOptionPane.ERROR_MESSAGE);
				}
				else {
					klik_gracza_int = Integer.parseInt (pole_zwiekszanie_stawki.getText());
					pole_zwiekszanie_stawki.setText ("");
					pole_zwiekszanie_stawki.setEnabled (false);
					but_zwiekszanie_stawki.setEnabled (false);
					for (int k = 0 ; k < 5 ; k++) {
						but_karta[k].setText ("Karta " + (k+1));
						but_karta[k].setEnabled (false);
					}
					czy_kliknal = true;
				}
			}
			catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog (null, "Musisz wpisac liczbe calkowita", "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if (source == but_wyswietl) {
			klik_gracza_int = -9;
			czy_kliknal = true;
		}
		
		if (source == but_schowaj) {
			for (int k = 0 ; k < 5 ; k++) {
				but_karta[k].setText ("Karta " + (k+1));
			}
		}
		
		if (source == but_sprawdz) {
			klik_gracza_int = 10;
			czy_kliknal = true;
		}
		
		for (int k = 0 ; k < 5 ; k++) {
			if (source == but_karta[k]) {
				klik_gracza_int = k;
				but_karta[k].setEnabled (false);
				but_karta[k].setText ("Karta " + (k+1));
				czy_kliknal = true;
			}
		}
		
		if (source == but_rezygnacja) {
			klik_gracza_int = -666;
			czy_kliknal = true;
		}
	}
	
	public void uaktualnij_stawke() {
		lab_stawka.setText ("Aktualna stawka - " + stol.stawka);
	}

	public void uaktualnij_zetony_na_stole() {
		lab_zetony_na_stole.setText ("Zetony graczy na stole - ");
		for (int g = 0 ; g < stol.ilosc_graczy ; g++) {
			lab_zetony_na_stole.setText (lab_zetony_na_stole.getText() + stol.gracz[g].nazwa_gracza + " " + stol.zetony_w_grze[g] + " | ");
		}
	}	
	
	public void uaktualnij_ranking() {
		lab_ranking_zwyciestw.setText ("Aktualny ranking zwyciestw graczy - ");
		for (int g = 0 ; g < stol.ilosc_graczy ; g++) {
			lab_ranking_zwyciestw.setText (lab_ranking_zwyciestw.getText() + stol.gracz[g].nazwa_gracza + " " + stol.gracz[g].liczba_zwyciestw + " | ");
		}
	}
	
	public void wyswietl_komunikat (String tytul, String text) {
		JOptionPane.showMessageDialog (null, text, tytul, JOptionPane.INFORMATION_MESSAGE);
	}
}