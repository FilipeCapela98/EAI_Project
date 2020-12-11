package nl.rug.eai.imagestream.twitterstreamingservice;

import nl.rug.eai.imagestream.twitterstreamingservice.Utility.TwitterStreamingUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
public class TwitterStreamingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitterStreamingServiceApplication.class, args);
        ApplicationContext applicationContext = SpringApplication.run(TwitterStreamingServiceApplication.class, args);
        // TwitterStreamingUtil twitterApp = applicationContext.getBean(TwitterStreamingUtil.class);
        try {
            TwitterStreamingUtil.startStreaming("Cat");
        } catch (IOException | URISyntaxException e) {
            System.out.println("FAILED");
        }

        // twitter.start();
    }

}
