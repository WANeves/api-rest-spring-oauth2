package zup.presentation.handler;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import zup.common.dto.ResponseErrorDto;

/**
 * {@link ControllerAdvice} responsável por manipular os erros globalmente.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public @ResponseBody
    ResponseErrorDto defaultErrorHandler(Exception e) throws Exception {
        ResponseStatus annotation = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);
        if (annotation != null) {
            return new ResponseErrorDto(annotation.code().value(), annotation.reason());
        }

        return new ResponseErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public @ResponseBody
    ResponseErrorDto notFound() {
        return new ResponseErrorDto(HttpStatus.NOT_FOUND.value(), "Recurso não encontrado.");
    }
}
