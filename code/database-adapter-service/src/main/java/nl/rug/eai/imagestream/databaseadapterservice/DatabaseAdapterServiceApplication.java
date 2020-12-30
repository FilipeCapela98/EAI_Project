package nl.rug.eai.imagestream.databaseadapterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DatabaseAdapterServiceApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(DatabaseAdapterServiceApplication.class, args);
//		ImageJSONController imageJSONController = applicationContext.getBean(ImageJSONController.class);
//
//		imageJSONController.start("{'hello': 'myname'}");
	}
}
