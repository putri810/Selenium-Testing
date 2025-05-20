package restAssured;

import java.util.List;
import java.util.Map;
import org.testng.annotations.Test;
import org.testng.Assert;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class testRestAssured {
String token;
    
    @Test()
    public void Register(){
        RestAssured.baseURI = "https://whitesmokehouse.com";
         String bodyRegister = "{\n" + //
                    "  \"email\": \"zahwaputrihamida@gmail.com\",\n" + //
                    "  \"full_name\":\"Zahwa Putri Hamida\",\n" + //
                    "  \"password\": \"@dmin123\",\n" + //
                    "  \"department\":\"Technology\",\n" + //
                    "  \"phone_number\":\"083232323232\"\n" + //
                    "}";

        // Send POST request to register endpoint
        Response responseRegister = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(bodyRegister)
                .log().all()
                .when()
                .post("/webhook/api/register");
        // Print the response
        System.out.println("Response: " + responseRegister.asPrettyString());
        
        // Validate the response

        Assert.assertEquals(responseRegister.getStatusCode(), 200,
                "Expected status code 200 but got " + responseRegister.getStatusCode());
        
        Assert.assertEquals(responseRegister.jsonPath().get("email"),"zahwaputrihamida@gmail.com",
                "Expected email zahwaputrihamida@gmail.com but got " + responseRegister.jsonPath().get("email"));
        Assert.assertEquals(responseRegister.jsonPath().get("full_name"),"Zahwa Putri Hamida",
                "Expected full_name Zahwa Putri Hamida but got " + responseRegister.jsonPath().get("full_name"));
        Assert.assertEquals(responseRegister.jsonPath().get("password"),"@dmin123",
                "Expected password @dmin123 but got " + responseRegister.jsonPath().get("password"));
        Assert.assertEquals(responseRegister.jsonPath().get("department"),"Technology",
                "Expected department Technology but got " + responseRegister.jsonPath().get("department"));
        Assert.assertEquals(responseRegister.jsonPath().get("phone_number"),"083232323232",
                "Expected phone_number 083232323232 but got " + responseRegister.jsonPath().get("phone_number"));
    }

    @Test()
    public void testLogin(){
        // Define the base URL for the API
        RestAssured.baseURI = "https://whitesmokehouse.com";

        // Create login request
        String requestBody = "{\n" + //
                        "  \"email\": \"zahwaputrihamida@gmail.com\",\n" + //
                        "  \"password\": \"@dmin123\"\n" + //
                        "}";
        // Send POST request to login endpoint
        Response response = RestAssured.given()
                .contentType("application/json")
                .header("Content-Type", "application/json")
                .body(requestBody)
                .log().all()
                .when()
                .post("/webhook/api/login");
        // Print the response
        System.out.println("Response: " + response.asPrettyString()); 
        token = response.jsonPath().getString("token");  
        System.out.println("Token: " + token);      
    }

    @Test(dependsOnMethods = "loginTest")
    public void AddObjects(){

        RestAssured.baseURI = "https://whitesmokehouse.com";

        String requestBody = "{\n" +
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

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(requestBody)
                .log().all()
                .post("/webhook/api/objects");

        System.out.println("Response: " + response.asPrettyString());
        assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();

        // Validasi detail properti
        assert response.jsonPath().getString("name").equals("[Apple MacBook Zahwa]") 
                : "Expected name is Apple MacBook Zahwa but got " + response.jsonPath().getString("name");
        assert response.jsonPath().getString("data.year").equals("[2025]") 
                : "Expected year is 2025 but got " + response.jsonPath().getString("data.year");
        assert response.jsonPath().getString("data.price").equals("[20000000]") 
                : "Expected price is 20000000 but got " + response.jsonPath().getString("data.price");
        assert response.jsonPath().getString("data.cpu_model").equals("[Intel Core i7]") 
                : "Expected cpu_model is Intel Core i7 but got " + response.jsonPath().getString("data.cpu_model");
        assert response.jsonPath().getString("data.color").equals("[starlight]") 
                : "Expected color is starlight but got " + response.jsonPath().getString("data.color");
        assert response.jsonPath().getString("data.capacity").equals("[2]") 
                : "Expected capacity is 2 but got " + response.jsonPath().getString("data.capacity");
        assert response.jsonPath().getString("data.screen_size").equals("[14]") 
                : "Expected screen_size is 14 but got " + response.jsonPath().getString("data.screen_size");

    }

    @Test(dependsOnMethods = "testLogin")
    public void testGetAllListObjects(){
        /*
         * Define the base URL for the API
         * String baseUrl = "https://whitesmokehouse.com";
         */
        RestAssured.baseURI = "https://whitesmokehouse.com";
        // Create Get All List Objects request
        // Send GET request to objects endpoint
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get("/webhook/api/objects");
        // Print the response
        System.out.println("Response: " + response.asPrettyString());
    }

    @Test(dependsOnMethods = "loginTest")
    public void updateObject(){
        RestAssured.baseURI = "https://whitesmokehouse.com";
        
        // Create Update objects request
        String bodyUpdate = "{\n" +
                "    \"name\": \"Apple MacBook Zahwa Putri Hamida\",\n" +
                "    \"data\": {\n" +
                "        \"year\": 2028,\n" +
                "        \"price\": 22000000,\n" +
                "        \"cpu_model\": \"Intel Core i9\",\n" +
                "        \"hard_disk_size\": \"2 TB\",\n" +
                "        \"capacity\": \"5\",\n" +
                "        \"screen_size\": \"15,5\",\n" +
                "        \"color\": \"coral\"\n" +
                "    }\n" +
                "}";
                         
        // Send update request to object endpoint
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(bodyUpdate)
                .log().all()
                .when()
                .put("/webhook/37777abe-a5ef-4570-a383-c99b5f5f7906/api/objects/202");

        System.out.println("Response: " + response.asPrettyString());
        assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();

        // Validasi detail properti
        assert response.jsonPath().getString("name").equals("[Apple MacBook Pro Zahwa Putri Hamida]") 
                : "Expected name is Apple MacBook Pro Zahwa Putri Hamida but got " + response.jsonPath().getString("name");
        assert response.jsonPath().getString("data.year").equals("[2028]") 
                : "Expected year is 2028 but got " + response.jsonPath().getString("data.year");
        assert response.jsonPath().getString("data.price").equals("[22000000]") 
                : "Expected price is 22000000 but got " + response.jsonPath().getString("data.price");
        assert response.jsonPath().getString("data.color").equals("[coral]") 
                : "Expected color is coral but got " + response.jsonPath().getString("data.color");
        assert response.jsonPath().getString("data.capacity").equals("[5]") 
                : "Expected capacity is 5 but got " + response.jsonPath().getString("data.capacity");
        assert response.jsonPath().getString("data.screen_size").equals("[15,5]") 
                : "Expected screen_size is 15,5 but got " + response.jsonPath().getString("data.screen_size");
    }

    @Test(dependsOnMethods = "testLogin")
    public void partiallyUpdateObject(){
        RestAssured.baseURI = "https://whitesmokehouse.com";
        // Create Update objects request
        String bodyUpdatepartial = "{\n" +
                "    \"name\": \"Zahwa Updated Partial\",\n" +
                "    \"data\": {\n" +
                "    \"year\": 2035\n" +
                "    }\n" +
                "}";
                         
        // Send update request to object endpoint
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(bodyUpdatepartial)
                .log().all()
                .when()
                .patch("/webhook/39a0f904-b0f2-4428-80a3-391cea5d7d04/api/object/202");

        System.out.println("Response: " + response.asPrettyString());
        assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();

        // Validation property details
        assert response.jsonPath().getString("name").equals("Zahwa Updated Partial") 
                : "Expected name is Zahwa Updated Partial but got " + response.jsonPath().getString("name");
        assert response.jsonPath().getString("data.year").equals("2035") 
                : "Expected year is 2035 but got " + response.jsonPath().getString("data.year");
    }

    @Test(dependsOnMethods = "testLogin")
     public void deletedObject() {

        // Send DELETE request to object endpoint
        Response response = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + token)
            .log().all()
            .when()
            .delete("/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/objects/203");
        // Print the response
        System.out.println("Response: " + response.asPrettyString()); 

        // Validate the response
        Assert.assertEquals(response.getStatusCode(), 200,
            "Expected status code 200 but got " + response.getStatusCode());
        }

    @Test(dependsOnMethods = "testLogin")
    public void getSingleObject(){
         RestAssured.baseURI = "https://whitesmokehouse.com";
        // Create Get Single Object request
        // Send GET request to Single Object endpoint
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get("/webhook/8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/202");
        // Print the response
        System.out.println("Response: " + response.asPrettyString());
        // Validate the response
        // Assert.assertEquals(response.getStatusCode(), 200);
        assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();
        assert response.jsonPath().getString("id").equals("200") : "Expected id is 200 but got " + response.jsonPath().getString("id");
        assert response.jsonPath().getString("name").equals("Apple MacBook Pro Zahwa") : "Expected Name is Apple MacBook Pro Zahwa but got " + response.jsonPath().getString("name");        
        assert response.jsonPath().getString("data.year").equals("2025") : "Expected Year is 2025 but got " + response.jsonPath().getString("data.year");       
        assert response.jsonPath().getString("data.price").equals("20000000") : "Expected price is 20000000 but got " + response.jsonPath().getString("data.price");       
        assert response.jsonPath().getString("data.cpu_model").equals("Intel Core i7") : "Expected cpu_model is Intel Core i7 but got " + response.jsonPath().getString("data.cpu_model");
        assert response.jsonPath().getString("data.hard_disk_size").equals("1 TB") : "Expected hard_disk_size is 1 TB but got " + response.jsonPath().getString("data.hard_disk_size");
        assert response.jsonPath().getString("data.color").equals("starlight") : "Expected color is red but got " + response.jsonPath().getString("data.color");
        assert response.jsonPath().getString("data.capacity").equals("2") : "Expected capacity is 2 but got " + response.jsonPath().getString("data.capacity");
        assert response.jsonPath().getString("data.screen_size").equals("14") : "Expected screen_size is 2 but got " + response.jsonPath().getString("data.screen_size");
    }

    @Test(dependsOnMethods = "testLogin")
    public void getAllDepartement() {
    RestAssured.baseURI = "https://whitesmokehouse.com";

    Response response = RestAssured.given()
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + token)
        .log().all()
        .when()
        .get("/webhook/api/department");

    // Cek status code
    assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();

        // Validasi detail properti
    assert response.jsonPath().getString("[0].id").equals("1") 
            : "Expected id is 1 but got " + response.jsonPath().getString("[0].id");
    assert response.jsonPath().getString("[0].department").equals("Technology") 
            : "Expected department is Technology but got " + response.jsonPath().getString("[0].department");
    assert response.jsonPath().getString("[1].id").equals("2") 
            : "Expected id is 2 Updated but got " + response.jsonPath().getString("[1].id");
    assert response.jsonPath().getString("[1].department").equals("Human Resource") 
            : "Expected department is Human Resource but got " + response.jsonPath().getString("[1].department");
    assert response.jsonPath().getString("[2].id").equals("3") 
            : "Expected id is 3 but got " + response.jsonPath().getString("[2].id");
    assert response.jsonPath().getString("[2].department").equals("Finance") 
            : "Expected department is Finance but got " + response.jsonPath().getString("[2].department");
    }
}
