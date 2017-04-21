package webService;

import java.lang.annotation.Annotation;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.jws.WebService;

import DAO.MainController;
import Model.Carte;
import Model.Comanda;

@WebService(endpointInterface="webService.WebServiceInterface")
public class WebServiceClass implements WebServiceInterface{

	@Override
	public boolean login(String username, String password) {
		System.out.println("Server login!");
		return MainController.getInstance().login(username, password);
	}

	@Override
	public ArrayList<Carte> getCarti() {
		System.out.println("getCarti server!");
		return MainController.getInstance().getCarti();
	}

	@Override
	public Comanda getComandaByUser(String username) {
		System.out.println("getComandaByUser server!");
		return MainController.getInstance().getComandaByUser(username);
	}

	@Override
	public ArrayList<Carte> deleteBookAndReturnNewListOfBooks(String nume) {
		System.out.println("deleteBookAndReturnNewListOfBooks server!");
		try {
			return MainController.getInstance().deleteBookAndReturnNewListOfBooks(nume);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<Carte> insertBook(Carte carte) {
		System.out.println("insertBook server!");
		return MainController.getInstance().insertBook(carte);
	}

	@Override
	public ArrayList<Carte> update(String numeCarte, String atribut,
			String atributNou) {
		System.out.println("update by server!");
		return MainController.getInstance().update(numeCarte, atribut, atributNou);
	}

	@Override
	public ArrayList<String> searchBooks(String text) {
		System.out.println("searchBooks by server!");
		return MainController.getInstance().searchBooks(text);
	}

	@Override
	public String register(String username, String password,
			String confirmPassword) {
		System.out.println("register by server!");
		return MainController.getInstance().register(username, password, confirmPassword);
	}

	@Override
	public String addBookToCart(String bookName, String attribute) {
		System.out.println("addBookToCart by server!");
		return MainController.getInstance().addBookToCart( bookName, attribute);
	}

	@Override
	public Comanda getOrderByUser(String username) {
		System.out.println("getOrder by server!");
		return MainController.getInstance().getOrderByUser(username);
	}

	@Override
	public Carte[] getOrderBooksByUser(String username) {
		System.out.println("getOrderBooks by server");
		return MainController.getInstance().getOrderBooksByUser(username);
	}

	@Override
	public void sendOrder(String username,String userMail) {
		System.out.println("sendOrder by server");
		MainController.getInstance().sendOrder(username, userMail);
	}

	@Override
	public Carte[] deleteBookFromOrder(String numeCarte, String username) {
		System.out.println("deleteBookFromOrder by server");
		return MainController.getInstance().deleteBookFromOrder(numeCarte, username);
	}

	@Override
	public String findBooksByAutor(String autor) {
		System.out.println("findBooksByAutor by server");
		return MainController.getInstance().findBooksByAutor(autor);
	}
}
