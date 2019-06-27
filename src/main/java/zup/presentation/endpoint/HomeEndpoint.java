package zup.presentation.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint do contexto inicial da aplicação.
 */

@RestController
public class HomeEndpoint {

    /**
     * Retorna a mensagem do contexto inicial.
     * @return Mensagem do contexto inicial.
     */
    @RequestMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("API RESTful RECRUITMENT ZUP.");
    }
}
