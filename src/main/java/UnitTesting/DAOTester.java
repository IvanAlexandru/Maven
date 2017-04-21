package UnitTesting;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import DAO.MainController;
import Model.Carte;
import Model.Comanda;
import Model.Utilizator;

@RunWith(MockitoJUnitRunner.class)
public class DAOTester {
	private MainController mainController;
	private Utilizator utilizator;
	
	Comanda comanda = new Comanda();
	
	@Before
	public void setUp(){
		mainController = MainController.getInstance();
		utilizator = mock(Utilizator.class);
		Set<Carte> carti = new HashSet<>();
		carti.add(new Carte("48374387", "Harry", "j.k.", 12,
			"12-04-2019", "USEdu", 12));
		carti.add(new Carte("48374387", "Harry", "j.k.", 36,
				"12-04-2019", "USEdu", 36));
		comanda.setCarti(carti);
	}
	
	
	
	@Test
	public void testRegister(){
		//when(query.list()).thenReturn(utilizatori);
		when(utilizator.getUsername()).thenReturn("Mika");
		Assert.assertEquals(mainController.register("Mika", "123","123"),"success");
		Assert.assertEquals(mainController.getCostOfOrder(comanda),48.0);
		mainController.createComandForUser(utilizator);
	}
	
}
