package zup.common.mapper.core;

import org.springframework.stereotype.Component;
import zup.common.dto.ProductDto;
import zup.common.entity.Product;
import zup.common.mapper.ProductMapper;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementação padrão do {@link ProductMapper}.
 */
@Component
public class ProductMapperImpl implements ProductMapper {

	@Override
	public Optional<ProductDto> toDto(Optional<Product> entidade) {

		if (!entidade.isPresent()) {
			return Optional.empty();
		}
		
		Optional<ProductDto> dto = Optional.of(new ProductDto());
		dto.get().setId(entidade.get().getId());
		dto.get().setName(entidade.get().getName());
		dto.get().setDescription(entidade.get().getDescription());
		dto.get().setCategory(entidade.get().getCategory());

		return dto;
	}

	@Override
	public Product toEntity(ProductDto dto) {

		if (dto == null) {
			return null;
		}
		Product entidade = new Product();
		entidade.setId(dto.getId());
		entidade.setName(dto.getName());
		entidade.setDescription(dto.getDescription());
		entidade.setCategory(dto.getCategory());

		return entidade;
	}

	@Override
	public List<ProductDto> toDtoList(List<Product> products) {

		List<ProductDto> prods = new ArrayList<>();

		if (products == null) {
			return null;
		}

		for (Product prod :  products){
			prods.add(toDto(Optional.of(prod)).get());
		}

		return prods;
	}
}
