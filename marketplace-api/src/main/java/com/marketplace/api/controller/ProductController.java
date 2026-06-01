package com.marketplace.api.controller;

import com.marketplace.api.model.Product;
import com.marketplace.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gerenciar operações de Produtos.
 * 
 * Endpoints para criar, ler, atualizar e deletar produtos.
 * Base URL: /api/produtos
 * 
 * @author Marketplace Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    /**
     * GET /api/produtos - Obtém todos os produtos.
     * 
     * @return lista de todos os produtos com status 200
     */
    @GetMapping
    public ResponseEntity<List<Product>> obterTodos() {
        List<Product> produtos = productService.obterTodos();
        return ResponseEntity.ok(produtos);
    }
    
    /**
     * GET /api/produtos/{id} - Obtém um produto pelo ID.
     * 
     * @param id identificador do produto
     * @return produto encontrado com status 200, ou 404 se não encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obterPorId(@PathVariable Long id) {
        Optional<Product> product = productService.obterPorId(id);
        
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Produto não encontrado");
    }
    
    /**
     * GET /api/produtos/buscar/nome?nome=termo - Busca produtos por nome.
     * 
     * @param nome termo de busca
     * @return lista de produtos encontrados
     */
    @GetMapping("/buscar/nome")
    public ResponseEntity<List<Product>> buscarPorNome(@RequestParam String nome) {
        List<Product> produtos = productService.buscarPorNome(nome);
        return ResponseEntity.ok(produtos);
    }
    
    /**
     * GET /api/produtos/buscar/categoria?categoria=nome - Busca produtos por categoria.
     * 
     * @param categoria nome da categoria
     * @return lista de produtos da categoria
     */
    @GetMapping("/buscar/categoria")
    public ResponseEntity<List<Product>> buscarPorCategoria(@RequestParam String categoria) {
        List<Product> produtos = productService.buscarPorCategoria(categoria);
        return ResponseEntity.ok(produtos);
    }
    
    /**
     * GET /api/produtos/estoque/disponiveis - Obtém produtos em estoque.
     * 
     * @return lista de produtos com quantidade > 0
     */
    @GetMapping("/estoque/disponiveis")
    public ResponseEntity<List<Product>> obterProdutosEmEstoque() {
        List<Product> produtos = productService.obterProdutosEmEstoque();
        return ResponseEntity.ok(produtos);
    }
    
    /**
     * POST /api/produtos - Cria um novo produto.
     * 
     * @param product dados do novo produto
     * @return produto criado com status 201
     */
    @PostMapping
    public ResponseEntity<Product> criar(@Valid @RequestBody Product product) {
        Product created = productService.criar(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    /**
     * PUT /api/produtos/{id} - Atualiza um produto.
     * 
     * @param id identificador do produto
     * @param product dados atualizados
     * @return produto atualizado com status 200
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Product product) {
        try {
            Product updated = productService.atualizar(id, product);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro: " + e.getMessage());
        }
    }
    
    /**
     * DELETE /api/produtos/{id} - Deleta um produto.
     * 
     * @param id identificador do produto
     * @return status 204 se sucesso, 404 se não encontrado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            productService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro: " + e.getMessage());
        }
    }
    
    /**
     * PATCH /api/produtos/{id}/estoque?quantidade=valor - Reduz estoque.
     * 
     * @param id identificador do produto
     * @param quantidade quantidade a reduzir
     * @return status 200 se sucesso, 400 se estoque insuficiente
     */
    @PatchMapping("/{id}/estoque")
    public ResponseEntity<?> reduzirEstoque(
            @PathVariable Long id,
            @RequestParam Integer quantidade) {
        
        if (productService.reduzirEstoque(id, quantidade)) {
            return ResponseEntity.ok("Estoque reduzido com sucesso");
        }
        return ResponseEntity.badRequest()
                .body("Quantidade insuficiente em estoque");
    }
}
