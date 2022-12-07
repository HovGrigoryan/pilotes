package com.shop.pilotes.controller;

import com.shop.pilotes.dto.request.PilotesRequestDto;
import com.shop.pilotes.dto.response.PilotesResponseDto;
import com.shop.pilotes.mapper.request.PilotesRequestMapper;
import com.shop.pilotes.model.Pilotes;
import com.shop.pilotes.model.User;
import com.shop.pilotes.security.CurrentUser;
import com.shop.pilotes.sevice.PilotesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pilotes")
public class PilotesController {

    private final PilotesService pilotesService;

    private final PilotesRequestMapper mapper;

    public PilotesController(PilotesService pilotesService, PilotesRequestMapper mapper) {
        this.pilotesService = pilotesService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<PilotesResponseDto> newOrder(@AuthenticationPrincipal CurrentUser currentUser, @RequestBody @Valid PilotesRequestDto pilotesRequestDto) {
        User loggedInUser = currentUser.getUser();
        Pilotes pilotes = mapper.mapToDto(pilotesRequestDto);

        return ResponseEntity.ok(pilotesService.save(pilotes, loggedInUser));
    }


    @PatchMapping(value = "/{id}")
    public ResponseEntity<PilotesResponseDto> updateOrder(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable Long id, @RequestBody @Valid PilotesRequestDto pilotesRequestDto) {
        User loggedInUser = currentUser.getUser();
        Pilotes pilotes = mapper.mapToDto(pilotesRequestDto);

        return ResponseEntity.ok(pilotesService.updatePilotes(id, pilotes, loggedInUser));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PilotesResponseDto>> newOrder(@RequestParam String query) {
        return ResponseEntity.ok(pilotesService.search(query));
    }

}
