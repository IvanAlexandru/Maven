package webService;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import Model.Carte;
import Model.Comanda;

@WebService
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT, use=SOAPBinding.Use.LITERAL)
public interface WebServiceInterface {

	@WebMethod public boolean login(String username, String password);
	@WebMethod public ArrayList<Carte> getCarti();
	@WebMethod public Comanda getComandaByUser(String username);
	@WebMethod public ArrayList<Carte> deleteBookAndReturnNewListOfBooks(String nume);
	@WebMethod public ArrayList<Carte> insertBook(Carte carte);
	@WebMethod public ArrayList<Carte> update(String numeCarte, String atribut, String atributNou);
	@WebMethod public ArrayList<String> searchBooks(String text);
	@WebMethod public String register(String username, String password, String confirmPassword);
	@WebMethod public String addBookToCart(String bookName, String attribute);
	@WebMethod public Comanda getOrderByUser(String username);
	@WebMethod public Carte[] getOrderBooksByUser(String username);
	@WebMethod public void sendOrder(String username,String userMail);
	@WebMethod public Carte[] deleteBookFromOrder(String numeCarte, String username);
	@WebMethod public String findBooksByAutor(String autor);
}
