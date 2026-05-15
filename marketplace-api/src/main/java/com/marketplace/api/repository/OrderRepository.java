package com.marketplace.api.repository;

import com.marketplace.api.model.Order;
import com.marketplace.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para a entidade Order.
 * 
 * Interface que estende JpaRepository, fornecendo operações CRUD padrão
 * e métodos customizados para consultar pedidos no banco de dados.
 * 
 * @author Marketplace Team
 * @version 1.0
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    /**
     * Busca todos os pedidos de um usuário específico.
     * 
     * @param usuario objeto User do qual buscar pedidos
     * @return lista de pedidos do usuário
     */
    List<Order> findByUsuario(User usuario);
    
    /**
     * Busca todos os pedidos de um usuário pelo ID.
     * 
     * @param usuarioId identificador do usuário
     * @return lista de pedidos do usuário
     */
    List<Order> findByUsuarioId(Long usuarioId);
    
    /**
     * Busca pedidos pelo status.
     * 
     * @param status status do pedido
     * @return lista de pedidos com o status especificado
     */
    List<Order> findByStatus(String status);
    
    /**
     * Conta a quantidade de pedidos com um status específico.
     * 
     * @param status status do pedido
     * @return quantidade de pedidos
     */
    long countByStatus(String status);
}
