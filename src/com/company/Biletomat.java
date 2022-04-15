package com.company;

import java.time.LocalDate;
import java.util.Scanner;

public class Biletomat {

    public String lokalizacja;
    public final static String[][] oferta = {
            {"20 minutowy","4.00"},
            {"60 minutowy lub 1 przejazdowy","6.00"},
            {"90 minutowy","8.00"},
            {"24 godzinny","17.00"},
            {"48 godzinny","35.00"},
            {"72 godzinny","50.00"},
            {"7 dniowy","56.00"}
    }; // ilosc roznych biletow, nazwa i cena
    public String[][] historia_transakcji = new String[100][4]; // historia transakcji wielkosci 100, 4 pola do zapisania
    public Bilet[] bilety = new Bilet[100];
    private int index_biletu = 0;
    private int index_transakcji = 0;
    public Saldo zarobek = new Saldo();

    public void sprzedaz(){
        Scanner input = new Scanner(System.in);

//        System.out.println(zarobek);
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
