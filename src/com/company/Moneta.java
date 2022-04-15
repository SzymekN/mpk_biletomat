package com.company;

public class Moneta extends Pieniadz {

    public double srednica;
    public enum Nominal{
        GR5(.05), GR10(.1),GR20(.2),GR50(.5),ZL1(1.),ZL2(2.),ZL5(5.);

        private double value;
        Nominal(double v){
            this.value = v;
        }

        public double getValue() {
            return value;
        }
    }

    @Override
    public String toString() {
        return "Moneta{" +
                "wartosc=" + getWartosc() +
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
        for(Nominal nominal : Nominal.values()){
            if (index == nominal.ordinal())
                return nominal.getValue();
        }
        return -1;
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
