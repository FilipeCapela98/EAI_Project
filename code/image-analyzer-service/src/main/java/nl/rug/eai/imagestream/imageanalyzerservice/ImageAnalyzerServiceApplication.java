package nl.rug.eai.imagestream.imageanalyzerservice;

import com.google.gson.Gson;
import nl.rug.eai.imagestream.commons.model.AnnotatedImage;
import nl.rug.eai.imagestream.commons.model.TweetImage;
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
		String identifier = "1";
		String raw_url = "https://pbs.twimg.com/media/EpOMW12XUAQXnrx.jpg";
		Gson gson = new Gson();
		TweetImage dummy_request = new TweetImage(identifier,"PNG",raw_url);
		imageAnalyzerProcess.start(gson.toJson(dummy_request));
//		AnnotatedImage processedImage = visionService.processImage(identifier, raw_url);
//		System.out.println(processedImage);
	}

}
