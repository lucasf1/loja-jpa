package br.com.alura.loja.testes;

import java.math.BigDecimal;

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

public class PerformanceConsultas {

	public static void main(String[] args) {

		popularBancoDeDados();
		EntityManager em = JPAUtil.getEntityManager();
		PedidoDao pedidoDao = new PedidoDao(em);
		
		Pedido pedido = pedidoDao.buscarPedidoComCliente(1L);
		
		em.close();
		System.out.println(pedido.getCliente().getNome());
	}

	private static void popularBancoDeDados() {
		
		Categoria celulares = new Categoria("CELULARES");
		Categoria videogames = new Categoria("VIDEOGAMES");
		Categoria informatica = new Categoria("INFORMATICA");
		
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);
		Produto videogame = new Produto("PS5", "Playstation 5", new BigDecimal("2000"), videogames);
		Produto macbook = new Produto("Mackbook", "Mackbook Pro Retina", new BigDecimal("6000"), informatica);
		
		Cliente cliente = new Cliente("Lucas", "123456789");
		
		Pedido pedido = new Pedido(cliente);		
		pedido.adicionarItem(new ItemPedido(10, pedido, celular));
		pedido.adicionarItem(new ItemPedido(40, pedido, videogame));
		
		Pedido pedido2 = new Pedido(cliente);
		pedido2.adicionarItem(new ItemPedido(20, pedido, macbook));

		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);
		ClienteDao clienteDao = new ClienteDao(em);
		PedidoDao pedidoDao = new PedidoDao(em);

		em.getTransaction().begin();

		categoriaDao.cadastrar(celulares);
		categoriaDao.cadastrar(videogames);
		categoriaDao.cadastrar(informatica);
		
		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(videogame);
		produtoDao.cadastrar(macbook);		
		
		clienteDao.cadastrar(cliente);
		
		pedidoDao.cadastrar(pedido);
		pedidoDao.cadastrar(pedido2);

		em.getTransaction().commit();
		em.close();
	}
}