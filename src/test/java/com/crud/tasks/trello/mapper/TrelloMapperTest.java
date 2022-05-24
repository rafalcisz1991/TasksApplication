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
    public void shouldFetchBoardsList() {
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
}
