package com.company;

import java.util.Scanner;

public class Saldo {
    private double utarg;
    private final int ilosc_przelewow = 100;
    private final int ilosc_monet = 200;
    private final int ilosc_akceptowanych_nominalow = 7;
    private final int startowych_monet = 5;

    private final Pieniadz[] przelewy = new Pieniadz[ilosc_przelewow];
    private int przelewy_index = 0;
    private final Moneta[][] monety = new Moneta[ilosc_akceptowanych_nominalow][ilosc_monet];
    private int[] monety_index = new int[ilosc_akceptowanych_nominalow];




    public Saldo(){
        for(int i = 0; i < ilosc_akceptowanych_nominalow;i++){
            for (int j = 0; j < startowych_monet; j++) {
                monety[i][monety_index[i]] = new Moneta(Moneta.indexNaWartosc(i));
                monety_index[i]++;
            }
        }
    }

    @Override
    public String toString() {
        String zawartosc = "";
        String przelane = "";
        for (int i = 0;i<ilosc_akceptowanych_nominalow;i++){
            zawartosc += (monety_index[i])+"*" + Moneta.indexNaWartosc(i)+"\n";
        }
        for (int i = 0;i<przelewy_index;i++) {
            przelane += przelewy[i].getWartosc();
        }
            return "Saldo{" +
                "\nutarg=" + utarg +
                ",\nprzelewy=" + przelane +
                ",\nmonety=" + zawartosc +
                '}';
    }

    public boolean karta(double koszt){
        if(przelewy_index < ilosc_przelewow) {
            przelewy[przelewy_index] = new Pieniadz(koszt);
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
            wydajReszte(doWydania);

        for(int j = 0; j < i;j++){
            int index = Moneta.wartoscNaIndex(wartosci[j]);
            if (monety_index[index] >= ilosc_monet){
                System.out.println("Kasa pełna");
                break;
            }
            monety[index][monety_index[index]++] = new Moneta(wartosci[j]);
        }

        utarg += koszt;
        System.out.println(this);
        return true;

    }

    public void wydajReszte(double doWydania){
        int i = ilosc_akceptowanych_nominalow-1;
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
        if(proby==100)
            System.out.println("Brak możliości wydania reszty");
        else
            System.out.println("Wydano: " + wydano);

    }


}
