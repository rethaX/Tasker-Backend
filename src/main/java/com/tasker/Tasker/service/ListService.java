package com.tasker.Tasker.service;

import com.tasker.Tasker.model.Board;
import com.tasker.Tasker.model.ListEntity;
import com.tasker.Tasker.repository.BoardRepository;
import com.tasker.Tasker.repository.ListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListService {
    private static final Logger logger = LoggerFactory.getLogger(ListService.class);

    private final ListRepository listRepository;
    private final BoardRepository boardRepository;

    public ListService(ListRepository listRepository, BoardRepository boardRepository) {
        this.listRepository = listRepository;
        this.boardRepository = boardRepository;
    }

    public ListEntity createList(String name, Long boardId) {
        logger.info("Creating list: {} for board: {}", name, boardId);
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found with id: " + boardId));
        ListEntity list = new ListEntity();
        list.setName(name);
        list.setBoard(board);
        return listRepository.save(list);
    }

    public List<ListEntity> getListsByBoard(Long boardId) {
        logger.info("Fetching lists for board: {}", boardId);
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found with id: " + boardId));
        return board.getLists();
    }
}