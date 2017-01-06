/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.util;

/**
 *
 * @author luisvespa
 */
public class AppPreferences {
    
    public final static int TOKEN_PASSWORD_LENGTH = 8;
    
    public final static String CONST_USER_TYPE_ADMIN = "A";
    public final static String CONST_USER_TYPE_MOVIL = "M";
    
    public final static String MESSAGE_USER_FORGOT_FAILED = "The username or email is invalid.";
    public final static String MESSAGE_USER_AUTH_FAILED = "";
    public final static String MESSAGE_USER_NOT_ACCESS = "El id no existe o su usuario no tiene permisos suficientes para ejecutar esta petición";
    public final static String MESSAGE_USER_NOT_ACCESS_USER = "No tiene permisos suficientes para acceder a los datos de otro usuario";
    public final static String MESSAGE_HTTP_IS_EMPTY = "No hay registros para mostrar";
    public final static String MESSAGE_HTTP_ID_FAILED_NOT_ACCESS = "No se ha encontrado información para este Id o su usuario no tiene permisos suficientes para ejecutar a esta petición";
    public final static String MESSAGE_HTTP_ID_FAILED = "No se ha encontrado información para este Id, es invalido o no existe.";
    public final static String MESSAGE_HTTP_SAVE_OK = "El registro se guardo exitosamente.";
    public final static String MESSAGE_HTTP_UPDATE_OK = "El registro se actualizo exitosamente.";
    public final static String MESSAGE_HTTP_SAVE_FAILED = "No se pudo guardar el registro.";
    public final static String MESSAGE_HTTP_UPDATE_FAILED = "No se pudo actualizar el registro.";
    public final static String MESSAGE_HTTP_DELETE_OK = "El registro se eliminó exitosamente.";
    public final static String MESSAGE_HTTP_DELETE_FAILED = "No se pudo eliminar el registro.";
    
    public final static String MESSAGE_HTTP_SEND_SERVICEPAGE_OK = "Se ha enviado el email de la hoja de servicio exitosamente.";
    
}
