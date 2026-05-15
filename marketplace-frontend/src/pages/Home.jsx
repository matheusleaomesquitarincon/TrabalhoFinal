/**
 * Página Principal - Home
 * 
 * Exibe produtos em destaque e informações sobre o marketplace.
 */

import React, { useState, useEffect } from 'react';
import { obterProdutos } from '../services/api';
import ProductCard from '../components/ProductCard';
import './Home.css';

/**
 * Componente da página inicial.
 * 
 * @returns {JSX.Element} Página inicial
 */
export const Home = () => {
    const [produtos, setProdutos] = useState([]);
    const [carregando, setCarregando] = useState(true);
    const [erro, setErro] = useState(null);
    
    useEffect(() => {
        carregarProdutos();
    }, []);
    
    /**
     * Carrega produtos do servidor.
     */
    const carregarProdutos = async () => {
        try {
            setCarregando(true);
            const response = await obterProdutos();
            setProdutos(response.data);
            setErro(null);
        } catch (err) {
            console.error('Erro ao carregar produtos:', err);
            setErro('Erro ao carregar produtos. Tente novamente mais tarde.');
            setProdutos([]);
        } finally {
            setCarregando(false);
        }
    };
    
    /**
     * Manipula adição de produto ao carrinho.
     * 
     * @param {object} product - Produto a adicionar
     */
    const handleAddToCart = (product) => {
        console.log('Adicionado ao carrinho:', product);
        alert(`${product.nome} adicionado ao carrinho!`);
    };
    
    return (
        <div className="home">
            <section className="hero">
                <div className="container">
                    <h1>Bem-vindo ao Marketplace</h1>
                    <p>Encontre os melhores produtos de mercado com preços incríveis</p>
                </div>
            </section>
            
            <section className="products-section">
                <div className="container">
                    <h2>Nossos Produtos</h2>
                    
                    {carregando && (
                        <div className="loading">Carregando produtos...</div>
                    )}
                    
                    {erro && (
                        <div className="alert alert-danger">{erro}</div>
                    )}
                    
                    {!carregando && produtos.length === 0 && (
                        <div className="alert alert-info">Nenhum produto disponível no momento.</div>
                    )}
                    
                    {!carregando && produtos.length > 0 && (
                        <div className="products-grid">
                            {produtos.map((product) => (
                                <ProductCard
                                    key={product.id}
                                    product={product}
                                    onAddToCart={handleAddToCart}
                                />
                            ))}
                        </div>
                    )}
                </div>
            </section>
            
            <section className="benefits">
                <div className="container">
                    <h2>Por que nos escolher?</h2>
                    <div className="benefits-grid">
                        <div className="benefit-card">
                            <span className="benefit-icon">🚚</span>
                            <h3>Entrega Rápida</h3>
                            <p>Enviamos seus produtos rapidamente para sua casa.</p>
                        </div>
                        <div className="benefit-card">
                            <span className="benefit-icon">💰</span>
                            <h3>Preços Justos</h3>
                            <p>Os melhores preços do mercado em produtos de qualidade.</p>
                        </div>
                        <div className="benefit-card">
                            <span className="benefit-icon">✓</span>
                            <h3>Garantia</h3>
                            <p>Todos os produtos vêm com garantia de satisfação.</p>
                        </div>
                        <div className="benefit-card">
                            <span className="benefit-icon">📞</span>
                            <h3>Suporte 24/7</h3>
                            <p>Estamos sempre à disposição para ajudar você.</p>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    );
};

export default Home;
