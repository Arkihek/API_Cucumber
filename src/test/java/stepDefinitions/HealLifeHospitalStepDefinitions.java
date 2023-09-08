package stepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;

import static io.restassured.RestAssured.given;

public class HealLifeHospitalStepDefinitions {
    String token;
    RequestSpecification spec;
    String fullPath;
    Response response;
    @Given("API kullanici sisteme admin olarak giris yapar")
    public void apı_kullanici_sisteme_admin_olarak_giris_yapar() {

        token = "y5GJTAXSS35LdhbUBIuRvNyvGFpJ9k";

    }

    @Given("API kullanicisi url ile birlikte iki parametreli giris yapar parametreler {string} ve {string}")
    public void apı_kullanicisi_url_ile_birlikte_iki_parametreli_giris_yapar_parametreler_ve(String pp1, String pp2) {

        spec = new RequestSpecBuilder().setBaseUri("http://www.heallifehospital.com").build(); // ana sayfa oluşturuldu

        spec.pathParams("pp1",pp1,"pp2",pp2);// parametreler oluşturuldu

        fullPath = "/{pp1}/{pp2}"; // parametre girişi için kolay bir string oluşturuldu

    }

    @When("API kullanici bodysiz get request yapar")
    public void apı_kullanici_bodysiz_get_request_yapar() {

        response = given()
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

    }

    @Then("API kullanici istenen bilgileri test eder")
    public void apı_kullanici_istenen_bilgileri_test_eder() {

        response.then().assertThat().statusCode(200).body("message", Matchers.equalTo("Success"));

    }
}
