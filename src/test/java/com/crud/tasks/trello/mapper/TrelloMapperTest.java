package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrelloMapperTest {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    void testMapToBoards() {
        //Given
        ArrayList<TrelloBoardDto> boardDtoArrayList = new ArrayList<>();
        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("1", "name1", new ArrayList<>());
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("2", "name2", new ArrayList<>());

        //When
        boardDtoArrayList.add(trelloBoardDto1);
        boardDtoArrayList.add(trelloBoardDto2);
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(boardDtoArrayList);

        //Then
        assertFalse(trelloBoards.isEmpty());
        assertEquals(2, trelloBoards.size());

        TrelloBoard trelloBoard = trelloBoards.get(0);
        TrelloBoard trelloBoard2 = trelloBoards.get(1);

        assertNotNull(trelloBoard.getLists());
        assertNotNull(trelloBoard2.getLists());
        assertTrue(trelloBoard.getLists().isEmpty());
        assertTrue(trelloBoard2.getLists().isEmpty());
        assertEquals(trelloBoard.getName(), "name1");
        assertEquals(trelloBoard2.getName(), "name2");
        assertEquals(trelloBoard.getId(), "1");
        assertEquals(trelloBoard2.getId(), "2");
    }

    @Test
    void testMapToBoardsDto() {
        //Given
        ArrayList<TrelloBoard> trelloBoards = new ArrayList<>();
        TrelloBoard trelloBoard1 = new TrelloBoard("1", "name1", new ArrayList<>());
        TrelloBoard trelloBoard2 = new TrelloBoard("2", "name2", new ArrayList<>());

        //When
        trelloBoards.add(trelloBoard1);
        trelloBoards.add(trelloBoard2);
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);

        //Then
        assertFalse(trelloBoardDtos.isEmpty());
        assertEquals(2, trelloBoardDtos.size());

        TrelloBoardDto trelloBoardDto1 = trelloBoardDtos.get(0);
        TrelloBoardDto trelloBoardDto2 = trelloBoardDtos.get(1);

        assertNotNull(trelloBoardDto1.getLists());
        assertNotNull(trelloBoardDto2.getLists());
        assertTrue(trelloBoardDto1.getLists().isEmpty());
        assertTrue(trelloBoardDto2.getLists().isEmpty());
        assertEquals(trelloBoardDto1.getName(), "name1");
        assertEquals(trelloBoardDto2.getName(), "name2");
        assertEquals(trelloBoardDto1.getId(), "1");
        assertEquals(trelloBoardDto2.getId(), "2");
    }

    @Test
    void testMapToList() {
        //Given
        ArrayList<TrelloListDto> trelloListDtos = new ArrayList<>();
        TrelloListDto trelloListDto1 = new TrelloListDto("1", "list1", true);
        TrelloListDto trelloListDto2 = new TrelloListDto("2", "list2", false);

        //When
        trelloListDtos.add(trelloListDto1);
        trelloListDtos.add(trelloListDto2);
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtos);

        //Then
        assertFalse(trelloLists.isEmpty());
        assertEquals(trelloLists.size(), 2);

        TrelloList trelloList1 = trelloLists.get(0);
        TrelloList trelloList2 = trelloLists.get(1);

        assertTrue(trelloList1.isClosed());
        assertFalse(trelloList2.isClosed());
        assertEquals(trelloList1.getId(), "1");
        assertEquals(trelloList2.getId(), "2");
        assertEquals(trelloList1.getName(), "list1");
        assertEquals(trelloList2.getName(), "list2");
    }

    @Test
    void testMapToListDto() {
        //Given
        ArrayList<TrelloList> trelloLists = new ArrayList<>();
        TrelloList trelloList1 = new TrelloList("1", "list1", true);
        TrelloList trelloList2 = new TrelloList("2", "list2", false);

        //When
        trelloLists.add(trelloList1);
        trelloLists.add(trelloList2);
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);

        //Then
        assertFalse(trelloListDtos.isEmpty());
        assertEquals(trelloListDtos.size(), 2);

        TrelloListDto trelloListDto1 = trelloListDtos.get(0);
        TrelloListDto trelloListDto2 = trelloListDtos.get(1);
        assertTrue(trelloListDto1.isClosed());
        assertFalse(trelloListDto2.isClosed());
        assertEquals(trelloListDto1.getId(), "1");
        assertEquals(trelloListDto2.getId(), "2");
        assertEquals(trelloListDto1.getName(), "list1");
        assertEquals(trelloListDto2.getName(), "list2");
    }

    @Test
    void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("card1", "desc1", "pos1", "1");

        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertNotNull(trelloCardDto);
        assertEquals(trelloCardDto.getName(), "card1");
        assertEquals(trelloCardDto.getDescription(), "desc1");
        assertEquals(trelloCardDto.getPos(), "pos1");
        assertEquals(trelloCardDto.getListId(), "1");
    }

    @Test
    void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("card1", "desc1", "pos1", "1");

        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertNotNull(trelloCard);
        assertEquals(trelloCard.getName(), "card1");
        assertEquals(trelloCard.getDescription(), "desc1");
        assertEquals(trelloCard.getPos(), "pos1");
        assertEquals(trelloCard.getListId(), "1");
    }
}
