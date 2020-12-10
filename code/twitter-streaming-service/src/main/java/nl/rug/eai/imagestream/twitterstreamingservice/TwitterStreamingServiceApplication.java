package nl.rug.eai.imagestream.twitterstreamingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.List;

@SpringBootApplication
public class TwitterStreamingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitterStreamingServiceApplication.class, args);
        ApplicationContext applicationContext = SpringApplication.run(TwitterStreamingServiceApplication.class, args);
        TwitterStreamTest twitterApp = applicationContext.getBean(TwitterStreamTest.class);
        try {

            List<String> Twitter = twitterApp.searchtweets();
        } catch (TwitterException e) {
            System.out.println("FAILED");
        }

        // twitter.start();
    }

}
