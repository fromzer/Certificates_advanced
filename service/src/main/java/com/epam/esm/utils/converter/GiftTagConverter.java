package com.epam.esm.utils.converter;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.model.GiftTag;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class GiftTagConverter {

    public static GiftTag convertToServiceLayerEntity(TagDTO entityDto) {
        return entityDto == null ? null :
                GiftTag.builder()
                        .id(entityDto.getId())
                        .name(entityDto.getName())
                        .build();
    }

    public static TagDTO convertToPersistenceLayerEntity(GiftTag entityDto) {
        return entityDto == null ? null :
                TagDTO.builder()
                        .id(entityDto.getId())
                        .name(entityDto.getName())
                        .build();
    }
}
