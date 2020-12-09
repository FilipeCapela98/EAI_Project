package nl.rug.eai.imagestream.imageanalyzerservice.service;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.LocalizedObjectAnnotation;
import com.google.cloud.vision.v1.NormalizedVertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
public class CloudVisionService {
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private CloudVisionTemplate cloudVisionTemplate;

    public void start() {
        Resource imageResource = this.resourceLoader.getResource("file:src/main/resources/friends.jpg");
        Resource outputImageResource = this.resourceLoader.getResource("file:src/main/resources/output.jpg");
        AnnotateImageResponse response = this.cloudVisionTemplate.analyzeImage(
                imageResource, Feature.Type.OBJECT_LOCALIZATION);
        try {
            writeWithObjects(imageResource.getFile().toPath(), outputImageResource.getFile().toPath(), response.getLocalizedObjectAnnotationsList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response.getLocalizedObjectAnnotationsList().toString());
    }

    private static void writeWithObjects(Path inputPath, Path outputPath, List<LocalizedObjectAnnotation> objects) {
        try {
            BufferedImage img = ImageIO.read(inputPath.toFile());
            annotateWithObjects(img, objects);
            ImageIO.write(img, "jpg", outputPath.toFile());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void annotateWithObjects(BufferedImage img, List<LocalizedObjectAnnotation> objects) {
        for (LocalizedObjectAnnotation object : objects) {
            annotateWithObject(img, object);
        }
    }

    private static void annotateWithObject(BufferedImage img, LocalizedObjectAnnotation object) {
        int width = img.getWidth();
        int height = img.getHeight();
        Graphics2D gfx = img.createGraphics();
        Polygon poly = new Polygon();
        for (NormalizedVertex vertex : object.getBoundingPoly().getNormalizedVerticesList()) {
            poly.addPoint((int) (vertex.getX() * width), (int) (vertex.getY() * height));
        }
        gfx.setStroke(new BasicStroke(5));
        gfx.setColor(new Color(0x00ff00));
        gfx.draw(poly);
    }

}
