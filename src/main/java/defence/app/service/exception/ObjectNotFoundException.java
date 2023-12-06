package defence.app.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException {


    public ObjectNotFoundException(String message) {
        super(message);

    }



}
//Класът ObjectNotFoundException е част от обработката на грешки в Spring приложението.
// Декларира изключение, което се хвърля, когато обект не бъде намерен, и по този начин предизвиква HTTP статус
// код 404 (NOT FOUND).
//Анотацията @ResponseStatus(HttpStatus.NOT_FOUND) указва как Spring да реагира при хвърлянето на този вид изключение.
// В конкретният случай, когато се хвърли ObjectNotFoundException, Spring ще генерира HTTP отговор със статус код 404 (NOT FOUND).
// Статус код 404 се използва, когато търсеният ресурс не е открит.
//Класът ми ObjectNotFoundException наследява RuntimeException, което е тип изключение, което може да се хвърля
// без да бъде декларирано в сигнатурата на метода или обработено.
// Този клас има конструктор, който приема съобщение и го предава към конструктора на родителския клас
// (RuntimeException), като така се задава съобщението за грешка