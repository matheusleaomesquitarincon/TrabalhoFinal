package com.marketplace.api.config;

import com.marketplace.api.model.Product;
import com.marketplace.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;

/**
 * Carregador de dados iniciais para o banco de dados.
 * 
 * Popula o banco de dados com produtos de exemplo ao iniciar a aplicação.
 * Executa apenas uma vez e depois verifica se os dados já existem.
 * 
 * @author Marketplace Team
 * @version 1.0
 */
@Configuration
public class DataLoader implements CommandLineRunner {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Verifica se já existem produtos no banco
        if (productRepository.count() > 0) {
            return;
        }
        
        // Criar produtos de exemplo
        Product[] productsData = {
            criarProduto("Maçã Gala", "Maçã fresca e suculenta, ideal para consumo direto", "4.50", 50, "Frutas"),
            criarProduto("Tomate Caqui", "Tomate selecionado, perfeito para saladas e molhos", "5.99", 40, "Legumes"),
            criarProduto("Banana Nanica", "Banana amarela, doce e nutritiva", "3.50", 60, "Frutas"),
            criarProduto("Cenoura Orgânica", "Cenoura fresca e crocante, sem agrotóxicos", "6.00", 35, "Legumes"),
            criarProduto("Morango Fresquinho", "Morangos vermelhos, maduros e saborosos", "12.00", 25, "Frutas"),
            criarProduto("Alface Crespa", "Alface folhuda, perfeita para saladas", "4.00", 30, "Legumes"),
            criarProduto("Laranja Pêra", "Laranja suculenta, ótima para suco", "5.50", 45, "Frutas"),
            criarProduto("Melancia Vermelha", "Melancia doce e refrescante, perfeita para o verão", "25.00", 15, "Frutas"),
            criarProduto("Abobora Cabotiá", "Abóbora nutritiva, ótima para vários pratos", "8.50", 20, "Legumes"),
            criarProduto("Frango Peito Sem Pele", "Peito de frango fresco, resfriado", "18.00", 50, "Carnes")
        };
        
        // Salvar todos os produtos
        for (Product product : productsData) {
            try {
                productRepository.save(product);
            } catch (Exception e) {
                // Erro ao salvar produto
            }
        }
    }
    
    /**
     * Cria um novo produto com valores padrão.
     * 
     * @param nome nome do produto
     * @param descricao descrição do produto
     * @param preco preço em formato string
     * @param quantidade quantidade em estoque
     * @param categoria categoria do produto
     * @return produto criado
     */
    private Product criarProduto(String nome, String descricao, String preco, Integer quantidade, String categoria) {
        Product product = new Product();
        product.setNome(nome);
        product.setDescricao(descricao);
        product.setPreco(new BigDecimal(preco));
        product.setQuantidade(quantidade);
        product.setCategoria(categoria);
        product.setImagem("https://via.placeholder.com/300x200?text=" + nome.replace(" ", "+"));
        return product;
    }
}
