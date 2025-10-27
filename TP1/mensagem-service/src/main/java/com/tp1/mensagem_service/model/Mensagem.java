package com.tp1.mensagem_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mensagem {
    private Long id;
    private String texto;
    private Usuario remetente;
    private Usuario destinatario;
    private LocalDateTime dataEnvio;
    private String status;
}