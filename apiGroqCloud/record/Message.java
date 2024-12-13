package apiGroqCloud.record;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Anderson Andrade Dev
 * @Data de Criação 12/12/2024
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Message(String role, String content) {
}
