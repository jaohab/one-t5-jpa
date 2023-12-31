package br.com.alura.loja.testes;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

public class CadastroDeProdutoV2 {
	
	public static void main(String[] args) {

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
