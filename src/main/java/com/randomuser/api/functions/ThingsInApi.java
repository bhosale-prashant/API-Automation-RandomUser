package com.randomuser.api.functions;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ThingsInApi {

	public Properties prop;

	// Load data from properties file to [roperties object
	public Properties init_prop(){
		try {
			FileInputStream ip = new FileInputStream("src\\test\\resources\\Config_and_OR.properties");
			prop = new Properties();
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	// Retrieve data from Get call depending on given arguments
	public List <String> retriveFromGetResponse (int elementCount, String elementLocator, String forApi) throws InterruptedException {
		List <String> listElementValues = new ArrayList <String> ();
		for (int i=0; i<elementCount; i++) {
			RestAssured.baseURI = forApi;
			Response response = given().get();

			String elementValueExtracted = response.then().extract().path(elementLocator);
			int attempts = 0;
			while (!elementValueExtracted.matches("[a-zA-Z]*") && attempts < 5) {
				System.out.println("Response seems problematic, retrying again after a pause");
				Thread.sleep(3000);
				response = given().get();
				elementValueExtracted = response.then().extract().path(elementLocator);
				attempts++;
			}
			System.out.println("Extracted element value as "+elementValueExtracted);
			listElementValues.add(elementValueExtracted);
			response.then().assertThat().statusCode(200);
		}
		System.out.println(listElementValues.size()+" element values were extracted from the response: "+listElementValues);
		return listElementValues;
	}

}
