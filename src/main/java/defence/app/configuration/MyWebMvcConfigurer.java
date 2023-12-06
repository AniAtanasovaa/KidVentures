package defence.app.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyCustomInterceptor())
                .addPathPatterns("/change"); //URL-ите, за които искам интерсепторът да бъде приложен
    }
}
//MyWebMvcConfigurer е конфигурационен клас за Spring MVC, който имплементира интерфейса WebMvcConfigurer.
// Този интерфейс предоставя възможност за конфигуриране на различни аспекти на Spring MVC приложението.
//В конкретния случай, класът има за цел да регистрира (addInterceptors) един Interceptor за обработка на
// HTTP заявките, когато те преминават през контролера в Spring MVC, който пък е моят интерцептор MyCustomInterceptor.
//Моят интерцептор ще се използва само за заявки, направени към пътя "/change". Други пътища няма да бъдат засегнати.