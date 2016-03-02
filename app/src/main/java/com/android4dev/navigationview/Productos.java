package com.android4dev.navigationview;

import android.graphics.Bitmap;

public class Productos {

	private String nombre;
	private String descripcion;
	private String precio;
	private String URLimagen;
	public Bitmap imagen;



	public Productos() {
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
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio= precio;
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