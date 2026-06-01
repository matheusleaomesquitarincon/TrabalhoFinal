package com.marketplace.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade que representa um Pedido no sistema de marketplace.
 * 
 * A classe Order armazena informações sobre pedidos realizados pelos usuários,
 * incluindo status, data, total e relacionamento com o usuário que fez o pedido.
 * 
 * @author Marketplace Team
 * @version 1.0
 */
@Entity
@Table(name = "pedidos")
public class Order {
    
    /**
     * Identificador único do pedido.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Relacionamento com o usuário que fez o pedido.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;
    
    /**
     * Valor total do pedido.
     */
    @Positive(message = "Total deve ser maior que zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
    
    /**
     * Status do pedido: PENDENTE, CONFIRMADO, ENVIADO, ENTREGUE ou CANCELADO.
     */
    @Column(nullable = false, length = 20)
    private String status;
    
    /**
     * Data e hora do pedido.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataPedido;
    
    /**
     * Data e hora da entrega estimada.
     */
    @Column(length = 50)
    private String dataEntregaEstimada;
    
    /**
     * Observações adicionais sobre o pedido.
     */
    @Column(length = 500)
    private String observacoes;
    
    /**
     * Endereço de entrega.
     */
    @Column(nullable = false, length = 255)
    private String enderecoEntrega;
    
    /**
     * Data e hora da última atualização.
     */
    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;
    
    /**
     * Inicializa as datas e status quando a entidade é criada.
     */
    @PrePersist
    protected void onCreate() {
        dataPedido = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
        if (status == null) {
            status = "PENDENTE";
        }
    }
    
    /**
     * Atualiza a data de modificação antes de persistir.
     */
    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    // Construtores
    public Order() {
    }

    public Order(User usuario, BigDecimal total, String enderecoEntrega) {
        this.usuario = usuario;
        this.total = total;
        this.enderecoEntrega = enderecoEntrega;
        this.status = "PENDENTE";
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getDataEntregaEstimada() {
        return dataEntregaEstimada;
    }

    public void setDataEntregaEstimada(String dataEntregaEstimada) {
        this.dataEntregaEstimada = dataEntregaEstimada;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
