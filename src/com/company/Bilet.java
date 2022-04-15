package com.company;

public class Bilet {

    public String rodzaj;
    public double cena;

    public Bilet(String r, double c){
        rodzaj = r;
        cena = c;
    }

    @Override
    public String toString() {
        return "Bilet{" +
                "rodzaj='" + rodzaj + '\'' +
                ", cena=" + cena +
                '}';
    }
}
