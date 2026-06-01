/**
 * Página de Produtos
 * 
 * Exibe todos os produtos com opções de busca e filtro.
 */

import React, { useState, useEffect } from 'react';
import {
    obterProdutos,
    buscarProdutosPorNome,
    buscarProdutosPorCategoria
} from '../services/api';
import { adicionarAoCarrinho } from '../services/cartService';
import ProductCard from '../components/ProductCard';
import './Produtos.css';

/**
 * Componente da página de produtos.
 * 
 * @returns {JSX.Element} Página de produtos
 */
export const Produtos = () => {
    const [produtos, setProdutos] = useState([]);
    const [carregando, setCarregando] = useState(true);
    const [erro, setErro] = useState(null);
    const [busca, setBusca] = useState('');
    const [categoria, setCategoria] = useState('');
    const [categorias, setCategorias] = useState([]);
    
    useEffect(() => {
        carregarProdutos();
    }, []);
    
    /**
     * Carrega todos os produtos e extrai categorias.
     */
    const carregarProdutos = async () => {
        try {
            setCarregando(true);
            const response = await obterProdutos();
            setProdutos(response.data);
            
            // Extrair categorias únicas
            const cats = [...new Set(response.data.map(p => p.categoria).filter(Boolean))];
            setCategorias(cats);
            
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
     * Filtra produtos por nome.
     */
    const handleBusca = async (e) => {
        e.preventDefault();
        if (!busca.trim()) {
            carregarProdutos();
            return;
        }
        
        try {
            setCarregando(true);
            const response = await buscarProdutosPorNome(busca);
            setProdutos(response.data);
            setErro(null);
        } catch (err) {
            console.error('Erro na busca:', err);
            setErro('Erro ao buscar produtos.');
        } finally {
            setCarregando(false);
        }
    };
    
    /**
     * Filtra produtos por categoria.
     */
    const handleFiltroCategoria = async (cat) => {
        setCategoria(cat);
        if (!cat) {
            carregarProdutos();
            return;
        }
        
        try {
            setCarregando(true);
            const response = await buscarProdutosPorCategoria(cat);
            setProdutos(response.data);
            setErro(null);
        } catch (err) {
            console.error('Erro ao filtrar:', err);
            setErro('Erro ao filtrar produtos.');
        } finally {
            setCarregando(false);
        }
    };
    
    /**
     * Manipula adição de produto ao carrinho.
     */
    const handleAddToCart = (product) => {
        adicionarAoCarrinho(product, 1);
        // Disparar evento customizado para atualizar o badge do carrinho
        window.dispatchEvent(new Event('carrinhoAtualizado'));
        alert(`✓ ${product.nome} adicionado ao carrinho!`);
    };
    
    return (
        <div className="produtos">
            <div className="container">
                <h1>Nossos Produtos</h1>
                
                <div className="filters-section">
                    <form className="search-form" onSubmit={handleBusca}>
                        <input
                            type="text"
                            placeholder="Buscar produtos..."
                            value={busca}
                            onChange={(e) => setBusca(e.target.value)}
                        />
                        <button type="submit" className="btn btn-primary">
                            🔍 Buscar
                        </button>
                    </form>
                    
                    <div className="categories">
                        <button
                            className={`category-btn ${!categoria ? 'active' : ''}`}
                            onClick={() => handleFiltroCategoria('')}
                        >
                            Todas
                        </button>
                        {categorias.map((cat) => (
                            <button
                                key={cat}
                                className={`category-btn ${categoria === cat ? 'active' : ''}`}
                                onClick={() => handleFiltroCategoria(cat)}
                            >
                                {cat}
                            </button>
                        ))}
                    </div>
                </div>
                
                {carregando && (
                    <div className="loading">Carregando produtos...</div>
                )}
                
                {erro && (
                    <div className="alert alert-danger">{erro}</div>
                )}
                
                {!carregando && produtos.length === 0 && (
                    <div className="alert alert-info">
                        Nenhum produto encontrado. Tente outra busca ou categoria.
                    </div>
                )}
                
                {!carregando && produtos.length > 0 && (
                    <>
                        <p className="results-count">
                            {produtos.length} produto(s) encontrado(s)
                        </p>
                        <div className="products-grid">
                            {produtos.map((product) => (
                                <ProductCard
                                    key={product.id}
                                    product={product}
                                    onAddToCart={handleAddToCart}
                                />
                            ))}
                        </div>
                    </>
                )}
            </div>
        </div>
    );
};

export default Produtos;
