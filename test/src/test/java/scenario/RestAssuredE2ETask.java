package scenario;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestAssuredE2ETask {
    /*
    * Scenario : Add Product
    * Test Case 01: Add data object
        1. Hit the endpoint add object with valid data
        2. Hit the endpoint get object data with valid data
    * Test Case 02: Update data object     
        1. Hit the endpoint login with valid data
        2. Hit the endpoint update with valid data
        3. Hit the endpoint get object with valid data
    * Test Case 03: Delete data object     
        1. Hit the endpoint login with valid data
        2. Hit the endpoint delete with valid data
        3. Hit the endpoint get object with valid data and result must be empty/ null
    */

    String tokenLogin, idProduct;

    @BeforeClass
    public void setup(){
        // Define the base URL for the API
        RestAssured.baseURI = "https://whitesmokehouse.com";

        // Login to the API and get the token
        String jsonLogin = "{\n" + //
                        "  \"email\": \"zahwaputrihamida@gmail.com\",\n" + //
                        "  \"password\": \"@dmin123\"\n" + //
                        "}";

        // Send POST request to login endpoint
        Response responseLogin = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(jsonLogin)
                .log().all()
                .when()
                .post("/webhook/api/login");
        Assert.assertEquals(responseLogin.getStatusCode(), 200, "Expected status code 200 but got " + responseLogin.getStatusCode());
        tokenLogin = responseLogin.jsonPath().getString("token");    
    }

    @Test(priority = 1)
    public void addObject(){
        /*
        * Test Case 01: Add data object
          1. Hit the endpoint add object with valid data
          2. Hit the endpoint get object data with valid data
        */

        String requestObject = "{\n" +
                "    \"name\": \"Apple MacBook Zahwa\",\n" +
                "    \"data\": {\n" +
                "        \"year\": 2025,\n" +
                "        \"price\": 20000000,\n" +
                "        \"cpu_model\": \"Intel Core i7\",\n" +
                "        \"hard_disk_size\": \"1 TB\",\n" +
                "        \"capacity\": \"2\",\n" +
                "        \"screen_size\": \"14\",\n" +
                "        \"color\": \"starlight\"\n" +
                "    }\n" +
                "}";

        // Hit the endpoint add object with valid data
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + tokenLogin)
                .body(requestObject)
                .log().all()
                .post("/webhook/api/objects");

        System.out.println("Response: " + response.asPrettyString());
        assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();

        // Get list of added product id
        List<Integer> ids = response.jsonPath().getList("id");
        idProduct = ids.get(0).toString();
        System.out.println("ID Prodcut: " + idProduct);

        // Hit the endpoint get object data with valid data
        Response responseGetObject = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + tokenLogin)
                .log().all()
                .when()
                .get("/webhook/8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/"+ idProduct );
        
        // Print the response
        System.out.println("Response: " + responseGetObject.asPrettyString());

        // Validate the response
        Assert.assertEquals(responseGetObject.jsonPath().getString("name"),"Apple MacBook Zahwa");
        Assert.assertEquals(responseGetObject.jsonPath().getString("data.year"),"2025");
        Assert.assertEquals(responseGetObject.jsonPath().getString("data.price"),"20000000");
        Assert.assertEquals(responseGetObject.jsonPath().getString("data.cpu_model"),"Intel Core i7");
        Assert.assertEquals(responseGetObject.jsonPath().getString("data.hard_disk_size"),"1 TB");
        Assert.assertEquals(responseGetObject.jsonPath().getString("data.capacity"),"2");
        Assert.assertEquals(responseGetObject.jsonPath().getString("data.screen_size"),"14");
        Assert.assertEquals(responseGetObject.jsonPath().getString("data.color"),"starlight");
    }

    @Test(priority = 2)
    public void updateObject(){
    /*
        * Test Case - 002: Update data object     
          1. Hit the endpoint login with valid data
          2. Hit the endpoint update with valid data
          3. Hit the endpoint get object with valid data
    */

        String bodyUpdate = "{\n" +
                "    \"name\": \"Apple MacBook Zahwa Putri Hamida\",\n" +
                "    \"data\": {\n" +
                "        \"year\": 2028,\n" +
                "        \"price\": 22000000,\n" +
                "        \"cpu_model\": \"Intel Core i9\",\n" +
                "        \"hard_disk_size\": \"2 TB\",\n" +
                "        \"capacity\": \"5\",\n" +
                "        \"screen_size\": \"15\",\n" +
                "        \"color\": \"coral\"\n" +
                "    }\n" +
                "}";
                         
        // Send update request to object endpoint
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + tokenLogin)
                .body(bodyUpdate)
                .log().all()
                .when()
                .put("/webhook/37777abe-a5ef-4570-a383-c99b5f5f7906/api/objects/" + idProduct);

        System.out.println("Response: " + response.asPrettyString());
        assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();

        // Hit the endpoint get object data with valid data
        Response responseGetUpdatedObject = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + tokenLogin)
                .log().all()
                .when()
                .get("/webhook/8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/" + idProduct );
        
        // Print the response
        System.out.println("Response: " + responseGetUpdatedObject.asPrettyString());

        // Validate the response
        Assert.assertEquals(responseGetUpdatedObject.jsonPath().getString("name"),"Apple MacBook Zahwa Putri Hamida");
        Assert.assertEquals(responseGetUpdatedObject.jsonPath().getString("data.year"),"2028");
        Assert.assertEquals(responseGetUpdatedObject.jsonPath().getString("data.price"),"22000000");
        Assert.assertEquals(responseGetUpdatedObject.jsonPath().getString("data.cpu_model"),"Intel Core i9");
        Assert.assertEquals(responseGetUpdatedObject.jsonPath().getString("data.hard_disk_size"),"2 TB");
        Assert.assertEquals(responseGetUpdatedObject.jsonPath().getString("data.capacity"),"5");
        Assert.assertEquals(responseGetUpdatedObject.jsonPath().getString("data.screen_size"),"15");
        Assert.assertEquals(responseGetUpdatedObject.jsonPath().getString("data.color"),"coral");
    }

    @Test(priority = 3)
    public void deleteData(){
    /*
    * Test Case - 003: Delete data object     
        1. Hit the endpoint login with valid data
        2. Hit the endpoint delete with valid data
        3. Hit the endpoint get object with valid data and result must be null
    */

        // Send DELETE request to object endpoint
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + tokenLogin)
                .log().all()
                .when()
                .delete("/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/objects/" + idProduct);
       
        // Print the response
         System.out.println("Response: " + response.asPrettyString()); 

        // Validate the response
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 but got " + response.getStatusCode());
       
        // Hit the endpoint get object data with valid data
        Response responseGetsObject = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + tokenLogin)
                .log().all()
                .when()
                .get("/webhook/8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/" + idProduct );
        
        // Print the response
        System.out.println("Response: " + responseGetsObject.asPrettyString());
            Assert.assertEquals(responseGetsObject.getStatusCode(), 200, "Expected status code 200 but got " + responseGetsObject.getStatusCode());
            Assert.assertNull(responseGetsObject.jsonPath().get("name"), "Expected name null but got " + responseGetsObject.jsonPath().get("name"));
    }
}