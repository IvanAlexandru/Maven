package DAO;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import Model.Carte;
import Model.Comanda;
import Model.Utilizator;
import Service.Batch;
import Service.SendMailTLS;

public class MainController implements DAOInterface{
	
	private SessionFactory factory;
	private static MainController instance;
	private Batch batch;
	
	private MainController(){
			factory = new Configuration().configure("HibernateConfiguration/hibernate.cfg.xml").buildSessionFactory();
	} 
	
	public static MainController getInstance(){
		if(instance == null){
			return instance = new MainController();
		}
		return instance;
	}
	

	public boolean login(String username, String password){
		System.out.println("Login!");
		Session session = factory.openSession();
		Transaction tx = null; 
		try{
			tx = session.beginTransaction();
			List users = session.createQuery("FROM Utilizator").list();
			tx.commit();
			return isUserRegistered(users, username, password);
		}catch(HibernateException e){
			if(tx!=null){tx.rollback();}
			e.printStackTrace();
		}finally{
			session.close();
		}
		return false;
	}

	private boolean isUserRegistered(List users, String username,
			String password) {
		Iterator<Utilizator> it = users.iterator();
		while(it.hasNext()){
			Utilizator u = it.next();
			if(username.equals(u.getUsername()) && password.equals(u.getPassword())){
				System.out.println("Login reusit!");
				return true;
			}
		}
		System.out.println("Login nereusit!");
		return false;
	}

	public ArrayList<Carte> getCarti(){
		System.out.println("Functie noua!");
		ArrayList<Carte> carti = new ArrayList<>();
		Session session = factory.openSession();
		Transaction tx = null;
				try{
					tx = session.beginTransaction();
					carti = (ArrayList<Carte>)session.createQuery("FROM Carte").list();
					tx.commit();
					return carti;
				}catch(HibernateException e){
					if(tx!=null){tx.rollback();}
					e.printStackTrace();
				}finally{
					session.close();
				}
				return null;
	}
	
	
	public ArrayList<Carte> insertBook(Carte c){
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Integer id = (Integer)session.save(c); 
			tx.commit();
			return getCarti();
		}catch(HibernateException e){
			if(tx!=null)tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return null;
	}
	
	public ArrayList<Carte> deleteBookAndReturnNewListOfBooks(String numeCarte) throws SQLException {
		deleteBookFromExistingOrders(numeCarte);
		ArrayList<Carte> carti = new ArrayList<>();
		Carte carte = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			carti = getCarti();
			carte = getBookFromList(carti, numeCarte);
			session.delete(carte);
			tx.commit();
			carti = getCarti();
			return carti;
		} catch (HibernateException e) {
			if(tx!=null)tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return null;
	}
	
	
	private void deleteBookFromExistingOrders(String numeCarte) {
		ArrayList<Comanda> comenzi = new ArrayList<>();
		Comanda comanda = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			comenzi = (ArrayList<Comanda>)session.createQuery("FROM Comanda").list();
			for(Comanda c : comenzi){
				Set<Carte> carti = c.getCarti();
				Iterator it = carti.iterator();
				while(it.hasNext()){
					Carte carte = (Carte) it.next();
					if(carte.getNume().equals(numeCarte)){
						carti.remove(carte);
						break;
					}
				}
				session.update(c);
			}
			tx.commit();
		} catch (HibernateException e) {
			if(tx!=null)tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
	}

	private Carte getBookFromList(ArrayList<Carte> carti, String numeCarte) {
		for(Carte c : carti){
			if(c.getNume().equals(numeCarte)){
				return c;
			}
		}
		return null;
	}

	public ArrayList<Carte> update(String numeCarte, String atribut, String atributNou){
		Session session = factory.openSession();
		Transaction tx = null;
		ArrayList<Carte> carti = new ArrayList<>();
		Carte carte = null;
		try{
			tx = session.beginTransaction();
			carti = getCarti();
			carte = getBookFromList(carti, numeCarte);
			setAtributNou(carte,atribut,atributNou);
			session.update(carte);
			tx.commit();
			System.out.println("Carte updatata!");
			return carti;
		}catch(HibernateException e){
			if(tx!=null)tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return null;
	}


	private void setAtributNou(Carte carte, String atribut, String atributNou) {
		// TODO Auto-generated method stub
		switch(atribut){
		case "nume":{
			carte.setNume(atributNou);
		}break;
		case "autor":{
			carte.setAutor(atributNou);
		}break;
		case "pret":{
			carte.setPret(Double.parseDouble(atributNou));
		}break;
		case "stoc":{
			carte.setStoc(Integer.parseInt(atributNou));
		}break;
		case "dataDeLansare":{
			carte.setDataDeLansare(atributNou);
		}break;
		case "codDeBare":{
			carte.setCodDeBare(atributNou);
		}break;
		case "editura":{
			carte.setEditura(atributNou);
		}break;
		}
	}
	
	public ArrayList<String> searchBooks(String text){
		ArrayList<String> cartiGasite = new ArrayList<>();
		ArrayList<Carte> carti = getCarti();
		Iterator i = carti.iterator();
		while(i.hasNext()){
			Carte c = (Carte)i.next();
			if(c.getNume().contains(text)){
				cartiGasite.add(c.getNume());
			}
		}
		return cartiGasite;
	}

	public String register(String username, String password,String confirmPassword) {
		System.out.println("Register function!");
		if(!password.equals(confirmPassword)){
			return "Please enter identical passwords!";
		}
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Query query = session.createQuery("FROM Utilizator");
			ArrayList<Utilizator> utilizatori = (ArrayList<Utilizator>) query.list();
			Utilizator utilizator = new Utilizator(username,password);
			boolean isUser = checkIfUserExists(utilizator, utilizatori);
			if(isUser){
				return "Username already exists!";
			}else{
				session.save(utilizator);
				tx.commit();
				createComandForUser(utilizator);
				return "success";
			}
		}catch(HibernateException ex){
			if(tx!=null)tx.rollback();
			ex.printStackTrace();
		}finally{
			session.close();
		}
		return "Registration process failed!";
	}

