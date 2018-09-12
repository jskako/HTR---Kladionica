/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htr;

/**
 *
 * @author josips
 */
import java.sql.Connection;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailRecovery {

    public void MailRecovery(Connection conn, String recoveryMail, String recoveryPassword, String recoveryUsername) {

        final String username = "htrprojectcompany@gmail.com";
        final String password = "HtRProjeCt1_";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("htrprojectcompany@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recoveryMail));
            message.setSubject("HTR - Password Recovery");
            message.setText("Username: " + recoveryUsername
                    + "\n Password: " + recoveryPassword);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
