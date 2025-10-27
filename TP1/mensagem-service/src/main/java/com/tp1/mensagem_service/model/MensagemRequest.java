package com.tp1.mensagem_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MensagemRequest {
    private Long remetenteId;
    private Long destinatarioId;
    private String texto;
}