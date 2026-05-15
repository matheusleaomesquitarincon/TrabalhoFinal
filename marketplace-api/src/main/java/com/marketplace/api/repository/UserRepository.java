package com.marketplace.api.repository;

import com.marketplace.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

/**
 * Repositório para a entidade User.
 * 
 * Interface que estende JpaRepository, fornecendo operações CRUD padrão
 * e métodos customizados para consultar usuários no banco de dados.
 * 
 * @author Marketplace Team
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Busca um usuário pelo email.
     * 
     * @param email email do usuário
     * @return Optional contendo o usuário se encontrado
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Busca usuários pelo tipo (CLIENTE ou ADMIN).
     * 
     * @param tipo tipo de usuário
     * @return lista de usuários do tipo especificado
     */
    List<User> findByTipo(String tipo);
    
    /**
     * Busca usuários pelo status.
     * 
     * @param status status do usuário (ATIVO ou INATIVO)
     * @return lista de usuários com o status especificado
     */
    List<User> findByStatus(String status);
    
    /**
     * Busca usuários pelo nome contendo o termo especificado.
     * 
     * @param nome termo de busca
     * @return lista de usuários encontrados
     */
    List<User> findByNomeIgnoreCaseContaining(String nome);
    
    /**
     * Verifica se um email já existe no banco.
     * 
     * @param email email a verificar
     * @return true se existe, false caso contrário
     */
    boolean existsByEmail(String email);
}
