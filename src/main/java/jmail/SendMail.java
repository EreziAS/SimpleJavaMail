package jmail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class SendMail {
    private String to;
    private Properties props;
    Session session;


    public SendMail(String username, String password) {

        props = new Properties();
        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp-mail.outlook.com");

        session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void EnvoiEmail(String username, String target, String subject, String text) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(target));
            message.setSubject(subject);
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(text, "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Done");
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void EnvoiEmailComplex(String username, String target, String subject, String text, String file) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(target));
            message.setSubject(subject);

            Multipart msg = new MimeMultipart();

            MimeBodyPart txt = new MimeBodyPart();
            txt.setText(text);
            msg.addBodyPart(txt);

            String path = file;

            MimeBodyPart fichier = new MimeBodyPart();
            DataSource ds = new FileDataSource(path);
            fichier.setDataHandler(new DataHandler(ds));
            fichier.setFileName(path);
            msg.addBodyPart(fichier);

            message.setContent(msg);

            Transport.send(message);
            System.out.println("Done");


        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
