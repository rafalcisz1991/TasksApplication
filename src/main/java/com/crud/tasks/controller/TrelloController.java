package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/trello")
@RequiredArgsConstructor
@CrossOrigin("*")
@SuppressWarnings("unchecked")
public class TrelloController {

    private final TrelloClient trelloClient;

    @GetMapping("boards")
    public List <TrelloBoardDto> getTrelloBoards() throws NoSuchMethodException {

        List <TrelloBoardDto> myTrelloBoards = new ArrayList<>();

       try {
           Method getBoard = TrelloClient.class.getDeclaredMethod("getTrelloBoards");
           getBoard.setAccessible(true);
           myTrelloBoards = (List<TrelloBoardDto>) getBoard.invoke(trelloClient);

           List <TrelloBoardDto> filteredTrelloBoards =
           myTrelloBoards.stream()
                           .filter(l -> !l.getName().isEmpty() && !l.getId().isEmpty() && l.getName().contains("Kodilla"))
                                   .collect(Collectors.toList());

           filteredTrelloBoards.forEach(trelloBoardDto -> {
                   System.out.println("This board contains lists: ");
                   System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());
                   trelloBoardDto.getLists().forEach(trelloList -> {
                       System.out.println(trelloList.getName() + " - " + trelloList.getId() + " - " + trelloList.isClosed());
                   });
           });
       }
       catch (Exception e) {
           System.out.println("Thrown exception: " + e);
       }
        return myTrelloBoards;
    }

    @PostMapping("cards")
    public CreatedTrelloCard createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return trelloClient.createNewCard(trelloCardDto);
    }
}
