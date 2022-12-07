package com.shop.pilotes.sevice;

import com.shop.pilotes.dto.response.PilotesResponseDto;
import com.shop.pilotes.model.Pilotes;
import com.shop.pilotes.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PilotesService {

    PilotesResponseDto save(Pilotes pilotes, User loggedInUser);

    PilotesResponseDto updatePilotes(Long id, Pilotes pilotes, User loggedInUser);

    List<PilotesResponseDto> search(String query);

}
