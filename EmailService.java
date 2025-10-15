import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {
    private static final String FROM_EMAIL = "smgasbookingagecyoffical@gmail.com"; // Sender email
    private static final String PASSWORD = "lnko amcj pdcy fzle";                   // Gmail App Password

    // ✅ Get SMTP Session
    private static Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
            }
        });
    }

    // ✅ General method to send email
    public static boolean sendEmail(String toEmail, String subject, String messageText) {
        try {
            Message message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(FROM_EMAIL, "SohamGas Agency"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(messageText);

            Transport.send(message);
            System.out.println("✅ Email sent successfully to: " + toEmail);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Failed to send email to: " + toEmail);
            return false;
        }
    }

    // ✅ Predefined booking confirmation email
    public static void confirmBooking(String customerEmail, String customerName, String bookingDetails) {
        String subject = "Booking Confirmation – SohamGas Agency";

        String body = "Dear " + customerName + ",\n\n"
                + "We are pleased to inform you that your gas booking has been successfully confirmed with SohamGas Agency.\n\n"
                + "📌 Booking Details:\n" + bookingDetails + "\n\n"
                + "Should you have any questions or require further assistance, please feel free to contact our support team.\n\n"
                + "Thank you for trusting SohamGas Agency. We value your association with us.\n\n"
                + "Warm regards,\n"
                + "SohamGas Agency Team";


        sendEmail(customerEmail, subject, body);
    }
}
