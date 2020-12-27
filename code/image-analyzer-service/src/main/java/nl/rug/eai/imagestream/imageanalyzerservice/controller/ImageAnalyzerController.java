package nl.rug.eai.imagestream.imageanalyzerservice.controller;

import lombok.extern.slf4j.Slf4j;
import nl.rug.eai.imagestream.imageanalyzerservice.service.ImageAnalyzerRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ImageAnalyzerController {

    @Autowired
    ImageAnalyzerRunner imageAnalyzerRunner;

    public synchronized void start(String topic) {
        log.info("Received request: " + topic);
        imageAnalyzerRunner.run(topic);
    }
}
