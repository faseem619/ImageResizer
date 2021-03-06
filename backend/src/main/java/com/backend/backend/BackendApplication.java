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


	private static final Logger log = LoggerFactory.getLogger("john");
	private final FileDetailsService fileDetailsService;
	@Autowired
	public BackendApplication(FileDetailsService fileDetailsService) {
		this.fileDetailsService = fileDetailsService;
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	@GetMapping()
	public String getLandingPage(){
		return "Hello There. You Shouldnt be here. Go to https://image-resizer-123.netlify.app/";
	}

	//stores modified image with details into db
	@PostMapping()
	public String PostFile(@RequestParam("image") MultipartFile image,@RequestParam("width") int width,@RequestParam("height") int height, @RequestParam("id") String id) throws IllegalStateException, IOException{
		
		// convert the multipartfile into a file
		// the file is convereted to a buffered image
		// then the image is resized with the help of Graphics2d 
		// we obtain a byte array from the output stream to store in the DB
		File imageFile = multipartToFile(image, image.getOriginalFilename());
		
		BufferedImage tobeModifiedImage = ImageIO.read(imageFile);

		BufferedImage buffered =  resizeImage(tobeModifiedImage,width, height,image.getContentType());
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ImageIO.write(buffered, image.getContentType().split("/",2)[1], output );
		byte [] data = output.toByteArray();
		
		FileDetails temp =new FileDetails(id,data,width,height);
		fileDetailsService.saveFile(temp);
		return  "success";

	}
	// returns modified image
	@GetMapping(value = "/{id}")
	public ResponseEntity<ByteArrayResource> GetFile(@PathVariable("id") String id) throws IOException{
		FileDetails fileDetails=fileDetailsService.GetFile(id);

		// standard http headers for file transfer
		HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=compressed.jpg");
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
	@Bean //function to apply cors to all routes in one place.
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/").allowedOrigins("https://image-resizer-123.netlify.app/");
				registry.addMapping("/**").allowedOrigins("https://image-resizer-123.netlify.app/");
			}
		};
	}
	

	//function to convert mulltipartt file (default type when sending  file over http) to normal java file type
	public  static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
		File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName); //stored in temp folder to avoid permission problems
		multipart.transferTo(convFile);
		return convFile;
	}
	public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight,String imageType) throws IOException {
		BufferedImage resizedImage;
		if(imageType.equals("image/png"))
			resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);

		else
			resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

		Graphics2D graphics2D = resizedImage.createGraphics();
		graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
		graphics2D.dispose();
		return resizedImage;
	}
	



}
