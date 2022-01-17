import io.restassured.response.Response;
import models.Product;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static requests.LoginEndpoint.postLoginRequest;
import static requests.ProductEndpoint.*;

public class GetProductTest extends TestBase{

    private User validUser1;
    private Product validProduct;
    private Product product;


    @BeforeClass
    public void generateTestData() {
        validUser1 = new User("sandrine", "sandrine@email.com", "123Aa@", "true");
        validProduct = new Product("Cadeiras", 500, "Marrom", 5);
        product = new Product("Garrafas", 500, "Marrom", 5);
        postUserRequest(SPEC, validUser1);
        postLoginRequest(SPEC, validUser1);
        postProductRequest(SPEC, validProduct, validUser1);

    }

    @Test
    public void shouldReturnProductByIdAndStatus200(){
        Response getProductResponse = getProductRequest(SPEC, validProduct, validUser1);
        getProductResponse.
                then().
                assertThat().
                body("_id", equalTo(validProduct.getProductID())).
                statusCode(200);
    }

    @Test
    public void shouldReturnAllProductsAndStatusCode200(){
        Response getAllProductsResponse = getAllProductsRequest(SPEC, validUser1);
        getAllProductsResponse.
                then().
                assertThat().
                statusCode(200).
                body("quantidade", is(3));
    }

    @Test
    public void shouldReturnProductNotFoundAndStatus400(){
        Response getProductResponse = getProductRequest(SPEC, product, validUser1);
        getProductResponse.
                then().
                assertThat().
                statusCode(400).
                body("message", equalTo("Produto não encontrado"));
    }

    @AfterClass
    public void removeTestData() {
       deleteProductRequest(SPEC, validProduct, validUser1);
       deleteUserRequest(SPEC, validUser1);
    }

}
