import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.testng.ScreenShooter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Configuration.*;
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
                .shouldHave(text("Learning JavaScript Design Patterns"));

        books
                .stream()
                .forEach(el -> {el.$("[role=\"gridcell\"]", 1).$("a").scrollTo().click();back();} );

        softAssert.assertAll();
    }

    @Test
    public void innerElementTest(){
        open("");
        ElementsCollection javascriptBooks = $(".books-wrapper").$$("[role=\"rowgroup\"]");
        List<SelenideElement> javascriptBooksList = javascriptBooks
                .stream()
                .filter(el -> el.$("[role=\"gridcell\"]", 3).getText().equals("O'Reilly Media"))
                .filter(el -> el.$("[role=\"gridcell\"]",1).getText().toLowerCase().contains("javascript"))
                .toList();

        List<SelenideElement> eachBook = javascriptBooksList
                .stream()
                        .filter(el -> el.lastChild().$(".rt-td", 0).lastChild().getText().equals("img")).toList();

        for (SelenideElement selectedBooks :
                eachBook) {
            selectedBooks.$(".rt-td",0).lastChild().shouldHave(attribute("src"));
        }
    }
}

