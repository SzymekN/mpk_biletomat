package com.company;

import java.time.LocalDate;
import java.util.Scanner;

public class Biletomat {

    private final String lokalizacja;
    private final static String[][] oferta = {
            {"20 minutowy","4.00"},
            {"60 minutowy lub 1 przejazdowy","6.00"},
            {"90 minutowy","8.00"},
            {"24 godzinny","17.00"},
            {"48 godzinny","35.00"},
            {"72 godzinny","50.00"},
            {"7 dniowy","56.00"}
    }; // ilosc roznych biletow, nazwa i cena
    private String[][] historia_transakcji = new String[100][4];    // historia transakcji wielkosci 100, 4 pola do zapisania
    private Bilet[] bilety = new Bilet[100];                        // bilety wydrukowane przez biletomat
    private int index_biletu = 0;                                   // index aktualnie za ostatnim stworzonym biletem
    private int index_transakcji = 0;                               // index za ostatnią transakcją w tablicy histioria_transankcji
    private final Saldo zarobek = new Saldo();                            // stan kasy w biletomacie

    Biletomat(){
        lokalizacja = "Nieznana";
    }

    Biletomat(String lok){
        lokalizacja = lok;
    }

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

    private class Bilet extends RodzajBiletu{
        private final LocalDate dataWydania;

        public Bilet(String r, double c){
            rodzaj = r;
            cena = c;
            dataWydania = LocalDate.now();
        }

        @Override
        public String toString() {
            return "Bilet{" +
                    "rodzaj='" + getRodzaj() + '\'' +
                    ", cena=" + getCena() + '\'' +
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

        boolean transakcjaUdana = false;
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

    public void wypiszHistorie(LocalDate data){
        System.out.println("Historia z dnia: "+ data);
        for (int i =0;i<index_transakcji;i++){
            String[] transakcja = historia_transakcji[i];
            if(transakcja[0].equals(data.toString()))
                System.out.println(transakcja[0]+" : "+transakcja[1]+" : " + transakcja[2]+" : "+transakcja[3]);
        }
    }

    public void wypiszOferte(){
        for (int i =0; i < oferta.length; i++){
            System.out.println((i+1)+". "+ oferta[i][0]+", cena: "+oferta[i][1]);
        }
    }


}
