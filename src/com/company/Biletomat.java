package com.company;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Klasa realizujaca sprzedaż biletów
 */
public class Biletomat {

    /**lokalizacja biletomatu*/
    private final String lokalizacja;
    /**rodzaje roznych biletow, nazwa i cena*/
    private final static String[][] oferta = {
            {"20 minutowy","4.00"},
            {"60 minutowy lub 1 przejazdowy","6.00"},
            {"90 minutowy","8.00"},
            {"24 godzinny","17.00"},
            {"48 godzinny","35.00"},
            {"72 godzinny","50.00"},
            {"7 dniowy","56.00"}
    };

    /** historia transakcji wielkosci 100, 4 pola do zapisania - KOMPOZYCJA*/
    private final String[][] historia_transakcji = new String[100][4];

    /**bilety wydrukowane przez biletomat - KOMPOZYCJA*/
    private final Bilet[] bilety = new Bilet[100];

    /**index aktualnie za ostatnim stworzonym biletem*/
    private int index_biletu = 0;

    /**index za ostatnią transakcją w tablicy histioria_transankcji*/
    private int index_transakcji = 0;

    /**stan kasy w biletomacie - KOMPOZYCJA*/
    private final Saldo zarobek = new Saldo();

    Biletomat(){
        lokalizacja = "Nieznana";
    }

    Biletomat(String lok){
        lokalizacja = lok;
    }

    // klasa wewnętrzna
    private abstract class RodzajBiletu{
        protected String rodzaj;
        protected double cena;

        public String getRodzaj() {
            return rodzaj;
        }

        public double getCena() {
            return cena;
        }
    }

    // DZIEDZICZENIE po klasie Bilet
    private class Bilet extends RodzajBiletu{
        private final LocalDate dataWydania;

        public Bilet(String r, double c){
            rodzaj = r;
            cena = c;
            dataWydania = LocalDate.now();
        }

        @Override
        public String toString() {
            return "Bilet{" + '\n' +
                    "rodzaj='" + getRodzaj() + '\n' +
                    "cena=" + getCena() + '\n' +
                    "dataWydania='" + getDataWydania() +
                    '}';
        }

        public LocalDate getDataWydania() {
            return dataWydania;
        }
    }

    public String getLokalizacja(){
        return lokalizacja;
    }

    public void sprzedaz(){
        Scanner input = new Scanner(System.in);

        System.out.println("Wybierz bilet: ");
        wypiszOferte();
        System.out.println();
        int wybor = input.nextInt()-1;

        System.out.println("Podaj ilość: ");
        int ilosc = input.nextInt();
        double koszt = ilosc* Double.parseDouble(oferta[wybor][1]);

        System.out.println("Rodzaj płatności: \n1. Karta\n2. Gotówka ");
        int platnosc = input.nextInt();

        boolean transakcjaUdana;
        if(platnosc == 1)
            transakcjaUdana = zarobek.karta(koszt);
        else
            transakcjaUdana = zarobek.gotowka(koszt);

        if (transakcjaUdana){
            double cena = Double.parseDouble(oferta[wybor][1]);
            String rodzaj = oferta[wybor][0];
            for (int i =0; i< ilosc;i++) {
                bilety[index_biletu++] = new Bilet(rodzaj, cena);
            }

            historia_transakcji[index_transakcji][0] = LocalDate.now().toString();
            historia_transakcji[index_transakcji][1] = rodzaj;
            historia_transakcji[index_transakcji][2] = ""+ilosc;
            historia_transakcji[index_transakcji++][3] = ""+koszt;

        }

    }

    @Override
    public String toString() {
        String str = "";
        for (int i =0;i<index_transakcji;i++){
            String[] transakcja = historia_transakcji[i];
            str += transakcja[0]+" : "+transakcja[1]+" : " + transakcja[2]+" : "+transakcja[3] + "\n";
        }
        return str;
    }

    /**
     * Wypisuje wszytkie sprzedane biletu
     * @param data wypisuje bilety sprzedane tego dnia
     * */
    public void wypiszHistorie(LocalDate data){
        System.out.println("Historia z dnia: "+ data);
        for (int i =0;i<index_transakcji;i++){
            String[] transakcja = historia_transakcji[i];
            if(transakcja[0].equals(data.toString()))
                System.out.println(transakcja[0]+" : "+transakcja[1]+" : " + transakcja[2]+" : "+transakcja[3]);
        }
    }

    /**Wypisuje wszystkie możliwe do kupienia bilety*/
    public void wypiszOferte(){
        for (int i =0; i < oferta.length; i++){
            System.out.println((i+1)+". "+ oferta[i][0]+", cena: "+oferta[i][1]);
        }
    }


}
