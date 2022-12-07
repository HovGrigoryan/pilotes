package com.shop.pilotes.dto.response;

import com.shop.pilotes.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String telephone;
    private List<PilotesResponseDto> pilotes;

}
