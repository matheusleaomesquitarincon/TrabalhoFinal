package com.marketplace.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Marketplace API.
 * 
 * Esta classe inicia a aplicação Spring Boot e ativa todos os
 * componentes como controllers, services e repositories.
 * 
 * Para iniciar: mvn spring-boot:run
 * 
 * @author Marketplace Team
 * @version 1.0
 */
@SpringBootApplication
public class MarketplaceApplication {
    
    /**
     * Método principal que inicia a aplicação.
     * 
     * @param args argumentos da linha de comando
     */
    public static void main(String[] args) {
        SpringApplication.run(MarketplaceApplication.class, args);
    }
}
