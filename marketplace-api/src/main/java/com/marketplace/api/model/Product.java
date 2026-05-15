package com.marketplace.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade que representa um Produto no sistema de marketplace.
 * 
 * A classe Product armazena informações sobre os produtos disponíveis,
 * incluindo nome, descrição, preço, quantidade em estoque e data de criação.
 * 
 * @author Marketplace Team
 * @version 1.0
 */
@Entity
@Table(name = "produtos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    /**
     * Identificador único do produto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Nome do produto. Campo obrigatório.
     */
    @NotBlank(message = "Nome do produto é obrigatório")
    @Column(nullable = false, length = 150)
    private String nome;
    
    /**
     * Descrição detalhada do produto.
     */
    @Column(length = 500)
    private String descricao;
    
    /**
     * Preço unitário do produto.
     * Valor deve ser positivo.
     */
    @Positive(message = "Preço deve ser maior que zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;
    
    /**
     * Quantidade de produtos disponíveis em estoque.
     */
    @Column(nullable = false)
    private Integer quantidade;
    
    /**
     * URL da imagem do produto.
     */
    @Column(length = 255)
    private String imagem;
    
    /**
     * Categoria do produto.
     */
    @Column(length = 100)
    private String categoria;
    
    /**
     * Data e hora de criação do registro.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    
    /**
     * Data e hora da última atualização.
     */
    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;
    
    /**
     * Inicializa as datas quando a entidade é criada.
     */
    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }
    
    /**
     * Atualiza a data de modificação antes de persistir.
     */
    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }
}
