package requests;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Product;
import models.User;
import static io.restassured.RestAssured.given;

public class ProductEndpoint extends RequestBase{

    public static Response postProductRequest(RequestSpecification spec, Product product, User user) {
        Response postProductResponse =
                given().
                        spec(spec).
                        header("Authorization", user.bearerToken).
                        header("Content-Type","application/json").
                        and().
                        body(product.getProductInformation()).
                        when().
                        post("produtos");

        product.setProductID(getValueFromResponse(postProductResponse, "_id"));
        return postProductResponse;
    }

    public static Response getProductRequest(RequestSpecification spec, Product product, User user) {
        Response getProductResponse =
                given().
                        spec(spec).
                        header("Authorization", user.bearerToken).
                        when().
                        get("produtos/" + product.id);
        return getProductResponse;
    }

    public static Response getAllProductsRequest(RequestSpecification spec, User user){
        Response getAllProductsResponse =
                given().
                        spec(spec).
                        header("Authorization", user.bearerToken).
                        when().
                        get("produtos/");
            return getAllProductsResponse;
        }

    public static Response deleteProductRequest(RequestSpecification spec, Product product, User user) {
        Response deleteProductResponse =
                given().
                        spec(spec).
                        header("Authorization", user.bearerToken).
                        pathParam("_id", product.id).
                        when().
                        delete("produtos/{_id}");
        return deleteProductResponse;

    }
}

