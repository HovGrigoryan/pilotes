package com.shop.pilotes.mapper.response;

import com.shop.pilotes.dto.response.PilotesResponseDto;
import com.shop.pilotes.mapper.Mapper;
import com.shop.pilotes.model.Pilotes;
import org.springframework.stereotype.Component;

@Component
public class PilotesResponseMapper implements Mapper<PilotesResponseDto, Pilotes> {

    @Override
    public PilotesResponseDto mapToDto(Pilotes pilotes) {
        return PilotesResponseDto.builder()
                .id(pilotes.getId())
                .deliveryAddress(pilotes.getDeliveryAddress())
                .numberOfPilotes(pilotes.getNumberOfPilotes().value)
                .orderTotal(pilotes.getOrderTotal())
                .createdAt(pilotes.getCreatedAt())
                .build();
    }

}
