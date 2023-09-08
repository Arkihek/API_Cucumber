package stepDefinations;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
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

        //1- Url ve reqBody hazirlama
        spec = new RequestSpecBuilder().setBaseUri("https://restful-booker.herokuapp.com").build();

        spec.pathParams("pp1","booking");

        String fullPath = "/{pp1}";

        JSONObject bookingdates = new JSONObject(); // Inner objemiz
        bookingdates.put("checkin","2021-06-01");
        bookingdates.put("checkout","2021-06-10");

        JSONObject reqBody = new JSONObject();
        reqBody.put("firstname","Ahmet");
        reqBody.put("lastname","Bulut");
        reqBody.put("totalprice",500);
        reqBody.put("depositpaid",false);
        reqBody.put("bookingdates",bookingdates);
        reqBody.put("additionalneeds","wi-fi");

        Response response = given()
                .contentType(ContentType.JSON)
                .spec(spec)
                .headers(
                        "Content-Type",ContentType.JSON,
                        "Accept",ContentType.JSON
                )
                .when()
                .log().all()
                .body(reqBody.toString())
                .post(fullPath);










    }
}
