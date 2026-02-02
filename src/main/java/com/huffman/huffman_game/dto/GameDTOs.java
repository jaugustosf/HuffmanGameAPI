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

    public record ValidationRequest(
            String word,
            List<NodeConnection> structure,
            List<LeafNode> leaves
    ) {}

    public record NodeConnection(
            String parentId,
            String leftId,
            String rightId,
            int frequency
    ) {}

    public record LeafNode(
            String id,
            String character,
            int frequency
    ) {}

    public record ValidationResponse(
            boolean isValid,
            String message,
            int userBits,
            int optimalBits
    ) {}
}
