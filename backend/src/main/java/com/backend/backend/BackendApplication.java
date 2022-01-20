package com.backend.backend;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RestController
@SpringBootApplication
public class BackendApplication {

	private static final Logger log = LoggerFactory.getLogger("john");
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	@PostMapping()
	public String GetFile(@RequestParam("image") MultipartFile image,@RequestParam("height") int width,@RequestParam("height") int height){
		
		FileDetails temp =new FileDetails(image,width,height);
		log.info(temp.toString());

		return  "success";

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


}
