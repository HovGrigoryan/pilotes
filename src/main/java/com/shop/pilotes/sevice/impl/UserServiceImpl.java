package com.shop.pilotes.sevice.impl;

import com.shop.pilotes.dto.response.UserResponseDto;
import com.shop.pilotes.exception.EntityAlreadyExistException;
import com.shop.pilotes.mapper.response.UserResponseMapper;
import com.shop.pilotes.model.User;
import com.shop.pilotes.repository.UserRepository;
import com.shop.pilotes.sevice.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserResponseMapper mapper;

    public UserServiceImpl(UserRepository userRepository, UserResponseMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public UserResponseDto save(User user) {
        if (user.getId() == null && userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EntityAlreadyExistException("User with " + user.getEmail() + " email is already exist.");
        }

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        return mapper.mapToDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto findUser(Long id) {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isEmpty()) {
            throw new EntityNotFoundException("User with " + id + " id is not exist.");
        }

        return mapper.mapToDto(userById.get());
    }

    @Override
    public List<User> findUserByFirstNameContains(String like) {
        return userRepository.findByFirstNameContains(like);
    }

}