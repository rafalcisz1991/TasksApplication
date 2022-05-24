package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloBoardDto;
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

    //mapToBoards() test
    @Test
    void shouldFetchBoardsList() {
        //Given
        ArrayList<TrelloBoardDto> boardDtoArrayList = new ArrayList<>();
        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("name1", "1", new ArrayList<>());
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("name2", "2", new ArrayList<>());

        //When
        boardDtoArrayList.add(trelloBoardDto1);
        boardDtoArrayList.add(trelloBoardDto2);
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(boardDtoArrayList);

        //Then
        TrelloBoard trelloBoard = trelloBoards.get(0);
        TrelloBoard trelloBoard2 = trelloBoards.get(1);

        assertTrue(trelloBoards.contains(trelloBoard));
        assertTrue(trelloBoards.contains(trelloBoard2));
        assertFalse(trelloBoards.isEmpty());
        assertNotNull(trelloBoard.getLists());
        assertNotNull(trelloBoard2.getLists());
        assertEquals(2, trelloBoards.size());
        assertTrue(trelloBoard.getLists().isEmpty());
        assertTrue(trelloBoard2.getLists().isEmpty());
        assertEquals(trelloBoard.getName(), "name1");
        assertEquals(trelloBoard2.getName(), "name2");
        assertEquals(trelloBoard.getId(), "1");
        assertEquals(trelloBoard2.getId(), "2");
    }

    @Test
    void shouldFetchBoardsDtoList() {
        //Given
        ArrayList<TrelloBoard> trelloBoards = new ArrayList<>();
        TrelloBoard trelloBoard1 = new TrelloBoard("1", "name1", new ArrayList<>());
        TrelloBoard trelloBoard2 = new TrelloBoard("2", "name2", new ArrayList<>());

        //When
        trelloBoards.add(trelloBoard1);
        trelloBoards.add(trelloBoard2);
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);

        //Then
        TrelloBoardDto trelloBoardDto1 = trelloBoardDtos.get(0);
        TrelloBoardDto trelloBoardDto2 = trelloBoardDtos.get(1);

        assertTrue(trelloBoardDtos.contains(trelloBoardDto1));
        assertTrue(trelloBoardDtos.contains(trelloBoardDto2));
        assertFalse(trelloBoardDtos.isEmpty());
        assertNotNull(trelloBoardDto1.getLists());
        assertNotNull(trelloBoardDto2.getLists());
        assertEquals(2, trelloBoardDtos.size());
        assertTrue(trelloBoardDto1.getLists().isEmpty());
        assertTrue(trelloBoardDto2.getLists().isEmpty());
        assertEquals(trelloBoardDto1.getName(), "name1");
        assertEquals(trelloBoardDto2.getName(), "name2");
        assertEquals(trelloBoardDto1.getId(), "1");
        assertEquals(trelloBoardDto2.getId(), "2");

    }


}
