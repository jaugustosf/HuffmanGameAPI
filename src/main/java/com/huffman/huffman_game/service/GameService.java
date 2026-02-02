package com.huffman.huffman_game.service;

import com.huffman.huffman_game.dto.GameDTOs;
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

    public HuffmanNode buildSolutionTree(List<HuffmanNode> initialNodes) {
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(initialNodes);

        while (queue.size() > 1) {
            HuffmanNode left = queue.poll();
            HuffmanNode right = queue.poll();

            HuffmanNode parent = new HuffmanNode();
            parent.setFrequency(left.getFrequency() + right.getFrequency());
            parent.setLeft(left);
            parent.setRight(right);

            queue.add(parent);
        }
        return queue.poll();
    }

    public GameDTOs.ValidationResponse validateUserTree(GameDTOs.ValidationRequest request) {
        int optimalBits = calculateOptimalHuffmanBits(request.word().toUpperCase());
        int userBits = calculateUserTreeBits(request);

        boolean isValid = userBits == optimalBits;

        if (userBits <= 0) {
            return new GameDTOs.ValidationResponse(false, "A árvore parece incompleta ou desconexa.", 0, optimalBits);
        }

        String msg = isValid ? "Parabéns! Árvore perfeita." :
                "Sua árvore usa " + userBits + " bits, mas o ideal seria " + optimalBits + " bits.";

        return new GameDTOs.ValidationResponse(isValid, msg, userBits, optimalBits);
    }

    private int calculateUserTreeBits(GameDTOs.ValidationRequest request) {
        int totalBits = 0;

        Map<String, String> parentMap = new HashMap<>();

        for (GameDTOs.NodeConnection conn : request.structure()) {
            if (conn.leftId() != null) parentMap.put(conn.leftId(), conn.parentId());
            if (conn.rightId() != null) parentMap.put(conn.rightId(), conn.parentId());
        }

        for (GameDTOs.LeafNode leaf : request.leaves()) {
            int depth = 0;
            String currentId = leaf.id();

            while (parentMap.containsKey(currentId)) {
                depth++;
                currentId = parentMap.get(currentId);

                if (depth > 50) return -1; // Proteção contra loop infinito
            }

            if (depth == 0 && !request.structure().isEmpty()) return -1;

            totalBits += (leaf.frequency() * depth);
        }

        return totalBits;
    }

    private int calculateOptimalHuffmanBits(String word) {
        if (word == null || word.isEmpty()) return 0;

        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : word.toCharArray()) freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);

        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int freq : freqMap.values()) queue.add(freq);

        if (queue.size() == 1) return word.length();

        int totalCost = 0;

        while (queue.size() > 1) {
            int a = queue.poll();
            int b = queue.poll();
            int sum = a + b;
            totalCost += sum;
            queue.add(sum);
        }

        return totalCost;
    }
}
