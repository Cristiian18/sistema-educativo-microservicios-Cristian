package com.microservicios.matriculas.client;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

@Component
@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        log.warn("==> Entrando a CustomErrorDecoder");
        log.error("===> Error en llamada Feign - Método: {}", methodKey);
        log.error("===> Status: {}", response.status());
        log.error("===> Reason: {}", response.reason());
        System.out.println("===> Método: " + methodKey);
        System.out.println("===> Status: " + response.status());
        System.out.println("===> Reason: " + response.reason());

        // Log headers
        log.warn("===> Headers:");
        response.headers().forEach((key, values) -> {
            log.warn("     {}: {}", key, values);
            System.out.println("===> Header: " + key + " = " + values);
        });

        // Leer el cuerpo si existe
        String responseBody = null;
        if (response.body() != null) {
            try {
                responseBody = IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8);
                log.error("===> Response body: {}", responseBody);
                System.out.println("===> Response body: " + responseBody);
            } catch (IOException e) {
                log.warn("===> No se pudo leer el cuerpo de la respuesta", e);
            }
        } else {
            log.warn("===> El cuerpo de la respuesta es null");
            System.out.println("===> El cuerpo de la respuesta es null");
        }

        String errorMessage = String.format("Error en la llamada al servicio - Status: %d, Reason: %s, Body: %s",
                response.status(), response.reason(), responseBody);

        return switch (response.status()) {
            case 403 -> new RuntimeException("Forbidden - No autorizado para acceder al recurso. " + errorMessage);
            case 404 -> new RuntimeException("Recurso no encontrado. " + errorMessage);
            default -> new RuntimeException(errorMessage);
        };
    }
}
