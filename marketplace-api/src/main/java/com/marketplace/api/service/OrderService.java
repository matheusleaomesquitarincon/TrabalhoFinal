package com.marketplace.api.service;

import com.marketplace.api.model.Order;
import com.marketplace.api.model.OrderItem;
import com.marketplace.api.model.User;
import com.marketplace.api.repository.OrderRepository;
import com.marketplace.api.repository.OrderItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Serviço para operações relacionadas a Pedidos.
 * 
 * Gerencia a lógica de negócios para operações CRUD de pedidos,
 * gerenciamento de itens, alteração de status e cálculos.
 * 
 * @author Marketplace Team
 * @version 1.0
 */
@Service
@Slf4j
@Transactional
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    /**
     * Obtém todos os pedidos.
     * 
     * @return lista de todos os pedidos
     */
    public List<Order> obterTodos() {
        log.info("Obtendo todos os pedidos");
        return orderRepository.findAll();
    }
    
    /**
     * Obtém um pedido pelo ID.
     * 
     * @param id identificador do pedido
     * @return Optional contendo o pedido se encontrado
     */
    public Optional<Order> obterPorId(Long id) {
        log.info("Obtendo pedido com ID: {}", id);
        return orderRepository.findById(id);
    }
    
    /**
     * Obtém todos os pedidos de um usuário específico.
     * 
     * @param usuarioId identificador do usuário
     * @return lista de pedidos do usuário
     */
    public List<Order> obterPedidosPorUsuario(Long usuarioId) {
        log.info("Obtendo pedidos do usuário: {}", usuarioId);
        return orderRepository.findByUsuarioId(usuarioId);
    }
    
    /**
     * Obtém pedidos por status.
     * 
     * @param status status dos pedidos a buscar
     * @return lista de pedidos com o status especificado
     */
    public List<Order> obterPedidosPorStatus(String status) {
        log.info("Obtendo pedidos com status: {}", status);
        return orderRepository.findByStatus(status);
    }
    
    /**
     * Cria um novo pedido.
     * 
     * @param order objeto Order a ser criado
     * @return pedido criado
     */
    public Order criar(Order order) {
        log.info("Criando novo pedido para usuário: {}", order.getUsuario().getId());
        return orderRepository.save(order);
    }
    
    /**
     * Atualiza um pedido existente.
     * 
     * @param id identificador do pedido
     * @param order dados atualizados do pedido
     * @return pedido atualizado
     */
    public Order atualizar(Long id, Order order) {
        log.info("Atualizando pedido com ID: {}", id);
        Optional<Order> existente = orderRepository.findById(id);
        
        if (existente.isPresent()) {
            Order o = existente.get();
            o.setStatus(order.getStatus());
            o.setObservacoes(order.getObservacoes());
            o.setEnderecoEntrega(order.getEnderecoEntrega());
            return orderRepository.save(o);
        }
        
        log.warn("Pedido com ID {} não encontrado", id);
        throw new RuntimeException("Pedido não encontrado");
    }
    
    /**
     * Deleta um pedido pelo ID.
     * 
     * @param id identificador do pedido
     */
    public void deletar(Long id) {
        log.info("Deletando pedido com ID: {}", id);
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            log.warn("Pedido com ID {} não encontrado", id);
            throw new RuntimeException("Pedido não encontrado");
        }
    }
    
    /**
     * Altera o status de um pedido.
     * Transições válidas: PENDENTE -> CONFIRMADO -> ENVIADO -> ENTREGUE
     * Qualquer status pode -> CANCELADO
     * 
     * @param id identificador do pedido
     * @param novoStatus novo status do pedido
     * @return pedido atualizado
     */
    public Order alterarStatus(Long id, String novoStatus) {
        log.info("Alterando status do pedido {} para: {}", id, novoStatus);
        Optional<Order> order = orderRepository.findById(id);
        
        if (order.isPresent()) {
            Order o = order.get();
            o.setStatus(novoStatus);
            return orderRepository.save(o);
        }
        
        log.warn("Pedido com ID {} não encontrado", id);
        throw new RuntimeException("Pedido não encontrado");
    }
    
    /**
     * Adiciona um item ao pedido.
     * 
     * @param pedidoId identificador do pedido
     * @param item item a ser adicionado
     * @return item adicionado
     */
    public OrderItem adicionarItem(Long pedidoId, OrderItem item) {
        log.info("Adicionando item ao pedido: {}", pedidoId);
        Optional<Order> order = orderRepository.findById(pedidoId);
        
        if (order.isPresent()) {
            item.setPedido(order.get());
            OrderItem savedItem = orderItemRepository.save(item);
            
            // Atualizar total do pedido
            atualizarTotalPedido(pedidoId);
            
            return savedItem;
        }
        
        log.warn("Pedido com ID {} não encontrado", pedidoId);
        throw new RuntimeException("Pedido não encontrado");
    }
    
    /**
     * Remove um item do pedido.
     * 
     * @param itemId identificador do item
     */
    public void removerItem(Long itemId) {
        log.info("Removendo item: {}", itemId);
        Optional<OrderItem> item = orderItemRepository.findById(itemId);
        
        if (item.isPresent()) {
            Long pedidoId = item.get().getPedido().getId();
            orderItemRepository.deleteById(itemId);
            
            // Atualizar total do pedido
            atualizarTotalPedido(pedidoId);
        } else {
            log.warn("Item com ID {} não encontrado", itemId);
            throw new RuntimeException("Item não encontrado");
        }
    }
    
    /**
     * Obtém todos os itens de um pedido.
     * 
     * @param pedidoId identificador do pedido
     * @return lista de itens do pedido
     */
    public List<OrderItem> obterItensPedido(Long pedidoId) {
        log.info("Obtendo itens do pedido: {}", pedidoId);
        return orderItemRepository.findByPedidoId(pedidoId);
    }
    
    /**
     * Atualiza o total do pedido baseado na soma dos subtotais dos itens.
     * 
     * @param pedidoId identificador do pedido
     */
    private void atualizarTotalPedido(Long pedidoId) {
        log.debug("Atualizando total do pedido: {}", pedidoId);
        Optional<Order> order = orderRepository.findById(pedidoId);
        
        if (order.isPresent()) {
            List<OrderItem> itens = orderItemRepository.findByPedidoId(pedidoId);
            BigDecimal total = itens.stream()
                    .map(OrderItem::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            Order o = order.get();
            o.setTotal(total);
            orderRepository.save(o);
            log.debug("Total do pedido {} atualizado para: {}", pedidoId, total);
        }
    }
    
    /**
     * Conta pedidos por status.
     * 
     * @param status status dos pedidos
     * @return quantidade de pedidos
     */
    public long contarPorStatus(String status) {
        log.info("Contando pedidos com status: {}", status);
        return orderRepository.countByStatus(status);
    }
}
