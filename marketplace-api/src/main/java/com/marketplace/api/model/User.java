package com.marketplace.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
