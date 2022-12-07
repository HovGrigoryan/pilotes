package com.shop.pilotes.controller;

import com.shop.pilotes.dto.request.UserRequestDto;
import com.shop.pilotes.dto.response.UserResponseDto;
import com.shop.pilotes.mapper.request.UserRequestMapper;
import com.shop.pilotes.model.User;
import com.shop.pilotes.sevice.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UserRequestMapper mapper;

    public UserController(UserService userService, UserRequestMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> newUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        User user = mapper.mapToDto(userRequestDto);
        return ResponseEntity.ok(userService.save(user));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUser(id));
    }

}
