/**
 * Serviço de API para comunicação com o backend.
 * 
 * Centraliza todas as requisições HTTP para o servidor,
 * fornecendo métodos para operar com Produtos, Usuários e Pedidos.
 */

import axios from 'axios';

// Configuração base da API
const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

// =====================================================
// PRODUTOS
// =====================================================

/**
 * Obtém todos os produtos do catálogo.
 * 
 * @returns {Promise} Lista de produtos
 */
export const obterProdutos = () => {
    return api.get('/produtos');
};

/**
 * Obtém um produto específico pelo ID.
 * 
 * @param {number} id - ID do produto
 * @returns {Promise} Dados do produto
 */
export const obterProduto = (id) => {
    return api.get(`/produtos/${id}`);
};

/**
 * Busca produtos pelo nome.
 * 
 * @param {string} nome - Termo de busca
 * @returns {Promise} Lista de produtos encontrados
 */
export const buscarProdutosPorNome = (nome) => {
    return api.get('/produtos/buscar/nome', { params: { nome } });
};

/**
 * Busca produtos por categoria.
 * 
 * @param {string} categoria - Nome da categoria
 * @returns {Promise} Lista de produtos da categoria
 */
export const buscarProdutosPorCategoria = (categoria) => {
    return api.get('/produtos/buscar/categoria', { params: { categoria } });
};

/**
 * Obtém apenas produtos em estoque.
 * 
 * @returns {Promise} Lista de produtos disponíveis
 */
export const obterProdutosEmEstoque = () => {
    return api.get('/produtos/estoque/disponiveis');
};

/**
 * Cria um novo produto.
 * 
 * @param {object} produtoData - Dados do novo produto
 * @returns {Promise} Produto criado
 */
export const criarProduto = (produtoData) => {
    return api.post('/produtos', produtoData);
};

/**
 * Atualiza um produto existente.
 * 
 * @param {number} id - ID do produto
 * @param {object} produtoData - Dados atualizados
 * @returns {Promise} Produto atualizado
 */
export const atualizarProduto = (id, produtoData) => {
    return api.put(`/produtos/${id}`, produtoData);
};

/**
 * Deleta um produto.
 * 
 * @param {number} id - ID do produto
 * @returns {Promise} Resposta da deleção
 */
export const deletarProduto = (id) => {
    return api.delete(`/produtos/${id}`);
};

/**
 * Reduz o estoque de um produto.
 * 
 * @param {number} id - ID do produto
 * @param {number} quantidade - Quantidade a reduzir
 * @returns {Promise} Resposta da operação
 */
export const reduzirEstoque = (id, quantidade) => {
    return api.patch(`/produtos/${id}/estoque`, null, { params: { quantidade } });
};

// =====================================================
// USUÁRIOS
// =====================================================

/**
 * Obtém todos os usuários.
 * 
 * @returns {Promise} Lista de usuários
 */
export const obterUsuarios = () => {
    return api.get('/usuarios');
};

/**
 * Obtém um usuário pelo ID.
 * 
 * @param {number} id - ID do usuário
 * @returns {Promise} Dados do usuário
 */
export const obterUsuario = (id) => {
    return api.get(`/usuarios/${id}`);
};

/**
 * Busca usuário por email.
 * 
 * @param {string} email - Email do usuário
 * @returns {Promise} Dados do usuário
 */
export const buscarUsuarioPorEmail = (email) => {
    return api.get(`/usuarios/email/${email}`);
};

/**
 * Busca usuários por nome.
 * 
 * @param {string} nome - Termo de busca
 * @returns {Promise} Lista de usuários encontrados
 */
export const buscarUsuariosPorNome = (nome) => {
    return api.get('/usuarios/buscar/nome', { params: { nome } });
};

/**
 * Obtém todos os clientes.
 * 
 * @returns {Promise} Lista de clientes
 */
export const obterClientes = () => {
    return api.get('/usuarios/tipo/clientes');
};

/**
 * Obtém todos os administradores.
 * 
 * @returns {Promise} Lista de admins
 */
export const obterAdministradores = () => {
    return api.get('/usuarios/tipo/admin');
};

/**
 * Cria um novo usuário.
 * 
 * @param {object} usuarioData - Dados do novo usuário
 * @returns {Promise} Usuário criado
 */
