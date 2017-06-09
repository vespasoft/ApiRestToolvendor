/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.beecode.toolvendor.email.SendEmail;
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
        String emailSubject = "Bienvenido a ToolvendorApp";
        String emailBodyMovil = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                    "\n" +
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                    "\n" +
                    "<head>\n" +
                    "\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                    "\n" +
                    "<title>Toolvendor App</title>\n" +
                    "\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
                    "\n" +
                    "</head>\n" +
                    "\n" +
                    "<body style=\"margin: 0; padding: 0;\">\n" +
                    "\n" +
                    "   <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "    <tr>\n" +
                    "       <td >\n" +
                    "         <table align=\"center\" bgcolor=\"#70bbd9\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse;\">\n" +
                    "\n" +
                    "            <tr>\n" +
                    "              <td align=\"center\" bgcolor=\"#70bbd9\" style=\"color: #ffffff; padding: 10px 0 30px 0; font-family: Arial, sans-serif; font-size: 28px;\">\n" +
                    "                 <b>Toolvendor App</b>\n" +
                    "              </td>\n" +
                    "            </tr>\n" +
                    "            <tr>\n" +
                    "              <td  bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px;\">\n" +
                    "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "                    <tr>\n" +
                    "                       <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 24px;\">\n" +
                    "                         <b>Bienvenido a Toolvendor App!</b>\n" +
                    "                       </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
                    "\n" +
                    "                      Nunca fue tan facil mejorar los procesos de ventas de su compañia, Le invitamos a gestionar su flota de empleados mediante nuestra plataforma.\n" +
                    "\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
                    "\n" +
                    "                        <h4>A continuación le recordamos sus datos de acceso a su panel de control:</h4>\n" +
                    "\n" +
                    "        username: "+ user.getEmail() +" \n" +
                    "        password: "+ user.getPassword() +"\n" +
                    "\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
                    "\n" +
                    "                      <br><br>Con Toolvendor App puedes administrar tu cartera de clientes y tener acceso directo a tu catalogo de productos.\n" +
                    "\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                       <td>\n" +
                    "                         <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "                          <tr>\n" +
                    "                             <td width=\"260\" valign=\"top\">\n" +
                    "                               <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "                                  <tr>\n" +
                    "                                    <td align=\"center\" style=\"padding: 30px 0 30px 0;\">\n" +
                    "                                       <img src=\"customers.png\" alt=\"\" width=\"100\" height=\"110\" style=\"display: block;\" />\n" +
                    "                                    </td>\n" +
                    "                                  </tr>\n" +
                    "                                  <tr>\n" +
                    "                                    <td style=\"padding: 25px 0 0 0;\">\n" +
                    "\n" +
                    "                                     Lorem ipsum dolor sit amet, consectetur adipiscing elit. In tempus adipiscing felis, sit amet blandit ipsum volutpat sed. Morbi porttitor, eget accumsan dictum, nisi libero ultricies ipsum, in posuere mauris neque at erat.\n" +
                    "\n" +
                    "                                    </td>\n" +
                    "                                  </tr>\n" +
                    "                              </table>\n" +
                    "                             </td>\n" +
                    "                             <td style=\"font-size: 0; line-height: 0;\" width=\"20\">\n" +
                    "\n" +
                    "                              &nbsp;\n" +
                    "\n" +
                    "                             </td>\n" +
                    "                             <td width=\"260\" valign=\"top\">\n" +
                    "                               <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "                                <tr>\n" +
                    "                                  <td  align=\"center\" style=\"padding: 30px 0 30px 0;\">\n" +
                    "                                     <img src=\"mail.png\" alt=\"\" width=\"100\" height=\"110\" style=\"display: block;\" />\n" +
                    "                                  </td>\n" +
                    "                                </tr>\n" +
                    "                                <tr>\n" +
                    "                                  <td style=\"padding: 25px 0 0 0;\">\n" +
                    "\n" +
                    "                                   Lorem ipsum dolor sit amet, consectetur adipiscing elit. In tempus adipiscing felis, sit amet blandit ipsum volutpat sed. Morbi porttitor, eget accumsan dictum, nisi libero ultricies ipsum, in posuere mauris neque at erat.\n" +
                    "\n" +
                    "                                  </td>\n" +
                    "                                </tr>\n" +
                    "                                </table>\n" +
                    "                             </td>\n" +
                    "                          </tr>\n" +
                    "                         </table>\n" +
                    "                       </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
                    "                        <br>\n" +
                    "                        <br>\n" +
                    "                        Le deseamos el mayor exito posible ya que su satisfacción es nuestro compromiso.\n" +
                    "                        Atentamente, el quipo de soporte Toolvendor App\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                 </table>\n" +
                    "              </td>\n" +
                    "            </tr>\n" +
                    "            <tr>\n" +
                    "              <td bgcolor=\"#ee4c50\" style=\"padding: 30px 30px 30px 30px;\">\n" +
                    "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "                  <tr>\n" +
                    "                    <td  align=\"right\">\n" +
                    "                      <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "                        <tr>\n" +
                    "                          <td>\n" +
                    "                          <a href=\"http://www.twitter.com/\">\n" +
                    "                           <img src=\"images/tw.gif\" alt=\"Twitter\" width=\"38\" height=\"38\" style=\"display: block;\" border=\"0\" />\n" +
                    "                          </a>\n" +
                    "                          </td>\n" +
                    "\n" +
                    "                        <td style=\"font-size: 0; line-height: 0;\" width=\"20\">&nbsp;</td>\n" +
                    "                          <td>\n" +
                    "                          <a href=\"http://www.twitter.com/\">\n" +
                    "                           <img src=\"images/fb.gif\" alt=\"Facebook\" width=\"38\" height=\"38\" style=\"display: block;\" border=\"0\" />\n" +
                    "                          </a>\n" +
                    "                          </td>\n" +
                    "                        </tr>\n" +
                    "                        </table>\n" +
                    "                    </td>\n" +
                    "                    <td style=\"color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\">\n" +
                    "                     &reg; Toolvendor App, beecode 2017<br/>\n" +
                    "                     <a href=\"#\" style=\"color: #ffffff;\"><font color=\"#ffffff\">Unsubscribe</font></a> to this newsletter instantly\n" +
                    "\n" +
                    "                    </td>\n" +
                    "                  </tr>\n" +
                    "                </table>\n" +
                    "              </td>\n" +
                    "            </tr>\n" +
                    "          </table>\n" +
                    "       </td>\n" +
                    "    </tr>\n" +
                    "   </table>\n" +
                    "\n" +
                    "</body>\n" +
                    "\n" +
                    "</html>";
        String emailBodyAdmin = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                    "\n" +
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                    "\n" +
                    "<head>\n" +
                    "\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                    "\n" +
                    "<title>Toolvendor App</title>\n" +
                    "\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
                    "\n" +
                    "</head>\n" +
                    "\n" +
                    "<body style=\"margin: 0; padding: 0;\">\n" +
                    "\n" +
                    "   <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "    <tr>\n" +
                    "       <td >\n" +
                    "         <table align=\"center\" bgcolor=\"#70bbd9\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse;\">\n" +
                    "\n" +
                    "            <tr>\n" +
                    "              <td align=\"center\" bgcolor=\"#70bbd9\" style=\"color: #ffffff; padding: 10px 0 30px 0; font-family: Arial, sans-serif; font-size: 28px;\">\n" +
                    "                 <b>Toolvendor App</b>\n" +
                    "              </td>\n" +
                    "            </tr>\n" +
                    "            <tr>\n" +
                    "              <td  bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px;\">\n" +
                    "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "                    <tr>\n" +
                    "                       <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 24px;\">\n" +
                    "                         <b>Bienvenido a Toolvendor App!</b>\n" +
                    "                       </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
                    "\n" +
                    "                      Nunca fue tan facil mejorar los procesos de ventas de su compañia, Le invitamos a gestionar su flota de empleados mediante nuestra plataforma.\n" +
                    "\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
                    "\n" +
                    "                        <h4>A continuación le recordamos sus datos de acceso a su panel de control:</h4>\n" +
                    "\n" +
                    "        username: "+ user.getEmail() +" \n" +
                    "        password: "+ user.getPassword() +"\n" +
                    "\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
                    "\n" +
                    "                      <br><br>Con Toolvendor App puedes administrar tu cartera de clientes y tener acceso directo a tu catalogo de productos.\n" +
                    "\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                       <td>\n" +
                    "                         <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "                          <tr>\n" +
                    "                             <td width=\"260\" valign=\"top\">\n" +
                    "                               <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "                                  <tr>\n" +
                    "                                    <td align=\"center\" style=\"padding: 30px 0 30px 0;\">\n" +
                    "                                       <img src=\"customers.png\" alt=\"\" width=\"100\" height=\"110\" style=\"display: block;\" />\n" +
                    "                                    </td>\n" +
                    "                                  </tr>\n" +
                    "                                  <tr>\n" +
                    "                                    <td style=\"padding: 25px 0 0 0;\">\n" +
                    "\n" +
                    "                                     Lorem ipsum dolor sit amet, consectetur adipiscing elit. In tempus adipiscing felis, sit amet blandit ipsum volutpat sed. Morbi porttitor, eget accumsan dictum, nisi libero ultricies ipsum, in posuere mauris neque at erat.\n" +
                    "\n" +
                    "                                    </td>\n" +
                    "                                  </tr>\n" +
                    "                              </table>\n" +
                    "                             </td>\n" +
                    "                             <td style=\"font-size: 0; line-height: 0;\" width=\"20\">\n" +
                    "\n" +
                    "                              &nbsp;\n" +
                    "\n" +
                    "                             </td>\n" +
                    "                             <td width=\"260\" valign=\"top\">\n" +
                    "                               <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "                                <tr>\n" +
                    "                                  <td  align=\"center\" style=\"padding: 30px 0 30px 0;\">\n" +
                    "                                     <img src=\"mail.png\" alt=\"\" width=\"100\" height=\"110\" style=\"display: block;\" />\n" +
                    "                                  </td>\n" +
                    "                                </tr>\n" +
                    "                                <tr>\n" +
                    "                                  <td style=\"padding: 25px 0 0 0;\">\n" +
                    "\n" +
                    "                                   Lorem ipsum dolor sit amet, consectetur adipiscing elit. In tempus adipiscing felis, sit amet blandit ipsum volutpat sed. Morbi porttitor, eget accumsan dictum, nisi libero ultricies ipsum, in posuere mauris neque at erat.\n" +
                    "\n" +
                    "                                  </td>\n" +
                    "                                </tr>\n" +
                    "                                </table>\n" +
                    "                             </td>\n" +
                    "                          </tr>\n" +
                    "                         </table>\n" +
                    "                       </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                      <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
                    "                        <br>\n" +
                    "                        <br>\n" +
                    "                        Le deseamos el mayor exito posible ya que su satisfacción es nuestro compromiso.\n" +
                    "                        Atentamente, el quipo de soporte Toolvendor App\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                 </table>\n" +
                    "              </td>\n" +
                    "            </tr>\n" +
                    "            <tr>\n" +
                    "              <td bgcolor=\"#ee4c50\" style=\"padding: 30px 30px 30px 30px;\">\n" +
                    "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "                  <tr>\n" +
                    "                    <td  align=\"right\">\n" +
                    "                      <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "                        <tr>\n" +
                    "                          <td>\n" +
                    "                          <a href=\"http://www.twitter.com/\">\n" +
                    "                           <img src=\"images/tw.gif\" alt=\"Twitter\" width=\"38\" height=\"38\" style=\"display: block;\" border=\"0\" />\n" +
                    "                          </a>\n" +
                    "                          </td>\n" +
                    "\n" +
                    "                        <td style=\"font-size: 0; line-height: 0;\" width=\"20\">&nbsp;</td>\n" +
                    "                          <td>\n" +
                    "                          <a href=\"http://www.twitter.com/\">\n" +
                    "                           <img src=\"images/fb.gif\" alt=\"Facebook\" width=\"38\" height=\"38\" style=\"display: block;\" border=\"0\" />\n" +
                    "                          </a>\n" +
                    "                          </td>\n" +
                    "                        </tr>\n" +
                    "                        </table>\n" +
                    "                    </td>\n" +
                    "                    <td style=\"color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\">\n" +
                    "                     &reg; Toolvendor App, beecode 2017<br/>\n" +
                    "                     <a href=\"#\" style=\"color: #ffffff;\"><font color=\"#ffffff\">Unsubscribe</font></a> to this newsletter instantly\n" +
                    "\n" +
                    "                    </td>\n" +
                    "                  </tr>\n" +
                    "                </table>\n" +
                    "              </td>\n" +
                    "            </tr>\n" +
                    "          </table>\n" +
                    "       </td>\n" +
                    "    </tr>\n" +
                    "   </table>\n" +
                    "\n" +
                    "</body>\n" +
                    "\n" +
                    "</html>";
        
        SendEmail se = new SendEmail();
        // depende del tipo de usuario A o M se envia un email distinto (formato).
        if ( user.getUsertype().getType().equalsIgnoreCase(AppPreferences.CONST_USER_TYPE_ADMIN)) {
            se.SendMailTSL(toEmail, emailSubject, emailBodyAdmin, "text/html");
            //se.SendMailSSL(toEmail, emailSubject, emailBodyAdmin, "text/html");
        }
            
        else if ( user.getUsertype().getType().equalsIgnoreCase(AppPreferences.CONST_USER_TYPE_MOVIL)) {
            se.SendMailTSL(toEmail, emailSubject, emailBodyMovil, "text/html");
            //se.SendMailSSL(toEmail, emailSubject, emailBodyAdmin, "text/html");
        }
    }

    @Override
    public void SendEmailForgot(User user) {
        String toEmail = user.getEmail();
        String emailSubject = "Restauracion de contraseña";
        String emailBody = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
            "\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "\n" +
            "<head>\n" +
            "\n" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
            "\n" +
            "<title>Toolvendor App</title>\n" +
            "\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
            "\n" +
            "</head>\n" +
            "\n" +
            "<body style=\"margin: 0; padding: 0;\">\n" +
            "\n" +
            "   <table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
            "    <tr>\n" +
            "       <td >\n" +
            "         <table align=\"center\" bgcolor=\"#70bbd9\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse;\">\n" +
            "\n" +
            "            <tr>\n" +
            "              <td align=\"center\" bgcolor=\"#70bbd9\" style=\"color: #ffffff; padding: 10px 0 30px 0; font-family: Arial, sans-serif; font-size: 28px;\">\n" +
            "                 <b>Toolvendor App</b>\n" +
            "              </td>\n" +
            "            </tr>\n" +
            "            <tr>\n" +
            "              <td  bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px;\">\n" +
            "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
            "                    <tr>\n" +
            "                       <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 24px;\">\n" +
            "                         <b>Hola "+user.getName()+",</b>\n" +
            "                       </td>\n" +
            "                    </tr>\n" +
            "                    <tr>\n" +
            "                      <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
            "                        <br>\n" +
            "                        Usted ha iniciado el proceso de restauración de contraseña satisfactoriamente,\n" +
            "                        por motivos de seguridad se ha generado una nueva contraseña temporal\n" +
            "                        con la cual debe iniciar sessión para completar el proceso.<br><br>\n" +
            "\n" +
            "                      </td>\n" +
            "                    </tr>\n" +
            "                    <tr>\n" +
            "                      <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
            "\n" +
            "                        <h3>Hemos generado una nueva contraseña temporal: "+user.getPassword()+"</h3>\n" +
            "\n" +
            "                      </td>\n" +
            "                    </tr>\n" +
            "                    <tr>\n" +
            "                      <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
            "\n" +
            "                      <br><br>Luego de haber iniciado sessión le recomendamos cambiar su contraseña por una mas facil de recordar.\n" +
            "\n" +
            "                      </td>\n" +
            "                    </tr>\n" +
            "                    <tr>\n" +
            "                      <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
            "                        <br>\n" +
            "                        <br>\n" +
            "                          Atentamente, el equipo de soporte ToolvendorApp\n" +
            "                      </td>\n" +
            "                    </tr>\n" +
            "                 </table>\n" +
            "              </td>\n" +
            "            </tr>\n" +
            "            <tr>\n" +
            "              <td bgcolor=\"#ee4c50\" style=\"padding: 30px 30px 30px 30px;\">\n" +
            "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
            "                  <tr>\n" +
            "                    <td  align=\"right\">\n" +
            "                      <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
            "                        <tr>\n" +
            "                          <td>\n" +
            "                          <a href=\"http://www.twitter.com/\">\n" +
            "                           <img src=\"images/tw.gif\" alt=\"Twitter\" width=\"38\" height=\"38\" style=\"display: block;\" border=\"0\" />\n" +
            "                          </a>\n" +
            "                          </td>\n" +
            "\n" +
            "                        <td style=\"font-size: 0; line-height: 0;\" width=\"20\">&nbsp;</td>\n" +
            "                          <td>\n" +
            "                          <a href=\"http://www.twitter.com/\">\n" +
            "                           <img src=\"images/fb.gif\" alt=\"Facebook\" width=\"38\" height=\"38\" style=\"display: block;\" border=\"0\" />\n" +
            "                          </a>\n" +
            "                          </td>\n" +
            "                        </tr>\n" +
            "                        </table>\n" +
            "                    </td>\n" +
            "                    <td style=\"color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\">\n" +
            "                     &reg; Toolvendor App, beecode 2017<br/>\n" +
            "                     <a href=\"#\" style=\"color: #ffffff;\"><font color=\"#ffffff\">Unsubscribe</font></a> to this newsletter instantly\n" +
            "\n" +
            "                    </td>\n" +
            "                  </tr>\n" +
            "                </table>\n" +
            "              </td>\n" +
            "            </tr>\n" +
            "          </table>\n" +
            "       </td>\n" +
            "    </tr>\n" +
            "   </table>\n" +
            "\n" +
            "</body>\n" +
            "\n" +
            "</html>";
        
        SendEmail se = new SendEmail();
        se.SendMailTSL(toEmail, emailSubject, emailBody, "text/html");
        //se.SendMailSSL(toEmail, emailSubject, emailBody, "text/html");
    }
    
    @Override
    public void SendEmailProgramVisit(User user, Visit visit) {
        String toEmail = user.getEmail();
        String emailSubject = "Te han asignado una nueva visita";
        String emailBody = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
        "\n" +
        "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "\n" +
        "<head>\n" +
        "\n" +
        "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
        "\n" +
        "<title>Toolvendor App</title>\n" +
        "\n" +
        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
        "\n" +
        "</head>\n" +
        "\n" +
        "<body style=\"margin: 0; padding: 0;\">\n" +
        "\n" +
        "   <table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
        "    <tr>\n" +
        "       <td >\n" +
        "         <table align=\"center\" bgcolor=\"#70bbd9\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse;\">\n" +
        "\n" +
        "            <tr>\n" +
        "              <td align=\"center\" bgcolor=\"#70bbd9\" style=\"color: #ffffff; padding: 10px 0 30px 0; font-family: Arial, sans-serif; font-size: 28px;\">\n" +
        "                 <b>Toolvendor App</b>\n" +
        "              </td>\n" +
        "            </tr>\n" +
        "            <tr>\n" +
        "              <td  bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px;\">\n" +
        "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
        "                    <tr>\n" +
        "                       <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 24px;\">\n" +
        "                         <b>Hola "+user.getName()+",</b>\n" +
        "                       </td>\n" +
        "                    </tr>\n" +
        "                    <tr>\n" +
        "                      <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
        "                        <br>\n" +
        "                        Te han programado una nueva visita para el "+ visit.getScheduledDate()+",<br><br>\n" +
        "\n" +
        "                      </td>\n" +
        "                    </tr>\n" +
        "                    <tr>\n" +
        "                      <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
        "\n" +
        "                        <h3>Datos de la Visita: </h3>\n" +
        "\n" +
        "                      </td>\n" +
        "                    </tr>\n" +
        "                    <tr>\n" +
        "                      <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
        "\n" +
        "                        Hora: "+visit.getScheduledDate()+"\n" +
        "\n" +
        "                      </td>\n" +
        "                    </tr>\n" +
        "                    <tr>\n" +
        "                      <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
        "\n" +
        "                        Motivo:  "+visit.getReason()+"\n" +
        "\n" +
        "                      </td>\n" +
        "                    </tr>\n" +
        "                    <tr>\n" +
        "                      <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
        "\n" +
        "                        Cliente:  "+visit.getCustomer().getCompanyName()+" \n" +
        "\n" +
        "                      </td>\n" +
        "                    </tr>\n" +
        "                    <tr>\n" +
        "                      <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
        "\n" +
        "                      <br><br>Le recordamos que debe iniciar la visita antes de la hora programada para que pueda ser aceptada por el sistema de lo contrario cambiará a estatus: Vencida.\n" +
        "\n" +
        "                      </td>\n" +
        "                    </tr>\n" +
        "                    <tr>\n" +
        "                      <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
        "                        <br>\n" +
        "                        <br>\n" +
        "                          Atentamente, el equipo de soporte ToolvendorApp\n" +
        "                      </td>\n" +
        "                    </tr>\n" +
        "                 </table>\n" +
        "              </td>\n" +
        "            </tr>\n" +
        "            <tr>\n" +
        "              <td bgcolor=\"#ee4c50\" style=\"padding: 30px 30px 30px 30px;\">\n" +
        "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
        "                  <tr>\n" +
        "                    <td  align=\"right\">\n" +
        "                      <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
        "                        <tr>\n" +
        "                          <td>\n" +
        "                          <a href=\"http://www.twitter.com/\">\n" +
        "                           <img src=\"images/tw.gif\" alt=\"Twitter\" width=\"38\" height=\"38\" style=\"display: block;\" border=\"0\" />\n" +
        "                          </a>\n" +
        "                          </td>\n" +
        "\n" +
        "                        <td style=\"font-size: 0; line-height: 0;\" width=\"20\">&nbsp;</td>\n" +
        "                          <td>\n" +
        "                          <a href=\"http://www.twitter.com/\">\n" +
        "                           <img src=\"images/fb.gif\" alt=\"Facebook\" width=\"38\" height=\"38\" style=\"display: block;\" border=\"0\" />\n" +
        "                          </a>\n" +
        "                          </td>\n" +
        "                        </tr>\n" +
        "                        </table>\n" +
        "                    </td>\n" +
        "                    <td style=\"color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\">\n" +
        "                     &reg; Toolvendor App, beecode 2017<br/>\n" +
        "                     <a href=\"#\" style=\"color: #ffffff;\"><font color=\"#ffffff\">Unsubscribe</font></a> to this newsletter instantly\n" +
        "\n" +
        "                    </td>\n" +
        "                  </tr>\n" +
        "                </table>\n" +
        "              </td>\n" +
        "            </tr>\n" +
        "          </table>\n" +
        "       </td>\n" +
        "    </tr>\n" +
        "   </table>\n" +
        "\n" +
        "</body>\n" +
        "\n" +
        "</html>";
        
        SendEmail se = new SendEmail();
        se.SendMailTSL(toEmail, emailSubject, emailBody, "text/html");
    }

    @Override
    public void SendEmailVisit(Customer cstmr, Visit visit) {
        String toEmail = cstmr.getContactEmail();
        String emailSubject = "Bienvenido a Toolvendor App";
        String emailBody = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
        "\n" +
        "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        "\n" +
        "<head>\n" +
        "\n" +
        "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
        "\n" +
        "<title>Toolvendor App</title>\n" +
        "\n" +
        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
        "\n" +
        "</head>\n" +
        "\n" +
        "<body style=\"margin: 0; padding: 0;\">\n" +
        "\n" +
        "   <table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
        "    <tr>\n" +
        "       <td >\n" +
        "         <table align=\"center\" bgcolor=\"#70bbd9\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse;\">\n" +
        "\n" +
        "            <tr>\n" +
        "              <td align=\"center\" bgcolor=\"#70bbd9\" style=\"color: #ffffff; padding: 10px 0 30px 0; font-family: Arial, sans-serif; font-size: 28px;\">\n" +
        "                 <b>Toolvendor App</b>\n" +
        "              </td>\n" +
        "            </tr>\n" +
        "            <tr>\n" +
        "              <td  bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px;\">\n" +
        "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
        "                    <tr>\n" +
        "                       <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 24px;\">\n" +
        "                         <b>Estimado "+cstmr.getContactName()+",</b>\n" +
        "                       </td>\n" +
        "                    </tr>\n" +
        "                    <tr>\n" +
        "                      <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
        "\n" +
        "                       <h4>Hemos finalizado exitosamente su visita,</h4> \n" +
        "\n" +
        "                       <b>En este correo le enviamos adjunto un PDF con todo el detalle de su visita.</b>\n" +
        "\n" +
        "                      </td>\n" +
        "                    </tr>\n" +
        "                    <tr>\n" +
        "                      <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
        "                        <br>\n" +
        "                        <br>\n" +
        "                          Atentamente, el equipo de soporte ToolvendorApp\n" +
        "                      </td>\n" +
        "                    </tr>\n" +
        "                 </table>\n" +
        "              </td>\n" +
        "            </tr>\n" +
        "            <tr>\n" +
        "              <td bgcolor=\"#ee4c50\" style=\"padding: 30px 30px 30px 30px;\">\n" +
        "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
        "                  <tr>\n" +
        "                    <td  align=\"right\">\n" +
        "                      <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
        "                        <tr>\n" +
        "                          <td>\n" +
        "                          <a href=\"http://www.twitter.com/\">\n" +
        "                           <img src=\"images/tw.gif\" alt=\"Twitter\" width=\"38\" height=\"38\" style=\"display: block;\" border=\"0\" />\n" +
        "                          </a>\n" +
        "                          </td>\n" +
        "\n" +
        "                        <td style=\"font-size: 0; line-height: 0;\" width=\"20\">&nbsp;</td>\n" +
        "                          <td>\n" +
        "                          <a href=\"http://www.twitter.com/\">\n" +
        "                           <img src=\"images/fb.gif\" alt=\"Facebook\" width=\"38\" height=\"38\" style=\"display: block;\" border=\"0\" />\n" +
        "                          </a>\n" +
        "                          </td>\n" +
        "                        </tr>\n" +
        "                        </table>\n" +
        "                    </td>\n" +
        "                    <td style=\"color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\">\n" +
        "                     &reg; Toolvendor App, beecode 2017<br/>\n" +
        "                     <a href=\"#\" style=\"color: #ffffff;\"><font color=\"#ffffff\">Unsubscribe</font></a> to this newsletter instantly\n" +
        "\n" +
        "                    </td>\n" +
        "                  </tr>\n" +
        "                </table>\n" +
        "              </td>\n" +
        "            </tr>\n" +
        "          </table>\n" +
        "       </td>\n" +
        "    </tr>\n" +
        "   </table>\n" +
        "\n" +
        "</body>\n" +
        "\n" +
        "</html>";
        
        SendEmail se = new SendEmail();
        se.SendMailTSL(toEmail, emailSubject, emailBody, "text/html");
    }
    
    @Override
    public void SendEmailOrder(Customer cstmr, Order order) {
        String toEmail = cstmr.getContactEmail();
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
                        "        <h4>Estimado "+cstmr.getContactName()+",</h4> \n" +
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
        
        SendEmail se = new SendEmail();
        se.SendMailTSL(toEmail, emailSubject, emailBody, "text/html");
    }
    
    String emailTemplateAdmin = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
        "<html lang=\"en\">\n" +
        "<head>\n" +
        "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
        "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
        "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
        "\n" +
        "  <title></title>\n" +
        "\n" +
        "  <style type=\"text/css\">\n" +
        "  </style>    \n" +
        "</head>\n" +
        "<body style=\"margin:0; padding:0; background-color:#F2F2F2;\">\n" +
        "  <center>\n" +
        "    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#F2F2F2\">\n" +
        "        <tr>\n" +
        "            <td align=\"center\" valign=\"top\">\n" +
        "                \n" +
        "                \n" +
        "            </td>\n" +
        "        </tr>\n" +
        "    </table>\n" +
        "  </center>\n" +
        "</body>\n" +
        "</html>";
    
}
