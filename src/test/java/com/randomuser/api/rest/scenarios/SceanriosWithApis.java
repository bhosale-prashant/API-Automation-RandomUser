package com.randomuser.api.rest.scenarios;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.randomuser.api.functions.ThingsInApi;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class SceanriosWithApis extends ThingsInApi {
	
	public ThingsInApi apiCommon = new ThingsInApi();
	public Properties prop;
	
	@BeforeTest
	public void setup(){
		prop = apiCommon.init_prop();
	}
	
	@Test
    public void getApiWithAndWithoutParameters() throws InterruptedException {
		
		// List of first names
		List <String> listFirstNames = apiCommon.retriveFromGetResponse(Integer.parseInt(prop.getProperty("user.complete.details.count.element.extract")), 
				prop.getProperty("user.complete.details.response.locator.element.extract"), 
				prop.getProperty("user.complete.details.api"));
		
		List <String> listNameAgeGenderNationality = new ArrayList <String> ();
		
		// Getting age, gender and nationality details for each firstName
		for (String eachFirstName : listFirstNames) {
			String replaceMeInApi = prop.getProperty("user.paramName.replace");
			
			String ageApi = prop.getProperty("user.age.paramName.api");
			ageApi = ageApi.replaceFirst(replaceMeInApi, eachFirstName);		
			//int age = given().get(ageApi).then().extract().path(prop.getProperty("user.age.paramName.response.locator.element.extract"));

			String genderApi = prop.getProperty("user.gender.paramName.api");
			genderApi = genderApi.replaceFirst(replaceMeInApi, eachFirstName);		
			String gender = given().get(genderApi).then().extract().path(prop.getProperty("user.gender.paramName.response.locator.element.extract"));
			
			String nationalityApi = prop.getProperty("user.nationality.paramName.api");
			nationalityApi = nationalityApi.replaceFirst(replaceMeInApi, eachFirstName);
			
			Response response = given().get(nationalityApi);
			JsonPath jsonPathEvaluator = response.jsonPath();
			
			String country_id_locator = prop.getProperty("user.nationality.paramName.response.locator.element.extract.1");
			String probability_locator = prop.getProperty("user.nationality.paramName.response.locator.element.extract.2");
			
			int numberOfCountries = jsonPathEvaluator.getList(country_id_locator).size();
			String finalCountryProbability = "";
			
			if (numberOfCountries == 0) {
				finalCountryProbability = "DATA_ABSENT_HERE";
			}
			
			for (int i=0; i<numberOfCountries; i++) {
				
				String eachCountry_id_locator = country_id_locator+"["+i+"]";
				String eachProbability_locator = probability_locator+"["+i+"]";
					
				finalCountryProbability = finalCountryProbability+" "+jsonPathEvaluator.get(eachCountry_id_locator)+" >> "+jsonPathEvaluator.get(eachProbability_locator)+" | ";	
			}
			listNameAgeGenderNationality.add(String.valueOf(given().get(ageApi).then().extract().path(prop.getProperty("user.age.paramName.response.locator.element.extract")))+" years old "+gender+" "+eachFirstName+" has probability of nationality as --> | "+finalCountryProbability);
		}
		Collections.sort(listNameAgeGenderNationality);
		
		System.out.println("========= Here is the expected output =========");		
		for (String eachNameAgeGenderNationality : listNameAgeGenderNationality) {
			eachNameAgeGenderNationality = eachNameAgeGenderNationality.replaceFirst("null", "DATA_ABSENT_HERE");
			System.out.println(eachNameAgeGenderNationality);
		}
    }
}
