package zup.business.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zup.business.ProductService;
import zup.common.dto.ProductDto;
import zup.common.entity.Product;
import zup.common.mapper.ProductMapper;
import zup.repository.ProductRepository;
import zup.utils.Util;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Implementação padrão de {@link ProductService}.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Optional<ProductDto> findOne(Long id) {
        log.info("Buscando um product pelo ID {}", id);

        return productMapper.toDto(this.productRepository.findById(id));
    }


    @Override
    public List<ProductDto> findAll() {
        log.info("Buscando todos os products");

        Collection<Product> collectionFromIteralbe = Util.getCollectionFromIteralbe(productRepository.findAll());
        return productMapper.toDtoList((List<Product>) collectionFromIteralbe);
    }

    @Override
    public Optional<ProductDto> save(Product entidade) {
        log.info("Persistindo product: {}", entidade);
        return productMapper.toDto(Optional.of(this.productRepository.save(entidade)));
    }

    @Override
    public void delete(Long id) {
        log.info("Removendo product pelo ID {}", id);
        productRepository.delete(id);
    }

    @Override
    public void deleteAll() {
        log.info("Removendo todos os products");
        productRepository.deleteAll();
    }
}