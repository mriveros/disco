package com.mriveros.disco.disco;

import android.graphics.Bitmap;

public class Reservas {

	private String nombre;
	private String evento;
	private String URLimagen;
	public Bitmap imagen;



	public Reservas() {
		super();
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEvento() {
		return evento;
	}
	public void setEvento(String evento) {
		this.evento = evento;
	}
	public String getURLimagen() {
		return URLimagen;
	}
	public void setURLimagen(String uRLimagen) {
		URLimagen = uRLimagen;
	}
	public Bitmap getImagen() {
		// TODO Auto-generated method stub
		return imagen;
	}
}