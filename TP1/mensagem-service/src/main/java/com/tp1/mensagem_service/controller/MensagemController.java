package com.tp1.mensagem_service.controller;

import com.tp1.mensagem_service.client.UsuarioClient;
import com.tp1.mensagem_service.model.Mensagem;
import com.tp1.mensagem_service.model.MensagemRequest;
import com.tp1.mensagem_service.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/mensagens")
public class MensagemController {

    @Autowired
    private UsuarioClient usuarioClient;

    private final List<Mensagem> mensagens = new ArrayList<>();
    private Long proximoId = 1L;

    // Endpoint que CONSOME o serviço de usuários
    @PostMapping("/enviar")
    public ResponseEntity<Mensagem> enviarMensagem(@RequestBody MensagemRequest request) {
        try {
            // COMUNICAÇÃO ENTRE MICROSSERVIÇOS
            Usuario remetente = usuarioClient.buscarUsuario(request.getRemetenteId());
            Usuario destinatario = usuarioClient.buscarUsuario(request.getDestinatarioId());

            if (remetente == null || destinatario == null) {
                return ResponseEntity.badRequest().build();
            }

            Mensagem mensagem = new Mensagem(
                    proximoId++,
                    request.getTexto(),
                    remetente,
                    destinatario,
                    LocalDateTime.now(),
                    "ENVIADA"
            );

            mensagens.add(mensagem);
            return ResponseEntity.ok(mensagem);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Lista usuários disponíveis (consome outro microsserviço)
    @GetMapping("/usuarios-disponiveis")
    public ResponseEntity<List<Usuario>> listarUsuariosDisponiveis() {
        try {
            List<Usuario> usuarios = usuarioClient.listarUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Lista todas mensagens
    @GetMapping
    public ResponseEntity<List<Mensagem>> listarMensagens() {
        return ResponseEntity.ok(mensagens);
    }

    // Mensagens enviadas por um usuário específico
    @GetMapping("/usuario/{id}/enviadas")
    public ResponseEntity<List<Mensagem>> mensagensEnviadas(@PathVariable Long id) {
        List<Mensagem> enviadas = mensagens.stream()
                .filter(m -> m.getRemetente().getId().equals(id))
                .toList();
        return ResponseEntity.ok(enviadas);
    }

    // Mensagens recebidas por um usuário específico
    @GetMapping("/usuario/{id}/recebidas")
    public ResponseEntity<List<Mensagem>> mensagensRecebidas(@PathVariable Long id) {
        List<Mensagem> recebidas = mensagens.stream()
                .filter(m -> m.getDestinatario().getId().equals(id))
                .toList();
        return ResponseEntity.ok(recebidas);
    }
}