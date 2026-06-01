package com.marketplace.api.service;

import com.marketplace.api.model.Product;
import com.marketplace.api.repository.ProductRepository;
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
        return productRepository.findAll();
    }
    
    /**
     * Obtém um produto pelo ID.
     * 
     * @param id identificador do produto
     * @return Optional contendo o produto se encontrado
     */
    public Optional<Product> obterPorId(Long id) {
        return productRepository.findById(id);
    }
    
    /**
     * Busca produtos pelo nome.
     * 
     * @param nome termo de busca
     * @return lista de produtos encontrados
     */
    public List<Product> buscarPorNome(String nome) {
        return productRepository.findByNomeIgnoreCaseContaining(nome);
    }
    
    /**
     * Busca produtos por categoria.
     * 
     * @param categoria nome da categoria
     * @return lista de produtos da categoria
     */
    public List<Product> buscarPorCategoria(String categoria) {
        return productRepository.findByCategoria(categoria);
    }
    
    /**
     * Obtém produtos com estoque disponível.
     * 
     * @return lista de produtos em estoque
     */
    public List<Product> obterProdutosEmEstoque() {
        return productRepository.findByQuantidadeGreaterThan(0);
    }
    
    /**
     * Cria um novo produto.
     * 
     * @param product objeto Product a ser criado
     * @return produto criado
     */
    public Product criar(Product product) {
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
        
        throw new RuntimeException("Produto não encontrado");
    }
    
    /**
     * Deleta um produto pelo ID.
     * 
     * @param id identificador do produto
     */
    public void deletar(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
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
        Optional<Product> product = productRepository.findById(id);
        
        if (product.isPresent()) {
            Product p = product.get();
            if (p.getQuantidade() >= quantidade) {
                p.setQuantidade(p.getQuantidade() - quantidade);
                productRepository.save(p);
                return true;
            }
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

        Optional<Product> product = productRepository.findById(id);
        
        if (product.isPresent()) {
            Product p = product.get();
            p.setQuantidade(p.getQuantidade() + quantidade);
            productRepository.save(p);
        } else {
            throw new RuntimeException("Produto não encontrado");
        }
    }
}
