package com.soe.bean;

import java.io.Serializable;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class LoginBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean dialogDatosCuentaVisible;
	private boolean dialogRegistrarUsuario;

	
	public void init() {
		try {
			setDialogDatosCuentaVisible(false);
			setDialogRegistrarUsuario(false);

		} catch (Exception e) {
			//log.error("init|Fallo al inicializar la clase. " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void loginUsuario(){
		
		setDialogDatosCuentaVisible(true);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Login", "Inicio de Sesion correcta"));
	}
	
	public void registrarUsuario(){

		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro", "Se registro correctamente"));
		setDialogRegistrarUsuario(false);
		
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
		this.dialogRegistrarUsuario = dialogRegistrarUsuario;
	}
	
	
	

}
