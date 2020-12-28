package nl.rug.eai.imagestream.imagepreprocessingservice;

import com.google.gson.Gson;
import nl.rug.eai.imagestream.commons.model.FilteredTweetImage;
import nl.rug.eai.imagestream.imagepreprocessingservice.controller.ImagePreprocessingController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ImagePreprocessingServiceApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(ImagePreprocessingServiceApplication.class, args);
		ImagePreprocessingController imagePreprocessingController = applicationContext.getBean(ImagePreprocessingController.class);
		Gson gson = new Gson();

		FilteredTweetImage[] tweetImages = {new FilteredTweetImage("1","PNG","https://pbs.twimg.com/media/EpOMW12XUAQXnrx.jpg","cat"),
				new FilteredTweetImage("3","PNG","https://pbs.twimg.com/media/Ep4KOCYUwAI3DVY?format=jpg&name=large","dog")
				,new FilteredTweetImage("4","PNG","https://pbs.twimg.com/media/EqWLQmgW8AEubu4.jpg","dog"),
				new FilteredTweetImage("5","PNG","https://pbs.twimg.com/media/EqWLQmgW8AEubu4.png","dog"),
				new FilteredTweetImage("6","PNG","https://pbs.twimg.com/media/Eowl0bbUwAACm9C?format=gif","dog"),
				new FilteredTweetImage("7","PNG","https://pbs.twimg.com/media/EpOMW12XUAQXnrx.knn","cat")
		};
		for(int i=0;i<tweetImages.length;i++) {
			imagePreprocessingController.start(gson.toJson(tweetImages[i]));
		}

	}

}
