package com.huffman.huffman_game.controller;

import com.huffman.huffman_game.dto.GameDTOs;
import com.huffman.huffman_game.model.HuffmanNode;
import com.huffman.huffman_game.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game")
// @CrossOrigin permite que o React (porta 3000) converse com o Java (porta 8080)
@CrossOrigin(origins = "http://localhost:3000")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public ResponseEntity<InitialGameResponse> startGame(@RequestBody String text) {
        List<HuffmanNode> nodes = gameService.generateInitialNodes(text);
        return ResponseEntity.ok(new InitialGameResponse(nodes));
    }

    @PostMapping("/validate")
    public ResponseEntity<GameDTOs.ValidationResponse> validateTree(@RequestBody GameDTOs.ValidationRequest request) {
        GameDTOs.ValidationResponse response = gameService.validateUserTree(request);
        return ResponseEntity.ok(response);
    }

    public record InitialGameResponse(List<HuffmanNode> initialNodes) {}
}
