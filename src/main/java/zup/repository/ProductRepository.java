package zup.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import zup.common.entity.Product;

import java.util.Optional;

/**
 * {@link Repository} utilizado para manipulação de {@link Product}s.
 */
public interface ProductRepository extends CrudRepository<Product, Long> {

    Optional<Product> findById(Long id);
}