export const criarUsuario = (usuarioData) => {
    return api.post('/usuarios', usuarioData);
};

/**
 * Atualiza um usuário.
 * 
 * @param {number} id - ID do usuário
 * @param {object} usuarioData - Dados atualizados
 * @returns {Promise} Usuário atualizado
 */
export const atualizarUsuario = (id, usuarioData) => {
    return api.put(`/usuarios/${id}`, usuarioData);
};

/**
 * Deleta um usuário.
 * 
 * @param {number} id - ID do usuário
 * @returns {Promise} Resposta da deleção
 */
export const deletarUsuario = (id) => {
    return api.delete(`/usuarios/${id}`);
};

/**
 * Ativa um usuário.
 * 
 * @param {number} id - ID do usuário
 * @returns {Promise} Resposta da ativação
 */
export const ativarUsuario = (id) => {
    return api.patch(`/usuarios/${id}/ativar`);
};

/**
 * Desativa um usuário.
 * 
 * @param {number} id - ID do usuário
 * @returns {Promise} Resposta da desativação
 */
export const desativarUsuario = (id) => {
    return api.patch(`/usuarios/${id}/desativar`);
};

// =====================================================
// PEDIDOS
// =====================================================

/**
 * Obtém todos os pedidos.
 * 
 * @returns {Promise} Lista de pedidos
 */
export const obterPedidos = () => {
    return api.get('/pedidos');
};

/**
 * Obtém um pedido pelo ID.
 * 
 * @param {number} id - ID do pedido
 * @returns {Promise} Dados do pedido
 */
export const obterPedido = (id) => {
    return api.get(`/pedidos/${id}`);
};

/**
 * Obtém pedidos de um usuário.
 * 
 * @param {number} usuarioId - ID do usuário
 * @returns {Promise} Lista de pedidos do usuário
 */
export const obterPedidosPorUsuario = (usuarioId) => {
    return api.get(`/pedidos/usuario/${usuarioId}`);
};

/**
 * Obtém pedidos por status.
 * 
 * @param {string} status - Status dos pedidos
 * @returns {Promise} Lista de pedidos com o status
 */
export const obterPedidosPorStatus = (status) => {
    return api.get(`/pedidos/status/${status}`);
};

/**
 * Cria um novo pedido.
 * 
 * @param {object} pedidoData - Dados do novo pedido
 * @returns {Promise} Pedido criado
 */
export const criarPedido = (pedidoData) => {
    return api.post('/pedidos', pedidoData);
};

/**
 * Atualiza um pedido.
 * 
 * @param {number} id - ID do pedido
 * @param {object} pedidoData - Dados atualizados
 * @returns {Promise} Pedido atualizado
 */
export const atualizarPedido = (id, pedidoData) => {
    return api.put(`/pedidos/${id}`, pedidoData);
};

/**
 * Deleta um pedido.
 * 
 * @param {number} id - ID do pedido
 * @returns {Promise} Resposta da deleção
 */
export const deletarPedido = (id) => {
    return api.delete(`/pedidos/${id}`);
};

/**
 * Altera o status de um pedido.
 * 
 * @param {number} id - ID do pedido
 * @param {string} status - Novo status
 * @returns {Promise} Pedido atualizado
 */
export const alterarStatusPedido = (id, status) => {
    return api.patch(`/pedidos/${id}/status`, null, { params: { status } });
};

/**
 * Adiciona item ao pedido.
 * 
 * @param {number} pedidoId - ID do pedido
 * @param {object} itemData - Dados do item
 * @returns {Promise} Item adicionado
 */
export const adicionarItemAoPedido = (pedidoId, itemData) => {
    return api.post(`/pedidos/${pedidoId}/itens`, itemData);
};

/**
 * Remove item do pedido.
 * 
 * @param {number} itemId - ID do item
 * @returns {Promise} Resposta da remoção
 */
export const removerItemDoPedido = (itemId) => {
    return api.delete(`/pedidos/itens/${itemId}`);
};

/**
 * Obtém itens de um pedido.
 * 
 * @param {number} pedidoId - ID do pedido
 * @returns {Promise} Lista de itens do pedido
 */
export const obterItensPedido = (pedidoId) => {
    return api.get(`/pedidos/${pedidoId}/itens`);
};

export default api;
