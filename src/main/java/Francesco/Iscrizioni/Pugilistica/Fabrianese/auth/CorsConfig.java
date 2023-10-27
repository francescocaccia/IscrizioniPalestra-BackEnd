package Francesco.Iscrizioni.Pugilistica.Fabrianese.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("https://iscrizioni-palestra-front-end-git-main-francescocaccia.vercel.app")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS").allowCredentials(true)
						.maxAge(3600);
			}
		};
	}
}
