package com.shop.pilotes.dto.request;

import com.shop.pilotes.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private String telephone;
}
