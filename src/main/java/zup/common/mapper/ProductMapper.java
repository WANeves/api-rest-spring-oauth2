package zup.common.mapper;

import zup.common.dto.ProductDto;
import zup.common.entity.Product;

import java.util.List;

/**
 * {@link MapperEntityDto} responsável por converter a entidade {@link Product} em um {@link ProductDto},
 * e vice e versa.
 */
public interface ProductMapper extends MapperEntityDto<Product, ProductDto> {

    /**
     * Converte uma lista de entidades de products em uma lista de DTO de products utilizando a conversão padrão.
     * @param products Lista de entidade de products.
     * @return Lista de DTO de products.
     */
    List<ProductDto> toDtoList(List<Product> products);
}
