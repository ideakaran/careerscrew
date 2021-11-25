package com.project.careerscrew.services.mail;

import com.project.careerscrew.config.AppProperties;
import com.project.careerscrew.utils.exceptions.AppExceptionConstants;
import com.project.careerscrew.utils.exceptions.CustomAppException;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@Slf4j
public class EmailService {

    private String defaultSourceEmailAddress;

    private final JavaMailSender javaMailSender;
    private final FreeMarkerConfigurer freemarkerConfigurer;
    private final AppProperties appProperties;

    public EmailService(JavaMailSender javaMailSender, FreeMarkerConfigurer freemarkerConfigurer, AppProperties appProperties) {
        this.javaMailSender = javaMailSender;
        this.freemarkerConfigurer = freemarkerConfigurer;
        this.appProperties = appProperties;
    }

    @PostConstruct
    protected void init() {
        defaultSourceEmailAddress = appProperties.getMail().getDefaultEmailAddress();
    }

    public void sendSimpleMessage(String destinationEmail,
                                  String subject,
                                  String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(defaultSourceEmailAddress);
            message.setTo(destinationEmail);
            message.setSubject(subject);
            message.setText(text);

            javaMailSender.send(message);
        } catch (MailException e) {
            log.error("sendSimpleMessage failed MessagingException {} ", e.getMessage());
        }

    }


    public void sendMessageUsingFreemarkerTemplate(String destinationEmail,
                                                   String subject,
                                                   Map<String, Object> templateModel,
                                                   MessageTemplateCodeUtil.TemplatesPath templatesPath) {
        sendMessageUsingFreemarkerTemplate(destinationEmail, subject, templateModel, templatesPath, false);
    }

    public boolean sendMessageUsingFreemarkerTemplate(String destinationEmail,
                                                      String subject,
                                                      Map<String, Object> templateModel,
                                                      MessageTemplateCodeUtil.TemplatesPath templatesPath,
                                                      boolean shouldFailSilently) {

        log.info("Initiated: sendMessageUsingFreemarkerTemplate - template: {} , toEmailAddress: {} ", templatesPath.getTemplatePath(), destinationEmail);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {

            Template freemarkerTemplate = freemarkerConfigurer.getConfiguration().getTemplate(templatesPath.getTemplatePath());
            String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            helper.setFrom(defaultSourceEmailAddress);
            helper.setTo(destinationEmail);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            if (e instanceof MessagingException) {
                log.error("sendMessageUsingFreemarkerTemplate failed MessagingException {} ", e.getMessage());
            } else {
                log.error("sendMessageUsingFreemarkerTemplate failed Exception {} ", e.getMessage());
            }
            if (!shouldFailSilently) {
                throw new CustomAppException(AppExceptionConstants.MAIL_SENDING_EXCEPTION);
            }
            return false;
        }
        return true;
    }

}
