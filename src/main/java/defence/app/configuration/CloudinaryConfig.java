package defence.app.configuration;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CloudinaryConfig {



    private final String CLOUD_NAME = "da8uhjfq5";
    private final String API_KEY = "694139327482814";
    private final String API_SECRET = "";



    @Bean
    public Cloudinary cloudinary() {


        return new Cloudinary(ObjectUtils.asMap("cloud_name", CLOUD_NAME,
                "api_key", API_KEY,
                "api_secret", API_SECRET,
                "secure", true));
    }
}
