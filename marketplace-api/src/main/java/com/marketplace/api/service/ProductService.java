package com.marketplace.api.service;

import com.marketplace.api.model.Product;
import com.marketplace.api.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Serviço para operações relacionadas a Produtos.
 * 
 * Gerencia a lógica de negócios para operações CRUD de produtos,
 * buscas e atualizações de estoque.
 * 
 * @author Marketplace Team
 * @version 1.0
 */
@Service
@Slf4j
@Transactional
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    /**
     * Obtém todos os produtos cadastrados.
     * 
     * @return lista de todos os produtos
     */
    public List<Product> obterTodos() {
        log.info("Obtendo todos os produtos");
        return productRepository.findAll();
    }
    
    /**
     * Obtém um produto pelo ID.
     * 
     * @param id identificador do produto
     * @return Optional contendo o produto se encontrado
     */
    public Optional<Product> obterPorId(Long id) {
        log.info("Obtendo produto com ID: {}", id);
        return productRepository.findById(id);
    }
    
    /**
     * Busca produtos pelo nome.
     * 
     * @param nome termo de busca
     * @return lista de produtos encontrados
     */
    public List<Product> buscarPorNome(String nome) {
        log.info("Buscando produtos com nome: {}", nome);
        return productRepository.findByNomeIgnoreCaseContaining(nome);
    }
    
    /**
     * Busca produtos por categoria.
     * 
     * @param categoria nome da categoria
     * @return lista de produtos da categoria
     */
    public List<Product> buscarPorCategoria(String categoria) {
        log.info("Buscando produtos da categoria: {}", categoria);
        return productRepository.findByCategoria(categoria);
    }
    
    /**
     * Obtém produtos com estoque disponível.
     * 
     * @return lista de produtos em estoque
     */
    public List<Product> obterProdutosEmEstoque() {
        log.info("Obtendo produtos em estoque");
        return productRepository.findByQuantidadeGreaterThan(0);
    }
    
    /**
     * Cria um novo produto.
     * 
     * @param product objeto Product a ser criado
     * @return produto criado
     */
    public Product criar(Product product) {
        log.info("Criando novo produto: {}", product.getNome());
        return productRepository.save(product);
    }
    
    /**
     * Atualiza um produto existente.
     * 
     * @param id identificador do produto
     * @param product dados atualizados do produto
     * @return produto atualizado
     */
    public Product atualizar(Long id, Product product) {
        log.info("Atualizando produto com ID: {}", id);
        Optional<Product> existente = productRepository.findById(id);
        
        if (existente.isPresent()) {
            Product p = existente.get();
            p.setNome(product.getNome());
            p.setDescricao(product.getDescricao());
            p.setPreco(product.getPreco());
            p.setQuantidade(product.getQuantidade());
            p.setImagem(product.getImagem());
            p.setCategoria(product.getCategoria());
            return productRepository.save(p);
        }
        
        log.warn("Produto com ID {} não encontrado", id);
        throw new RuntimeException("Produto não encontrado");
    }
    
    /**
     * Deleta um produto pelo ID.
     * 
     * @param id identificador do produto
     */
    public void deletar(Long id) {
        log.info("Deletando produto com ID: {}", id);
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            log.warn("Produto com ID {} não encontrado", id);
            throw new RuntimeException("Produto não encontrado");
        }
    }
    
    /**
     * Reduz a quantidade de um produto em estoque.
     * Utilizado quando um produto é adicionado ao carrinho/pedido.
     * 
     * @param id identificador do produto
     * @param quantidade quantidade a reduzir
     * @return true se sucesso, false se quantidade insuficiente
     */
    public boolean reduzirEstoque(Long id, Integer quantidade) {
        log.info("Reduzindo estoque do produto {}: {} unidades", id, quantidade);
        Optional<Product> product = productRepository.findById(id);
        
        if (product.isPresent()) {
            Product p = product.get();
            if (p.getQuantidade() >= quantidade) {
                p.setQuantidade(p.getQuantidade() - quantidade);
                productRepository.save(p);
                return true;
            }
            log.warn("Quantidade insuficiente para produto ID: {}", id);
        }
        return false;
    }
    
    /**
     * Aumenta a quantidade de um produto em estoque.
     * Utilizado para devoluções ou reposição.
     * 
     * @param id identificador do produto
     * @param quantidade quantidade a aumentar
     */
    public void aumentarEstoque(Long id, Integer quantidade) {
        log.info("Aumentando estoque do produto {}: {} unidades", id, quantidade);
        Optional<Product> product = productRepository.findById(id);
        
        if (product.isPresent()) {
            Product p = product.get();
            p.setQuantidade(p.getQuantidade() + quantidade);
            productRepository.save(p);
        } else {
            log.warn("Produto com ID {} não encontrado", id);
            throw new RuntimeException("Produto não encontrado");
        }
    }
}
