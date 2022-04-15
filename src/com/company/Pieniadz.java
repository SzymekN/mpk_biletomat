package com.company;

public class Pieniadz {

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
}
