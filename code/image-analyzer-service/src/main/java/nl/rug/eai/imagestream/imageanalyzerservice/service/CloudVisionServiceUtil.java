package nl.rug.eai.imagestream.imageanalyzerservice.service;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.LocalizedObjectAnnotation;
import com.google.cloud.vision.v1.NormalizedVertex;
import com.google.gson.Gson;
import nl.rug.eai.imagestream.commons.model.AnnotatedImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/*
 * Code to perform cloud vision analysis on the image
 * */
@Service
@Slf4j
public class CloudVisionServiceUtil {
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private CloudVisionTemplate cloudVisionTemplate;

    public static Integer[] mColors = {
            0x0000FF, // light blue
            0x0B5394, // dark blue
            0xFF0000, // red
            0xFF9900, // orange
            0xFF00FF, // purple
            0x00FF00, // green
            0x666666, // dark gray
            0xEA9999 // pink
    };

    public AnnotatedImage processImage(String identifier,String tag, String image_url) {
        Gson gson = new Gson();
        String identifiedObject = "";
        String annotatedImage = "";
        try {
            URL imageResource_url = new URL(image_url);
            Resource imageResource = this.resourceLoader.getResource(image_url);
            AnnotateImageResponse response = this.cloudVisionTemplate.analyzeImage(
                    imageResource, Feature.Type.OBJECT_LOCALIZATION);
            annotatedImage = writeWithObjects(imageResource_url, response.getLocalizedObjectAnnotationsList());
            Map<String, Long> imageLabels =
                    response.getLocalizedObjectAnnotationsList()
                            .stream()
                            .collect(Collectors.groupingBy(
                                    LocalizedObjectAnnotation::getName, Collectors.counting()));
            identifiedObject = gson.toJson(imageLabels);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new AnnotatedImage(identifier,tag, identifiedObject,annotatedImage);
    }

    private static String writeWithObjects(URL inputImage, List<LocalizedObjectAnnotation> objects) {
        String imageString = "";
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            BufferedImage img = ImageIO.read(inputImage);
            annotateWithObjects(img, objects);
            ImageIO.write(img, "jpg", bos);
            byte[] imageBytes = bos.toByteArray();
            java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
            imageString = encoder.encodeToString(imageBytes);
            bos.close();
            return imageString;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    public static void annotateWithObjects(BufferedImage img, List<LocalizedObjectAnnotation> objects) {
        for (LocalizedObjectAnnotation object : objects) {
            annotateWithObject(img, object);
        }
    }

    private static void annotateWithObject(BufferedImage img, LocalizedObjectAnnotation object) {
        int width = img.getWidth();
        int height = img.getHeight();
        Random randomGenerator = new Random(); // Construct a new Random number generator
        int randomNumber = randomGenerator.nextInt(mColors.length);
        Graphics2D gfx = img.createGraphics();
        Polygon poly = new Polygon();
        for (NormalizedVertex vertex : object.getBoundingPoly().getNormalizedVerticesList()) {
            poly.addPoint((int) (vertex.getX() * width), (int) (vertex.getY() * height));
        }
        gfx.setStroke(new BasicStroke(4));
//        gfx.setColor(new Color(0x00ff00));
        gfx.setColor(new Color(mColors[randomNumber]));
        gfx.draw(poly);
    }
}
