package defence.app.web;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.AccessDeniedException;

@ControllerAdvice // try-catch за всичките контролери
public class GlobalErrorHandlingController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    public ModelAndView handleNotFound(ObjectNotFoundException exception) {

        ModelAndView modelAndView = new ModelAndView("error/404");
//        modelAndView.addObject("id", exception.getIdentifier());

        return modelAndView;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServerErrorException.class)
    public ModelAndView handleServerError(ServerErrorException serverErrorException) {

        ModelAndView modelAndView = new ModelAndView("error/500");

        return modelAndView;
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDeniedError(AccessDeniedException accessDeniedException) {

        ModelAndView modelAndView = new ModelAndView("error/access-denied");

        return modelAndView;
    }
}

//Todo logback appender за google  има и в спиринг