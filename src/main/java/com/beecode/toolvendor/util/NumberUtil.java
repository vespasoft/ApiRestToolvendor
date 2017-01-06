/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.util;

import java.text.DecimalFormat;
import java.util.Random;

/**
 *
 * @author luisvespa
 */
public class NumberUtil {
    
    //--- metodo que valida si un dato es numerico
    public static boolean isNumeric(String cadena){
            try {
                    Integer.parseInt(cadena);
                    return true;
            } catch (NumberFormatException nfe){
                    return false;
            }
    }

    //--- metodo que valida si un dato es float
    public static boolean isFloat(String cadena){
            try {
                    Float.parseFloat(cadena);
                    return true;
            } catch (NumberFormatException nfe){
                    return false;
            }
    }
    
    //--- metodo que valida si un dato es float
    public static boolean isDouble(String cadena){
            try {
                    Double.parseDouble(cadena);
                    return true;
            } catch (NumberFormatException nfe){
                    return false;
            }
    }
    
    // Devuelve valor formateado a Miles
    public static String FormatCurrency(float valor){
            DecimalFormat formato = new DecimalFormat("###,###,###.00");
            String ValorFormat = formato.format(valor);

            return ValorFormat;
    }
    
    public static String getTwoDigitNum(Double num) {
        // String.format("%.2f", number)
        // DecimalFormat df = new DecimalFormat("#.##");
        // String twoDigitNum = df.format(num);
        String twoDigitNum = String.format("%.2f", num);

        return twoDigitNum;
    }

    public static int getRandomInteger() {
        Random rnd = new Random();
        int result= 0;
        // calcula un numero aleatoreo entero
        result = (int)(rnd.nextDouble() * 100);
        return result;
    }

    public static double getRandomDouble() {
        Random rnd = new Random();
        double result= 0;
        // calcula un numero aleatoreo entero
        result = (double)(rnd.nextDouble() * 100);
        return result;
    }
    
}
