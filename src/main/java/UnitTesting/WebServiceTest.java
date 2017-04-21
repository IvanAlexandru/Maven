package UnitTesting;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import webService.WebServiceClass;

public class WebServiceTest {

	private WebServiceClass webService;
	
	@Before
	public void setUp(){
		webService = new WebServiceClass();
	}
	
	@Test
	public void testWebService(){
		Assert.assertEquals(webService.findBooksByAutor("Shake"), "Autorul nu exista in baza de date!");
	}
}
