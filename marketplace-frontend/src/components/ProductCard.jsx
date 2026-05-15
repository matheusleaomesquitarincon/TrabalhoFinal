/**
 * Componente ProductCard
 * 
 * Exibe um cartão com informações de um produto.
 */

import React from 'react';
import './ProductCard.css';

/**
 * Componente de cartão de produto.
 * 
 * @param {object} product - Dados do produto
 * @param {function} onAddToCart - Callback ao adicionar ao carrinho
 * @returns {JSX.Element} Cartão do produto
 */
export const ProductCard = ({ product, onAddToCart }) => {
    const temEstoque = product.quantidade > 0;
    
    return (
        <div className="product-card">
            <div className="product-image">
                {product.imagem ? (
                    <img src={product.imagem} alt={product.nome} />
                ) : (
                    <div className="placeholder">📦</div>
                )}
            </div>
            
            <div className="product-info">
                <h3 className="product-name">{product.nome}</h3>
                
                <p className="product-description">
                    {product.descricao ? product.descricao.substring(0, 100) + '...' : 'Sem descrição'}
                </p>
                
                <div className="product-category">
                    <span className="badge">{product.categoria || 'Sem categoria'}</span>
                </div>
                
                <div className="product-footer">
                    <div className="price">
                        <span className="currency">R$</span>
                        <span className="amount">{parseFloat(product.preco).toFixed(2)}</span>
                    </div>
                    
                    <div className="stock">
                        {temEstoque ? (
                            <span className="in-stock">✓ Em estoque ({product.quantidade})</span>
                        ) : (
                            <span className="out-of-stock">✕ Fora de estoque</span>
                        )}
                    </div>
                </div>
                
                <button
                    className={`btn-add-cart ${!temEstoque ? 'disabled' : ''}`}
                    onClick={() => onAddToCart(product)}
                    disabled={!temEstoque}
                >
                    {temEstoque ? '🛒 Adicionar ao Carrinho' : 'Indisponível'}
                </button>
            </div>
        </div>
    );
};

export default ProductCard;
