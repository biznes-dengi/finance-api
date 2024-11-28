package com.finance.app.auth.process;

import com.finance.app.auth.boundary.request.TelegramRequest;
import com.finance.app.auth.boundary.response.ValidationResponse;
import com.finance.app.auth.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TelegramProcess {

    @Value("${spring.security.telegram.token}")
    private String token;

    public ValidationResponse validate(final TelegramRequest request) {
        final var user = request.user();
        final var telegramId = Long.parseLong(user.get("id"));
        final var receivedHash = user.remove("hash");
        final var payload = user.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("\n"));
        final var calculatedHash = calculateHmacSHA256(payload, token);
        return ValidationResponse.aResponse()
                .telegramId(telegramId)
                .firstName(user.get("first_name"))
                .username(user.get("username"))
                .valid(calculatedHash.equals(receivedHash))
                .build();
    }

    private String calculateHmacSHA256(final String data, final String key) {
        try {
            final var sha256Hmac = Mac.getInstance("HmacSHA256");
            final var secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256Hmac.init(secretKeySpec);
            final var result = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(result);
        } catch (Exception exc) {
            throw new AuthException(HttpStatus.UNAUTHORIZED, "Error during hash calculation. Please check configuration");
        }
    }

    private String bytesToHex(final byte[] bytes) {
        final var builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append("%02x".formatted(b));
        }
        return builder.toString();
    }
}
