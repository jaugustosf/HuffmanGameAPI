package com.huffman.huffman_game.service;

import com.huffman.huffman_game.model.HuffmanNode;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    public List<HuffmanNode> generateInitialNodes(String text) {
        if (text == null || text.trim().isEmpty()) {
            return Collections.emptyList();
        }

        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : text.toUpperCase().toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        List<HuffmanNode> leafNodes = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            leafNodes.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        Collections.sort(leafNodes);
        return leafNodes;
    }
}