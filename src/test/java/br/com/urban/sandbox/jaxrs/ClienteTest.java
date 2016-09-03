package br.com.urban.sandbox.jaxrs;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.urban.sandbox.jaxrs.modelo.Carrinho;
import br.com.urban.sandbox.jaxrs.modelo.Projeto;

public class ClienteTest {
	
	private HttpServer server;

	@Before
	public void before() {
		server = Servidor.inicializarServidor();
	}
	
	@After
	public void after() {
		server.shutdownNow();
	}
	
	@Test
	public void deveBuscarOCarrinhoEsperado() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		String conteudo = target.path("/carrinhos").request().get(String.class);
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
	}
	
	@Test
    public void deveBuscarOProjetoEsperado() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080");
        String conteudo = target.path("/projetos").request().get(String.class);
        Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
        assertEquals("Minha loja", projeto.getNome());
    }

}
