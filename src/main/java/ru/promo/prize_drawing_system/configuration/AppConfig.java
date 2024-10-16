package ru.promo.prize_drawing_system.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.promo.prize_drawing_system.configuration.properties.JwtConfigProperties;
import ru.promo.prize_drawing_system.configuration.properties.MailConfigProperties;

@Configuration
@EnableConfigurationProperties({
        MailConfigProperties.class,
        JwtConfigProperties.class
})
public class AppConfig {
}
