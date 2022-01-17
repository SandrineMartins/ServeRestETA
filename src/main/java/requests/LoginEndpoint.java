package requests;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import models.User;

public class LoginEndpoint extends RequestBase {

    public static io.restassured.response.Response postLoginRequest(RequestSpecification spec, User user) {
        Response postLoginResponse =
                given().
                        spec(spec).
                        header("Content-Type", "application/json").
                        and().
                        body(user.getUserCredentials()).
                        when().
                            post("login");

        user.setUserBearerToken(getValueFromResponse(postLoginResponse, "authorization"));
        return postLoginResponse;
    }

  }
