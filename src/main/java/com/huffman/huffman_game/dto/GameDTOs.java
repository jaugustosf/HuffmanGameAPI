package com.huffman.huffman_game.dto;

import com.huffman.huffman_game.model.HuffmanNode;
import lombok.Data;
import java.util.List;

@Data
public class GameDTOs {
    private List<HuffmanNode> initialNodes;

    public GameDTOs(List<HuffmanNode> initialNodes) {
        this.initialNodes = initialNodes;
    }
}
