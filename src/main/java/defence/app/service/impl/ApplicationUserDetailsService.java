package defence.app.service.impl;

import defence.app.model.entity.RoleEntity;
import defence.app.model.entity.UserEntity;
import defence.app.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class ApplicationUserDetailsService implements UserDetailsService { //Класът служи като реализация
    // на интерфейса UserDetailsService от Spring Security.

    private final UserRepository userRepository;
    public ApplicationUserDetailsService(UserRepository userRepository) {
        this.userRepository=userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //Метода се използва за аутентикация на потребители, когато се опитват да влязат в системата.
        // Дава възможност на Spring Security да вземе потребителските данни от базата данни и да ги използва за
        // проверка при входа на потребител.

        return
                userRepository.findByUsername(username)
                .map(ApplicationUserDetailsService::map)
                        // този метод е маперът между username и userDetails
                        //В метода map се извиква статичния метод map, който преобразува обектите от тип RoleEntity в GrantedAuthority.
                        //Извиква се върху Optional, ако той е попълнен (ако има потребител със съответното
                        // потребителско име).Този метод извиква статичния метод map на същия клас ApplicationUserDetailsService.

                .orElseThrow(()-> new UsernameNotFoundException("Потребител " + username + " не е намерен!"));
    }

    private static UserDetails map(UserEntity userEntity) { // методът map преобразува данните за потребителя (UserEntity)
        // в обект от тип UserDetails, който предоставя Spring Security.

       return User
               .withUsername(userEntity.getUsername())// Този статичен метод създава UserDetails обект
               // от потребителското име на UserEntity

               .password(userEntity.getPassword()) // задава паролата на потребителя от UserEntity

                .authorities(userEntity.getRoles().stream()
                        .map(ApplicationUserDetailsService::map).toList()) //дефинират ролите на потребителя.
               // Използва се map методът, който конвертира ролите от RoleEntity към GrantedAuthority обекти.
               // Резултатът е списък от роли, които се предоставят на потребителя

               .build(); // методът, който създава финалния UserDetails обект, който ще бъде използван от
        // Spring Security за проверка на аутентикацията на потребителя.

    }

    private static GrantedAuthority map(RoleEntity roleEntity) {

        return new SimpleGrantedAuthority(
                "ROLE_" + roleEntity.getRole().name()
        );
        // Методът map в класа ApplicationUserDetailsService извършва мапиране на обект от тип RoleEntity към обект
        // от тип GrantedAuthority. В Spring Security, GrantedAuthority представлява ролята на потребителя.
        // Методът играе ключова роля при изграждането на списъка от роли за конкретен потребител.
        // SimpleGrantedAuthority е стандартната имплементация на интерфейса GrantedAuthority в Spring Security.
        // Той представлява проста представка на ролята на потребителя.
        // "ROLE_" + roleEntity.getRole().name(): Създава текстово представяне на ролята, като прибавя префикс "ROLE_". Този префикс е стандартен в Spring Security и се използва за яснота и консистентност
       // Когато този метод се извиква, резултатът е GrantedAuthority обект, който представлява ролята на потребителя.
        // Този обект ще бъде включен в списъка от роли, които ще бъдат предоставени на потребителя при създаване на
        // UserDetails обект (в метода map(UserEntity userEntity)). Така потребителят ще има правилните роли, които ще бъдат използвани от Spring Security за авторизацията и определянето на правата му.
    }


}
//ПЪЛНО ОБЯСНЕНИЕ ЗА UserDetails:
// UserDetails е интерфейс в Spring Security, който предоставя информация за потребителя. Този интерфейс дефинира
// методи, които трябва да бъдат имплементирани от обект, представляващ детайли за потребителя.
//В основата си, UserDetails съхранява информацията, необходима за аутентикацията и авторизацията на потребителя.
// Този обект съдържа информация като потребителско име, парола, списък от роли (предоставени права),
// статус на акаунта (активен, заключен, изтекъл) и други.
//Основните методи, които трябва да се имплементират при използване на UserDetails:
//getUsername(): Връща потребителското име на потребителя.
//getPassword(): Връща паролата на потребителя.
//getAuthorities(): Връща колекция от обекти, представляващи правата (ролите) на потребителя.
// Този метод е от значение при авторизацията, когато системата трябва да знае какви действия са разрешени за
// даден потребител.
//isEnabled(): Връща булева стойност, която показва дали потребителят е активен. Например, ако акаунтът е бил
// заключен или деактивиран, този метод ще върне false.
//isAccountNonExpired(), isAccountNonLocked(), isCredentialsNonExpired(): Тези методи определят дали акаунтът не
// е изтекъл, не е заключен и не са изтекли учетните данни (паролата). Те връщат булеви стойности, указващи
// съответно статуса на акаунта и учетните данни.
//В резултат, когато Spring Security използва UserDetails, той извиква тези методи, за да вземе необходимата информация за потребителя по време на аутентикация и авторизация. Класът User, предоставен от Spring Security, е стандартна имплементация на интерфейса UserDetails.