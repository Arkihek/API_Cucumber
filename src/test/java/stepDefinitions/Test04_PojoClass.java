package stepDefinitions;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Assert;
import org.testng.annotations.Test;
import pojos.Jsonpojo;
import pojos.heroPojo;

import static io.restassured.RestAssured.given;

public class Test04_PojoClass {

    static RequestSpecification spec;
    @Test
    public void test01(){
                        /*
https://restful-booker.herokuapp.com/booking url’ine asagidaki body’ye sahip bir POST
request gonderdigimizde
{
  “firstname” : “Ahmet”,
  “lastname” : “Bulut”,
  “totalprice” : 500,
  “depositpaid” : false,
  “bookingdates” : {
                    “checkin” : “2021-06-01”,
                    “checkout” : “2021-06-10”
                    },
   “additionalneeds” : “wi-fi”
}
            donen Response’un,
            status code’unun 200,
            ve content type’inin application-json, ve response body’sindeki
            “firstname”in,“Ahmet”, ve “lastname”in, “Bulut”,
            ve “totalprice”in,500,
            ve “depositpaid”in,false,
            ve “checkin” tarihinin 2021-06-01 ve “checkout” tarihinin 2021-06-10 ve “additionalneeds”in,“wi-fi” olduğunu test edin (edited)
     */
        spec = new RequestSpecBuilder().setBaseUri("https://restful-booker.herokuapp.com").build(); // ana sayfa oluşturuldu

        spec.pathParams("pp1","booking");// parametreler oluşturuldu

        String fullPath = "/{pp1}"; // parametre girişi için kolay bir string oluşturuldu

        JSONObject innerBody = new JSONObject();  // kucuk objemiz
        innerBody.put("checkin","2021-06-01");
        innerBody.put("checkout","2021-06-10");

        JSONObject reqBody = new JSONObject(); // ana request body oluşturuldu
        reqBody.put("firstname","Ahmet");
        reqBody.put("lastname","Bulut");
        reqBody.put("totalprice",500);
        reqBody.put("depositpaid",false);
        reqBody.put("bookingdates",innerBody);
        reqBody.put("additionalneeds","wi-fi");
        Response response = given()
                .contentType(ContentType.JSON)  // gönderdiğim veriler json formatında
                .spec(spec) // olşturduğum ( spec isimli obje )base url ve parametreleri kullanacağım
                .headers(
                        "Content-Type", ContentType.JSON,  // gönderdiğim bilgilerJson formatında
                        "Accept",ContentType.JSON       // bana cevabı ( response) Json formatında gönder
                )
                .when()
                .body(reqBody.toString())  // request içinde body varsa
                .log().all()            // oluşturuduğumuz requesti toplu halde görmek için
                .post(fullPath);        // parametrelerle beraber, request type girişi
        System.out.println("************ Response **************");
        response.prettyPrint();

        heroPojo responseHeroPojo = response.as(heroPojo.class);
        System.out.println(responseHeroPojo);

        Assert.assertEquals("Ahmet",responseHeroPojo.getBooking().getFirstname());
        Assert.assertEquals("Bulut",responseHeroPojo.getBooking().getLastname());
        Assert.assertEquals(500,responseHeroPojo.getBooking().getTotalprice());
        Assert.assertEquals(false,responseHeroPojo.getBooking().isDepositpaid());
        Assert.assertEquals("2021-06-01",responseHeroPojo.getBooking().getBookingdates().getCheckin());
        Assert.assertEquals("2021-06-10",responseHeroPojo.getBooking().getBookingdates().getCheckout());
        Assert.assertEquals("wi-fi",responseHeroPojo.getBooking().getAdditionalneeds());

        // ONEMLI PUFF NOKTA
        // Response`i "https://www.jsonschema2pojo.org" sitesinden olusturdugumuz pojo classlar ile test edelim
        Jsonpojo responseJsonObject = response.as(Jsonpojo.class);
        Assert.assertEquals("Ahmet",responseJsonObject.booking.firstname);

    }

}
