package com.company;

public class Moneta extends Pieniadz {

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
