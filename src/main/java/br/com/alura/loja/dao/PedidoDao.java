package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

public class PedidoDao {

    private EntityManager em;

    public PedidoDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Pedido pedido) {
        this.em.persist(pedido);
    }

    // public void atualizar(Pedido pedido) {
    //     this.em.merge(pedido);
    // }

    // public void excluir(Pedido pedido) {
    //     pedido = em.merge(pedido);
    //     this.em.remove(pedido);
    // }

    // public Pedido buscarPorId(Long id) {
    //     return em.find(Pedido.class, id);
    // }

    // public List<Pedido> buscarTodos() {
    //     String jpql = "SELECT p FROM pedido p";
    //     return em.createQuery(jpql, Pedido.class).getResultList();
    // }

    // public List<Pedido> buscarPorNome(String nome) {
    //     String jpql = "SELECT p FROM pedido p WHERE p.nome = ?1";
    //     return em.createQuery(jpql, Pedido.class)
    //             .setParameter(1, nome)
    //             .getResultList();
    // }

    // public List<Pedido> buscarPorNomeDaCategoria(String nome) {
    //     String jpql = "SELECT p FROM pedido p WHERE p.categoria.nome = ?1";
    //     return em.createQuery(jpql, Pedido.class)
    //             .setParameter(1, nome)
    //             .getResultList();
    // }

    // public BigDecimal buscarPrecoComNome(String nome) {
    //     String jpql = "SELECT p.preco FROM Pedido p WHERE p.categoria.nome = ?1";
    //     return em.createQuery(jpql, BigDecimal.class)
    //             .setParameter(1, nome)
    //             .getSingleResult();
    // }

    public BigDecimal valorTotalVendido() {
        String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
        return em.createQuery(jpql, BigDecimal.class)
                .getSingleResult();
    }

    public List<RelatorioDeVendasVo> relatorioDeVendas() {
        String jpql = "SELECT NEW br.com.alura.loja.vo.RelatorioDeVendasVo(" 
                + "produto.nome, "
                + "SUM(item.quantidade), "
                + "MAX(pedido.data)) "
                + "FROM Pedido pedido "
                + "JOIN pedido.itens item "
                + "JOIN item.produto produto "
                + "GROUP BY produto.nome "
                + "ORDER BY item.quantidade DESC";
        return em.createQuery(jpql, RelatorioDeVendasVo.class)
                .getResultList();
    }

    public Pedido buscarPedidoComCliente(Long id) {
        return em.createQuery("SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id = ?1", Pedido.class)
                .setParameter(1, id)
                .getSingleResult();
    }

}
