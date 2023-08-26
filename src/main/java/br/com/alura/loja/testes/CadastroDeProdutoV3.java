package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

public class CadastroDeProdutoV3 {
	
	public static void main(String[] args) {

		cadastrarProduto();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);

		//Buscar por ID
		Produto p = produtoDao.buscarPorId(1l);
		System.out.println(p.getPreco());

		//Buscar todos
		List<Produto> todos = produtoDao.buscarTodos();
		todos.forEach(prod -> System.out.println(prod.getNome()));

		//Buscar por nome
		List<Produto> todosNome = produtoDao.buscarPorNome("Xiaomi Redmi");
		todosNome.forEach(prod -> System.out.println(prod.getNome()));

		//Buscar por nome da categoria
		List<Produto> todosCategoria = produtoDao.buscarPorNome("Celulares");
		todosCategoria.forEach(prod -> System.out.println(prod.getNome()));

		//Buscar preço por nome
		BigDecimal preco = produtoDao.buscarPrecoComNome("Xiaomi Redmi");
		System.out.println(preco);

	}

	private static void cadastrarProduto() {

		EntityManager em = JPAUtil.getEntityManager();

		//Cadastrar uma categoria
		Categoria celulares = new Categoria("Celulares");
		CategoriaDao categoriaDao = new CategoriaDao(em);

		//Cadastrar um produto
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);
		ProdutoDao produtoDao = new ProdutoDao(em);
		
		em.getTransaction().begin();

        categoriaDao.cadastrar(celulares);
        produtoDao.cadastrar(celular);

        em.getTransaction().commit();
        em.close();

	}

}
