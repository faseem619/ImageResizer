package com.backend.backend;



import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.*;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
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


	
	private final FileDetailsService fileDetailsService;
	@Autowired
	public BackendApplication(FileDetailsService fileDetailsService) {
		this.fileDetailsService = fileDetailsService;
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	@PostMapping()
	public String PostFile(@RequestParam("image") MultipartFile image,@RequestParam("width") int width,@RequestParam("height") int height, @RequestParam("id") String id) throws IllegalStateException, IOException{
		
		FileDetails temp =new FileDetails(id,multipartToFile(image,"tempimage"),width,height);
		if(!fileDetailsService.checkIfExist(id))
			fileDetailsService.saveFile(temp);
		return  "success";

	}

	@GetMapping(value = "/{id}")
	public Image GetFile(@PathVariable("id") String id){
		FileDetails fileDetails=null;
		if(fileDetailsService.checkIfExist(id))
			fileDetails = fileDetailsService.GetFile(id);
		Image image=null;
		Image compressedImage =null;

		try {
			 image = ImageIO.read(fileDetails.image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{

			compressedImage = image.getScaledInstance(fileDetails.width,fileDetails.height,Image.SCALE_SMOOTH);
		}

		return compressedImage;
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/").allowedOrigins("http://localhost:3000");
			}
		};
	}
	public  static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
    File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
    multipart.transferTo(convFile);
    return convFile;
}


}
