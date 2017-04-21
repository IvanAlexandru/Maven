package webService;

import javax.xml.ws.Endpoint;

public class WebServicePublisher {

	public static void main(String [] args){
		System.out.println("Pulishing WebService.....");
		Endpoint.publish("http://localhost:9980/OnlineLibraryWebService", new WebServiceClass());
		System.out.println("WebService published!");
	}
}
