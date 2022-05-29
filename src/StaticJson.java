import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.testng.annotations.Test;
import files.ReUsableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class StaticJson {

@Test
public void addBook() throws IOException {


RestAssured.baseURI="http://216.10.245.166";

    //addBookAPI

String resp=given().
header("Content-Type","application/json").
body(GenerateStringFromResource("C:\\Users\\pranith\\Documents\\Addbookdetails.json")).
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

public static String GenerateStringFromResource(String path) throws IOException {


    return new String(Files.readAllBytes(Paths.get(path)));

  }

}

