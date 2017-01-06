/*
 * Fecha.java
 *
 */

package com.beecode.toolvendor.util;

import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.GregorianCalendar;

 /**
 * Clase que maneja diferentes transformaciones entre formatos de fecha y hora
 */
 public class DateUtil {
    
 /**
     * Constructur de Fecha
     */
 public DateUtil() {
    }
    
 /****                                   Constantes                                    ****/
  public static final int DIA = GregorianCalendar.DAY_OF_MONTH;
  public static final int MES = GregorianCalendar.MONTH;
  public static final int ANO = GregorianCalendar.YEAR;
  public static final int HORA = GregorianCalendar.HOUR;
  public static final int MINUTO = GregorianCalendar.MINUTE;
  public static final int SEGUNDO = GregorianCalendar.SECOND;
 
    /**
   * Retorna la fecha del sistema como un String en Formato DD_MM_YYYY
   * @return String
   */
  public static String ObtenerFechaSistemaSeparadoUnders() {
    java.util.Date fecha = new java.util.Date();
    SimpleDateFormat formato = new SimpleDateFormat("dd_MM_yyyy");
    String fechaActual = formato.format(fecha);
    return fechaActual;
  }
  
  /**
     * Metodo que devuelve un objeto String de la forma dd/mm/yyyy hh:mm:ss a partir de un Objeto java.util.Timestamp
     * @return String
     * @param fechaSql Timestamp
     */
  public static String ObtenerfechaHora(java.sql.Timestamp fechaSql) {      
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    String diaHora = formato.format(fechaSql);  
    return diaHora;
  }

  /**
   * Metodo que devuelve un objeto String de la forma dd/mm/yyyy a partir de un Objeto java.util.Timestamp
   * @param fechaSql Timestamp
   * @return String
   */
  public static String Obtenerfecha(java.sql.Timestamp fechaSql) {
      SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
      String dia = formato.format(fechaSql);
    return dia;
  }

  /**
   * Metodo que devuelve un objeto String de la forma hh:mm:ss a partir de un Objeto java.util.Timestamp
   * @param fechaSql Timestamp
   * @return String
   */
  public static String Obtenerhora(java.sql.Timestamp fechaSql) {
    SimpleDateFormat formato = new SimpleDateFormat("hh:mm:ss");
    String hora = formato.format(fechaSql);  
    return hora;          
  }
  
   /**
   * Metodo que devuelve un objeto String de la forma dd/mm/yyyy a partir de un Objeto java.util.Date
   * @param fechaSql Date
   * @return String
   */
  public static String Obtenerfecha(java.sql.Date fechaSql) {    
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    String fecha = formato.format(fechaSql);
    return fecha;
  }  
    
     /**
   * Metodo que devuelve un objeto String de la forma hh:mm:ss aa a partir de un Objeto java.util.Time
   * @param horaSql Time
   * @return String
   */
  public static String ObtenerHora(java.sql.Time horaSql) {    
    SimpleDateFormat formato = new SimpleDateFormat("hh:mm:ss aa");
    String hora = formato.format(horaSql);
    return hora;
  }
  
    /**
   * Metodo que devuelve un objeto java.util.Date a partir de un Objeto java.util.Timestamp
   * @param fechaSql Timestamp
   * @return java.util.Date
   */
  public static Date ObtenerDate(java.sql.Timestamp fechaSql) {
    Date dia = new Date(fechaSql.getTime());
    return dia;
  }
  
     /**
   * Metodo que devuelve un objeto java.util.Time a partir de un Objeto java.util.Timestamp
   * @param fechaSql Timestamp
   * @return java.util.Time
   */
  public static Time ObtenerTime(java.sql.Timestamp fechaSql) {
    Time hora = new Time(fechaSql.getTime());
    return hora;
  }
    
   /**
     * Metodo que devuelve un objeto java.util.Timestamp a partir de dos Objetos String el primero con formato dd/MM/yy y el segundo con formato hh:mm:ss
     * @param fechaSQL String
     * @param horaSql String
     * @return java.util.Timestamp
     */
  public static Timestamp ObtenerTimestamp(String fechaSQL, String horaSql) {
    Timestamp fechaHora = null;
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    try { 
        java.util.Date dia = formato.parse(fechaSQL + " " + horaSql);
        fechaHora = new Timestamp(dia.getTime());
    }
    catch (Exception excepcion) {
        fechaHora = null;
    }    
    return fechaHora;
  }          
  
    /**
   * Metodo que devuelve un objeto java.util.Date a partir de un Objeto String con formato dd/mm/yyyy
   * @param fechaSql String
   * @return java.util.Date
   */
  public static Date ObtenerDate(String fechaSql) {
    Date dia = null;
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    try { 
        java.util.Date diaAux = formato.parse(fechaSql);
        dia = new Date(diaAux.getTime());
    }
    catch (Exception excepcion) {
        System.out.println("Error en fecha "+excepcion);
        dia = null;
    }    
    return dia;
  }
  
   /**
   * Metodo que devuelve un objeto java.util.Time a partir de un Objeto String con formato hh:mm:ss
   * @param horaSql String
   * @return java.util.Time
   */
  public static Time ObtenerTime(String horaSql) {
    Time hora = null;
    SimpleDateFormat formato = new SimpleDateFormat("hh:mm:ss");
    try { 
        java.util.Date dia = formato.parse(horaSql);
        hora = new Time(dia.getTime());
    }
    catch (Exception excepcion) {
        hora = null;
    }    
    return hora;
  }   
    
  public static Date modificarDate(java.sql.Date fecha, int campo ,int modificar) {
    GregorianCalendar  calendario = new GregorianCalendar();
    Date dia = null;
    
    if( (campo == GregorianCalendar.DAY_OF_MONTH) || (campo == GregorianCalendar.MONTH) || (campo == GregorianCalendar.YEAR) ){
        calendario.setTimeInMillis(fecha.getTime());
        calendario.add(campo,modificar);
        dia = new Date(calendario.getTimeInMillis());        
    }
    return dia;        
  }
  
  public static Timestamp modificarTimestamp(java.sql.Timestamp fecha, int campo ,int modificar) {
    GregorianCalendar  calendario = new GregorianCalendar();
    Timestamp dia = null;
    
    if( (campo == GregorianCalendar.DAY_OF_MONTH) || (campo == GregorianCalendar.MONTH) || (campo == GregorianCalendar.YEAR) || (campo == GregorianCalendar.HOUR) || (campo == GregorianCalendar.MINUTE) || (campo == GregorianCalendar.SECOND) ){
        calendario.setTimeInMillis(fecha.getTime());
        calendario.add(campo,modificar);
        dia = new Timestamp(calendario.getTimeInMillis());        
    }
    return dia;        
  }
  
  public static Time modificarTime(java.sql.Time hora, int campo ,int modificar) {
    GregorianCalendar  calendario = new GregorianCalendar();
    Time dia = null;
    
    if( (campo == GregorianCalendar.HOUR) || (campo == GregorianCalendar.MINUTE) || (campo == GregorianCalendar.SECOND) ){
        calendario.setTimeInMillis(hora.getTime());
        calendario.add(campo,modificar);
        dia = new Time(calendario.getTimeInMillis());        
    }
    return dia;        
  }
  
  public static Timestamp sistemaTimestamp() {
    Timestamp diaHora = new Timestamp (System.currentTimeMillis());
    return diaHora;
  }
  
  public static Date sistemaDate() {
    Date dia = new Date (System.currentTimeMillis());
    return dia;
  }
  
  public static Time sistemaTime() {
    Time hora = new Time (System.currentTimeMillis());
    return hora;
  }
  
  
   public static java.util.Date ConvertirUtilDate(java.sql.Date fechaSql) {    
        java.util.Date fechaUtil = new java.util.Date(fechaSql.getTime());
    return fechaUtil;
  }  
   
  public static java.sql.Date ConvertirSqlDate(java.util.Date fechaUtil) {    
        java.sql.Date fechaSql = new java.sql.Date(fechaUtil.getTime());
    return fechaSql;
  }  
  
  public static java.sql.Date fechaMas(java.sql.Date fch, int dias){ 
     Calendar cal = new GregorianCalendar(); 
     cal.setTimeInMillis(fch.getTime()); 
     cal.add(Calendar.DATE, dias); 
     
     return new Date(cal.getTimeInMillis()); 
   } 

   public static java.sql.Date fechaMenos(java.sql.Date fch, int dias){ 
     Calendar cal = new GregorianCalendar(); 
     cal.setTimeInMillis(fch.getTime()); 
     cal.add(Calendar.DATE, - dias); 
     return new Date(cal.getTimeInMillis()); 
   }  

  
  
}