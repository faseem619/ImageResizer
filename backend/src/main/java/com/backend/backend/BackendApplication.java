package com.backend.backend;



import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.IOException;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableConfigurationProperties // to enable second annoation
@EntityScan(basePackages = {"com.backend.backend"}) //to scan for entities in a  a package
@RestController
@SpringBootApplication
public class BackendApplication {


	private final Logger log = LoggerFactory.getLogger("john");
	private final FileDetailsService fileDetailsService;
	@Autowired
	public BackendApplication(FileDetailsService fileDetailsService) {
		this.fileDetailsService = fileDetailsService;
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	//stores modified image with details into db
	@PostMapping()
	public String PostFile(@RequestParam("image") MultipartFile image,@RequestParam("width") int width,@RequestParam("height") int height, @RequestParam("id") String id) throws IllegalStateException, IOException{
		
		// convert the multipartfile into a file, then into an image
		// the image is then scaled to the values given
		// then the image is converted to a buffered image to get an output stream
		// we obtain a byte array from the output stream to store in the DB
		File imageFile = multipartToFile(image, "tempfile");
		Image tobeModifiedImage = ImageIO.read(imageFile);
		Image modifiedImage =  tobeModifiedImage.getScaledInstance(width,height,Image.SCALE_SMOOTH);
		BufferedImage buffered = imageToBufferedImage(modifiedImage);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ImageIO.write(buffered, "jpg", output );
		byte [] data = output.toByteArray();
		
		FileDetails temp =new FileDetails(id,data,width,height);
		fileDetailsService.saveFile(temp);
		return  "success";

	}
	// returns modified image
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(value = "/{id}")
	public ResponseEntity<ByteArrayResource> GetFile(@PathVariable("id") String id) throws IOException{
		FileDetails fileDetails=fileDetailsService.GetFile(id);

		// standard http headers for file transfer
		HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=img.jpg");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
		log.info(fileDetails.toString());

		// converted to  resource so javascript can turn it into a blob type
		ByteArrayResource resource = new ByteArrayResource(fileDetails.getImage());

	

		return ResponseEntity.ok()
                .headers(header)
                .contentLength(fileDetails.getImage().length)
                .contentType(MediaType.parseMediaType("application/octet-stream")) //important
                .body(resource);
	}
	@Bean //function to apply cors to all routes in one place. cors configuration in postmapping left for future reference
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/").allowedOrigins("http://localhost:3000");
			}
		};
	}

	//function to convert mulltipartt file (default type when sending  file over http) to normal java file type
	public  static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
    File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName); //stored in temp folder to avoid permission problems
    multipart.transferTo(convFile);
    return convFile;
	}
	//function to convert an image into a buffered image
	public static BufferedImage imageToBufferedImage(Image im) {
     BufferedImage bi = new BufferedImage
        (im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_ARGB);
     Graphics bg = bi.getGraphics();
     bg.drawImage(im, 0, 0, null);
     bg.dispose();
     return bi;
  }


}
