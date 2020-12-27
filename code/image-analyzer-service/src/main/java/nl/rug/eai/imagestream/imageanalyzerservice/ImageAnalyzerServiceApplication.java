package nl.rug.eai.imagestream.imageanalyzerservice;

import com.google.gson.Gson;
import nl.rug.eai.imagestream.commons.model.AnnotatedImage;
import nl.rug.eai.imagestream.commons.model.FilteredTweetImage;
import nl.rug.eai.imagestream.imageanalyzerservice.controller.ImageAnalyzerController;
import nl.rug.eai.imagestream.imageanalyzerservice.service.CloudVisionServiceUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImageAnalyzerServiceApplication {

	public static void main(String[] args) {

		ApplicationContext applicationContext = SpringApplication.run(ImageAnalyzerServiceApplication.class, args);
		ImageAnalyzerController imageAnalyzerProcess = applicationContext.getBean(ImageAnalyzerController.class);
//		CloudVisionServiceUtil visionService = applicationContext.getBean(CloudVisionServiceUtil.class);
		Gson gson = new Gson();

		FilteredTweetImage[] tweetImages = {new FilteredTweetImage("1","PNG","https://pbs.twimg.com/media/EpOMW12XUAQXnrx.jpg","cat"),
				new FilteredTweetImage("3","PNG","https://pbs.twimg.com/media/Ep4KOCYUwAI3DVY?format=jpg&name=large","dog")
//				,new FilteredTweetImage("4","PNG","https://pbs.twimg.com/media/Epxs4uTU0AAz3Fs?format=jpg&name=large","dog"),
//				new FilteredTweetImage("5","PNG","https://pbs.twimg.com/media/EpPMY9LVEAAmHi7?format=jpg&name=large","dog"),
//				new FilteredTweetImage("6","PNG","https://pbs.twimg.com/media/Eowl0bbUwAACm9C?format=jpg&name=large","dog"),
//				new FilteredTweetImage("7","PNG","https://pbs.twimg.com/media/Br3GFsoIEAAg2F4?format=jpg&name=small","cat")
				};
		for(int i=0;i<tweetImages.length;i++) {
			imageAnalyzerProcess.start(gson.toJson(tweetImages[i]));
		}
	}

}
