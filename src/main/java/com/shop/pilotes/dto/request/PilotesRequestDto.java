package com.shop.pilotes.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PilotesRequestDto {
    private String deliveryAddress;
    private Integer numberOfPilotes;
}
