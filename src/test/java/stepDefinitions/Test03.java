package stepDefinitions;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;

public class Test03 {

    static RequestSpecification spec;

    @Test
    public void test01() {

        ///api/ipdList endpoint'ine gecerli authorization bilgileri ile bir GET request gönderildiginde
        // dönen status code'un 200 oldugu ve
        // response message bilgisinin "Success" oldugu dogrulanmali.
        //http://www.heallifehospital.com/api/ipdList

        spec = new RequestSpecBuilder().setBaseUri("http://www.heallifehospital.com").build(); // ana sayfa oluşturuldu

        spec.pathParams("pp1","api","pp2","ipdList");// parametreler oluşturuldu

        String fullPath = "/{pp1}/{pp2}"; // parametre girişi için kolay bir string oluşturuldu

        String token = "KfkdYtea9caPXvcuA7X19FfYiiV5uQ";

        Response response = given()
                .contentType(ContentType.JSON)  // gönderdiğim veriler json formatında
                .spec(spec) // olşturduğum ( spec isimli obje )base url ve parametreleri kullanacağım
                .headers(
                        "Authorization","Bearer "+token, // gerekli authorization bilgisi giriş satırı
                        "Content-Type", ContentType.JSON,  // gönderdiğim bilgilerJson formatında
                        "Accept",ContentType.JSON       // bana cevabı ( response) Json formatında gönder
                )
                .when()
                //.body(reqBody.toString())  // request içinde body varsa
                .log().all()            // oluşturuduğumuz requesti toplu halde görmek için
                .get(fullPath);        // parametrelerle beraber, request type girişi

        System.out.println("************ Response **************");
        response.prettyPrint();

        response.then().assertThat().statusCode(200).body("message", Matchers.equalTo("Success"));
    }
}
