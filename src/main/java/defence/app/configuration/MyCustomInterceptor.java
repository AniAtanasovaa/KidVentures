package defence.app.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
// Spring interceptor, който се използва за обработка на заявките преди те да достигнат контролера,
// след изпълнението на заявките и след завършването на изпълнението на заявките.
//В случаят ще проверявам дали потребителят има роля ADMIN и само ако има, само тогава
//може да променя ролята на друг потребител т.е. да достига до пътя "/change"
public class MyCustomInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //Този метод се извиква преди изпълнението на заявката в контролера.
//Проверявам, дали текущият потребител е аутентикиран и дали principal в Authentication обекта е от тип UserDetails (т.е., дали потребителят е аутентикиран потребител в системата).
//Ако потребителят е администратор, той има роля "ROLE_ADMIN", и заявката продължава изпълнението си.
//Ако потребителят не е администратор, заявката се пренасочва към страницата за отказан достъп (/access-denied) и изпълнението на заявката се спира.

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //Authentication обектът в Spring Security представлява информацията за аутентикацията на текущия потребител.
        // Този обект съдържа информация като потребителското име, парола (след успешна аутентикация),
        // списък от роли (роли или оторизации) и други данни, свързани с потребителя и процеса на аутентикация.
        //Authentication обектът е централен компонент в Spring Security и играе ключова роля при определянето на
        // това дали потребителят е успешно аутентикиран и какви права или роли притежава.


        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Проверявам дали текущият потребител е администратор
            if (userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                return true; // Ако е администратор, продължавам изпълнението на заявката
            }
        }

        response.sendRedirect("/access-denied"); // В противен случай, пренасочвам към страницата за отказан достъп
        return false; // Изпълнението на заявката ще бъде спряно
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // Логика след изпълнение на заявката, преди визуализация на изгледа
        System.out.println("Interceptor: Post-handle logic");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // Логика след завършване на изпълнението на заявката
        System.out.println("Interceptor: After completion logic");
    }
}