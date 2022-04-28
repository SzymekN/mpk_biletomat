package com.company;

import java.util.Scanner;

public class Saldo {

    /**maksymalna ilosc przelewow do zapamietania*/
    private final int MAX_PRZELEWOW = 100;
    /**maksymalna ilosc egzemplarzy danej monety*/
    private final int MAX_MONET = 200;
    /**ilosc akceptowanych nominalow*/
    private final int ILOSC_NOMINALOW = 7;
    /**startowa ilosc monet kazdego nominalu*/
    private final int STARTOWYCH_MONET = 5;

    /**zarobione pieniadze*/
    private double utarg;
    /**historia przelewow - KOMPOZYCJA*/
    private final PlatnoscBezgotowkowa[] przelewy = new PlatnoscBezgotowkowa[MAX_PRZELEWOW];
    /**index za ostatnim przelewem*/
    private int przelewy_index = 0;
    /**monety w biletomacie - KOMPOZYCJA*/
    private final Moneta[][] monety = new Moneta[ILOSC_NOMINALOW][MAX_MONET];
    /**index za ostatnia moneta danego nominalu*/
    private int[] monety_index = new int[ILOSC_NOMINALOW];


    private abstract class Pieniadz {
        /**Wartość pieniądza*/
        private double wartosc;

        public Pieniadz(){
            this.setWartosc(0);
        }

        public Pieniadz(double wartosc){
            this.setWartosc(wartosc);
        }

        public double getWartosc() {
            return wartosc;
        }

        public void setWartosc(double wartosc) {
            this.wartosc = wartosc;
        }

        @Override
        public String toString() {
            return "Pieniadz{" +
                    "wartosc=" + wartosc +
                    '}';
        }
    }

    //DZIEDZICZENIE po klasie Pieniadz
    private class PlatnoscBezgotowkowa extends Pieniadz {


        /**Nr konta osoby która kupiła bilet*/
        private final int nrKontaKupujacego;
        /**Najewiększy numer konta - górny zakres losowania*/
        private final static int MAX = 9_999_999;
        /**Najmniejszy numer konta - dolny zakres losowania*/
        private final static int MIN = 1_000_000;

        private int wylosujKonto(){
            return (int) ((Math.random() *  (MAX - MIN)) + MIN);
        }

        PlatnoscBezgotowkowa(){
            super();
            nrKontaKupujacego = wylosujKonto();
        }

        PlatnoscBezgotowkowa(double w){
            super(w);
            nrKontaKupujacego = wylosujKonto();
        }

        public int getNrKontaKupujacego() {
            return nrKontaKupujacego;
        }

        @Override
        public String toString() {
            return "PlatnoscBezgotowkowa{" + super.toString() +
                    "nrKontaKupujacego=" + nrKontaKupujacego +
                    '}';
        }
    }

    //DZIEDZICZENIE po klasie Pieniadz
    private class Moneta extends Pieniadz{
        /**Srednica monety*/
        private double srednica;

        private enum Nominal{
            GR5(0.05), GR10(0.1),GR20(0.2),GR50(0.5),ZL1(1.0),ZL2(2.0),ZL5(5.0);

            private final double value;
            Nominal(double v){
                this.value = v;
            }

            public double getValue() {
                return value;
            }
        }


        @Override
        public String toString() {
            return "Moneta{" + super.toString() +
                    "srednica=" + srednica +
                    '}';
        }

        public static int wartoscNaIndex(double wartosc){
            for(Nominal nominal : Nominal.values()){
                if (wartosc == nominal.getValue())
                    return nominal.ordinal();
            }
            return -1;
        }

        public static double indexNaWartosc(int index){
            return Nominal.values()[index].getValue();
//        for(Nominal nominal : Nominal.values()){
//            if (index == nominal.ordinal())
//                return nominal.getValue();
//        }
//        return -1;
        }


        public Moneta(double wartosc){
            super(wartosc);

            // automat akceptuje monety o wartości powyżej 2gr
            if (wartosc == 0.05)
                srednica = 6;
            else if (wartosc == 0.1)
                srednica = 3;
            else if (wartosc == .2)
                srednica = 4;
            else if (wartosc == .5)
                srednica = 5;
            else if (wartosc == 1.)
                srednica = 8;
            else if (wartosc == 2.)
                srednica = 7;
            else if (wartosc == 5.)
                srednica = 10;

        }


    }

