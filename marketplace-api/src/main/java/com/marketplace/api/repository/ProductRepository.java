package com.marketplace.api.repository;

import com.marketplace.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para a entidade Product.
 * 
 * Interface que estende JpaRepository, fornecendo operações CRUD padrão
 * e métodos customizados para consultar produtos no banco de dados.
 * 
 * @author Marketplace Team
 * @version 1.0
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    /**
     * Busca produtos pelo nome contendo o termo especificado.
     * Busca case-insensitive.
     * 
     * @param nome termo de busca no nome do produto
     * @return lista de produtos encontrados
     */
    List<Product> findByNomeIgnoreCaseContaining(String nome);
    
    /**
     * Busca produtos por categoria.
     * 
     * @param categoria nome da categoria
     * @return lista de produtos da categoria
     */
    List<Product> findByCategoria(String categoria);
    
    /**
     * Busca produtos com estoque disponível.
     * 
     * @return lista de produtos com quantidade maior que zero
     */
    List<Product> findByQuantidadeGreaterThan(Integer quantidade);
    
    /**
     * Valida se um produto existe.
     * 
     * @param id identificador do produto
     * @return true se existe, false caso contrário
     */
    boolean existsById(Long id);
}
