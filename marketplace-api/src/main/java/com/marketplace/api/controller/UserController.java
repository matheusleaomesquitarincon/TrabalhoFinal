package com.marketplace.api.controller;

import com.marketplace.api.model.User;
import com.marketplace.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gerenciar operações de Usuários.
 * 
 * Endpoints para criar, ler, atualizar e deletar usuários.
 * Base URL: /api/usuarios
 * 
 * @author Marketplace Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * GET /api/usuarios - Obtém todos os usuários.
     * 
     * @return lista de todos os usuários com status 200
     */
    @GetMapping
    public ResponseEntity<List<User>> obterTodos() {
        List<User> usuarios = userService.obterTodos();
        return ResponseEntity.ok(usuarios);
    }
    
    /**
     * GET /api/usuarios/{id} - Obtém um usuário pelo ID.
     * 
     * @param id identificador do usuário
     * @return usuário encontrado com status 200, ou 404 se não encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obterPorId(@PathVariable Long id) {
        Optional<User> user = userService.obterPorId(id);
        
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Usuário não encontrado");
    }
    
    /**
     * GET /api/usuarios/email/{email} - Busca usuário por email.
     * 
     * @param email email do usuário
     * @return usuário encontrado ou 404
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<?> buscarPorEmail(@PathVariable String email) {
        Optional<User> user = userService.buscarPorEmail(email);
        
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Usuário não encontrado");
    }
    
    /**
     * GET /api/usuarios/buscar/nome?nome=termo - Busca usuários por nome.
     * 
     * @param nome termo de busca
     * @return lista de usuários encontrados
     */
    @GetMapping("/buscar/nome")
    public ResponseEntity<List<User>> buscarPorNome(@RequestParam String nome) {
        List<User> usuarios = userService.buscarPorNome(nome);
        return ResponseEntity.ok(usuarios);
    }
    
    /**
     * GET /api/usuarios/tipo/clientes - Obtém todos os clientes.
     * 
     * @return lista de usuários com tipo CLIENTE
     */
    @GetMapping("/tipo/clientes")
    public ResponseEntity<List<User>> obterClientes() {
        List<User> clientes = userService.obterClientes();
        return ResponseEntity.ok(clientes);
    }
    
    /**
     * GET /api/usuarios/tipo/admin - Obtém todos os administradores.
     * 
     * @return lista de usuários com tipo ADMIN
     */
    @GetMapping("/tipo/admin")
    public ResponseEntity<List<User>> obterAdministradores() {
        List<User> admins = userService.obterAdministradores();
        return ResponseEntity.ok(admins);
    }
    
    /**
     * POST /api/usuarios - Cria um novo usuário.
     * 
     * @param user dados do novo usuário
     * @return usuário criado com status 201
     */
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody User user) {
        try {
            User created = userService.criar(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body("Erro: " + e.getMessage());
        }
    }
    
    /**
     * PUT /api/usuarios/{id} - Atualiza um usuário.
     * 
     * @param id identificador do usuário
     * @param user dados atualizados
     * @return usuário atualizado com status 200
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody User user) {
        try {
            User updated = userService.atualizar(id, user);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro: " + e.getMessage());
        }
    }
    
    /**
     * DELETE /api/usuarios/{id} - Deleta um usuário.
     * 
     * @param id identificador do usuário
     * @return status 204 se sucesso, 404 se não encontrado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            userService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro: " + e.getMessage());
        }
    }
    
    /**
     * PATCH /api/usuarios/{id}/ativar - Ativa um usuário.
     * 
     * @param id identificador do usuário
     * @return status 200 se sucesso
     */
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<?> ativar(@PathVariable Long id) {
        try {
            userService.ativar(id);
            return ResponseEntity.ok("Usuário ativado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro: " + e.getMessage());
        }
    }
    
    /**
     * PATCH /api/usuarios/{id}/desativar - Desativa um usuário.
     * 
     * @param id identificador do usuário
     * @return status 200 se sucesso
     */
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<?> desativar(@PathVariable Long id) {
        try {
            userService.desativar(id);
            return ResponseEntity.ok("Usuário desativado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro: " + e.getMessage());
        }
    }
}
