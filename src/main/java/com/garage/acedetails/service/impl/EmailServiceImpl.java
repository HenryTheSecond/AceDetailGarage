package com.garage.acedetails.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.entity.ConfirmToken;
import com.garage.acedetails.service.EmailService;
import com.garage.acedetails.util.EmailVerificationTemplate;

@Service
public class EmailServiceImpl implements EmailService {
  @Autowired
  private JavaMailSender javaMailSender;

  @Autowired
  @Value("${spring.mail.username}")
  private String sender;

  @Override
  public void sendRegisterConfirmationToken(ConfirmToken confirmToken) {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();

    try {
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
      mimeMessageHelper.setFrom(sender);
      mimeMessageHelper.setTo(confirmToken.getAccount().getEmail());
      mimeMessageHelper.setText(EmailVerificationTemplate
          .emailVerificationTemplate(confirmToken.getAccount().getUsername(), confirmToken.getToken()), true);
      mimeMessageHelper.setSubject(ApplicationConstants.VERIFICATION_REGISTER_EMAIL_SUBJECT);
      Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          javaMailSender.send(mimeMessage);
        }
      });
      thread.start();
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void sendWeeklyGoodsBillStatistics(Workbook workbook, LocalDate date) {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    try {
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
      mimeMessageHelper.setFrom(sender);
      mimeMessageHelper.setTo("congtuyen2032001@gmail.com");
      mimeMessageHelper.setText("Statistics from " + date.minusWeeks(1) + " to " + date);
      mimeMessageHelper.setSubject("Statistics from " + date.minusWeeks(1) + " to " + date);

      // Convert Workbook to Byte Array
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      workbook.write(os);
      DataSource dataSource = new ByteArrayDataSource(os.toByteArray(), "application/vnd.ms-excel");
      mimeMessageHelper.addAttachment("Statistics.xlsx", dataSource);

      new Thread(new Runnable() {
        @Override
        public void run() {
          javaMailSender.send(mimeMessage);
        }
      }).start();

    } catch (MessagingException | IOException e) {
      e.printStackTrace();
    }
  }

}
