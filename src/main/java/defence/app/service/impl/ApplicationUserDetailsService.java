package defence.app.service.impl;

import defence.app.model.entity.RoleEntity;
import defence.app.model.entity.UserEntity;
import defence.app.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class ApplicationUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    public ApplicationUserDetailsService(UserRepository userRepository) {
        this.userRepository=userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // този метод е маперът между username i userDetails
        return
                userRepository.findByUsername(username)
                .map(ApplicationUserDetailsService::map)
                .orElseThrow(()-> new UsernameNotFoundException("User " + username + " not found!"));
    }

    private static UserDetails map(UserEntity userEntity) {

       return User
               .withUsername(userEntity.getUsername())
               .password(userEntity.getPassword())
                .authorities(userEntity.getRoles().stream()
                        .map(ApplicationUserDetailsService::map).toList())

               .build();

    }

//    private List<GrantedAuthority> getAllAuthorities (UserEntity userEntity) {
//        return userEntity.getRoles().stream().map(this::map).toList();
//    }

    private static GrantedAuthority map(RoleEntity roleEntity) {
        return new SimpleGrantedAuthority(
                "ROLE_" + roleEntity.getRole().name()
        );
    }


}
