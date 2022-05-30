package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrelloFacadeTest {

    @InjectMocks
    private TrelloFacade trelloFacade;
    @Mock
    private TrelloService trelloService;
    @Mock
    private TrelloValidator trelloValidator;
    @Mock
    private TrelloMapper trelloMapper;

    @Test
    void shouldFetchEmptyList() {
        // Given
        List<TrelloListDto> trelloLists =
                List.of(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoards =
                List.of(new TrelloBoardDto("1", "test", trelloLists));

        List<TrelloList> mappedTrelloLists =
                List.of(new TrelloList("1", "test_list", false));

        List<TrelloBoard> mappedTrelloBoards =
                List.of(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(List.of());
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(List.of());

        // When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        // Then
        assertThat(trelloBoardDtos).isNotNull();
        assertThat(trelloBoardDtos.size()).isEqualTo(0);
    }

    @Test
    void shouldFetchTrelloBoards() {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "my_list", false));
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "my_task", trelloLists));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "my_list", false));
        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("1", "my_task", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(trelloBoards);
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(mappedTrelloBoards);

        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        //Then
        assertNotNull(trelloBoardDtos);
        assertFalse(trelloBoardDtos.isEmpty());
        assertEquals(1, trelloBoardDtos.size());

        TrelloBoardDto trelloBoardDto = trelloBoardDtos.get(0);

        assertEquals("1", trelloBoardDto.getId());
        assertEquals("my_task", trelloBoardDto.getName());
        assertNotNull(trelloBoardDto.getLists());
        assertFalse(trelloBoardDto.getLists().isEmpty());
        assertEquals(1, trelloBoardDto.getLists().size());
        assertEquals("my_list", trelloBoardDto.getLists().get(0).getName());
        assertEquals("1", trelloBoardDto.getLists().get(0).getId());
        assertFalse(trelloBoardDto.getLists().get(0).isClosed());
    }

    @Test
    public void shouldCreateCardTest() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "card1", "testCard", "pos1", "list1");

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1", "card1", "http://Test.com");

        TrelloCard trelloCard = new TrelloCard("name1", "desc1", "pos1", "1");

        when(trelloService.createTrelloCard(null)).thenReturn(createdTrelloCardDto);
        when(trelloMapper.mapToCard(trelloCardDto)).thenReturn(trelloCard);

        //When
        CreatedTrelloCardDto newCard = trelloFacade.createCard(trelloCardDto);

        //Then
        assertNotNull(newCard);
        assertEquals("1", newCard.getId());
        assertEquals("card1", newCard.getName());
        assertEquals("http://Test.com", newCard.getShortUrl());
    }
}
