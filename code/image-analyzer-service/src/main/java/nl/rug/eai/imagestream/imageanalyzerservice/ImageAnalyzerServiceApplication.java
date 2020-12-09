package nl.rug.eai.imagestream.imageanalyzerservice;

import nl.rug.eai.imagestream.imageanalyzerservice.service.CloudVisionService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImageAnalyzerServiceApplication {

	public static void main(String[] args) {

		ApplicationContext applicationContext = SpringApplication.run(ImageAnalyzerServiceApplication.class, args);
		CloudVisionService visionService = applicationContext.getBean(CloudVisionService.class);
		visionService.start();
	}

}
