package com.plataforma.ecommerce.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TestController {
    @GetMapping("/public/test")
    public String allAccess() {
        return "Contenido Público";
    }

    @GetMapping("/usuarios/test")
    public String usuarioAccess() {
        return "Contenido para Usuarios";
    }

    @GetMapping("/vendedores/test")
    public String vendedorAccess() {
        return "Contenido para Vendedores";
    }

    @GetMapping("/admin/test")
    public String adminAccess() {
        return "Contenido para Administradores";
    }
}
