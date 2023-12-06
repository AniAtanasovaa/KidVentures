package defence.app.configuration;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CloudinaryConfig {

    private final String CLOUD_NAME = "da8uhjfq5";
    private final String API_KEY = "694139327482814";
    private final String API_SECRET = "e4IJlIl_cWuxYmGIs6ipFN_17pA";

    @Bean
    public Cloudinary cloudinary() {

        return new Cloudinary(ObjectUtils.asMap("cloud_name", CLOUD_NAME,
                "api_key", API_KEY,
                "api_secret", API_SECRET,
                "secure", true));
    }
}
//Cloudinary - уеб услуга за управление на изображения и видеа в облак. Използвам този клас, за да конфигурирам и
// предоставя Cloudinary обект, който после може да бъде инжектиран и използван в другите части на приложението,
//за да го използвам за управление на изображения и видеа в Cloudinary