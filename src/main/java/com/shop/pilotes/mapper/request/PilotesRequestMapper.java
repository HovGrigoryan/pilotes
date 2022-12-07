package com.shop.pilotes.mapper.request;

import com.shop.pilotes.config.GlobalConfig;
import com.shop.pilotes.dto.request.PilotesRequestDto;
import com.shop.pilotes.enums.NumberOfPilotes;
import com.shop.pilotes.mapper.Mapper;
import com.shop.pilotes.model.Pilotes;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class PilotesRequestMapper implements Mapper<Pilotes, PilotesRequestDto> {

    private final GlobalConfig config;

    public PilotesRequestMapper(GlobalConfig config) {
        this.config = config;
    }

    @Override
    public Pilotes mapToDto(PilotesRequestDto pilotesRequestDto) {
        NumberOfPilotes numberOfPilotes = NumberOfPilotes.from(pilotesRequestDto.getNumberOfPilotes());

        return Pilotes.builder()
                .deliveryAddress(pilotesRequestDto.getDeliveryAddress())
                .numberOfPilotes(numberOfPilotes)
                .orderTotal(config.getSinglePrice().multiply(BigDecimal.valueOf(numberOfPilotes.value)))
                .createdAt(LocalDateTime.now())
                .build();
    }
}
