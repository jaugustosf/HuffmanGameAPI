# --- Estágio 1: Build (Compilação) ---
# Se você usa Java 21, troque '17' por '21' abaixo
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia os arquivos do projeto para dentro do container
COPY . .

# Faz o build pular os testes para ser mais rápido
RUN mvn clean package -DskipTests

# --- Estágio 2: Run (Execução) ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia o .jar gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta (Documentação apenas)
EXPOSE 8080

# Comando para iniciar o Java
# O segredo aqui é o -Dserver.port para pegar a porta do Render
ENTRYPOINT ["java", "-Dserver.port=${PORT:8080}", "-jar", "app.jar"]