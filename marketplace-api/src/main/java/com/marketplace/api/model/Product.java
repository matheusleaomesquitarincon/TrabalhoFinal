package com.marketplace.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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

    // Construtores
    public Product() {
    }

    public Product(String nome, String descricao, BigDecimal preco, Integer quantidade, 
                   String imagem, String categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.imagem = imagem;
        this.categoria = categoria;
    }

    // Getters e Setters explícitos para suporte IDE
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
