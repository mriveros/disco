package com.android4dev.navigationview;

import android.graphics.Bitmap;

public class Eventos {

	private String nombre;
	private String descripcion;
	private String fecha;
	private String URLimagen;
	public Bitmap imagen;



	public Eventos() {
		super();
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripicion() {
		return descripcion;
	}
	public void setDescripicion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha= fecha;
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