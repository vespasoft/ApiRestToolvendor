/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.email.SendEmailOffice365;
import com.beecode.toolvendor.interfaces.EmailService;
import com.beecode.toolvendor.model.Customer;
import com.beecode.toolvendor.model.Order;
import com.beecode.toolvendor.model.User;
import com.beecode.toolvendor.model.Visit;
import com.beecode.toolvendor.util.AppPreferences;

/**
 *
 * @author luisvespa
 */
public class EmailServiceImpl implements EmailService {

    
    @Override
    public void SendEmailWellcome(User user) {
        String toEmail = user.getEmail();
        String emailSubject = "Bienvenido a Toolvendor App";
        String emailBodyAdmin = "<html>\n" +
                            "    <head>\n" +
                            "        <title>Toolvendor App</title>\n" +
                            "        <meta charset=\"UTF-8\">\n" +
                            "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                            "    </head>\n" +
                            "    <body>\n" +
                            "        <H3>Bienvenido al sistema Toolvendor App, </H3> \n" +
                            "        \n" +
                            "        <h4>Nunca fue tan facil mejorar los procesos de ventas de su compañia, Le invitamos a gestionar su flota de empleados mediante nuestra plataforma.\n" +
                            "            </h4> \n" +
                            "        \n" +
                            "        \n" +
                            "        <h4>A continuación le recordamos sus datos de acceso a su panel de control:</h4>\n" +
                            "        \n" +
                            "        <h4>username: "+ user.getEmail() +" </h4>\n" +
                            "        <h4>password: "+ user.getPassword() +"</h4>\n" +
                            "        \n" +
                            "        \n" +
                            "        <h4>puede ingresar al panel de control a traves del siguiente enlace: </h4>\n" +
                            "        <h4>www.toolvendorapp.com/cpanel</h4>\n" +
                            "        \n" +
                            "        <h4>Le deseamos el mayor exito posible ya que su satisfacción es nuestro compromiso.</h4>\n" +
                            "        <h4>Atentamente, el quipo de soporte Toolvendor App</h4>\n" +
                            "    </body>\n" +
                            "</html>";
        
        String emailBodyMovil = "<html>\n" +
                            "    <head>\n" +
                            "        <title>Toolvendor App</title>\n" +
                            "        <meta charset=\"UTF-8\">\n" +
                            "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                            "    </head>\n" +
                            "    <body>\n" +
                            "        <H3>Bienvenido al sistema Toolvendor App, </H3> \n" +
                            "        \n" +
                            "        <h4>Se ha creado su cuenta de usuario móvil satisfactoriamente, le invitamos a descargar el toolvendor app desde la tienda Play Store.\n" +
                            "            </h4> \n" +
                            "        \n" +
                            "        <h4>A continuación le recordamos sus datos de acceso al sistema:</h4>\n" +
                            "        \n" +
                            "        <h4>username: "+ user.getEmail() +" </h4>\n" +
                            "        <h4>password: "+ user.getPassword() +"</h4>\n" +
                            "        \n" +
                            "        \n" +
                            "        <h4>Le deseamos el mayor exito posible ya que su satisfacción es nuestro compromiso.</h4>\n" +
                            "        <h4>Atentamente, el quipo de soporte Toolvendor App</h4>\n" +
                            "    </body>\n" +
                            "</html>";
        
        SendEmailOffice365 se = new SendEmailOffice365();
        // depende del tipo de usuario A o M se envia un email distinto (formato).
        if ( user.getUsertype().getType().equalsIgnoreCase(AppPreferences.CONST_USER_TYPE_ADMIN))
            se.sendEmailHTML(toEmail, emailSubject, emailBodyAdmin);
        else if ( user.getUsertype().getType().equalsIgnoreCase(AppPreferences.CONST_USER_TYPE_MOVIL))
            se.sendEmailHTML(toEmail, emailSubject, emailBodyMovil);
    }

    @Override
    public void SendEmailForgot(User user) {
        String toEmail = user.getEmail();
        String emailSubject = "Bienvenido a Toolvendor App";
        String emailBody = "<html>\n" +
                        "    <head>\n" +
                        "        <title>Toolvendor App</title>\n" +
                        "        <meta charset=\"UTF-8\">\n" +
                        "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    </head>\n" +
                        "    <body>\n" +
                        "        <H3>Hola "+user.getName()+", </H3> \n" +
                        "        \n" +
                        "        <h4>Usted ha iniciado el proceso de recuperación de contraseña exitosamente, \n" +
                        "            se a generado por motivos de seguridad una nueva contraseña temporal, la cual deberá iniciar sessión en la aplicación movil para completar el proceso.</h4> \n" +
                        "            \n" +
                        "        \n" +
                        "        \n" +
                        "        <h3>Su nueva contraseña es la siguiente: "+user.getPassword()+"</h3>\n" +
                        "        \n" +
                        "        <h4>Una vez iniciado sessión puede cambiar su contraseña por una mas facil de recordar. </h4>\n" +
                        "        \n" +
                        "        <h4>Atentamente, el equipo de soporte Toolvendor App</h4>\n" +
                        "    </body>\n" +
                        "</html>";
        
        SendEmailOffice365 se = new SendEmailOffice365();
        se.sendEmailHTML(toEmail, emailSubject, emailBody);
    }
    
