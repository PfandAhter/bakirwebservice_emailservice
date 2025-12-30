package com.bakirwebservice.emailservice.service.impl;

import com.bakirwebservice.emailservice.api.request.*;
import com.bakirwebservice.emailservice.repository.EmailValidatorRepository;
import com.bakirwebservice.emailservice.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor

public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    private final EmailValidatorRepository validatorRepository;

    private static final String LOGO_PATH = System.getProperty("user.dir") + File.separator + "logo" + File.separator + "bakirbank-transparent.png";

    ClassPathResource logoFile = new ClassPathResource("/logo/bakirbank-transparent.png");

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendResenVerificationEmail(String email, String code) {

    }

    @Override
    public void sendCustomEmailByAdmin(SendEmailByAdminRequest request) {
        String email = request.getEmail();
        String subject = request.getSubject();
        String message = request.getMessage();

        String htmlContent = """
    <html>
    <body style="font-family: Arial, sans-serif; background-color: #f4f4f7; padding: 40px;">
      <table align="center" cellpadding="0" cellspacing="0"
        style="max-width: 500px; background: white; border-radius: 12px;
               overflow: hidden; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
        <tr>
          <td style="text-align: center; background-color: #101820; padding: 20px;">
            <img src='cid:bakirbank-logo' alt="BAKIRBANK" style="width: 120px;"/>
          </td>
        </tr>
        <tr>
          <td style="padding: 30px; text-align: left; color: #333;">
            <h2 style="color: #101820; margin-bottom: 20px; text-align:center;">
              %s
            </h2>

            <p style="font-size: 15px; color: #555; line-height: 1.6; white-space: pre-line;">
              %s
            </p>

            <p style="color: #777; font-size: 13px; margin-top: 30px; text-align:center;">
              Bu e-posta BAKIRBANK yönetimi tarafından gönderilmiştir.<br/>
              Lütfen bu e-postayı yanıtlamayınız.
            </p>
          </td>
        </tr>
        <tr>
          <td style="background-color: #f1f1f1; padding: 15px; text-align: center;
                     font-size: 13px; color: #888;">
            © 2025 BAKIRBANK - Tüm hakları saklıdır.
          </td>
        </tr>
      </table>
    </body>
    </html>
    """.formatted(subject, message);

        sendHtmlEmailWithLogo(email, htmlContent, subject);
    }

    @Override
    public void sendVerificationEmail(VerifyUserRequest verifyUserRequest) {
        String otp = verifyUserRequest.getOtp();
        String formattedOtp = otp.substring(0, 3) + " - " + otp.substring(3);

        String htmlContent = """
        <html>
        <body style="font-family: Arial, sans-serif; background-color: #f4f4f7; padding: 40px;">
          <table align="center" cellpadding="0" cellspacing="0" style="max-width: 500px; background: white; border-radius: 12px; overflow: hidden; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
            <tr>
              <td style="text-align: center; background-color: #101820; padding: 20px;">
                <img src='cid:bakirbank-logo' alt="BAKIRBANK" style="width: 120px;"/>
              </td>
            </tr>
            <tr>
              <td style="padding: 30px; text-align: center; color: #333;">
                <h2 style="color: #101820; margin-bottom: 10px;">BAKIRBANK’a Hoş Geldiniz</h2>
                <p style="font-size: 15px; color: #555; margin-bottom: 25px;">
                  Hesabınızı aktifleştirmek için aşağıdaki doğrulama kodunu kullanın:
                </p>

                <table align="center" cellpadding="0" cellspacing="0" style="margin: 0 auto;">
                  <tr>
                    <td style="background-color: #101820; color: white; font-size: 24px; font-weight: bold; letter-spacing: 6px; padding: 14px 32px; border-radius: 10px; text-align: center; box-shadow: 0 2px 6px rgba(0,0,0,0.15);">
                      %s
                    </td>
                  </tr>
                </table>

                <p style="color: #777; font-size: 14px; margin-top: 30px;">
                  Bu kod 10 dakika boyunca geçerlidir.
                </p>
              </td>
            </tr>
            <tr>
              <td style="background-color: #f1f1f1; padding: 15px; text-align: center; font-size: 13px; color: #888;">
                © 2025 BAKIRBANK - Tüm hakları saklıdır.
              </td>
            </tr>
          </table>
        </body>
        </html>
    """.formatted(formattedOtp);

        String subject = "BAKIRBANK Kullanıcı Aktivasyonu";
        sendHtmlEmailWithLogo(verifyUserRequest.getUserEmail(), htmlContent, subject);
    }


    @Override
    public void sendPasswordResetEmail(VerifyUserRequest verifyUserRequest) {
        String otp = verifyUserRequest.getOtp();
        String formattedOtp = otp.substring(0, 3) + " - " + otp.substring(3);

        String htmlContent = """
    <html>
    <body style="font-family: Arial, sans-serif; background-color: #f4f4f7; padding: 40px;">
      <table align="center" cellpadding="0" cellspacing="0" style="max-width: 500px; background: white; border-radius: 12px; overflow: hidden; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
        <tr>
          <td style="text-align: center; background-color: #101820; padding: 20px;">
            <img src='cid:bakirbank-logo' alt="BAKIRBANK" style="width: 120px;"/>
          </td>
        </tr>
        <tr>
          <td style="padding: 30px; text-align: center; color: #333;">
            <h2 style="color: #101820; margin-bottom: 10px;">Şifre Sıfırlama Kodu</h2>
            <p style="font-size: 15px; color: #555; margin-bottom: 25px;">
              Şifrenizi sıfırlamak için aşağıdaki doğrulama kodunu kullanın:
            </p>

            <table align="center" cellpadding="0" cellspacing="0" style="margin: 0 auto;">
              <tr>
                <td style="background-color: #101820; color: white; font-size: 24px; font-weight: bold; letter-spacing: 6px; padding: 14px 32px; border-radius: 10px; text-align: center; box-shadow: 0 2px 6px rgba(0,0,0,0.15);">
                  %s
                </td>
              </tr>
            </table>

            <p style="color: #777; font-size: 14px; margin-top: 30px;">
              Bu kod 10 dakika boyunca geçerlidir.<br/>
              Eğer bu işlemi siz başlatmadıysanız, lütfen bu e-postayı dikkate almayın.
            </p>
          </td>
        </tr>
        <tr>
          <td style="background-color: #f1f1f1; padding: 15px; text-align: center; font-size: 13px; color: #888;">
            © 2025 BAKIRBANK - Tüm hakları saklıdır.
          </td>
        </tr>
      </table>
    </body>
    </html>
    """.formatted(formattedOtp);

        String subject = "BAKIRBANK Şifre Sıfırlama Hakkında";
        sendHtmlEmailWithLogo(verifyUserRequest.getUserEmail(), htmlContent, subject);
    }

    private String formatExpiryDate(String dateString) {
        if (dateString == null || dateString.isBlank()) return "";
        try {
            LocalDate date = LocalDate.parse(dateString);
            return date.format(DateTimeFormatter.ofPattern("MM/yy"));
        } catch (Exception e) {
            return dateString; // Hata olursa orjinalini dön
        }
    }

    private String getCardImageFilename(String network, String type) {
        if (network == null) return "default.png";

        String net = network.trim().toLowerCase();
        boolean isDebit = type != null && type.trim().equalsIgnoreCase("DEBIT");

        if (isDebit) {
            return switch (net) {
                case "visa" -> "debit-visa.png";
                case "mastercard" -> "debit-mastercard.png";
                case "amex" -> "debit-amex.png";
                case "troy" -> "debit-troy.png";
                default -> "default.png";
            };
        } else {
            return switch (net) {
                case "visa" -> "visa-bg.png";
                case "mastercard" -> "mastercard-bg.jpg";
                case "amex" -> "amex-bg.png";
                case "troy" -> "troy-bg.png";
                default -> "default.png";
            };
        }
    }

    @Override
    public void sendCardApprovalNotification(CardApprovalEmailRequest request) {
        // 1. Veri Hazırlığı
        String formattedCardNumber = formatCardNumber(request.getCardNumber());
        String formattedDate = formatExpiryDate(request.getExpiryDate()); // 12/29 formatı
        String cardImageFilename = getCardImageFilename(request.getCardNetwork(), request.getCardType());

        String htmlContent = """
    <html>
    <head>
      <style>
        /* Hover efekti için CSS sınıfı */
        .secure-blur {
            filter: blur(5px);
            transition: filter 0.3s ease;
            cursor: pointer;
            user-select: none;
        }
        .secure-blur:hover {
            filter: blur(0);
        }
      </style>
    </head>
    <body style="font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; background-color: #f4f4f7; padding: 40px;">
      <table align="center" cellpadding="0" cellspacing="0" style="max-width: 550px; background: white; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 15px rgba(0,0,0,0.05);">
        
        <tr>
          <td style="text-align: center; background-color: #101820; padding: 25px;">
            <img src='cid:bakirbank-logo' alt="BAKIRBANK" style="width: 140px;"/>
          </td>
        </tr>

        <tr>
          <td style="padding: 30px; text-align: center; color: #333;">
            <h2 style="color: #101820; margin-bottom: 10px; font-size: 24px;">🎉 Kartınız Onaylandı!</h2>
            <p style="font-size: 16px; color: #555; margin-bottom: 25px;">
              Sayın <strong>%s</strong>, kart başvurunuz başarıyla onaylanmıştır.
            </p>

            <div style="background-image: url('cid:card-bg'); 
                        background-size: cover; background-position: center;
                        color: white; padding: 25px 25px 35px 25px; 
                        border-radius: 16px; margin: 0 auto 25px auto; 
                        max-width: 380px; height: 210px; text-align: left; 
                        box-shadow: 0 10px 30px rgba(0,0,0,0.4);
                        position: relative; display: flex; flex-direction: column; justify-content: flex-end;">
              
              <p style="font-size: 21px; letter-spacing: 4px; margin: 0 0 20px 0; 
                        font-family: 'Courier New', monospace; text-shadow: 2px 2px 4px rgba(0,0,0,0.9); font-weight: bold;">
                %s
              </p>
              
              <div style="display: flex; justify-content: space-between; align-items: flex-end;">
                
                <div style="flex: 1;">
                  <p style="font-size: 10px; color: #ddd; margin: 0; text-shadow: 1px 1px 1px rgba(0,0,0,0.8);">KART SAHİBİ</p>
                  <p style="font-size: 14px; margin: 5px 0; text-transform: uppercase; text-shadow: 1px 1px 2px rgba(0,0,0,0.8);">%s</p>
                </div>
                <div style="flex: 1; text-align: right; margin-right: 5px; margin-bottom: 10px;">
                  <p style="font-size: 14px; margin: 5px 0; text-shadow: 1px 1px 2px rgba(0,0,0,0.8);">%s</p>
                </div>                
              </div>
            </div>
            
            <div style="background: #e8f5e9; border-left: 4px solid #4caf50; padding: 15px;\s
                text-align: left; margin: 20px 0; border-radius: 4px;">
                <strong style="color: #2e7d32;">💳 Kredi Limitiniz:</strong>
                <span style="font-size: 18px; color: #2e7d32; font-weight: bold;"> ₺%s</span>
            </div>

            <div style="background: #fff3e0; border-left: 5px solid #ef6c00; padding: 15px 20px;\s
                        margin: 0 0 25px 0; border-radius: 4px; display: flex; align-items: center; justify-content: space-between;">
                <div style="text-align: left;">
                    <span style="color: #ef6c00; font-weight: bold; font-size: 14px;">🔒 Güvenlik Kodu (CVV)</span>
                    <div style="font-size: 11px; color: #a65b00; margin-top: 2px;">Görmek için üzerine gelin</div>
                </div>
                
                <span style="font-size: 20px; color: #e65100; font-weight: bold; font-family: 'Courier New', monospace; letter-spacing: 2px; background: #ffe0b2; padding: 2px 13px; border-radius: 4px;">
                    *** (Güvenlik kodu: %s)
                </span>
            </div>

            <div style="background: #fafafa; border: 1px solid #eee; padding: 20px; 
                        border-radius: 8px; text-align: left;">
              <strong style="color: #555; display: block; margin-bottom: 10px;">⚠️ Güvenlik Hatırlatmaları:</strong>
              <ul style="color: #777; font-size: 13px; margin: 0; padding-left: 20px; line-height: 1.6;">
                <li>CVV kodunuzu banka personeli dahil kimseyle paylaşmayın.</li>
                <li>Şüpheli işlemlerde hemen <strong>0850 123 45 67</strong>'yi arayın.</li>
                <li>Kart bilgilerinizi kaydettikten sonra bu e-postayı silmenizi öneririz.</li>
              </ul>
            </div>

          </td>
        </tr>
        
        <tr>
          <td style="background-color: #f8f9fa; padding: 20px; text-align: center; font-size: 12px; color: #999; border-top: 1px solid #eee;">
            © 2025 BAKIRBANK - Tüm hakları saklıdır.<br/>
            Bu e-posta otomatik olarak oluşturulmuştur.
          </td>
        </tr>
      </table>
    </body>
    </html>
    """.formatted(
                request.getCardHolderName(),
                formattedCardNumber,
                request.getCardHolderName(),
                formattedDate,
                formatCurrency(request.getCreditLimit()),
                request.getCvv()
        );

        String subject = "BAKIRBANK - Kartınız Onaylandı! 🎉";
        sendHtmlEmailWithCardImage(request.getUserEmail(), htmlContent, subject, cardImageFilename);
    }

    private void sendHtmlEmailWithCardImage(String email, String htmlContent, String subject, String cardImageFilename) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(sender);
