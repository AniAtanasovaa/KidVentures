package defence.app.configuration;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;


@Configuration
public class ApplicationBeanConfiguration { //Инициализирам, като beans ModelMapper и

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    } // Улеснява копирането на данни (mapping) от един Java обект (източник) към друг (цел). Полезно, когато имаме два обекта със сходна структура, но различни типове или имена на полета.
    // ModelMapper автоматично открива подобията между тези обекти и извършва копирането на данните. Спестява код.
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) { //Използвам за изпращане на HTTP заявки към външни служби или ресурси.
        //Конфигурирам със стандартни HTTP хедъри за JSON съдържание.
        return restTemplateBuilder
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }


}
