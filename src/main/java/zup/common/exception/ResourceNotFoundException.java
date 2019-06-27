package zup.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção para recurso não encontrado.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Recurso solicitado não foi encontrado.")
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3459343624654912735L;
}
