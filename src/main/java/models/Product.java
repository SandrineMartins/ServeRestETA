package models;
import com.google.gson.JsonObject;

public class Product {

    public String nome;
    public Integer preco;
    public String descricao;
    public Integer quantidade;
    public String id;

    public Product(String nome, Integer preco, String descricao, Integer quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public void setProductID(String id) {
        this.id = id;
    }

    public String getProductID(){
        return id;
    }

    public String getProductInformation() {
        JsonObject productJsonRepresentation = new JsonObject();
        productJsonRepresentation.addProperty ("nome", this.nome);
        productJsonRepresentation.addProperty("preco", this.preco);
        productJsonRepresentation.addProperty("descricao", this.descricao);
        productJsonRepresentation.addProperty("quantidade", this.quantidade);
        return productJsonRepresentation.toString();
    }

}
