package com.rest.client;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.soe.entity.Cliente;
import com.soe.entity.Transaccion;

public class RestClient {

	String url;
	Client clienteWS;

	public boolean validarClientePorLogin(String login) {

		clienteWS = ClientBuilder.newClient();
		return clienteWS.target("http://localhost:8080/rest/login/validate_login?login=" + login)
				.request(MediaType.APPLICATION_JSON_TYPE).get(Boolean.class);

	}

	public double actualizarSaldoCliente(double saldoActual, double montoRetiroOdeposito) {

		clienteWS = ClientBuilder.newClient();
		return clienteWS.target("http://localhost:8080/rest/login/actualizar_saldo?saldo_actual=" + saldoActual+"&monto_retiro_deposito="+montoRetiroOdeposito)
				.request(MediaType.APPLICATION_JSON_TYPE).get(double.class);

	}

	public boolean validarClienteCredenciales(String login, String password) {

		clienteWS = ClientBuilder.newClient();
		return clienteWS.target(
				"http://localhost:8080/rest/login/validate_credenciales?login=" + login + "&password=" + password)
				.request(MediaType.APPLICATION_JSON_TYPE).get(Boolean.class);

	}

	public Cliente obtenerClientePorLogin(String login) {

		clienteWS = ClientBuilder.newClient();
		return clienteWS.target("http://localhost:8080/rest/login/obtener?login=" + login)
				.request(MediaType.APPLICATION_JSON_TYPE).get(Cliente.class);

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public List<Transaccion> obtenerTransaccionesPorLogin(String login) {
		clienteWS = ClientBuilder.newClient();
		return clienteWS.target("http://localhost:8080/rest/login/obtener_transaccion?login=" + login)
				.request(MediaType.APPLICATION_JSON_TYPE).get(new GenericType<List<Transaccion>>() {});

	}

	public boolean guardarCliente(Cliente cliente) {

		clienteWS = ClientBuilder.newClient();
		boolean res = false;
		Response respuesta = clienteWS.target("http://localhost:8080/rest/login/save")
				.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(cliente, MediaType.APPLICATION_JSON));

		if (respuesta.getStatusInfo().getReasonPhrase().equalsIgnoreCase("OK")) {
			res = true;
		}

		return res;
	}
	
	public boolean guardarTransaccion(Transaccion transaccion) {

		clienteWS = ClientBuilder.newClient();
		boolean res = false;
		Response respuesta = clienteWS.target("http://localhost:8080/rest/login/save_transaccion")
				.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(transaccion, MediaType.APPLICATION_JSON));

		if (respuesta.getStatusInfo().getReasonPhrase().equalsIgnoreCase("OK")) {
			res = true;
		}

		return res;
	}
	
	public boolean eliminarClientePorLogin(String login) {

		clienteWS = ClientBuilder.newClient();
		return clienteWS.target(
				"http://localhost:8080/rest/login/eliminar?login=" + login)
				.request(MediaType.APPLICATION_JSON_TYPE).get(Boolean.class);
	}

}
