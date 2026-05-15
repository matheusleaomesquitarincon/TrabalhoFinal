/**
 * Componente Header
 * 
 * Exibe o cabeçalho da aplicação com navegação e informações.
 */

import React from 'react';
import './Header.css';

/**
 * Componente de cabeçalho com navegação.
 * 
 * @returns {JSX.Element} Header renderizado
 */
export const Header = ({ usuarioLogado, onLogout }) => {
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
                            <li><a href="/">Início</a></li>
                            <li><a href="/produtos">Produtos</a></li>
                            <li><a href="/categorias">Categorias</a></li>
                            {usuarioLogado && (
                                <>
                                    <li><a href="/meus-pedidos">Meus Pedidos</a></li>
                                    <li><a href="/perfil">Perfil</a></li>
                                    <li>
                                        <button 
                                            className="btn-logout"
                                            onClick={onLogout}
                                        >
                                            Sair
                                        </button>
                                    </li>
                                </>
                            )}
                            {!usuarioLogado && (
                                <>
                                    <li><a href="/login" className="btn btn-primary">Login</a></li>
                                    <li><a href="/registro" className="btn btn-secondary">Registrar</a></li>
                                </>
                            )}
                        </ul>
                    </nav>
                </div>
            </div>
        </header>
    );
};

export default Header;
