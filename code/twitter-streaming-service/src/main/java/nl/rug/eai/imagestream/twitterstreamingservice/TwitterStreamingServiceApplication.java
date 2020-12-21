package nl.rug.eai.imagestream.twitterstreamingservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.twitterstreamingservice.controller.StreamController;
import nl.rug.eai.imagestream.twitterstreamingservice.twitterapi.TwitterStreamingRunner;
import nl.rug.eai.imagestream.twitterstreamingservice.twitterapi.TwitterStreamingUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Executable;
import java.net.URISyntaxException;

@SpringBootApplication
@EnableAsync
@Slf4j
public class TwitterStreamingServiceApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(TwitterStreamingServiceApplication.class, args);
        StreamController twitterApp = applicationContext.getBean(StreamController.class);

        twitterApp.start("cat");
        twitterApp.start("meme");

        try {
            Thread.sleep(60 * 1000);
            twitterApp.stop("meme");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // twitter.start();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


}
