package pet.project.pasteBinApplication.web.mappers;

import java.util.List;

public interface AbleMapping<E, D> {

    D toDto(E entity);

    List<D> toDto(List<E> entities);

    E toEntity(D dto);

    List<E> toEntity(List<D> dtos);
}
