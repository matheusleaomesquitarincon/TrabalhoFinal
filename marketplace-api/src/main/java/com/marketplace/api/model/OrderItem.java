package com.marketplace.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * Entidade que representa um Item dentro de um Pedido.
 * 
 * A classe OrderItem armazena informações sobre cada produto em um pedido,
 * incluindo quantidade, preço unitário e subtotal.
 * 
 * @author Marketplace Team
 * @version 1.0
 */
@Entity
@Table(name = "itens_pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    
    /**
     * Identificador único do item do pedido.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Relacionamento com o pedido ao qual este item pertence.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Order pedido;
    
    /**
     * Relacionamento com o produto.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Product produto;
    
    /**
     * Quantidade do produto neste item.
     */
    @Positive(message = "Quantidade deve ser maior que zero")
    @Column(nullable = false)
    private Integer quantidade;
    
    /**
     * Preço unitário do produto no momento da compra.
     */
    @Positive(message = "Preço deve ser maior que zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario;
    
    /**
     * Subtotal do item (quantidade * preço unitário).
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;
    
    /**
     * Calcula o subtotal antes de persistir.
     */
    @PrePersist
    @PreUpdate
    protected void calcularSubtotal() {
        if (quantidade != null && precoUnitario != null) {
            subtotal = precoUnitario.multiply(BigDecimal.valueOf(quantidade));
        }
    }
}
