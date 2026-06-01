/**
 * Componente Principal da Aplicação
 * 
 * Gerencia o roteamento e estado global da aplicação.
 */

import React, { useState } from 'react';
import Header from './components/Header';
import Home from './pages/Home';
import Produtos from './pages/Produtos';
import Carrinho from './pages/Carrinho';
import './App.css';

/**
 * Componente raiz da aplicação.
 * 
 * @returns {JSX.Element} Aplicação completa
 */
function App() {
    const [currentPage, setCurrentPage] = useState('home');
    const [usuarioLogado, setUsuarioLogado] = useState(false);
    
    /**
     * Navega para uma página.
     * 
     * @param {string} page - Nome da página
     */
    const navigate = (page) => {
        setCurrentPage(page);
    };
    
    /**
     * Faz logout do usuário.
     */
    const handleLogout = () => {
        setUsuarioLogado(false);
        setCurrentPage('home');
    };
    
    /**
     * Renderiza a página atual.
     */
    const renderPage = () => {
        switch (currentPage) {
            case 'home':
                return <Home />;
            case 'produtos':
                return <Produtos />;
            case 'carrinho':
                return <Carrinho />;
            default:
                return <Home />;
        }
    };
    
    return (
        <div className="app">
            <Header 
                onNavigate={navigate}
            />
            
            <main className="main-content">
                {renderPage()}
            </main>
            
            <footer className="footer">
                <div className="container">
                    <p>&copy; 2024 Marketplace. Todos os direitos reservados.</p>
                    <p>Desenvolvido como trabalho final de web development</p>
                </div>
            </footer>
        </div>
    );
}

export default App;
