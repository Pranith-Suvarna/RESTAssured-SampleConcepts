import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import files.ReUsableMethods;
import files.payload;


public class DynamicJson {


@Test(dataProvider="BooksData")
public void addBook(String isbn,String aisle){

RestAssured.baseURI="http://216.10.245.166";

    //addBookAPI

String resp=given().
header("Content-Type","application/json").
body(payload.Addbook(isbn,aisle)).
when().
post("/Library/Addbook.php").
then().assertThat().statusCode(200).
extract().response().asString();

JsonPath js= ReUsableMethods.rawToJson(resp);
String id=js.get("ID");

System.out.println(id);

   
   //deleteBookAPI

String delresp=given().
header("Content-Type","application/json").
body("{\r\n"
		+ "    \"ID\": \""+ id +"\"\r\n"
		+ "}").
when().
post("/Library/DeleteBook.php").
then().assertThat().statusCode(200).
extract().response().asString();

JsonPath js1= ReUsableMethods.rawToJson(delresp);
String msg=js1.get("msg");

System.out.println(msg);

}


@DataProvider(name="BooksData")
public Object[][]  getData() {

//array=collection of elements
//multidimensional array= collection of arrays

return new Object[][] {{"xyz","93763"},{"abc","42753"}, {"jhddj","5337"}};

 }



}