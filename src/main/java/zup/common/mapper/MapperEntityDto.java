package zup.common.mapper;

import java.util.Optional;

/**
 * Interface base para mapeamento entre entidades e DTOs.
 * @param <E> Tipo da entidade.
 * @param <D> Tipo do DTO.
 */
public interface MapperEntityDto<E, D> {

    /**
     * Converte a entidade fonte no DTO destino.
     * @param entidade Entidade fonte.
     * @return DTO de destino.
     */
    Optional<D> toDto(Optional<E> entidade);

    /**
     * Converte o DTO na entidade destino.
     * @param dto DTO fonte.
     * @return Entidade destino.
     */
    E toEntity(D dto);

}
