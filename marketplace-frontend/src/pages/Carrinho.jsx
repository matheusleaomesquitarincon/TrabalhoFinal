/**
 * Página de Carrinho de Compras
 * 
 * Exibe itens no carrinho e permite gerenciamento.
 */

import React, { useState, useEffect } from 'react';
import {
    obterCarrinho,
    removerDoCarrinho,
    atualizarQuantidade,
    limparCarrinho,
    calcularTotal
} from '../services/cartService';
import './Carrinho.css';

/**
 * Componente da página de carrinho.
 * 
 * @returns {JSX.Element} Página do carrinho
 */
export const Carrinho = () => {
    const [carrinho, setCarrinho] = useState([]);
    const [total, setTotal] = useState(0);

    useEffect(() => {
        carregarCarrinho();
    }, []);

    /**
     * Carrega os itens do carrinho
     */
    const carregarCarrinho = () => {
        const items = obterCarrinho();
        setCarrinho(items);
        atualizarTotal(items);
    };

    /**
     * Atualiza o total
     */
    const atualizarTotal = (items) => {
        const totalCalculado = items.reduce((acc, item) => {
            return acc + (parseFloat(item.preco) * item.quantidade);
        }, 0);
        setTotal(totalCalculado);
    };

    /**
     * Remove um item do carrinho
     */
    const handleRemover = (produtoId) => {
        const novoCarrinho = removerDoCarrinho(produtoId);
        setCarrinho(novoCarrinho);
        atualizarTotal(novoCarrinho);
    };

    /**
     * Atualiza a quantidade de um item
     */
    const handleAtualizarQuantidade = (produtoId, novaQuantidade) => {
        if (novaQuantidade <= 0) {
            handleRemover(produtoId);
            return;
        }
        
        const novoCarrinho = atualizarQuantidade(produtoId, novaQuantidade);
        setCarrinho(novoCarrinho);
        atualizarTotal(novoCarrinho);
    };

    /**
     * Limpa todo o carrinho
     */
    const handleLimparCarrinho = () => {
        if (window.confirm('Tem certeza que deseja limpar o carrinho?')) {
            limparCarrinho();
            setCarrinho([]);
            setTotal(0);
        }
    };

    /**
     * Finaliza a compra
     */
    const handleFinalizarCompra = () => {
        if (carrinho.length === 0) {
            alert('Seu carrinho está vazio!');
            return;
        }
        
        alert(`Compra no valor de R$ ${total.toFixed(2)} finalizada com sucesso!`);
        limparCarrinho();
        setCarrinho([]);
        setTotal(0);
    };

    if (carrinho.length === 0) {
        return (
            <div className="carrinho">
                <div className="container">
                    <h1>Carrinho de Compras</h1>
                    <div className="carrinho-vazio">
                        <p>🛒 Seu carrinho está vazio</p>
                        <p>Adicione produtos para começar suas compras!</p>
                        <a href="/produtos" className="btn btn-primary">
                            Continuar Comprando
                        </a>
                    </div>
                </div>
            </div>
        );
    }

    return (
        <div className="carrinho">
            <div className="container">
                <h1>Carrinho de Compras</h1>

                <div className="carrinho-content">
                    <div className="carrinho-items">
                        <table className="items-table">
                            <thead>
                                <tr>
                                    <th>Produto</th>
                                    <th>Preço Unitário</th>
                                    <th>Quantidade</th>
                                    <th>Subtotal</th>
                                    <th>Ação</th>
                                </tr>
                            </thead>
                            <tbody>
                                {carrinho.map((item) => (
                                    <tr key={item.id} className="carrinho-item">
                                        <td className="produto-info">
                                            {item.imagem && (
                                                <img 
                                                    src={item.imagem} 
                                                    alt={item.nome}
                                                    className="item-imagem"
                                                />
                                            )}
                                            <span>{item.nome}</span>
                                        </td>
                                        <td className="preco">
                                            R$ {parseFloat(item.preco).toFixed(2)}
                                        </td>
                                        <td className="quantidade">
                                            <div className="quantidade-control">
                                                <button
                                                    className="qty-btn"
                                                    onClick={() => handleAtualizarQuantidade(item.id, item.quantidade - 1)}
                                                >
                                                    −
                                                </button>
                                                <input
                                                    type="number"
                                                    min="1"
                                                    value={item.quantidade}
                                                    onChange={(e) => handleAtualizarQuantidade(item.id, parseInt(e.target.value) || 1)}
                                                    className="qty-input"
                                                />
                                                <button
                                                    className="qty-btn"
                                                    onClick={() => handleAtualizarQuantidade(item.id, item.quantidade + 1)}
                                                >
                                                    +
                                                </button>
                                            </div>
                                        </td>
                                        <td className="subtotal">
                                            R$ {(parseFloat(item.preco) * item.quantidade).toFixed(2)}
                                        </td>
                                        <td className="acoes">
                                            <button
                                                className="btn btn-danger"
                                                onClick={() => handleRemover(item.id)}
                                            >
                                                🗑️ Remover
                                            </button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>

                    <div className="carrinho-resume">
                        <div className="resume-card">
                            <h2>Resumo do Pedido</h2>
                            
                            <div className="resume-item">
                                <span>Total de Itens:</span>
                                <span className="valor">
                                    {carrinho.reduce((acc, item) => acc + item.quantidade, 0)}
                                </span>
                            </div>

                            <div className="resume-divider"></div>

                            <div className="resume-item total">
                                <span>Total:</span>
                                <span className="valor">R$ {total.toFixed(2)}</span>
                            </div>

                            <button
                                className="btn btn-success"
                                onClick={handleFinalizarCompra}
                            >
                                ✓ Finalizar Compra
                            </button>

                            <button
                                className="btn btn-secondary"
                                onClick={handleLimparCarrinho}
                            >
                                🔄 Limpar Carrinho
                            </button>

                            <a href="/produtos" className="btn btn-outline">
                                ← Continuar Comprando
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Carrinho;