    @Override
    public void SendEmailProgramVisit(User user, Visit visit) {
        String toEmail = user.getEmail();
        String emailSubject = "Bienvenido a Toolvendor App";
        String emailBody = "<html>\n" +
                        "    <head>\n" +
                        "        <title>Toolvendor App</title>\n" +
                        "        <meta charset=\"UTF-8\">\n" +
                        "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    </head>\n" +
                        "    <body>\n" +
                        "        <H3>Toolvendor App, </H3> \n" +
                        "        \n" +
                        "        <h4>Estimado "+user.getName()+",</h4> \n" +
                        "        \n" +
                        "        <h4>Usted tiene una visita programada el día "+ visit.getScheduledDate()+",</h4> \n" +
                        "        \n" +
                        "        <h4>Datos de la Visita</h4>\n" +
                        "        \n" +
                        "        <h4>Hora: "+visit.getScheduledDate()+"</h4>\n" +
                        "        \n" +
                        "        <h4>Motivo: "+visit.getReason()+"</h4>\n" +
                        "        \n" +
                        "        <h4>Cliente: "+visit.getCustomerId()+" </h4>\n" +
                        "        \n" +
                        "        <h4>Le recordamos que debe iniciar la visita antes de la hora programada para \n" +
                        "            que pueda ser aceptada por el sistema de lo contrario cambiará a estatus: Vencida</h4>\n" +
                        "        \n" +
                        "        \n" +
                        "        <h4>Atentamente, el quipo de soporte Toolvendor App</h4>\n" +
                        "    </body>\n" +
                        "</html>";
        
        SendEmailOffice365 se = new SendEmailOffice365();
        se.sendEmailHTML(toEmail, emailSubject, emailBody);
    }

    @Override
    public void SendEmailVisit(Customer cstmr, Visit visit) {
        String toEmail = cstmr.getEmail();
        String emailSubject = "Bienvenido a Toolvendor App";
        String emailBody = "<html>\n" +
                        "    <head>\n" +
                        "        <title>Toolvendor App</title>\n" +
                        "        <meta charset=\"UTF-8\">\n" +
                        "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    </head>\n" +
                        "    <body>\n" +
                        "        <H3>Toolvendor App </H3> \n" +
                        "        \n" +
                        "        <h4>Estimado "+cstmr.getFirstName()+",</h4> \n" +
                        "        \n" +
                        "        <h4>Hemos finalizado exitosamente su visita,</h4> \n" +
                        "        \n" +
                        "        <h4>En este correo le enviamos adjunto un PDF con todo el detalle de su visita.</h4>\n" +
                        "        \n" +
                        "        \n" +
                        "        <h4>Atentamente, </h4>\n" +
                        "        \n" +
                        "        <h4>Ana Mariela Canelones </h4>\n" +
                        "        <h4>Gerente de Ventas </h4>\n" +
                        "    </body>\n" +
                        "</html>";
        
        SendEmailOffice365 se = new SendEmailOffice365();
        se.sendEmailHTML(toEmail, emailSubject, emailBody);
    }
    
    @Override
    public void SendEmailOrder(Customer cstmr, Order order) {
        String toEmail = cstmr.getEmail();
        String emailSubject = "Bienvenido a Toolvendor App";
        String emailBody = "<html>\n" +
                        "    <head>\n" +
                        "        <title>Toolvendor App</title>\n" +
                        "        <meta charset=\"UTF-8\">\n" +
                        "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    </head>\n" +
                        "    <body>\n" +
                        "        <H3>Toolvendor App </H3> \n" +
                        "        \n" +
                        "        <h4>Estimado "+cstmr.getFirstName()+",</h4> \n" +
                        "        \n" +
                        "        <h4>Hemos procesado exitosamente su cotización,</h4> \n" +
                        "        \n" +
                        "        <h4>En este correo le enviamos adjunto un PDF con todo el detalle de su solicitud.</h4>\n" +
                        "        \n" +
                        "        <h4>Los precios y disponibilidad estan sujetos a cambios sin previo aviso.</h4>\n" +
                        "        \n" +
                        "        <h4>Atentamente, </h4>\n" +
                        "        \n" +
                        "        <h4>Ana Mariela Canelones </h4>\n" +
                        "        <h4>Gerente de Ventas </h4>\n" +
                        "    </body>\n" +
                        "</html>";
        
        SendEmailOffice365 se = new SendEmailOffice365();
        se.sendEmailHTML(toEmail, emailSubject, emailBody);
    }
    
}
