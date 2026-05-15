package com.marketplace.api.service;

import com.marketplace.api.model.User;
import com.marketplace.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Serviço para operações relacionadas a Usuários.
 * 
 * Gerencia a lógica de negócios para operações CRUD de usuários,
 * autenticação e validações.
 * 
 * @author Marketplace Team
 * @version 1.0
 */
@Service
@Slf4j
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Obtém todos os usuários.
     * 
     * @return lista de todos os usuários
     */
    public List<User> obterTodos() {
        log.info("Obtendo todos os usuários");
        return userRepository.findAll();
    }
    
    /**
     * Obtém um usuário pelo ID.
     * 
     * @param id identificador do usuário
     * @return Optional contendo o usuário se encontrado
     */
    public Optional<User> obterPorId(Long id) {
        log.info("Obtendo usuário com ID: {}", id);
        return userRepository.findById(id);
    }
    
    /**
     * Busca um usuário pelo email.
     * 
     * @param email email do usuário
     * @return Optional contendo o usuário se encontrado
     */
    public Optional<User> buscarPorEmail(String email) {
        log.info("Buscando usuário com email: {}", email);
        return userRepository.findByEmail(email);
    }
    
    /**
     * Busca usuários pelo nome.
     * 
     * @param nome termo de busca
     * @return lista de usuários encontrados
     */
    public List<User> buscarPorNome(String nome) {
        log.info("Buscando usuários com nome: {}", nome);
        return userRepository.findByNomeIgnoreCaseContaining(nome);
    }
    
    /**
     * Obtém todos os clientes.
     * 
     * @return lista de usuários com tipo CLIENTE
     */
    public List<User> obterClientes() {
        log.info("Obtendo todos os clientes");
        return userRepository.findByTipo("CLIENTE");
    }
    
    /**
     * Obtém todos os administradores.
     * 
     * @return lista de usuários com tipo ADMIN
     */
    public List<User> obterAdministradores() {
        log.info("Obtendo todos os administradores");
        return userRepository.findByTipo("ADMIN");
    }
    
    /**
     * Cria um novo usuário.
     * 
     * @param user objeto User a ser criado
     * @return usuário criado
     * @throws RuntimeException se email já existe
     */
    public User criar(User user) {
        log.info("Criando novo usuário: {}", user.getEmail());
        
        if (userRepository.existsByEmail(user.getEmail())) {
            log.warn("Email já existe: {}", user.getEmail());
            throw new RuntimeException("Email já cadastrado");
        }
        
        return userRepository.save(user);
    }
    
    /**
     * Atualiza um usuário existente.
     * 
     * @param id identificador do usuário
     * @param user dados atualizados do usuário
     * @return usuário atualizado
     */
    public User atualizar(Long id, User user) {
        log.info("Atualizando usuário com ID: {}", id);
        Optional<User> existente = userRepository.findById(id);
        
        if (existente.isPresent()) {
            User u = existente.get();
            u.setNome(user.getNome());
            u.setTelefone(user.getTelefone());
            u.setEndereco(user.getEndereco());
            u.setStatus(user.getStatus());
            // Nota: email e senha não devem ser atualizados por este método
            return userRepository.save(u);
        }
        
        log.warn("Usuário com ID {} não encontrado", id);
        throw new RuntimeException("Usuário não encontrado");
    }
    
    /**
     * Deleta um usuário pelo ID.
     * 
     * @param id identificador do usuário
     */
    public void deletar(Long id) {
        log.info("Deletando usuário com ID: {}", id);
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            log.warn("Usuário com ID {} não encontrado", id);
            throw new RuntimeException("Usuário não encontrado");
        }
    }
    
    /**
     * Ativa um usuário.
     * 
     * @param id identificador do usuário
     */
    public void ativar(Long id) {
        log.info("Ativando usuário com ID: {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            u.setStatus("ATIVO");
            userRepository.save(u);
        } else {
            log.warn("Usuário com ID {} não encontrado", id);
            throw new RuntimeException("Usuário não encontrado");
        }
    }
    
    /**
     * Desativa um usuário.
     * 
     * @param id identificador do usuário
     */
    public void desativar(Long id) {
        log.info("Desativando usuário com ID: {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            u.setStatus("INATIVO");
            userRepository.save(u);
        } else {
            log.warn("Usuário com ID {} não encontrado", id);
            throw new RuntimeException("Usuário não encontrado");
        }
    }
    
    /**
     * Valida credenciais de um usuário.
     * 
     * @param email email do usuário
     * @param senha senha do usuário
     * @return true se credenciais válidas, false caso contrário
     */
    public boolean validarCredenciais(String email, String senha) {
        log.info("Validando credenciais para: {}", email);
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && user.get().getSenha().equals(senha);
    }
}