//            helper.setFrom("noreply@bakirbank.com");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            //FileSystemResource logo = new FileSystemResource(new File(LOGO_PATH));
            helper.addInline("bakirbank-logo", logoFile);

            ClassPathResource cardBg = new ClassPathResource("logo/" + cardImageFilename);
            if (cardBg.exists()) {
                helper.addInline("card-bg", cardBg);
            } else {
                log.warn("Kart görseli JAR içinde bulunamadı: logo/" + cardImageFilename);
                helper.addInline("card-bg", new ClassPathResource("logo/default.png"));
            }

            javaMailSender.send(mimeMessage);
            log.info("Card Approval HTML mail sent to: " + email);
        } catch (MessagingException e) {
            log.error("Error while sending Card Approval mail to: " + email, e);
        }
    }

    @Override
    public void sendCardPendingNotification(CardPendingEmailRequest request) {
        String htmlContent = """
    <html>
    <body style="font-family: Arial, sans-serif; background-color: #f4f4f7; padding: 40px;">
      <table align="center" cellpadding="0" cellspacing="0" style="max-width: 500px; background: white; border-radius: 12px; overflow: hidden; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
        <tr>
          <td style="text-align: center; background-color: #101820; padding: 20px;">
            <img src='cid:bakirbank-logo' alt="BAKIRBANK" style="width: 120px;"/>
          </td>
        </tr>
        <tr>
          <td style="padding: 30px; text-align: center; color: #333;">
            <h2 style="color: #101820; margin-bottom: 10px;">📋 Başvurunuz Alındı</h2>
            <p style="font-size: 15px; color: #555; margin-bottom: 25px;">
              Sayın <strong>%s</strong>,
            </p>

            <!-- Başvuru Durumu -->
            <div style="background: #e3f2fd; border-radius: 10px; padding: 25px; margin: 20px 0;">
              <div style="font-size: 50px; margin-bottom: 15px;">⏳</div>
              <h3 style="color: #1565c0; margin: 0;">Başvurunuz İnceleniyor</h3>
              <p style="color: #1976d2; font-size: 14px; margin-top: 10px;">
                %s kartı başvurunuz değerlendirme aşamasındadır.
              </p>
            </div>

            <!-- Başvuru Detayları -->
            <table style="width: 100%%; border-collapse: collapse; margin: 20px 0;">
              <tr>
                <td style="padding: 12px; border-bottom: 1px solid #eee; text-align: left; color: #666;">
                  <strong>Başvuru Tarihi:</strong>
                </td>
                <td style="padding: 12px; border-bottom: 1px solid #eee; text-align: right; color: #333;">
                  %s
                </td>
              </tr>
              <tr>
                <td style="padding: 12px; border-bottom: 1px solid #eee; text-align: left; color: #666;">
                  <strong>Kart Tipi:</strong>
                </td>
                <td style="padding: 12px; border-bottom: 1px solid #eee; text-align: right; color: #333;">
                  %s
                </td>
              </tr>
              <tr>
                <td style="padding: 12px; text-align: left; color: #666;">
                  <strong>Tahmini Süre:</strong>
                </td>
                <td style="padding: 12px; text-align: right; color: #333;">
                  1-3 İş Günü
                </td>
              </tr>
            </table>

            <div style="background: #f5f5f5; padding: 15px; border-radius: 8px; margin: 20px 0;">
              <p style="color: #666; font-size: 14px; margin: 0;">
                📧 Başvurunuz onaylandığında kart bilgileriniz bu e-posta adresine gönderilecektir.
              </p>
            </div>

            <p style="color: #777; font-size: 13px; margin-top: 30px;">
              Sorularınız için 0850 XXX XX XX numaralı hattımızı arayabilirsiniz.
            </p>
          </td>
        </tr>
        <tr>
          <td style="background-color: #f1f1f1; padding: 15px; text-align: center; font-size: 13px; color: #888;">
            © 2025 BAKIRBANK - Tüm hakları saklıdır.
          </td>
        </tr>
      </table>
    </body>
    </html>
    """.formatted(
                request.getCardHolderName(),
                request.getCardType(),
                request.getApplicationDate(),
                request.getCardType()
        );

        String subject = "BAKIRBANK - Kart Başvurunuz Alındı";
        sendHtmlEmailWithLogo(request.getUserEmail(), htmlContent, subject);
    }

    @Override
    public void sendCardRejectionNotification(CardRejectionEmailRequest request) {
        String htmlContent = """
    <html>
    <body style="font-family: Arial, sans-serif; background-color: #f4f4f7; padding: 40px;">
      <table align="center" cellpadding="0" cellspacing="0" style="max-width: 500px; background: white; border-radius: 12px; overflow: hidden; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
        <tr>
          <td style="text-align: center; background-color: #101820; padding: 20px;">
            <img src='cid:bakirbank-logo' alt="BAKIRBANK" style="width: 120px;"/>
          </td>
        </tr>
        <tr>
          <td style="padding: 30px; text-align: center; color: #333;">
            <h2 style="color: #101820; margin-bottom: 10px;">Başvuru Durumu</h2>
            <p style="font-size: 15px; color: #555; margin-bottom: 25px;">
              Sayın <strong>%s</strong>,
            </p>

            <!-- Red Durumu -->
            <div style="background: #ffebee; border-radius: 10px; padding: 25px; margin: 20px 0;">
              <div style="font-size: 50px; margin-bottom: 15px;">❌</div>
              <h3 style="color: #c62828; margin: 0;">Başvurunuz İncelendi</h3>
              <p style="color: #d32f2f; font-size: 14px; margin-top: 10px;">
                Maalesef %s kartı başvurunuz şu anda onaylanamamıştır.
              </p>
            </div>

            <!-- Başvuru Detayları -->
            <table style="width: 100%%; border-collapse: collapse; margin: 20px 0;">
              <tr>
                <td style="padding: 12px; border-bottom: 1px solid #eee; text-align: left; color: #666;">
                  <strong>Başvuru Tarihi:</strong>
                </td>
                <td style="padding: 12px; border-bottom: 1px solid #eee; text-align: right; color: #333;">
                  %s
                </td>
              </tr>
              <tr>
                <td style="padding: 12px; border-bottom: 1px solid #eee; text-align: left; color: #666;">
                  <strong>Kart Tipi:</strong>
                </td>
                <td style="padding: 12px; border-bottom: 1px solid #eee; text-align: right; color: #333;">
                  %s
                </td>
              </tr>
              %s
            </table>

            <!-- Alternatif Öneriler -->
            <div style="background: #e8f5e9; border-left: 4px solid #4caf50; padding: 15px;
                        text-align: left; margin: 20px 0; border-radius: 4px;">
              <strong style="color: #2e7d32;">💡 Alternatif Öneriler:</strong>
              <ul style="color: #388e3c; font-size: 13px; margin: 10px 0; padding-left: 20px;">
                <li>Debit kart başvurusunda bulunabilirsiniz</li>
                <li>3 ay sonra tekrar başvuru yapabilirsiniz</li>
                <li>Müşteri temsilcimizle görüşerek detaylı bilgi alabilirsiniz</li>
              </ul>
            </div>

            <!-- İletişim -->
            <div style="background: #f5f5f5; padding: 15px; border-radius: 8px; margin: 20px 0;">
              <p style="color: #666; font-size: 14px; margin: 0;">
                📞 Sorularınız için <strong>0850 XXX XX XX</strong> numaralı hattımızı arayabilirsiniz.
              </p>
            </div>

            <p style="color: #777; font-size: 13px; margin-top: 30px;">
              BAKIRBANK'ı tercih ettiğiniz için teşekkür ederiz.
            </p>
          </td>
        </tr>
        <tr>
          <td style="background-color: #f1f1f1; padding: 15px; text-align: center; font-size: 13px; color: #888;">
            © 2025 BAKIRBANK - Tüm hakları saklıdır.
          </td>
        </tr>
      </table>
    </body>
    </html>
    """.formatted(
                request.getCardHolderName(),
                request.getCardType(),
                request.getApplicationDate(),
                request.getCardType(),
                formatRejectionReason(request.getRejectionReason())
        );

        String subject = "BAKIRBANK - Kart Başvuru Sonucu";
        sendHtmlEmailWithLogo(request.getUserEmail(), htmlContent, subject);
    }

    private String formatRejectionReason(String reason) {
        if (reason == null || reason.isBlank()) {
            return "";
        }
        return """
        <tr>
          <td colspan="2" style="padding: 12px; background: #fff3cd; text-align: left; color: #856404;">
            <strong>Not:</strong> %s
          </td>
        </tr>
        """.formatted(reason);
    }

    private String formatCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() != 16) {
            return cardNumber;
        }
        return cardNumber.replaceAll("(.{4})", "$1 ").trim();
    }

    private String formatCurrency(Double amount) {
        if (amount == null) return "0";
        return String.format("%,.2f", amount);
    }

    private void sendHtmlEmailWithLogo(String email, String htmlContent, String subject) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

//            helper.setFrom("noreply@bakirbank.com");
            helper.setFrom(sender);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            helper.addInline("bakirbank-logo", logoFile);

            javaMailSender.send(mimeMessage);
            log.info("Modern HTML mail sent to: " + email);
        } catch (MessagingException e) {
            log.error("Error while sending HTML mail to: " + email, e);
        }
    }
}
