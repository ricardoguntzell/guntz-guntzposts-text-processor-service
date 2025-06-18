# Text Processor Service - Microservice

Guntz Text Processor é um microsserviço de processamento de posts via Rabbitmq construído com Spring Boot.

Esse microsserviço vai consumir a fila (text-processor-service.post-processing.v1.q).

Fica de sua responsabilidade fazer a contagem de palavras no texto e o cálculo de um valor estimado com base na quantidade de palavras.

Uma vez processado, o resultado será enviado para a fila (post-service.post-processing-result.v1.q) que por sua vez será consumido pelo microsserviço Guntz Post Service.

## ✨ Funcionalidades

- ⚡ **Aplicação com Entrega Confiável**: Com uso de Mensageria via Rabbitmq, conseguimos garantindo maior confiabilidade na entrega das mensagens além de maior escabilidade

## 🛠️ Stack Tecnológica

- **Java 17** - Linguagem de programação
- **Spring Boot 3.x** - Framework principal
- **Spring Boot AMQP** - Stack de Mensageria via Rabbitmq, garantindo maior entrega e escabilidade dos serviços.
- **Spring Doc OpenAPI WebMvc UI** - Documentação da api de forma descomplicada
- **Spring Boot Devtools** - Para melhor produtividade durante o desenvolvimento
- **Docker** - Para melhor automatização, implantação, dimensionamento e gerenciamento de aplicações em contêineres
- **Docker Compose** - Ferramenta que simplifica o desenvolvimento e gerenciamento de aplicações com múltiplos contêinere
- **Lombok** - Facilitador de escrita de código limpo
- **UUID Generator** - Gerenciador de Id com UUID
- **Maven** - Gerenciador de dependências
- **Log Slf4j** - Log com Slf4j 

## 🚀 Início Rápido

### Pré-requisitos

- ☕ JDK 17+
- 🐘 Maven
- 🔧 Git
- 🔧 Rabbitmq
- 🔧 Docker
- 🔧 Docker Compose

### Instalação e Execução

1. **Clone o repositório**
   ```bash
   git clone https://github.com/ricardoguntzell/guntz-guntzposts-text-processor-service.git text-processor-service
   ```
2. **Inicie o TextProcessorService**
   ```bash
   cd text-processor-service
   ./mvnw spring-boot:run
   ```
   > 🌐 Serviço disponível em: http://localhost:8081

### Verificação Rápida

## 📖 Documentação da API
- http://localhost:8081/swagger-ui/index.html

## 📖 Resumo
- Este microsserviço consiste em consumir uma fila do rabbitmq, realizar o cálculo de algumas propriedades, produzir uma nova mensagem com esse resultado e gravar numa fila.

#### Mensagem Lida da fila Rabbitmq - Formato jSon
```
{
    "postId": "string", //UUID
    "postBody": "string"
}
```

#### Mensagem Produzida e postada na fila Rabbitmq - Formato jSon
```
{
    "postId": "string", //UUID
    "wordCount": 123,
    "calculatedValue": 12.3
}
```

## ⚙️ Configurações e Regras

### Validações
- **Cacular o body**: A quantidade de palavras no corpo do texto
- **Cacular o valor estimado das palavras no body**: O valor estimado (palavras * $0,10).

### Fluxo de Dados

1. **Recepção Assincrona da Mensagem**: Vai ler a mensagem através de binding.
2. **Processamento e Cálculo**: Realizar o cálculo das propriedades e determinar os seus valores.
3. **Postagem em Exchange**: Insere a mensagem na exchange do rabbitmq que posteiormente será lida pelo outro microserviço que por sua vez vai realizar o binding para a fila "post-service.post-processing-result.v1.q"