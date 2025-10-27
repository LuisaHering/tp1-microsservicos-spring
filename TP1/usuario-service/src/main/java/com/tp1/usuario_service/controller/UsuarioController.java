package com.tp1.usuario_service.controller;

import com.tp1.usuario_service.model.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    // Simulando banco de dados em memória
    private final List<Usuario> usuarios = new ArrayList<>();

    public UsuarioController() {
        // Dados iniciais
        usuarios.add(new Usuario(1L, "João Silva", "joao@email.com", "(11) 98765-4321"));
        usuarios.add(new Usuario(2L, "Maria Santos", "maria@email.com", "(21) 99876-5432"));
        usuarios.add(new Usuario(3L, "Pedro Costa", "pedro@email.com", "(31) 97654-3210"));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();

        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        usuario.setId((long) (usuarios.size() + 1));
        usuarios.add(usuario);
        return ResponseEntity.ok(usuario);
    }
}