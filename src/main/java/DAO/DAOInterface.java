package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import Model.Carte;
import Model.Comanda;

public interface DAOInterface {
	boolean login(String username, String password);
	ArrayList<Carte> getCarti();
	ArrayList<Carte> insertBook(Carte c);
	ArrayList<Carte> deleteBookAndReturnNewListOfBooks(String nume)  throws SQLException ;
	ArrayList<Carte> update(String numeCarte, String atribut, String atributNou);
	ArrayList<String> searchBooks(String text);
	void clearOrder(Comanda comanda);
}
 