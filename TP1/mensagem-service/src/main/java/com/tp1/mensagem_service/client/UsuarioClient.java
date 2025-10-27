package com.tp1.mensagem_service.client;

import com.tp1.mensagem_service.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "usuario-service", url = "http://localhost:8081")
public interface UsuarioClient {

    @GetMapping("/api/usuarios")
    List<Usuario> listarUsuarios();

    @GetMapping("/api/usuarios/{id}")
    Usuario buscarUsuario(@PathVariable("id") Long id);
}