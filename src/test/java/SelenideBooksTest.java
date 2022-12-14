import com.codeborne.selenide.*;
import com.codeborne.selenide.testng.ScreenShooter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class SelenideBooksTest {

    public SelenideBooksTest(){
        baseUrl = "https://demoqa.com/books ";
        WebDriverManager.chromedriver().setup();
        holdBrowserOpen = false;
        timeout = 5000;
        reportsFolder = "resources/Reports";
        ScreenShooter.captureSuccessfulTests = false;
        savePageSource = false;
    }

    @Test
    public void booksFilterTest(){
        open("");

        List<SelenideElement> javascriptBooksList = $(".books-wrapper")
                .$$("[role=\"rowgroup\"]")
                .stream()
                .filter(el -> el.$("[role=\"gridcell\"]", 3).getText().equals("O'Reilly Media"))
                .filter(el -> el.$("[role=\"gridcell\"]",1).getText().toLowerCase().contains("javascript"))
                .toList();
        ElementsCollection books = $$(javascriptBooksList);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(books.size(), 10);

        books
                .get(0)
                .$("[role=\"gridcell\"]",1)
                .shouldHave(Condition.text("Learning JavaScript Design Patterns"));

        books
                .stream()
                .forEach(el -> el.$("[role=\"gridcell\"]",1).$("a").click());

        softAssert.assertAll();
    }

//    @Test
//    public void innerElementTest(){
//        open("");
//        List<SelenideElement> javascriptBooksList = $(".books-wrapper")
//                .$$("[role=\"rowgroup\"]")
//                .stream()
//                .filter(el -> el.$("[role=\"gridcell\"]", 3).getText().equals("O'Reilly Media"))
//                .filter(el -> el.$("[role=\"gridcell\"]",1).getText().toLowerCase().contains("javascript"))
//                .toList();
//        ElementsCollection books = $$(javascriptBooksList);
//        books
//                .stream()
//                .filter(el -> el.$("img").getAttribute("src").length()>0);
//    }
}
