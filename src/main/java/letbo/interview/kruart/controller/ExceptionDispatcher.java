package letbo.interview.kruart.controller;

import letbo.interview.kruart.util.exception.FileNotExistsException;
import letbo.interview.kruart.util.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;


@RestControllerAdvice(annotations = RestController.class)
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionDispatcher {
    private static Logger log = LoggerFactory.getLogger(ExceptionDispatcher.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FileNotExistsException.class)
    public String handleError(FileNotExistsException e) {
        log.error(e.toString());
        return e.toString();
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)  // 404
    @ExceptionHandler({UserNotFoundException.class})
    public String handleError(UserNotFoundException e) {
        log.error(e.toString());
        return e.getMessage();
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public String handleError(HttpServletRequest req, MethodArgumentNotValidException e) {
        String msgErr = e.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(" and "));

        log.error(msgErr + " at " + req.getRequestURL());
        return msgErr;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleError(HttpServletRequest req, Exception e) {
        String error = String.format("Something has gone wrong. %s: %s", e.getClass().getName(), e.getMessage());
        log.error(error + " at " + req.getRequestURL());
        return error;
    }
}
