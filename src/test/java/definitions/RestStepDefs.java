package definitions;

import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;

public class RestStepDefs {
    @Given("I get candidate via api")
    public void iGetCandidateViaApi() {
        RestAssured.given()
                .baseUri("https://skryabin.com")
                .basePath("/recruit/api/v1/")
                .log().all()
                .when()
                .get("candidates/")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK);
    }
}
