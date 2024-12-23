# spring-ai-rag

A Retrieval-Augmented Generation (RAG) Spring Boot application built using Spring AI and integrated with the OpenAI API.

It serves as a virtual real estate assistant capable of answering frequently asked questions (FAQs) based on company domain knowledge, searching real estate listings by filtering criteria such as location, price range, and number of bedrooms, and maintaining conversation history to provide context-aware follow-up responses.

## Features

- **Retrieval-Augmented Generation (RAG)**: Enriches AI-generated responses by dynamically retrieving context from structured data stored in PostgreSQL with pgvector for embeddings.
- **Chat History Memory**: Maintains a conversation history to provide context-aware replies in multi-turn conversations. The application uses in-memory chat memory for the sake of simplicity—for production-ready systems, it should be changed to a persistent memory store (Spring AI currently only supports Apache Cassandra)
- **OpenAI Integration**: Leverages the Spring AI library to connect with OpenAI GPT models for text generation and embedding creation.
- **Function Calling Support**: Demonstrates OpenAI function calling to trigger SQL queries on a relational database and return structured responses.
- **RESTful API Design**: Provides chat and conversation history retrieval endpoints, ready for frontend chatbot integration.
- **Hexagonal Architecture**: Adopts ports and adapters for modularity, extensibility, and testability.

## Local Run

Set an enviroment variable with OpenAI API secret:

```shell
export SPRING_AI_OPENAI_API_KEY=<INSERT KEY HERE>
```

Run a local PostgreSQL database, serving as a vector store and traditional relational database as well:

```shell
docker compose up -d
```

Run the app itself:

```shell
./gradlew bootRun
```

## REST API

### Chat Endpoint

`POST /api/chat`
Generates a response based on user input.  

#### Request Body
```json
{
  "conversationId": "123",
  "message": "What is Spring AI?"
}
```

#### Response Body
**200 OK**

```json
{
  "message": "Spring AI is a framework for integrating AI into Spring Boot applications."
}
```

**Error Codes:**
- **400 Bad Request** – Invalid input or missing fields.
- **503 Service Unavailable** – Chat service temporarily unavailable.

### Conversation History Endpoint

`GET /api/conversation/{conversationId}?lastN=10`
Retrieve recent chat messages.

#### Response Body
**200 OK**

```json
{
  "conversationId": "123",
  "messages": [
    {
      "content": "Hello!",
      "type": "USER"
    },
    {
      "content": "Hi! How can I assist you?", 
      "type": "ASSISTANT"
    }
  ]
}
```

- **400 Bad Request** – Invalid lastN parameter.
- **404 Not Found** – Conversation ID does not exist.
