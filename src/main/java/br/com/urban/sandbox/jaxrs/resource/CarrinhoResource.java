package br.com.urban.sandbox.jaxrs.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.urban.sandbox.jaxrs.dao.CarrinhoDAO;
import br.com.urban.sandbox.jaxrs.modelo.Carrinho;

@Path("carrinhos")
public class CarrinhoResource {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String busca() {
		Carrinho carrinho = new CarrinhoDAO().busca(1l);
		return carrinho.toXML();
	}

}
