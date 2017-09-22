package com.rosalind.framework.base;

import static com.jayway.restassured.RestAssured.given;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
//import static org.hamcrest.Matchers.equalTo;

/*
 * Integration test cases
 */
public class RestSystemTestIT {
	private static Logger LOG = LoggerFactory.getLogger(RestSystemTestIT.class);
	public static final String REST_SERVICE_BASE_URI = "/AlmaMater";
	
	@BeforeClass
    public static void setup(){
		LOG.info("before class");
	}
	
	@Test
	public void testUrl() {
		given()
			.when()
			.log().all()
			.get(REST_SERVICE_BASE_URI + "/test")
			.then()
			.log().all()
			.statusCode(HttpStatus.OK.value());			
	}
	
	/*
	 * 
	 * BELOW GIVEN ARE EXAMPLES ON USAGE OF RESTASSURED and HAMCREST
	 * We can use assertJ also instead of HAMCREST
	 * 
	 */
	
	/*@Test
    public void aCarGoesIntoTheGarage() {
        Map<String,String> car = new HashMap<>();
        car.put("plateNumber", "xyx1111");
        car.put("brand", "audi");
        car.put("colour", "red");

        given()
        .contentType("application/json")
        .body(car)
        .when().post("/garage/slots").then()
        .statusCode(200);
    }*/
	
	/*@Test
    public void aCarIsRegisteredInTheGarage() {
        Car car = new Car();
        car.setPlateNumber("xyx1111");
        car.setBrand("audi");
        car.setColour("red");

        Slot slot = given()
        .contentType("application/json")
        .body(car)
        .when().post("/garage/slots")
        .as(Slot.class);

        assertFalse(slot.isEmpty());
        assertTrue(slot.getPosition() < 150);

    }*/
	
	
	/*@Test
    public void aCarEntersAndThenLeaves() {
        Car car = new Car();
        car.setPlateNumber("xyx1111");
        car.setBrand("audi");
        car.setColour("red");

        int positionTakenInGarage = given()
        .contentType("application/json")
        .body(car)
        .when().post("/garage/slots").then()
        .body("empty",equalTo(false))
        .extract().path("position");

        given().pathParam("slotID", positionTakenInGarage)
        .when().delete("/garage/slots/{slotID}").then()
        .statusCode(200);

    }*/
}
