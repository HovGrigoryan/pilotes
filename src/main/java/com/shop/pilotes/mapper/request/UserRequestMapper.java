package com.shop.pilotes.mapper.request;

import com.shop.pilotes.dto.request.UserRequestDto;
import com.shop.pilotes.mapper.Mapper;
import com.shop.pilotes.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserRequestMapper implements Mapper<User, UserRequestDto> {
    @Override
    public User mapToDto(UserRequestDto userRequestDto) {
        return User.builder()
                .firstName(userRequestDto.getFirstName())
                .lastName(userRequestDto.getLastName())
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .role(userRequestDto.getRole())
                .telephone(userRequestDto.getTelephone())
                .build();
    }
}
