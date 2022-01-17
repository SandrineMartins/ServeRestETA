
import io.restassured.response.Response;
import models.Product;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static requests.LoginEndpoint.postLoginRequest;
import static requests.ProductEndpoint.*;


public class PostProductTest extends TestBase {
    private User NonAdminUser;
    private User AdminUser;
    private Product validProduct;
    private Product invalidProduct;

    @BeforeClass
    public void generateTestData() {
        NonAdminUser = new User("sandrine", "san@email.com", "123Aa@", "false");
        AdminUser = new User("sandrine2", "san2@email.com", "123Aa@", "true");;
        validProduct = new Product("Mesa", 1000, "Marrom", 10);
        invalidProduct = new Product("Samsung 60 polegadas", 5240, "TV",49977);
        postUserRequest(SPEC, NonAdminUser);
        postLoginRequest(SPEC, NonAdminUser);
        postUserRequest(SPEC, AdminUser);
        postLoginRequest(SPEC, AdminUser);
    }

    @Test
    public void shouldReturnSuccessMessageNewProductAndStatusCode201() {
        Response postProductResponse = postProductRequest(SPEC, validProduct, AdminUser);
        postProductResponse.
                then().log().body().
                assertThat().
                statusCode(201).
                body("message", equalTo("Cadastro realizado com sucesso"));
    }

    @Test
    public void shouldReturnErrorExistingProductAndStatusCode401() {
        Response postProductResponse = postProductRequest(SPEC, invalidProduct, AdminUser);
        postProductResponse.
                then().
                assertThat().
                statusCode(400).
                body("message", equalTo("Já existe produto com esse nome"));
    }

    @Test
    public void shouldReturnErrorMessageAndStatus403() {
        Response ProductFailAdmin = postProductRequest(SPEC, validProduct, NonAdminUser);
        ProductFailAdmin.
                then().
                assertThat().
                statusCode(403).
                body("message", equalTo("Rota exclusiva para administradores"));
    }

    @AfterClass
    public void removeTestData() {
        deleteProductRequest(SPEC, validProduct, AdminUser);
        deleteUserRequest(SPEC, AdminUser);
        deleteUserRequest(SPEC, NonAdminUser);
    }

}