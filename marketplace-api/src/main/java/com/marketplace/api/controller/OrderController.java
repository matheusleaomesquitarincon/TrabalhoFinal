package com.marketplace.api.controller;

import com.marketplace.api.model.Order;
import com.marketplace.api.model.OrderItem;
import com.marketplace.api.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gerenciar operações de Pedidos.
 * 
 * Endpoints para criar, ler, atualizar e deletar pedidos e seus itens.
 * Base URL: /api/pedidos
 * 
 * @author Marketplace Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    /**
     * GET /api/pedidos - Obtém todos os pedidos.
     * 
     * @return lista de todos os pedidos com status 200
     */
    @GetMapping
    public ResponseEntity<List<Order>> obterTodos() {
        log.info("GET /api/pedidos - Obtendo todos os pedidos");
        List<Order> pedidos = orderService.obterTodos();
        return ResponseEntity.ok(pedidos);
    }
    
    /**
     * GET /api/pedidos/{id} - Obtém um pedido pelo ID.
     * 
     * @param id identificador do pedido
     * @return pedido encontrado com status 200, ou 404 se não encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obterPorId(@PathVariable Long id) {
        log.info("GET /api/pedidos/{} - Obtendo pedido", id);
        Optional<Order> order = orderService.obterPorId(id);
        
        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Pedido não encontrado");
    }
    
    /**
     * GET /api/pedidos/usuario/{usuarioId} - Obtém pedidos de um usuário.
     * 
     * @param usuarioId identificador do usuário
     * @return lista de pedidos do usuário
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Order>> obterPedidosPorUsuario(@PathVariable Long usuarioId) {
        log.info("GET /api/pedidos/usuario/{} - Obtendo pedidos", usuarioId);
        List<Order> pedidos = orderService.obterPedidosPorUsuario(usuarioId);
        return ResponseEntity.ok(pedidos);
    }
    
    /**
     * GET /api/pedidos/status/{status} - Obtém pedidos por status.
     * 
     * @param status status dos pedidos
     * @return lista de pedidos com o status especificado
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> obterPedidosPorStatus(@PathVariable String status) {
        log.info("GET /api/pedidos/status/{} - Obtendo pedidos", status);
        List<Order> pedidos = orderService.obterPedidosPorStatus(status);
        return ResponseEntity.ok(pedidos);
    }
    
    /**
     * POST /api/pedidos - Cria um novo pedido.
     * 
     * @param order dados do novo pedido
     * @return pedido criado com status 201
     */
    @PostMapping
    public ResponseEntity<Order> criar(@Valid @RequestBody Order order) {
        log.info("POST /api/pedidos - Criando novo pedido");
        Order created = orderService.criar(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    /**
     * PUT /api/pedidos/{id} - Atualiza um pedido.
     * 
     * @param id identificador do pedido
     * @param order dados atualizados
     * @return pedido atualizado com status 200
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Order order) {
        log.info("PUT /api/pedidos/{} - Atualizando", id);
        try {
            Order updated = orderService.atualizar(id, order);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro: " + e.getMessage());
        }
    }
    
    /**
     * DELETE /api/pedidos/{id} - Deleta um pedido.
     * 
     * @param id identificador do pedido
     * @return status 204 se sucesso, 404 se não encontrado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        log.info("DELETE /api/pedidos/{} - Deletando", id);
        try {
            orderService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro: " + e.getMessage());
        }
    }
    
    /**
     * PATCH /api/pedidos/{id}/status?status=novo - Altera status do pedido.
     * 
     * @param id identificador do pedido
     * @param status novo status
     * @return pedido atualizado com status 200
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> alterarStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        log.info("PATCH /api/pedidos/{}/status - Alterando para: {}", id, status);
        try {
            Order updated = orderService.alterarStatus(id, status);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro: " + e.getMessage());
        }
    }
    
    /**
     * POST /api/pedidos/{pedidoId}/itens - Adiciona item ao pedido.
     * 
     * @param pedidoId identificador do pedido
     * @param item item a adicionar
     * @return item adicionado com status 201
     */
    @PostMapping("/{pedidoId}/itens")
    public ResponseEntity<?> adicionarItem(
            @PathVariable Long pedidoId,
            @Valid @RequestBody OrderItem item) {
        log.info("POST /api/pedidos/{}/itens - Adicionando item", pedidoId);
        try {
            OrderItem added = orderService.adicionarItem(pedidoId, item);
            return ResponseEntity.status(HttpStatus.CREATED).body(added);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro: " + e.getMessage());
        }
    }
    
    /**
     * DELETE /api/pedidos/itens/{itemId} - Remove item do pedido.
     * 
     * @param itemId identificador do item
     * @return status 204 se sucesso
     */
    @DeleteMapping("/itens/{itemId}")
    public ResponseEntity<?> removerItem(@PathVariable Long itemId) {
        log.info("DELETE /api/pedidos/itens/{} - Removendo item", itemId);
        try {
            orderService.removerItem(itemId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro: " + e.getMessage());
        }
    }
    
    /**
     * GET /api/pedidos/{pedidoId}/itens - Obtém itens de um pedido.
     * 
     * @param pedidoId identificador do pedido
     * @return lista de itens do pedido
     */
    @GetMapping("/{pedidoId}/itens")
    public ResponseEntity<List<OrderItem>> obterItensPedido(@PathVariable Long pedidoId) {
        log.info("GET /api/pedidos/{}/itens - Obtendo itens", pedidoId);
        List<OrderItem> itens = orderService.obterItensPedido(pedidoId);
        return ResponseEntity.ok(itens);
    }
}
