package defence.app.service;

import defence.app.model.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public UserEntity getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (authentication != null && authentication.getPrincipal() instanceof UserEntity) {

            Long loggedUserId = ((UserEntity) authentication.getPrincipal()).getId();

            return (UserEntity) authentication.getPrincipal();

        }
        return null;
    }
}