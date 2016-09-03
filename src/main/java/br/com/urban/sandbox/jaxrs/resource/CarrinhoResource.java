package br.com.urban.sandbox.jaxrs.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import br.com.urban.sandbox.jaxrs.dao.CarrinhoDAO;
import br.com.urban.sandbox.jaxrs.modelo.Carrinho;

@Path("carrinhos")
public class CarrinhoResource {

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response busca(@PathParam("id") long id) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		
		//return carrinho.toXML();
		return Response.ok(carrinho.toXML()).build();
	}
	
//	@Path("{id}")
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public String buscaJson(@PathParam("id") long id) {
//		Carrinho carrinho = new CarrinhoDAO().busca(id);
//		return carrinho.toJson();
//	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response adiciona(String conteudo) {
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		new CarrinhoDAO().adiciona(carrinho);
		
		URI location = URI.create("/carrinhos/" + carrinho.getId());
		return Response.created(location).build();
	}
	
	@Path("{id}/produtos/{produtoId}")
	@DELETE
	public Response removeProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		carrinho.remove(produtoId);
		return Response.ok().build();
	}
	

}
