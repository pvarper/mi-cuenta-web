package com.soe.bean;

import java.io.Serializable;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.rest.client.RestClient;
import com.soe.entity.Cliente;

@ManagedBean
@ViewScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean dialogDatosCuentaVisible;
	private boolean dialogRegistrarUsuario;
	public Cliente cliente;
	private double monto;

	@PostConstruct
	public void init() {
		try {
			cliente = new Cliente();
			dialogDatosCuentaVisible = false;
			dialogRegistrarUsuario = false;

		} catch (Exception e) {
			// log.error("init|Fallo al inicializar la clase. " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void loginUsuario() {

		RestClient restClient = new RestClient();

		String validacion = validateIngreso();
		if (!validacion.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", validacion));
			cliente = new Cliente();
			return;

		}

		if (!restClient.validarClienteCredenciales(cliente.getLogin(), cliente.getPassword())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login", "usuario o password incorrectos"));
			cliente = new Cliente();
			return;
		}

		dialogDatosCuentaVisible = true;
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Login", "Inicio de Sesion correcta"));

		cliente = restClient.obtenerClientePorLogin(cliente.getLogin());

//		System.out.println(restClient.validarClientePorLogin(cliente.getLogin()));
//		System.out.println(restClient.obtenerClientePorLogin(cliente.getLogin()).toString());
//		
//		cliente = new Cliente();
//		cliente.setCi("56465465");
//		cliente.setLogin("choquec");
//		cliente.setPassword("password");
//		cliente.setNombre("Cesar");
//		cliente.setApellidos("Choque");
//		cliente.setTelefono("897897");
//		cliente.setSaldo(65546.54);
//		System.out.println(restClient.guardarCliente(cliente));

	}

	public void actualizarSaldoCliente(boolean deposito) {
		
		if(this.monto<0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR",
					"El monto no puede ser negativo"));
			return;
		}
		
		RestClient restClient = new RestClient();
		double saldoFinal=0;
		if(deposito) {
			saldoFinal=cliente.getSaldo()+this.monto;
		}else {
			if(monto>cliente.getSaldo()) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR",
						"El monto a debitar es mayor que su saldo "));
				return;
			}
			saldoFinal=cliente.getSaldo()-this.monto;
		}
		cliente.setSaldo(saldoFinal);

		if (!restClient.eliminarClientePorLogin(cliente.getLogin())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR",
					"Error Al eliminar el cliente: " + cliente.getLogin()));
			return;
		}

		if (!restClient.guardarCliente(cliente)) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registro",
					"Error al registrar, contacte al adminsitrador"));
			return;
		}

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro", "Se registro correctamente"));
		
		restClient.obtenerClientePorLogin(cliente.getLogin());
		cliente = restClient.obtenerClientePorLogin(cliente.getLogin());

	}

	public void registrarUsuario() {
		RestClient restClient = new RestClient();
		dialogRegistrarUsuario = true;
		String validacion = validateRegistroCliente();
		if (!validacion.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", validacion));
			return;

		}

		if (restClient.validarClientePorLogin(cliente.getLogin())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Registro",
					"El login ya existe, por favor intentar nuevamente"));
			cliente.setLogin("");
			return;
		}

		if (!restClient.guardarCliente(cliente)) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registro",
					"Error al registrar, contacte al adminsitrador"));
			return;
		}

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro", "Se registro correctamente"));
		dialogRegistrarUsuario = false;
		cliente = new Cliente();

	}

	public String validateIngreso() {

		if (cliente.getLogin() == null)
			return "Usuario invalido!";

		if (cliente.getLogin().trim().isEmpty())
			return "Usuario no puede estar vacion!";

		if (cliente.getPassword() == null)
			return "Contrasena invalido!";

		if (cliente.getPassword().trim().isEmpty())
			return "Contrasena no puede estar vacia!";

		if (!Pattern.matches("^[a-zA-Z]+$", cliente.getLogin())) {
			return "Usuario: solo se permiten letras";
		}

		if (!Pattern.matches("^[a-zA-Z0-9]+$", cliente.getPassword())) {
			return "Password: solo se permiten letras";
		}
		return "";
	}

	public String validateRegistroCliente() {

		if (cliente.getCi() == null)
			return "CI invalido!";

		if (cliente.getCi().trim().isEmpty())
			return "CI no puede estar vacio";

		if (cliente.getNombre() == null)
			return "Nombre invalido!";

		if (cliente.getNombre().trim().isEmpty())
			return "Nombre no puede estar vacio";

		if (cliente.getApellidos() == null)
			return "Apellido invalido!";

		if (cliente.getApellidos().trim().isEmpty())
			return "Apellido no puede estar vacio";

		if (cliente.getTelefono() == null)
			return "Telefono invalido!";

		if (cliente.getTelefono().trim().isEmpty())
			return "Telefono no puede estar vacio";

		if (cliente.getSaldo() < 0)
			return "Saldo invalido!";

		if (cliente.getLogin() == null)
			return "Login invalido!";

		if (cliente.getLogin().trim().isEmpty())
			return "Login no puede estar vacio";

		if (cliente.getPassword() == null)
			return "Password invalido!";

		if (cliente.getPassword().trim().isEmpty())
			return "Password no puede estar vacio";

		if (!Pattern.matches("^[a-zA-Z]+$", cliente.getLogin())) {
			return "Usuario: solo se permiten letras";
		}

		if (!Pattern.matches("^[a-zA-Z0-9]+$", cliente.getPassword())) {
			return "Password: solo se permiten letras";
		}

		if (!Pattern.matches("^[a-zA-Z\\s]+$", cliente.getNombre())) {
			return "Nombre: solo se permiten letras";
		}

		if (!Pattern.matches("^[a-zA-Z0-9\\s]+$", cliente.getApellidos())) {
			return "Apellido: solo se permiten letras";
		}

		if (!Pattern.matches("^[0-9]+$", cliente.getCi())) {
			return "CI: solo se permiten numeros";
		}

		if (!Pattern.matches("^[0-9]+$", cliente.getTelefono())) {
			return "Telefono: solo se permiten numeros";
		}

		return "";
	}

	public boolean isDialogDatosCuentaVisible() {
		return dialogDatosCuentaVisible;
	}

	public void setDialogDatosCuentaVisible(boolean dialogDatosCuentaVisible) {
		this.dialogDatosCuentaVisible = dialogDatosCuentaVisible;
	}

	public boolean isDialogRegistrarUsuario() {
		return dialogRegistrarUsuario;
	}

	public void setDialogRegistrarUsuario(boolean dialogRegistrarUsuario) {
		cliente = new Cliente();
		this.dialogRegistrarUsuario = dialogRegistrarUsuario;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

}
