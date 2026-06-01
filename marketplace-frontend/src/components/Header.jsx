/**
 * Componente Header
 * 
 * Exibe o cabeçalho da aplicação com navegação e informações.
 */

import React, { useState, useEffect } from 'react';
import { obterQuantidadeItens } from '../services/cartService';
import './Header.css';

/**
 * Componente de cabeçalho com navegação.
 * 
 * @returns {JSX.Element} Header renderizado
 */
export const Header = ({ onNavigate }) => {
    const [quantidadeCarrinho, setQuantidadeCarrinho] = useState(0);

    useEffect(() => {
        // Atualizar quantidade do carrinho
        const quantidade = obterQuantidadeItens();
        setQuantidadeCarrinho(quantidade);

        // Listener para mudanças no localStorage
        const handleStorageChange = () => {
            setQuantidadeCarrinho(obterQuantidadeItens());
        };

        window.addEventListener('storage', handleStorageChange);
        // Custom event para atualizações locais
        window.addEventListener('carrinhoAtualizado', handleStorageChange);

        return () => {
            window.removeEventListener('storage', handleStorageChange);
            window.removeEventListener('carrinhoAtualizado', handleStorageChange);
        };
    }, []);

    const handleNavigate = (page) => {
        if (onNavigate) {
            onNavigate(page);
        }
    };

    return (
        <header className="header">
            <div className="container">
                <div className="header-content">
                    <div className="logo">
                        <h1>🛒 Marketplace</h1>
                        <p className="subtitle">Vendas de Produtos de Mercado</p>
                    </div>
                    
                    <nav className="nav">
                        <ul>
                            <li><a href="#" onClick={(e) => { e.preventDefault(); handleNavigate('home'); }}>Início</a></li>
                            <li><a href="#" onClick={(e) => { e.preventDefault(); handleNavigate('produtos'); }}>Produtos</a></li>
                            <li className="carrinho-nav">
                                <a 
                                    href="#" 
                                    onClick={(e) => { e.preventDefault(); handleNavigate('carrinho'); }}
                                    className="carrinho-link"
                                >
                                    🛒 Carrinho
                                    {quantidadeCarrinho > 0 && (
                                        <span className="badge">{quantidadeCarrinho}</span>
                                    )}
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </header>
    );
};

export default Header;
