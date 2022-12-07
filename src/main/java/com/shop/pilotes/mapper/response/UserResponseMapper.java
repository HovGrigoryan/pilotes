package com.shop.pilotes.mapper.response;

import com.shop.pilotes.dto.response.UserResponseDto;
import com.shop.pilotes.mapper.Mapper;
import com.shop.pilotes.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class UserResponseMapper implements Mapper<UserResponseDto, User> {

    private final PilotesResponseMapper pilotesResponseMapper;

    public UserResponseMapper(PilotesResponseMapper pilotesResponseMapper) {
        this.pilotesResponseMapper = pilotesResponseMapper;
    }

    @Override
    public UserResponseDto mapToDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .telephone(user.getTelephone())
                .pilotes(user.getPilotes() != null ?
                        user.getPilotes().stream().map(pilotesResponseMapper::mapToDto).collect(Collectors.toList()) : new ArrayList<>())
                .build();
    }
}
