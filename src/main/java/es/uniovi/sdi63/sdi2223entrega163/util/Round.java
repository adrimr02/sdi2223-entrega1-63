package es.uniovi.sdi63.sdi2223entrega163.util;

public class Round {

    public static double twoCents(double importe) {
        return (double) Math.round( importe * 100) / 100;
    }

}
