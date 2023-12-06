package defence.app.service.impl;

import defence.app.model.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService { //класът AuthenticationService предоставя метод, който се използва
    // за вземане на информация за текущия аутентикиран потребител (логнат потребител).предоставя удобен
    // начин за получаване на информация за текущия аутентикиран потребител в различни части на приложението


    public UserEntity getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Този ред взема текущия Authentication обект от SecurityContextHolder. Authentication предоставя информация за текущата аутентикация в системата.


        if (authentication != null && authentication.getPrincipal() instanceof UserEntity) {
            //Тази част проверява дали authentication не е null и дали principal на authentication е инстанция
            // на UserEntity. В Spring Security, когато потребител е успешно аутентикиран,
            // principal на Authentication обекта съдържа информацията за потребителя.

            Long loggedUserId = ((UserEntity) authentication.getPrincipal()).getId();

            return (UserEntity) authentication.getPrincipal();
//        Ако authentication не е null и principal е UserEntity,
//        методът връща UserEntity обекта, който представлява текущия аутентикиран потребител.
        }
        return null; //Ако няма аутентикация или principal не е UserEntity, методът връща null.
    }
}