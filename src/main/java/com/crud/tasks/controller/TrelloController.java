package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.NewTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.facade.TrelloFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/trello")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TrelloController {

    @Autowired
    private final TrelloFacade trelloFacade;

    @Autowired
    private final TrelloClient trelloClient;

    @GetMapping("boards")
    public ResponseEntity<List<TrelloBoardDto>> getTrelloBoards() {
        return ResponseEntity.ok(trelloFacade.fetchTrelloBoards());
    }

    @PostMapping("cards")
    public ResponseEntity<CreatedTrelloCardDto> createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return ResponseEntity.ok(trelloFacade.createCard(trelloCardDto));
    }

    @GetMapping("batches")
    public List<NewTrelloCardDto> getTrelloBatches() {

        List<NewTrelloCardDto> trelloCards = trelloClient.getTrelloCardsWithMoreFields();

        System.out.println("This board contains following cards (IDs), with underlying batches");
        trelloCards.forEach(trelloCardDto -> {
            System.out.println(trelloCardDto.getCardId());
            System.out.println();
        });
        return trelloCards;
    }
}
