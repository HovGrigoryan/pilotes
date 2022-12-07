package com.shop.pilotes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PilotesResponseDto {
    private Long id;
    private String deliveryAddress;
    private Integer numberOfPilotes;
    private BigDecimal orderTotal;
    private LocalDateTime createdAt;
}
