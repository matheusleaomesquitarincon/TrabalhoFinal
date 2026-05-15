package com.marketplace.api.repository;

import com.marketplace.api.model.OrderItem;
import com.marketplace.api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositório para a entidade OrderItem.
 * 
 * Interface que estende JpaRepository, fornecendo operações CRUD padrão
 * e métodos customizados para consultar itens de pedido no banco de dados.
 * 
 * @author Marketplace Team
 * @version 1.0
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
    /**
     * Busca todos os itens de um pedido específico.
     * 
     * @param pedido objeto Order do qual buscar itens
     * @return lista de itens do pedido
     */
    List<OrderItem> findByPedido(Order pedido);
    
    /**
     * Busca todos os itens de um pedido pelo ID do pedido.
     * 
     * @param pedidoId identificador do pedido
     * @return lista de itens do pedido
     */
    List<OrderItem> findByPedidoId(Long pedidoId);
    
    /**
     * Conta a quantidade total de itens em um pedido.
     * 
     * @param pedidoId identificador do pedido
     * @return quantidade de itens
     */
    long countByPedidoId(Long pedidoId);
}
