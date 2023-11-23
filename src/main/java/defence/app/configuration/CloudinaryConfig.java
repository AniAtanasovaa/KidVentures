package defence.app.configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig { //TODO CLOUDINARY

    private final String CLOUD_NAME = "dhkodgkzy";
    private final String API_KEY = "144849744445361";
    private final String API_SECRET = System.getenv("CLOUDINARY_SECRET");

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap("cloud_name", CLOUD_NAME,
                "api_key", API_KEY,
                "api_secret", API_SECRET,
                "secure", true));
    }
}
