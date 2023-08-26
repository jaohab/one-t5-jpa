package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.alura.loja.modelo.Produto;

public class ProdutoDao {

    private EntityManager em;

    public ProdutoDao (EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Produto produto) {
        this.em.persist(produto);
    }

    public void atualizar(Produto produto) {
        this.em.merge(produto);
    }

    public void excluir(Produto produto) {
        produto = em.merge(produto);
        this.em.remove(produto);
    }

    public Produto buscarPorId(Long id) {
        return em.find(Produto.class, id);
    }

    public List<Produto> buscarTodos() {
        String jpql = "SELECT p FROM Produto p";
        return em.createQuery(jpql, Produto.class).getResultList();
    }

    public List<Produto> buscarPorNome(String nome) {
        String jpql = "SELECT p FROM Produto p WHERE p.nome = ?1";
        return em.createQuery(jpql, Produto.class)
                .setParameter(1, nome)
                .getResultList();
    }

    public List<Produto> buscarPorNomeDaCategoria(String nome) {
        // String jpql = "SELECT p FROM Produto p WHERE p.categoria.nome = ?1";
        // return em.createQuery(jpql, Produto.class)
        return em.createNamedQuery("Produto.produtosPorCategoria", Produto.class)
                .setParameter(1, nome)
                .getResultList();
    }

    public BigDecimal buscarPrecoComNome(String nome) {
        String jpql = "SELECT p.preco FROM Produto p WHERE p.categoria.nome = ?1";
        return em.createQuery(jpql, BigDecimal.class)
                .setParameter(1, nome)
                .getSingleResult();
    }

    //Método GoHorse
    public List<Produto> BuscarPorParametros(String nome, BigDecimal preco, LocalDate dataCadastro) {
        String jpql = "SELECT p FROM Produto p WHERE 1=1 ";

        if (nome != null && !nome.trim().isEmpty()) {
            jpql += "AND p.nome = ?1 ";
        }
        if (preco != null) {
            jpql += "AND p.preco = ?2 ";
        }
        if (dataCadastro != null) {
            jpql += "AND p.dataCadastro = ?3 ";
        }

        TypedQuery<Produto> query = em.createQuery(jpql, Produto.class);

        if (nome != null && !nome.trim().isEmpty()) {
            query.setParameter(1, nome);
        }
        if (preco != null) {
            query.setParameter(2, preco);
        }
        if (dataCadastro != null) {
            query.setParameter(3, dataCadastro);
        }

        return query.getResultList();
    }

    //Método Criteria API
    public List<Produto> BuscarPorParametrosCriteria(String nome, BigDecimal preco, LocalDate dataCadastro) {
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
        
        Root<Produto> from = query.from(Produto.class);
        Predicate filtros = builder.and();

        if (nome != null && !nome.trim().isEmpty()) {
            filtros = builder.and(filtros, builder.equal(from.get("nome"), nome));
        }
        if (preco != null) {
            filtros = builder.and(filtros, builder.equal(from.get("preco"), preco));
        }
        if (dataCadastro != null) {
            filtros = builder.and(filtros, builder.equal(from.get("dataCadastro"), dataCadastro));
        }

        query.where(filtros);

        return em.createQuery(query).getResultList();
    }

}