	public void createComandForUser(Utilizator utilizator) {
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Utilizator user = getUserByUsername(utilizator.getUsername());
			System.out.println(user.getUsername());
			Comanda comanda = new Comanda("Undefined",user);
			session.save(comanda);
			tx.commit();
		}catch(HibernateException ex){
			if(tx!=null){tx.rollback();}
			ex.printStackTrace();
		}finally{
			session.close();
		}
	}

	private Utilizator getUserByUsername(String username) {
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Utilizator user = null;
			ArrayList<Utilizator> utilizatori = (ArrayList<Utilizator>) 
					session.createQuery("FROM Utilizator").list();
			user = findUserInList(utilizatori, username);
			tx.commit();
			return user;
		}catch(HibernateException ex){
			if(tx!=null)tx.rollback();
			ex.printStackTrace();
		}finally{
			session.close();
		}
		return null;
	}

	private Utilizator findUserInList(ArrayList<Utilizator> utilizatori,
			String username) {
		for(Utilizator u : utilizatori){
			if(username.equals(u.getUsername())){
				return u;
			}
		}
		return null;
	}

	public boolean checkIfUserExists(Utilizator utilizator, ArrayList<Utilizator> utilizatori) {
		for(Utilizator u : utilizatori){
			if(u.getUsername().equals(utilizator.getUsername())){
				return true;
			}
		}
		return false;
	}

	public String addBookToCart(String bookName, String username) {
		Carte carte = getBookByName(bookName);
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Comanda comanda = getComandaByUser(username);
			System.out.println("Comanda id: "+ comanda.getId());
			Set carti = comanda.getCarti();
			boolean bookIsBought = checkIfBookExists(carti, bookName);
			if(!bookIsBought){
				carti.add(carte);
				comanda.setCarti(carti);
				session.update(comanda);
				tx.commit();
				return "Book "+bookName+" was added to your cart!";
			}else{
				comanda.setCarti(carti);
				session.update(comanda);
				tx.commit();
				return "Book "+bookName+" is already in your cart!";
			}
		}catch(HibernateException ex){
			if(tx!=null)tx.rollback();
			ex.printStackTrace();
			return "An error occured!";
		}finally{
			session.close();
		}
	}

	private boolean checkIfBookExists(Set carti, String bookName) {
		Iterator<Carte> it = carti.iterator();
		while(it.hasNext()){
			if(bookName.equals(it.next().getNume())){
				return true;
			}
		}
		return false;
	}

	private Carte getBookByName(String bookName) {
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Query query = session.createQuery("FROM Carte WHERE nume=:bookName");
			query.setParameter("bookName", bookName);
			ArrayList<Carte> carti = (ArrayList<Carte>) query.list();
			tx.commit();
			return carti.get(0);
		}catch(HibernateException ex){
			if(tx!=null)tx.rollback();
			ex.printStackTrace();
		}finally{
			session.close();
		}
		return null;
	}

	public Comanda getOrderByUser(String username) {
		System.out.println("///////////////////444444//////////////////////");
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Utilizator utilizator = getUserByUsername(username);
			Query query = session.createQuery("FROM Comanda WHERE utilizator_id=:utilizatorID");
			query.setParameter("utilizatorID", utilizator.getId());
			ArrayList<Comanda> comenzi = (ArrayList<Comanda>) query.list();
			Comanda comanda = (Comanda) session.get(Comanda.class, comenzi.get(0).getId());
			tx.commit();
			return comanda;
		}catch(HibernateException ex){
			if(tx!=null)tx.rollback();
			ex.printStackTrace();
		}finally{
			session.close();
		}
		return null;
	}

	public Comanda getComandaByUser(String username) {
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Comanda comanda = getOrderByUser(username);
			comanda = (Comanda)session.get(Comanda.class, comanda.getId());
			tx.commit();
			return comanda;
		}catch(HibernateException ex){
			if(tx!=null)tx.rollback();
			ex.printStackTrace();
		}finally{
			session.close();
		}
		return null;
	}

	public void clearOrder(Comanda comanda) {
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Set carti = comanda.getCarti();
			carti.clear();
			comanda.setCarti(carti);
			session.update(comanda);
			tx.commit();
		}catch(HibernateException ex){
			if(tx!=null)tx.rollback();
			ex.printStackTrace();
		}finally{
			session.close();
		}
	}

	/*public Carte[] getOrderBooksByUser(String username) {
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Comanda comanda = getOrderByUser(username);
			comanda = (Comanda)session.get(Comanda.class, comanda.getId());
			tx.commit();
			Iterator<Carte> it = comanda.getCarti().iterator();
			Carte[] carti = new Carte[comanda.getCarti().size()];
			int i = 0;
			while(it.hasNext()){
				carti[i] = it.next();
				i++;
			}
			return carti;
		}catch(HibernateException ex){
			if(tx!=null)tx.rollback();
			ex.printStackTrace();
		}finally{
			session.close();
		}
		return null;
	}*/
	
	public Carte[] getOrderBooksByUser(String username) {
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Comanda comanda = getOrderByUser(username);
			comanda = (Comanda)session.get(Comanda.class, comanda.getId());
			tx.commit();
			Iterator<Carte> it = comanda.getCarti().iterator();
			Carte[] carti = new Carte[comanda.getCarti().size()];
			int i = 0;
			while(it.hasNext()){
				carti[i] = it.next();
				i++;
			}
			return carti;
		}catch(HibernateException ex){
			if(tx!=null)tx.rollback();
			ex.printStackTrace();
		}finally{
			session.close();
		}
		return null;
	}

	public void sendOrder(String username,String userMail) {
		SendMailTLS sendMail = new SendMailTLS();
		Comanda comanda = getComandaByUser(username);
		String orderMessage = getOrderMessage(comanda);
		sendMail.sendMessage(userMail, orderMessage);
		clearOrder(comanda);
	}
	
	private String getOrderMessage(Comanda comanda) {
		String orderMessage = "Online Library will process your following order: "+"\n";
		orderMessage += "Order id: "+comanda.getId()+"\n";
		orderMessage += "Order date: "+getDate()+"\n";
		orderMessage += "Order products: "+comanda.getCarti()+"\n";
		orderMessage += "Order price: "+getCostOfOrder(comanda)+"\n"+"\n"+"\n";
		orderMessage += "We would like to thank you for your purchase!";
		return orderMessage;
	}

	private String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public double getCostOfOrder(Comanda comanda) {
		double cost = 0;
		Iterator<Carte> it = comanda.getCarti().iterator();
		while(it.hasNext()){
			cost += it.next().getPret();
		}
		return cost;
	}

	public Carte[] deleteBookFromOrder(String numeCarte, String username) {
		System.out.println("////////Schimbari valide!!!!!!!!!!!!!!!!");
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Comanda comanda = getOrderByUser(username);
			comanda = (Comanda)session.get(Comanda.class, comanda.getId());
			Carte carte = null;
			Set<Carte> carti = comanda.getCarti();
			carti = removeBookFromBookList(carti, numeCarte);
			comanda.setCarti(carti);
			session.update(comanda);
			tx.commit();
			return transformSetToArray(carti);
		}catch(HibernateException ex){
			if(tx!=null)tx.rollback();
			ex.printStackTrace();
		}finally{
			session.close();
		}
		return null;
	}

	private Carte[] transformSetToArray(Set<Carte> carti) {
		Iterator<Carte> it = carti.iterator();
		Carte[] arrayCarti = new Carte[carti.size()];
		int i = 0;
		while(it.hasNext()){
			arrayCarti[i] = it.next();
			i++;
		}
		return arrayCarti;
	}

	private Set<Carte> removeBookFromBookList(Set<Carte> carti, String numeCarte) {
		Iterator<Carte> it = carti.iterator();
		Carte carte = null;
		while(it.hasNext()){
			Carte c = it.next();
			if(numeCarte.equals(c.getNume())){
				carte = c;
				break;
			}
		}
		carti.remove(carte);
		return carti;
	}

	public String findBooksByAutor(String autor) {
		batch = new Batch();
		boolean isAutor = autorExists(autor);
		if(isAutor){
			return batch.writeBooksbyAutorToXML(autor);
		}else{
			return "Autorul nu exista in baza de date!";
		}
	}

	private boolean autorExists(String autor) {
		ArrayList<Carte> carti = getCarti();
		Iterator it = carti.iterator();
		Carte carte = null;
		while(it.hasNext()){
			carte = (Carte) it.next();
			if(carte.getAutor().equals(autor)){
				return true;
			}
		}
		return false;
	}
	
}
