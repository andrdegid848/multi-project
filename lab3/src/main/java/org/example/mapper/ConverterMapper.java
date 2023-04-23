package org.example.mapper;

import org.example.database.entity.BaseEntity;
import org.example.dto.BaseDto;
import org.modelmapper.Converter;

public interface ConverterMapper<E extends BaseEntity<?>, D extends BaseDto> {

    default Converter<E, D> toDtoConverter() {
        return context -> {
            E source = context.getSource();
            D destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    default Converter<D, E> toEntityConverter() {
        return context -> {
            D source = context.getSource();
            E destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    void mapSpecificFields(E entity, D dto);

    void mapSpecificFields(D dto, E entity);
}