    public Saldo(){
        for(int i = 0; i < ILOSC_NOMINALOW;i++){
            for (int j = 0; j < STARTOWYCH_MONET; j++) {
                monety[i][monety_index[i]] = new Moneta(Moneta.indexNaWartosc(i));
                monety_index[i]++;
            }
        }
    }

    @Override
    public String toString() {
        String zawartosc = "";
        String przelane = "";
        for (int i = 0;i<ILOSC_NOMINALOW;i++){
            zawartosc += (monety_index[i])+"*" + Moneta.indexNaWartosc(i)+"\n";
        }
        for (int i = 0;i<przelewy_index;i++) {
            przelane += przelewy[i].getWartosc() +", ";
        }
            return "Saldo{" +
                "\nutarg=" + utarg +
                ",\nprzelewy=" + przelane +
                ",\nmonety=" + zawartosc +
                '}';
    }

    /**
     * Realuzje płatność kartą
     * @param koszt koszt przelewu
     * @return true jeśli płatność się udała
     * */
    public boolean karta(double koszt){
        if(przelewy_index < MAX_PRZELEWOW) {
            przelewy[przelewy_index] = new PlatnoscBezgotowkowa(koszt);
            przelewy_index++;
            System.out.println("Kwota przelewu: "+koszt);
            utarg += koszt;
        }
        else {
            System.out.println("Historia pełna");
            return false;
        }
        return true;
    }

    /**
     * Realuzje płatność gotówką
     * @param koszt koszt kupowanych biletów
     * @return true jeśli płatność się udała
     * */
    public boolean gotowka(double koszt){
        double wplacone = 0;
        double[] wartosci = new double[100];
        double doWydania = koszt - wplacone;
        int i = 0;
        char wybor;
        Scanner input = new Scanner(System.in);

        while (doWydania > 0){
            System.out.println("Zostało do zapłacenia: "+(doWydania));
            System.out.println("Wrzuć monetę:\n" +
                    "1. 5gr, " +
                    "2. 10gr, " +
                    "3. 20gr, " +
                    "4. 50gr, " +
                    "5. 1zł, " +
                    "6. 2zł, " +
                    "7. 5zł, " +
                    "8. Zwroc, " +
                    "9. Anuluj");

            wybor = input.next().charAt(0);

            switch (wybor){
                case '1':
                    wartosci[i] = 0.05; wplacone += .05; i++; break;
                case '2':
                    wartosci[i] = 0.1; wplacone += .1; i++; break;
                case '3':
                    wartosci[i] = 0.2; wplacone += .2; i++; break;
                case '4':
                    wartosci[i] = 0.5; wplacone += .5; i++; break;
                case '5':
                    wartosci[i] = 1.0; wplacone += 1.0; i++; break;
                case '6':
                    wartosci[i] = 2.0; wplacone += 2.0; i++; break;
                case '7':
                    wartosci[i] = 5.0; wplacone += 5.0; i++; break;
                case '8':
                    i = 0; wplacone=0; break;
                case '9':
                    return false;
            }

            doWydania = (double) Math.round((koszt - wplacone)*100)/100;

        }

        if(wplacone > koszt)
            wydajReszte(Math.abs(doWydania));

        for(int j = 0; j < i;j++){
            int index = Moneta.wartoscNaIndex(wartosci[j]);
            if (monety_index[index] >= MAX_MONET){
                System.out.println("Kasa pełna");
                break;
            }
            monety[index][monety_index[index]++] = new Moneta(wartosci[j]);
        }

        utarg += koszt;
//        System.out.println(this);
        return true;

    }


    /**
     * Wydaje resztę na podstawie stanu automatu
     * @param doWydania kwota do wydania
     * */
    public void wydajReszte(double doWydania){
        int i = ILOSC_NOMINALOW-1;
        int proby = 0;
        String wydano = "";
        while (doWydania != 0 && proby!=100){
            if(i<0)
                break;
            if(doWydania - Moneta.indexNaWartosc(i) < 0 || monety_index[i] <=0 ) {
                i--;
                continue;
            }
            else{
                wydano += Moneta.indexNaWartosc(i) + ", ";
                monety[i][--monety_index[i]] = null;
                doWydania = (double) Math.round((doWydania - Moneta.indexNaWartosc(i))*100) / 100;
            }
            proby++;
        }
        if(proby==100 || doWydania > 0)
            System.out.println("Brak możliości wydania reszty");
        else
            System.out.println("Wydano: " + wydano);

    }


}
