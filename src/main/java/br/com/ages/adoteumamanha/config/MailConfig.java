package br.com.ages.adoteumamanha.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.ClientOptions;

@Configuration
public class MailConfig {

    @Value("${API_KEY:}")
    private String apiKey;

    @Value("${API_SECRET:}")
    private String apiSecret;

    @Bean
    public MailjetClient getMailJetSender() {
        return new MailjetClient(apiKey, apiSecret,
            new ClientOptions("v3.1")
        );
    }

}