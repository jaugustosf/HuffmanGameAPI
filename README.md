# Huffman Game

Este é um projeto de um jogo interativo para aprender sobre a codificação de Huffman.

## Visão Geral

O backend é construído com Spring Boot e fornece uma API REST para:
- Gerar os nós iniciais (folhas) a partir de um texto.
- Validar a árvore de Huffman construída pelo usuário.

O frontend (não incluso neste repositório) é uma aplicação React que consome essa API.

## Como Executar

Este é um projeto Spring Boot padrão. Você pode executá-lo a partir da sua IDE ou usando o Maven Wrapper:

```bash
./mvnw spring-boot:run
```

A aplicação será iniciada em `http://localhost:8080`.

## Endpoints da API

- `POST /api/game/start`: Envie um texto (ex: `"BANANA"`) e receba a lista de caracteres e suas frequências.
- `POST /api/game/validate`: Envie a estrutura da árvore montada pelo usuário para validação. O backend compara o custo em bits da árvore do usuário com o custo da árvore ótima.
