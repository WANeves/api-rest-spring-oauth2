package zup.business;

import zup.common.dto.ProductDto;
import zup.common.entity.Product;

import java.util.List;
import java.util.Optional;

/**
 * Service utilizado para product .
 */
public interface ProductService {

    /**
     * Recupera um product através de seu identificador.
     * @param id O identificador do product.
     * @return O product ou {@code null}, caso não exista.
     */
    Optional<ProductDto> findOne(Long id);

    /**
     * Retorna uma lista contendo todos os products.
     * @return Lista com todos os products.
     */
    List<ProductDto> findAll();

    /**
     * Salva um product.
     * @return retorna o product.
     */
    Optional<ProductDto> save(Product entidade);

    /**
     * Remove um product.
     */
     void delete(Long id);

    /**
     * Remove todos os products.
     */
    void deleteAll();

}
