package br.com.urban.sandbox.jaxrs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.logging.LoggingFeature.Verbosity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.urban.sandbox.jaxrs.modelo.Carrinho;
import br.com.urban.sandbox.jaxrs.modelo.Produto;
import br.com.urban.sandbox.jaxrs.modelo.Projeto;

public class ClienteTest {
	
	private HttpServer server;
	private Client client;
	private WebTarget target;

	@Before
	public void before() {
		server = Servidor.inicializarServidor();
		
		ClientConfig config = new ClientConfig();
		config.register(new LoggingFeature(Logger.getLogger(""), Level.INFO, Verbosity.HEADERS_ONLY, null));
		
		client = ClientBuilder.newClient(config);
		target = client.target("http://localhost:8080");
	}
	
	@After
	public void after() {
		server.shutdownNow();
	}
	
	@Test
	public void deveBuscarOCarrinhoEsperado() {
		String conteudo = target.path("/carrinhos/1").request().get(String.class);
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
	}
	
	@Test
    public void deveBuscarOProjetoEsperado() {
        String conteudo = target.path("/projetos/1").request().get(String.class);
        Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
        assertEquals("Minha loja", projeto.getNome());
    }
	
	@Test
	public void deveAdicionarCarrinho() {
        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");
        String xml = carrinho.toXML();
        
        Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
        
        Response response = target.path("/carrinhos").request().post(entity);
        assertEquals(201, response.getStatus());
        
        String location = response.getHeaderString("Location");
        String conteudo = client.target(location).request().get(String.class);
        assertTrue(conteudo.contains("Tablet"));
	}

}
