package org.tipSell.gateway.clientConfig;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }
}
