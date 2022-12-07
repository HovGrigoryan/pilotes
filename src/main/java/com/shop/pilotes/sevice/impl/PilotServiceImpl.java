package com.shop.pilotes.sevice.impl;

import com.shop.pilotes.config.GlobalConfig;
import com.shop.pilotes.dto.response.PilotesResponseDto;
import com.shop.pilotes.exception.OrderUpdateException;
import com.shop.pilotes.mapper.response.PilotesResponseMapper;
import com.shop.pilotes.model.Pilotes;
import com.shop.pilotes.model.User;
import com.shop.pilotes.repository.PilotesRepository;
import com.shop.pilotes.sevice.PilotesService;
import com.shop.pilotes.sevice.UserService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PilotServiceImpl implements PilotesService {

    private final PilotesRepository pilotesRepository;

    private final UserService userService;

    private final PilotesResponseMapper mapper;

    private final GlobalConfig config;

    public PilotServiceImpl(PilotesRepository pilotesRepository, UserService userService, PilotesResponseMapper mapper, GlobalConfig config) {
        this.pilotesRepository = pilotesRepository;
        this.userService = userService;
        this.mapper = mapper;
        this.config = config;
    }

    @Override
    public PilotesResponseDto save(Pilotes pilotes, User loggedInUser) {
        Pilotes savedPilot = pilotesRepository.save(pilotes);

        loggedInUser.getPilotes().add(savedPilot);
        userService.save(loggedInUser);

        return mapper.mapToDto(savedPilot);
    }

    @Override
    public PilotesResponseDto updatePilotes(Long id, Pilotes pilotes, User loggedInUser) {
        Optional<Pilotes> pilotesById = pilotesRepository.findById(id);
        if (pilotesById.isEmpty()) {
            throw new EntityNotFoundException("Order with " + id + " id is not exist.");
        }

        Pilotes updatedPilotes = pilotesById.get();

        long dateBetween = ChronoUnit.MINUTES.between(LocalDateTime.now(), updatedPilotes.getCreatedAt());
        if (Math.abs(dateBetween) > 5) {
            throw new OrderUpdateException("Order with " + id + " id was made 5 minutes ago, you can't update the order.");
        }

        if (pilotes.getDeliveryAddress() != null && !pilotes.getDeliveryAddress().isBlank()) {
            updatedPilotes.setDeliveryAddress(pilotes.getDeliveryAddress());
        }
        if (pilotes.getNumberOfPilotes() != null && pilotes.getNumberOfPilotes() != updatedPilotes.getNumberOfPilotes()) {
            updatedPilotes.setNumberOfPilotes(pilotes.getNumberOfPilotes());
            updatedPilotes.setOrderTotal(config.getSinglePrice().multiply(BigDecimal.valueOf(pilotes.getNumberOfPilotes().value)));
        }
        return save(updatedPilotes, loggedInUser);
    }

    @Override
    public List<PilotesResponseDto> search(String query) {
        List<User> userList = userService.findUserByFirstNameContains(query);
        List<Pilotes> pilotes = new ArrayList<>();
        userList.stream().filter(user -> user.getPilotes() != null).forEach(user -> pilotes.addAll(user.getPilotes()));

        return pilotes.stream().map(mapper::mapToDto).collect(Collectors.toList());
    }

}
