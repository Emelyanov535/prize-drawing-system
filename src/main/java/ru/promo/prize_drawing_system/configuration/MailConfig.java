package ru.promo.prize_drawing_system.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import ru.promo.prize_drawing_system.configuration.properties.MailConfigProperties;

import java.util.Properties;

@Configuration
@AllArgsConstructor
public class MailConfig {

    private final MailConfigProperties mailConfigProperties;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setPort(mailConfigProperties.getPort());
        mailSender.setHost(mailConfigProperties.getHost());
        mailSender.setUsername(mailConfigProperties.getUsername());
        mailSender.setPassword(mailConfigProperties.getPassword());

        Properties props = mailSender.getJavaMailProperties();

        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}
