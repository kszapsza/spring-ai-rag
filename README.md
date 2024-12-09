# spring-ai-rag

RAG (Retrieval-Augmented Generation) Spring Boot app built using Spring AI and integrated with OpenAI API

## Local run

Set an enviroment variable with OpenAI API secret:

```shell
export SPRING_AI_OPENAI_API_KEY=<INSERT KEY HERE>
```

Run a local PostgreSQL database, serving as a vector store:

```shell
docker compose up -d
```

Run the app itself:

```shell
./gradlew bootRun
```
