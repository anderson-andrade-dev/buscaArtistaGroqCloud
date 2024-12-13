package br.com.alura.screensound.APIGroqCloud;

import br.com.alura.screensound.APIGroqCloud.record.Choice;
import br.com.alura.screensound.APIGroqCloud.record.Message;
import br.com.alura.screensound.APIGroqCloud.record.RequestGroqCloud;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * Classe responsável por interagir com a API do GroqCloud para enviar uma solicitação de chat.
 * As informações são passadas em formato JSON e a resposta da API é tratada para retornar uma resposta processada.
 *
 * @author Anderson Andrade
 * @version 1.0
 * @since 12/12/2024
 */
public class ConsultaGroqCloud {

    // URL da API do GroqCloud para Chat Completions.
    private static final String apiUrl = "https://api.groq.com/openai/v1/chat/completions";

    // Chave da API configurada na variável de ambiente GROP_CLOUD
    private static final String apiKey = System.getenv("GROP_CLOUD");

    // Cliente HTTP para enviar solicitações
    private static final HttpClient client = HttpClient.newHttpClient();

    // ObjectMapper para manipulação de JSON
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Construtor privado para evitar instanciamento da classe.
    @Deprecated
    private ConsultaGroqCloud() {
        // Método construtor privado para evitar a instância direta da classe
    }

    /**
     * Obtém informações da API do GroqCloud respondendo a uma pergunta sobre um artista.
     * O texto fornecido é inserido em uma consulta de chat com a API.
     *
     * @param texto Texto com o nome do artista que será pesquisado.
     * @return A resposta da API com as informações sobre o artista e suas músicas.
     * @throws RuntimeException Se houver erro ao converter o objeto RequestGroqCloud para JSON.
     * @throws IllegalArgumentException Se a chave da API não estiver configurada corretamente.
     * @throws IOException Se ocorrer algum erro na comunicação com a API.
     * @throws InterruptedException Se a requisição for interrompida.
     */
    public static String obterInformacao(String texto) {
        // Formata a pergunta a ser enviada à API
        var pergunta = "Me Responda em Português Brasil e o Fale da carreira e liste 3 musicas do artista: %s".formatted(texto);

        // Cria o objeto de solicitação para a API
        var requestJson = new RequestGroqCloud("llama3-8b-8192", List.of(new Message("user", pergunta)));

        String json = null;

        // Tenta converter o objeto requestJson para JSON
        try {
            json = objectMapper.writeValueAsString(requestJson);
        } catch (JsonProcessingException e) {
            // Lança uma exceção se houver erro na conversão
            throw new RuntimeException("Erro ao converter o objeto para JSON: " + e.getMessage(), e);
        }

        // Verifica se a chave da API está configurada corretamente
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("Chave de API não encontrada. Certifique-se de que a variável de ambiente 'GROP_CLOUD' esteja configurada.");
        }

        // Cria a requisição HTTP para a API
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .header("Accept-Language", "pt-BR")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        // Tenta enviar a solicitação e processar a resposta
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Lê a resposta e a converte para um objeto JSON
                JsonNode rootNode = objectMapper.readTree(response.body());

                // Converte a parte da resposta "choices" para uma lista de objetos Choice
                List<Choice> choices = objectMapper.readValue(
                        rootNode.path("choices").toString(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, Choice.class)
                );

                // Retorna o conteúdo da mensagem da primeira escolha
                return choices.get(0).message().content();
            } else {
                // Lança uma exceção se a resposta não for bem-sucedida
                throw new IOException("Erro na requisição: " + response.statusCode() + " - " + response.body());
            }

        } catch (IOException | InterruptedException e) {
            // Captura e exibe erros de IO ou interrupção
            System.err.println("Erro ao enviar solicitação para a API: " + e.getMessage());
            e.printStackTrace();
            return "Erro ao processar a solicitação.";
        }
    }
}
