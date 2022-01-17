import models.User;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class GetUserTest extends TestBase{
    private User validUser1;
    private User validUser2;

    @BeforeClass
    public void generateTestData(){
        validUser1 = new User("Francisco", "fran@gmail.com", "1234@A", "false");
        postUserRequest(SPEC,validUser1);
        validUser2 = new User("Joana", "joana@gmail.com", "1234@B", "false");
        postUserRequest(SPEC,validUser2);
    }

    @DataProvider(name = "userQueryData")
    public Object[][] createQueryData(){
        return new Object[][]{
                {"nome", validUser1.nome},
                {"email", validUser1.email}

        };
    }

    @Test
    public void shouldReturnAllUserAndStatusCode200() {
        Response getUserResponse = getUsersRequest(SPEC);
        getUserResponse.
                then().
                assertThat().
                statusCode(200).
                body("quantidade", is(2)).
                body("quantidade", instanceOf(Integer.class));

    }

    @Test(dataProvider = "userQueryData")
    public void shouldReturnUserForQueryAndSatusCode200(String query, String queryValue){
        SPEC.queryParam(query, queryValue);
        Response getUserResponse = getUsersRequest(SPEC);
        //getUserResponse.then().log().all();

        FilterableRequestSpecification filterableRequestSpecification = (FilterableRequestSpecification) SPEC;
        filterableRequestSpecification.removeQueryParam(query);
    }

    @Test
    public void shouldReturnUserAndStatusCode200() {
        Response getUserResponse = getUsersRequest(SPEC);
    }

    @AfterClass
    public void removeTestData() {
        deleteUserRequest(SPEC, validUser1);
    }
}
