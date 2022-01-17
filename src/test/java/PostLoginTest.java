import io.restassured.response.Response;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import static requests.LoginEndpoint.postLoginRequest;

public class PostLoginTest extends TestBase{

    private User validUser1;

    @BeforeClass
    public void generateTestData(){
        validUser1 = new User("Francisco", "fran4@gmail.com", "1234@A", "false");
        postUserRequest(SPEC,validUser1);

    }

    @Test
    public void shouldReturnSuccessMessageAndStatusCode200(){
        Response loginUserResponse = postLoginRequest(SPEC,validUser1);
        loginUserResponse.
                then().
                    assertThat().
                    statusCode(200).
                body("message", equalTo("Login realizado com sucesso")).
                body("authorization", notNullValue());

    }

    @AfterClass
    public void removeTestData() {
        deleteUserRequest(SPEC, validUser1);
    }

}
