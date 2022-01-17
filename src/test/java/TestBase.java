import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import requests.UserEndpoints;

public class TestBase extends UserEndpoints {

    public RequestSpecification SPEC = new RequestSpecBuilder()
            .addHeader("accept", "application/json")
            .setBaseUri("http://localhost:3000/").build();
}