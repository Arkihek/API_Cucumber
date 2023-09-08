package stepDefinitions;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class Test01 {


    static RequestSpecification spec;

    public static void main(String[] args) {

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

        //1- Anasayfa ve reqBody hazirlama
        spec = new RequestSpecBuilder().setBaseUri("https://restful-booker.herokuapp.com").build(); // Anasayfa olusturuldu

        spec.pathParams("pp1","booking"); // Parametreler olusturuldu

        String fullPath = "/{pp1}"; // parametre girisi icin string`e atama yaptik

        JSONObject bookingdates = new JSONObject(); // Inner objemiz
        bookingdates.put("checkin","2021-06-01");
        bookingdates.put("checkout","2021-06-10");

        JSONObject reqBody = new JSONObject(); // ana request body olusturuldu
        reqBody.put("firstname","Ahmet");
        reqBody.put("lastname","Bulut");
        reqBody.put("totalprice",500);
        reqBody.put("depositpaid",false);
        reqBody.put("bookingdates",bookingdates);
        reqBody.put("additionalneeds","wi-fi");

        Response response = given()
                .contentType(ContentType.JSON) // gonderdigim veriler JSON formatinda
                .spec(spec) // olustudugum (spec isimli obje) base url ve parametreleri kullanacagim
                .headers(
                        "Content-Type",ContentType.JSON, // Gonderdigim bilgiler JSON formatinda
                        "Accept",ContentType.JSON // Bana cevabi (response) Json formatinda gonder
                )
                .when()
                .body(reqBody.toString()) // Request icinde body varsa girilir
                .log().all() // olusturdugumuz Request`i toplu halde gormek icin yazdim
                .post(fullPath); // Post islemini ve parametre bilgilerimi girdim

        response.prettyPrint();

        response
                .then()
                .assertThat()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .body("booking.firstname", Matchers.equalTo("Ahmet"))
                .body("booking.lastname", Matchers.equalTo("Bulut"))
                .body("booking.totalprice", Matchers.equalTo(500))
                .body("booking.depositpaid", Matchers.equalTo(false))
                .body("booking.bookingdates.checkin", Matchers.equalTo("2021-06-01"))
                .body("booking.bookingdates.checkout", Matchers.equalTo("2021-06-10"))
                .body("booking.additionalneeds", Matchers.equalTo("wi-fi"));












    }
}
