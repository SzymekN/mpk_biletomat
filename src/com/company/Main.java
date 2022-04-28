package com.company;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        Biletomat test = new Biletomat();
        while (true) {
            test.sprzedaz();
//            test.wypiszHistorie(LocalDate.now());
            System.out.println(test);
        }
    }

}
