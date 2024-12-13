package apiGroqCloud.record;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author Anderson Andrade Dev
 * @Data de Criação 12/12/2024
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestGroqCloud(String model, List<Message> messages) {
}
