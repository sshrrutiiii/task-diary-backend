import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOriginPatterns("*")   // allow all origins
                .allowedMethods("*")          // allow ALL methods (VERY IMPORTANT)
                .allowedHeaders("*")          // allow ALL headers
                .exposedHeaders("*")
                .allowCredentials(false);     // MUST be false with "*"
    }
}