/**
 * Created by Root on 16.10.2017.
 */

import java.util.Scanner;
import java.io.*;

public class Neuron {
    private double[][] dendryty;
    private double[] synapsy;
    private int[] odpowiedzi;

    private int iloscDanych;
    private double wspUczenia;

    public Neuron(int x, double y) {
        this.dendryty = new double[x][2];
        this.synapsy = new double[2];
        this.odpowiedzi = new int[x];

        this.iloscDanych = x;
        this.wspUczenia = y;
    }

    public void start() {
        synapsy[0] = Math.random();
        synapsy[1] = Math.random();

        String text;
        String el[];

        try {
            Scanner dane = new Scanner(new File("dane_uczace.txt"));

            for (int i = 0; i < iloscDanych; i++) {
                text = dane.nextLine();
                el = text.split("/");

                dendryty[i][0] = Double.parseDouble(el[0]);
                dendryty[i][1] = Double.parseDouble(el[1]);
                odpowiedzi[i] = Integer.parseInt(el[2]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        double sygnal, blad, pom;
        int out, max = 0;

        do {
            blad = 0;

            for (int i = 0; i < iloscDanych; i++) {
                sygnal = dendryty[i][0] * synapsy[0] + dendryty[i][1] * synapsy[1];

                if (sygnal >= 0) {
                    out = 1;
                } else {
                    out = -1;
                }

                pom = odpowiedzi[i] - out;

                synapsy[0] += wspUczenia * pom * dendryty[i][0];
                synapsy[1] += wspUczenia * pom * dendryty[i][1];

                blad += pom;
            }

            max++;
        } while (max < 100000 && blad != 0);

        if (max < 100000) {
            System.out.println("percepton nauczyl sie po: " + max + " razach\n");

            try {
                Scanner dane = new Scanner(new File("dane.txt"));

                for (int i = 0; i < iloscDanych; i++) {
                    text = dane.nextLine();
                    el = text.split("/");

                    dendryty[i][0] = Double.parseDouble(el[0]);
                    dendryty[i][1] = Double.parseDouble(el[1]);
                    odpowiedzi[i] = Integer.parseInt(el[2]);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        blad = 0;

        for (int i = 0; i < iloscDanych; i++) {
            sygnal = dendryty[i][0] * synapsy[0] + dendryty[i][1] * synapsy[1];

            if (sygnal >= 0) {
                out = 1;
            } else {
                out = -1;
            }

            System.out.println("\nliczby: " + dendryty[i][0] + " oraz: " + dendryty[i][1] + " odp: " + odpowiedzi[i] + " wynik: " + out);

            if (out != odpowiedzi[i]) {
                blad++;
            }
        }

        System.out.println("\nilosc bledow: " + blad);
    }
}