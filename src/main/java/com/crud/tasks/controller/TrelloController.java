package com.crud.tasks.controller;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("v1/trello")
@RequiredArgsConstructor
@CrossOrigin("*")
@SuppressWarnings("unchecked")
public class TrelloController {

    private final TrelloClient trelloClient;

    private boolean is

    @GetMapping("boards")
    public void getTrelloBoards() throws NoSuchMethodException {
       try {
           Method getBoard = TrelloClient.class.getDeclaredMethod("getTrelloBoards");
           getBoard.setAccessible(true);
           List<TrelloBoardDto> myTrelloBoards = (List<TrelloBoardDto>) getBoard.invoke(trelloClient);

           //getBoard.invoke(trelloClient);
           //List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

           //do zadania 22.2 - podpunkt 3
          /* TrelloBoardDto myTrelloBoardDto = new TrelloBoardDto();

           Field[] fieldsList = myTrelloBoardDto.getClass().getDeclaredFields();
           for (TrelloBoardDto trelloBoardDto : myTrelloBoards) {
               if (trelloBoardDto.get)
           }*/


           myTrelloBoards.forEach(trelloBoardDto -> {
               System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());
           });
       } catch (Exception e) {
           System.out.println("Thrown exception: " + e);
       }
    }
}
