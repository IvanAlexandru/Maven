package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

public class Comanda implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private int id;
	private String data;
	private Utilizator utilizator;
	private Set carti;
	public Comanda() {
	}
	public Comanda(String data, Utilizator utilizator) {
		super();
		this.data = data;
		this.utilizator = utilizator;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Utilizator getUtilizator() {
		return utilizator;
	}
	public void setUtilizator(Utilizator utilizator) {
		this.utilizator = utilizator;
	}
	public Set getCarti() {
		return carti;
	}
	public void setCarti(Set carti) {
		this.carti = carti;
	}
	
}
