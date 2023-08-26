package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.ItemPedido;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

public class CadastroDePedido {

    public static void main(String[] args) {

        popularBancoDeDados();

        EntityManager em = JPAUtil.getEntityManager();

        //Recuperando produto
		ProdutoDao produtoDao = new ProdutoDao(em);
        Produto produto1 = produtoDao.buscarPorId(1l);
        Produto produto2 = produtoDao.buscarPorId(2l);
        Produto produto3 = produtoDao.buscarPorId(3l);

        //Recuperando cliente
		ClienteDao clienteDao = new ClienteDao(em);
        Cliente cliente = clienteDao.buscarPorId(1l);

        //Pedido 1
        Pedido pedido1 = new Pedido(cliente);
        pedido1.adicionarItem(new ItemPedido(10, pedido1, produto1));
        pedido1.adicionarItem(new ItemPedido(40, pedido1, produto2));
        
        //Pedido 2
        Pedido pedido2 = new Pedido(cliente);
        pedido2.adicionarItem(new ItemPedido(20, pedido2, produto3));

        em.getTransaction().begin();

        PedidoDao pedidoDao = new PedidoDao(em);

        pedidoDao.cadastrar(pedido1);
        pedidoDao.cadastrar(pedido2);

        em.getTransaction().commit();

        BigDecimal totalVendido = pedidoDao.valorTotalVendido();
        System.out.println("VALOR TOTAL: " + totalVendido);

        List<RelatorioDeVendasVo> relatorio = pedidoDao.relatorioDeVendas();
        //Print 1
        for (RelatorioDeVendasVo obj : relatorio) {
            System.out.println(obj.getNomeProduto());
            System.out.println(obj.getQuantidadeVendida());
            System.out.println(obj.getDataUltimaVenda());
        }
        //Print 2
        relatorio.forEach(rel -> System.out.println(rel.toString()));

        Pedido pedido = pedidoDao.buscarPedidoComCliente(1l);

        em.close();

        System.out.println(pedido.getCliente().getNome());



    }

    private static void popularBancoDeDados() {

		EntityManager em = JPAUtil.getEntityManager();

		//Cadastrar uma categoria
		Categoria celulares = new Categoria("CELULARES");
        Categoria videogames = new Categoria("VIDEOGAMES");
        Categoria informatica = new Categoria("INFORMATICA");
		
		//Cadastrar um produto
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);
        Produto videogame = new Produto("PS5", "PlayStatio 5", new BigDecimal("8000"), videogames);
        Produto macbook = new Produto("MacBook", "MacBook Pro Retina", new BigDecimal("14000"), informatica);
		
        //Cadastrar um cliente
        Cliente cliente = new Cliente("Nome do Cliente", "123456-789");

        CategoriaDao categoriaDao = new CategoriaDao(em);
        ProdutoDao produtoDao = new ProdutoDao(em);
        ClienteDao clienteDao = new ClienteDao(em);
		
		em.getTransaction().begin();

        categoriaDao.cadastrar(celulares);
        categoriaDao.cadastrar(videogames);
        categoriaDao.cadastrar(informatica);

        produtoDao.cadastrar(celular);
        produtoDao.cadastrar(videogame);
        produtoDao.cadastrar(macbook);

        clienteDao.cadastrar(cliente);

        em.getTransaction().commit();
        em.close();

	}
    
}
