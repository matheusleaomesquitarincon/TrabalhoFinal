package com.marketplace.api.service;

import com.marketplace.api.model.User;
import com.marketplace.api.repository.UserRepository;
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
        return userRepository.findAll();
    }
    
    /**
     * Obtém um usuário pelo ID.
     * 
     * @param id identificador do usuário
     * @return Optional contendo o usuário se encontrado
     */
    public Optional<User> obterPorId(Long id) {
        return userRepository.findById(id);
    }
    
    /**
     * Busca um usuário pelo email.
     * 
     * @param email email do usuário
     * @return Optional contendo o usuário se encontrado
     */
    public Optional<User> buscarPorEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    /**
     * Busca usuários pelo nome.
     * 
     * @param nome termo de busca
     * @return lista de usuários encontrados
     */
    public List<User> buscarPorNome(String nome) {
        return userRepository.findByNomeIgnoreCaseContaining(nome);
    }
    
    /**
     * Obtém todos os clientes.
     * 
     * @return lista de usuários com tipo CLIENTE
     */
    public List<User> obterClientes() {
        return userRepository.findByTipo("CLIENTE");
    }
    
    /**
     * Obtém todos os administradores.
     * 
     * @return lista de usuários com tipo ADMIN
     */
    public List<User> obterAdministradores() {
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
        if (userRepository.existsByEmail(user.getEmail())) {
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
        
        throw new RuntimeException("Usuário não encontrado");
    }
    
    /**
     * Deleta um usuário pelo ID.
     * 
     * @param id identificador do usuário
     */
    public void deletar(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }
    
    /**
     * Ativa um usuário.
     * 
     * @param id identificador do usuário
     */
    public void ativar(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            u.setStatus("ATIVO");
            userRepository.save(u);
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }
    
    /**
     * Desativa um usuário.
     * 
     * @param id identificador do usuário
     */
    public void desativar(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            u.setStatus("INATIVO");
            userRepository.save(u);
        } else {
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
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && user.get().getSenha().equals(senha);
    }
}
