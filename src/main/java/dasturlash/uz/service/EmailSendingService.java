package dasturlash.uz.service;

import dasturlash.uz.util.RandomNumberGenerator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSendingService {
    @Value("${spring.mail.username}")
    private String fromAccount;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailHistoryService emailHistoryService;


    public void sendRegistrationStyledEmail(String toAccount) {
        Integer smsCode = RandomNumberGenerator.generate();

        String message = "Your verification code is: ";
        String body = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Registration Confirmation</title>\n" +
                "</head>\n" +
                "<body style=\"margin:0; padding:0; font-family:'Arial', sans-serif; background-color:#f0f2f5;\">\n" +
                "<table width=\"100%%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tr>\n" +
                "        <td align=\"center\">\n" +
                "            <!-- Main container -->\n" +
                "            <table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background: linear-gradient(135deg, #6a11cb, #2575fc); padding:40px; border-radius:20px; box-shadow:0 15px 50px rgba(0,0,0,0.3); color:#fff;\">\n" +
                "\n" +
                "                <!-- Header -->\n" +
                "                <tr>\n" +
                "                    <td align=\"center\">\n" +
                "                        <h1 style=\"font-size:36px; margin-bottom:15px; font-weight:bold;\">Welcome to Our Service!</h1>\n" +
                "                        <p style=\"font-size:18px; line-height:1.6; max-width:480px; margin:0 auto;\">\n" +
                "                            Thank you for registering! Use the verification code below to activate your account.\n" +
                "                        </p>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "\n" +
                "                <!-- Verification Code -->\n" +
                "                <tr>\n" +
                "                    <td align=\"center\" style=\"padding:35px 0;\">\n" +
                "                        <div style=\"\n" +
                "                                background-color: rgba(255,255,255,0.2);\n" +
                "                                color: #ffffff;\n" +
                "                                font-size: 32px;\n" +
                "                                font-weight: bold;\n" +
                "                                padding: 20px 40px;\n" +
                "                                border-radius: 12px;\n" +
                "                                letter-spacing: 4px;\n" +
                "                                display: inline-block;\n" +
                "                            \">%d</div>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "\n" +
                "            </table>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>";

        body = String.format(body, smsCode);

        sendMimeMessage("Registration code", body, toAccount);
        emailHistoryService.create(message, smsCode, toAccount);
    }


    private String sendMimeMessage(String subject, String body, String toAccount) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromAccount);

            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(body, true);
            javaMailSender.send(msg);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "Mail was send";
    }


    public String sendSimpleMessage(String toAccount, String subject, String content) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromAccount);
        msg.setTo(toAccount);
        msg.setSubject(subject);
        msg.setText(content);
        javaMailSender.send(msg);

        return "Mail was send";
    }
}
