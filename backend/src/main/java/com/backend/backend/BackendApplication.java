package com.backend.backend;



import java.io.File;

import java.io.IOException;




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
	@PostMapping()
	public String PostFile(@RequestParam("image") MultipartFile image,@RequestParam("width") int width,@RequestParam("height") int height, @RequestParam("id") String id) throws IllegalStateException, IOException{
		
		FileDetails temp =new FileDetails(id,image.getBytes(),width,height);
		
		fileDetailsService.saveFile(temp);
		return  "success";

	}
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(value = "/{id}")
	public ResponseEntity<ByteArrayResource> GetFile(@PathVariable("id") String id) throws IOException{
		FileDetails fileDetails=fileDetailsService.GetFile(id);


		HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=img.jpg");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
		log.info(fileDetails.toString());

		ByteArrayResource resource = new ByteArrayResource(fileDetails.getImage());

	

		return ResponseEntity.ok()
                .headers(header)
                .contentLength(fileDetails.getImage().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
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
