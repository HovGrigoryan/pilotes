package com.shop.pilotes.sevice.impl;

import com.shop.pilotes.config.GlobalConfig;
import com.shop.pilotes.dto.response.PilotesResponseDto;
import com.shop.pilotes.dto.response.UserResponseDto;
import com.shop.pilotes.enums.NumberOfPilotes;
import com.shop.pilotes.enums.Role;
import com.shop.pilotes.exception.OrderUpdateException;
import com.shop.pilotes.mapper.response.PilotesResponseMapper;
import com.shop.pilotes.model.Pilotes;
import com.shop.pilotes.model.User;
import com.shop.pilotes.repository.PilotesRepository;
import com.shop.pilotes.sevice.PilotesService;
import com.shop.pilotes.sevice.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PilotServiceImplTest {

    private PilotesService pilotesService;

    @Mock
    private PilotesRepository pilotesRepository;

    @Mock
    private UserService userService;

    @Mock
    private GlobalConfig config;

    @Mock
    private PilotesResponseMapper mapper;

    private final BigDecimal singlePrice = BigDecimal.valueOf(1.33);

    @BeforeEach
    void setUp() {
        pilotesService = new PilotServiceImpl(pilotesRepository, userService, mapper, config);

    }

    @Test
    void save() {
        Pilotes pilotes = pilotesBuilder();
        List<Pilotes> pilotesList = new LinkedList<>();
        PilotesResponseDto pilotesResponseDto = pilotesResponseDtoBuilder();
        List<PilotesResponseDto> pilotesResponseDtos = new LinkedList<>();
        User user = user(pilotesList);
        UserResponseDto userResponseDto = userResponseDto(pilotesResponseDtos, user);
        when(pilotesRepository.save(pilotes)).thenReturn(pilotes);
        when(userService.save(user)).thenReturn(userResponseDto);
        pilotesService.save(pilotes, user);
        assertEquals(pilotes.getDeliveryAddress(), pilotesResponseDto.getDeliveryAddress());
    }


    @Test
    void updatePilotes() {
        Long id = 2L;
        Pilotes pilotes = pilotesBuilder();
        List<Pilotes> pilotesList = new LinkedList<>();
        PilotesResponseDto pilotesResponseDto = pilotesResponseDtoBuilder();
        User user = user(pilotesList);
        when(pilotesRepository.findById(id)).thenReturn(Optional.of(pilotes));
        when(pilotesService.save(pilotes,user)).thenReturn(pilotesResponseDto);
        assertNotNull(pilotesService.updatePilotes(id, pilotes, user));
    }

    @Test
    void updatePilotesNegativeCase() {
        Long id = 2L;
        Pilotes pilotes = pilotesBuilder();
        List<Pilotes> pilotesList = new LinkedList<>();
        User user = user(pilotesList);
        when(pilotesRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> pilotesService.updatePilotes(id, pilotes, user));
    }

    @Test
    void updatePilotesNegativeCase2() {
        Long id = 2L;
        Pilotes pilotes = pilotesBuilder();
        pilotes.setCreatedAt(LocalDateTime.of(2021, 12, 12, 12, 12));
        List<Pilotes> pilotesList = new LinkedList<>();
        User user = user(pilotesList);
        when(pilotesRepository.findById(id)).thenReturn(Optional.of(pilotes));
        assertThrows(OrderUpdateException.class, () -> pilotesService.updatePilotes(id, pilotes, user));
    }

    @Test
    void updatePilotesNegativeCase3() {
        Long id = 2L;
        Pilotes pilotes = pilotesBuilder();
        List<Pilotes> pilotesList = new LinkedList<>();
        User user = user(pilotesList);
        Pilotes updatePilotes = pilotesBuilder();
        PilotesResponseDto pilotesResponseDto = pilotesResponseDtoBuilder();
        updatePilotes.setDeliveryAddress("address");
        updatePilotes.setNumberOfPilotes(NumberOfPilotes.MAX);
        when(pilotesRepository.findById(id)).thenReturn(Optional.of(pilotes));
        when(pilotesService.save(pilotes,user)).thenReturn(pilotesResponseDto);
        when(config.getSinglePrice()).thenReturn(singlePrice);
        PilotesResponseDto pilotesDtoResponse = pilotesService.updatePilotes(id, updatePilotes, user);
        assertNotNull(pilotesResponseDto);
    }


    @Test
    void search() {
        String query = "a";
        List<Pilotes> pilotesList = new LinkedList<>();
        User user = user(pilotesList);
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userService.findUserByFirstNameContains(query)).thenReturn(users);
        assertNotNull(pilotesService.search(query));
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

    private UserResponseDto userResponseDto(List<PilotesResponseDto> pilotesResponseDtos, User user) {
        return UserResponseDto.builder().
                firstName(user.getFirstName()).
                lastName(user.getLastName()).
                email(user.getEmail()).
                role(user.getRole())
                .telephone(user.getTelephone())
                .pilotes(pilotesResponseDtos).build();
    }

    private Pilotes pilotesBuilder() {
        return Pilotes.builder()
                .numberOfPilotes(NumberOfPilotes.MIN)
                .deliveryAddress("Delivery address")
                .createdAt(LocalDateTime.now())
                .build();
    }

    private PilotesResponseDto pilotesResponseDtoBuilder() {
        return PilotesResponseDto.builder()
                .numberOfPilotes(NumberOfPilotes.MIN.value)
                .deliveryAddress("Delivery address")
                .build();
    }
}