package stepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Assert;
import pojos.JsonPlaceHolderPojo;

import static io.restassured.RestAssured.given;

public class JsonplaceholderStepDefinitions {
    JsonPlaceHolderPojo jsonPlaceHolderPojo;
    JSONObject expData;
    RequestSpecification spec;
    String fullPath;
    Response response;
    @Given("API kullanicisi url ile birlikte iki parametreli giris yapar parametreler {string} ve {int}")
    public void apı_kullanicisi_url_ile_birlikte_iki_parametreli_giris_yapar_parametreler_ve(String pp1, Integer pp2) {

        spec = new RequestSpecBuilder().setBaseUri("https://jsonplaceholder.typicode.com").build();
        spec.pathParams("pp1",pp1,"pp2",pp2);
        fullPath = "/{pp1}/{pp2}";
    }
    @When("API kullanici request body ile put request yapar")
    public void apı_kullanici_request_body_ile_put_request_yapar() {

        JSONObject reqBody = new JSONObject(); // ana request body oluşturuldu
        reqBody.put("title","Ahmet");
        reqBody.put("body","Merhaba");
        reqBody.put("userId",10);
        reqBody.put("id",70);

        response = given()
                .contentType(ContentType.JSON)  // gönderdiğim veriler json formatında
                .spec(spec) // olşturduğum ( spec isimli obje )base url ve parametreleri kullanacağım
                .headers(
                        "Content-Type", ContentType.JSON,  // gönderdiğim bilgilerJson formatında
                        "Accept",ContentType.JSON       // bana cevabı ( response) Json formatında gönder
                )
                .when()
                .body(reqBody.toString())  // request içinde body varsa
                .log().all()            // oluşturuduğumuz requesti toplu halde görmek için
                .put(fullPath);        // parametrelerle beraber, request type girişi



    }
    @When("Excepted Data olusturulur")
    public void excepted_data_olusturulur() {
        expData = new JSONObject();
        expData.put("title","Ahmet");
        expData.put("body","Merhaba");
        expData.put("userId",10);
        expData.put("id",70);
    }

    @Then("Excepted Data ile response ayni mi dogrulanir")
    public void excepted_data_ile_response_ayni_mi_dogrulanir() {

        jsonPlaceHolderPojo = response.as(JsonPlaceHolderPojo.class);
        System.out.println(jsonPlaceHolderPojo);

        Assert.assertEquals(expData.get("title"),jsonPlaceHolderPojo.getTitle());
        Assert.assertEquals(expData.get("body"),jsonPlaceHolderPojo.getBody());
        Assert.assertEquals(expData.get("userId"),jsonPlaceHolderPojo.getUserId());
        Assert.assertEquals(expData.get("id"),jsonPlaceHolderPojo.getId());

    }
}
