package com.mriveros.disco.disco;

import android.graphics.Bitmap;

public class Galerias {

	private String nombre;
	private String profesion;
	private String equipo;
	private String URLimagen;
	public Bitmap imagen;
	


	public Galerias() {
		super();
	}
	
	public String getEquipo() {
		return equipo;
	}
	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getProfesion() {
		return profesion;
	}
	public void setProfesion(String profesion) {
		this.profesion = profesion;
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