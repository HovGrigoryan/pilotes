package com.shop.pilotes.sevice.impl;

import com.shop.pilotes.enums.Role;
import com.shop.pilotes.exception.EntityAlreadyExistException;
import com.shop.pilotes.mapper.response.UserResponseMapper;
import com.shop.pilotes.model.Pilotes;
import com.shop.pilotes.model.User;
import com.shop.pilotes.repository.UserRepository;
import com.shop.pilotes.sevice.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserResponseMapper mapper;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, mapper);
    }


    @Test
    void save() {
        List<Pilotes> pilotesList = new LinkedList<>();
        User user = user(pilotesList);
        when(userRepository.save(user)).thenReturn(user);
        assertNull(userService.save(user));
    }

    @Test
    void saveNegativeCase() {
        List<Pilotes> pilotesList = new LinkedList<>();
        User user = user(pilotesList);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        assertThrows(EntityAlreadyExistException.class, () -> userService.save(user));
    }

    @Test
    void findUser() {
        Long id = 1L;
        List<Pilotes> pilotesList = new LinkedList<>();
        User user = user(pilotesList);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        assertNull(userService.findUser(id));
    }

    @Test
    void findUserNegativeCase() {
        Long id = 1L;
        List<Pilotes> pilotesList = new LinkedList<>();
        User user = user(pilotesList);
        assertThrows(EntityNotFoundException.class, () -> userService.findUser(id));
    }

    @Test
    void findUserByFirstNameContains() {
        userService.findUserByFirstNameContains("test");
        verify(userRepository, times(1)).findByFirstNameContains(anyString());
    }

    private User user(List<Pilotes> pilotesList) {
        return User.builder().
                firstName("Leo").
                lastName("Smith").
                email("leo@gmail.com").
                password("leo").
                role(Role.ADMIN).
                telephone("1111").
                pilotes(pilotesList).build();
    }

}