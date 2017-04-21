package Model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="carti")
public class Carte implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9176505694631444681L;
	private int id;
	private String codDeBare;
	private String nume;
	private String autor;
	private double pret;
	private String dataDeLansare;
	private String editura;
	private int stoc;

	public Carte() {
	}

	public Carte(int id, String codDeBare, String nume, String autor,
			double pret, String dataDeLansare, String editura, int stoc) {
		super();
		this.id = id;
		this.codDeBare = codDeBare;
		this.nume = nume;
		this.autor = autor;
		this.pret = pret;
		this.dataDeLansare = dataDeLansare;
		this.editura = editura;
		this.stoc = stoc;
	}

	public Carte(String codDeBare, String nume, String autor, double pret,
			String dataDeLansare, String editura, int stoc) {
		super();
		this.codDeBare = codDeBare;
		this.nume = nume;
		this.autor = autor;
		this.pret = pret;
		this.dataDeLansare = dataDeLansare;
		this.editura = editura;
		this.stoc = stoc;
	}

	@XmlElement(name="id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlElement(name="codDeBare")
	public String getCodDeBare() {
		return codDeBare;
	}

	public void setCodDeBare(String codDeBare) {
		this.codDeBare = codDeBare;
	}

	@XmlElement(name="nume")
	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	@XmlElement(name="autor")
	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	@XmlElement(name="pret")
	public double getPret() {
		return pret;
	}

	public void setPret(double pret) {
		this.pret = pret;
	}

	
	public String getDataDeLansare() {
		return dataDeLansare;
	}

	public void setDataDeLansare(String dataDeLansare) {
		this.dataDeLansare = dataDeLansare;
	}

	@XmlElement(name="editura")
	public String getEditura() {
		return editura;
	}

	public void setEditura(String editura) {
		this.editura = editura;
	}

	public int getStoc() {
		return stoc;
	}

	public void setStoc(int stoc) {
		this.stoc = stoc;
	}

	public String toString(){
		return getNume() + " " +getPret();
	}
}
