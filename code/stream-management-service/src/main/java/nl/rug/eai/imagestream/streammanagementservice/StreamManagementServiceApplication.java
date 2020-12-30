package nl.rug.eai.imagestream.streammanagementservice;

import nl.rug.eai.imagestream.streammanagementservice.gateway.senders.StartCommandSender;
import nl.rug.eai.imagestream.commons.model.StartCommand;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class StreamManagementServiceApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(StreamManagementServiceApplication.class, args);
	}

}
