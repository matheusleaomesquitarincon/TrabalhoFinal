/**
 * Serviço de Carrinho
 * 
 * Gerencia o carrinho de compras usando localStorage e API
 */

const CART_KEY = 'marketplace_cart';

/**
 * Obtém o carrinho armazenado no localStorage
 */
export const obterCarrinho = () => {
    try {
        const cart = localStorage.getItem(CART_KEY);
        return cart ? JSON.parse(cart) : [];
    } catch (error) {
        console.error('Erro ao obter carrinho:', error);
        return [];
    }
};

/**
 * Salva o carrinho no localStorage
 */
export const salvarCarrinho = (carrinho) => {
    try {
        localStorage.setItem(CART_KEY, JSON.stringify(carrinho));
    } catch (error) {
        console.error('Erro ao salvar carrinho:', error);
    }
};

/**
 * Adiciona um produto ao carrinho
 */
export const adicionarAoCarrinho = (produto, quantidade = 1) => {
    const carrinho = obterCarrinho();
    
    // Verificar se o produto já está no carrinho
    const itemExistente = carrinho.find(item => item.id === produto.id);
    
    if (itemExistente) {
        itemExistente.quantidade += quantidade;
    } else {
        carrinho.push({
            id: produto.id,
            nome: produto.nome,
            preco: produto.preco,
            imagem: produto.imagem,
            quantidade: quantidade
        });
    }
    
    salvarCarrinho(carrinho);
    return carrinho;
};

/**
 * Remove um produto do carrinho
 */
export const removerDoCarrinho = (produtoId) => {
    const carrinho = obterCarrinho();
    const novoCarrinho = carrinho.filter(item => item.id !== produtoId);
    salvarCarrinho(novoCarrinho);
    return novoCarrinho;
};

/**
 * Atualiza a quantidade de um produto no carrinho
 */
export const atualizarQuantidade = (produtoId, quantidade) => {
    const carrinho = obterCarrinho();
    const item = carrinho.find(item => item.id === produtoId);
    
    if (item) {
        if (quantidade <= 0) {
            return removerDoCarrinho(produtoId);
        }
        item.quantidade = quantidade;
        salvarCarrinho(carrinho);
    }
    
    return carrinho;
};

/**
 * Limpa o carrinho
 */
export const limparCarrinho = () => {
    localStorage.removeItem(CART_KEY);
    return [];
};

/**
 * Calcula o total do carrinho
 */
export const calcularTotal = () => {
    const carrinho = obterCarrinho();
    return carrinho.reduce((total, item) => {
        return total + (parseFloat(item.preco) * item.quantidade);
    }, 0);
};

/**
 * Obtém o número de itens no carrinho
 */
export const obterQuantidadeItens = () => {
    const carrinho = obterCarrinho();
    return carrinho.reduce((total, item) => total + item.quantidade, 0);
};
