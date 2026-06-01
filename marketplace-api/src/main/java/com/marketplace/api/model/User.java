package com.marketplace.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * Entidade que representa um Usuário no sistema de marketplace.
 * 
 * A classe User armazena informações de usuários que podem ser clientes
 * ou administradores do sistema.
 * 
 * @author Marketplace Team
 * @version 1.0
 */
@Entity
@Table(name = "usuarios")
public class User {
    
    /**
     * Identificador único do usuário.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Nome completo do usuário. Campo obrigatório.
     */
    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false, length = 150)
    private String nome;
    
    /**
     * Email único do usuário. Campo obrigatório e deve ser válido.
     */
    @Email(message = "Email deve ser válido")
    @NotBlank(message = "Email é obrigatório")
    @Column(nullable = false, unique = true, length = 150)
    private String email;
    
    /**
     * Senha do usuário (idealmente hashada).
     */
    @NotBlank(message = "Senha é obrigatória")
    @Column(nullable = false, length = 255)
    private String senha;
    
    /**
     * Telefone de contato do usuário.
     */
    @Column(length = 20)
    private String telefone;
    
    /**
     * Endereço do usuário.
     */
    @Column(length = 255)
    private String endereco;
    
    /**
     * Tipo de usuário: CLIENTE ou ADMIN.
     */
    @Column(nullable = false, length = 20)
    private String tipo;
    
    /**
     * Status do usuário: ATIVO ou INATIVO.
     */
    @Column(nullable = false, length = 20)
    private String status;
    
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
     * Inicializa as datas e valores padrão quando a entidade é criada.
     */
    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
        if (tipo == null) {
            tipo = "CLIENTE";
        }
        if (status == null) {
            status = "ATIVO";
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
    public User() {
    }

    public User(String nome, String email, String senha, String tipo) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.status = "ATIVO";
    }

    // Getters e Setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
