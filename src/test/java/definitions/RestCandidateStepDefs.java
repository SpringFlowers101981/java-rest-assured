package definitions;

import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import support.TestDataManager;

import java.util.Map;

public class RestCandidateStepDefs {

    @Given("I validated CRUD operations for a candidates")
    public void iValidatedCRUDOperationsForACandidates() {
        Map<String, String> candidateToCreate = TestDataManager.getCandidateFromFile("junior_candidate", "rest_data");
        Map<String, String> credentials = TestDataManager.getRecruiterCredentialFromFile();
        String baseUrl = "https://skryabin.com/recruit/api/v1/";


        int candidateId = RestAssured.given()
                .baseUri(baseUrl)
                .header("Content-Type", "application/json")
                .auth().preemptive().basic(credentials.get("username"), credentials.get("password"))
                .body(candidateToCreate)
                .log().all()
                .when()
                .post("/candidates")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
//               .statusCode(201)
                .extract()
                .jsonPath()
                .getInt("id");

        //Read(GET) - get candidate

        Map<String, Object> map = RestAssured.given()
                .baseUri(baseUrl)
                .log().all()
                .when()
                .get("/candidates/" + candidateId)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .jsonPath()
                .getMap("");
        int idGetFromMethod = (Integer) map.get("id");

        Assertions.assertThat(candidateId).isEqualTo(idGetFromMethod);
    }
}


