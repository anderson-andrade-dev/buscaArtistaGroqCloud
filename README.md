![Banner da ConsultaGroqCloud](https://i.imgur.com/4wT0GSx.png)

# Consulta Groq Cloud API

API Java para integração com o **GroqCloud**, permitindo enviar perguntas e receber respostas estruturadas utilizando o modelo de linguagem avançado disponibilizado pela plataforma.

## Funcionalidades

- **Consultar informações sobre artistas**: Informe o nome de um artista para receber detalhes sobre sua carreira e uma lista de três músicas mais relevantes.
- **Integração com JSON**: Comunicação simplificada com a API utilizando `ObjectMapper` para serialização e desserialização.
- **Suporte ao Português**: Respostas configuradas para serem retornadas em Português (Brasil).

---

## Pré-requisitos

Antes de usar este projeto, você precisará:

1. **Cadastrar-se na GroqCloud**:
   - Acesse o site oficial da GroqCloud: [https://console.groq.com/playground](https://console.groq.com/playground).
   - Crie uma conta e obtenha sua chave de API.

2. **Configurar a variável de ambiente**:
   - Adicione a variável `GROP_CLOUD` ao seu sistema com a chave de API obtida:
     - **Windows**:
       ```cmd
       setx GROP_CLOUD "sua-chave-aqui"
       ```
     - **Linux/Mac**:
       ```bash
       export GROP_CLOUD="sua-chave-aqui"
       ```

3. **Dependências do Projeto**:
   Certifique-se de incluir as seguintes dependências no seu projeto:
   - **Jackson** (para manipulação de JSON).
   - **Java HttpClient** (padrão no Java 11+).

---

## Como Usar

### 1. Clonar o repositório
Clone o repositório em sua máquina local:

```bash
git clone https://github.com/seu-usuario/consulta-groqcloud.git
cd consulta-groqcloud
```

### 2. Configurar a chave de API
Garanta que a variável de ambiente `GROP_CLOUD` esteja configurada corretamente com a chave obtida no painel da GroqCloud.

### 3. Fazer uma consulta
No código Java, utilize o método estático `ConsultaGroqCloud.obterInformacao` para realizar uma consulta. 

#### Exemplo:

```java
public class Main {
    public static void main(String[] args) {
        String nomeArtista = "Elis Regina";
        try {
            String resposta = ConsultaGroqCloud.obterInformacao(nomeArtista);
            System.out.println(resposta);
        } catch (Exception e) {
            System.err.println("Erro ao obter informações: " + e.getMessage());
        }
    }
}
```

---

## Retorno da API

Quando uma consulta é bem-sucedida, a API retorna uma resposta estruturada com informações sobre o artista e três músicas.  
Exemplo de saída:

```json
{
  "mensagem": "Elis Regina foi uma das maiores cantoras brasileiras. Suas músicas mais conhecidas incluem: 'Aáguas de Março', 'O Bêbado e a Equilibrista', 'Como nossos pais'."
}
```

---

## Tratamento de Erros

A API pode lançar as seguintes exceções:

1. **`IllegalArgumentException`**:
   - Quando o parâmetro fornecido está nulo ou vazio.
   - Quando a variável de ambiente `GROP_CLOUD` não está configurada.

2. **`RuntimeException`**:
   - Quando ocorre falha ao converter objetos para JSON.

3. **`IOException`**:
   - Quando há falha na comunicação com a API.

---

## Contribuições

Contribuições são bem-vindas! Para contribuir:

1. Faça um fork do projeto.
2. Crie um branch para sua funcionalidade (`git checkout -b minha-nova-funcionalidade`).
3. Faça commit de suas alterações (`git commit -m 'Minha nova funcionalidade'`).
4. Envie o push para o branch (`git push origin minha-nova-funcionalidade`).
5. Abra um Pull Request.

---

## Licença

Este projeto está licenciado sob a licença MIT. Consulte o arquivo [LICENSE](LICENSE) para mais detalhes.


