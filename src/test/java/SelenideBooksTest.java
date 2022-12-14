import com.codeborne.selenide.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selectors.byTextCaseInsensitive;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class SelenideBooksTest {

    public SelenideBooksTest(){
        baseUrl = "https://demoqa.com/books ";
        WebDriverManager.chromedriver().setup();
        holdBrowserOpen = false;
        timeout = 5000;
        reportsFolder = "resources/Reports";
    }

    @Test
    public void booksFilterTest(){
        open("");

        List<SelenideElement> javascriptBooksList = $$(".books-wrapper [role=\"rowgroup\"]")
                .stream()
                .filter(el -> el.find("[role=\"gridcell\"]:nth-child(4)").getText().equals("O'Reilly Media"))
                .filter(el -> el.find("[role=\"gridcell\"]:nth-child(2)").getText().toLowerCase().contains("javascript"))
                .toList();
        ElementsCollection books = $$(javascriptBooksList);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(books.size(), 10);

        books
                .get(0)
                .find("[role=\"gridcell\"]:nth-child(2)")
                .shouldHave(Condition.text("Learning JavaScript Design Patterns1"));

        softAssert.assertAll();

        books
                .stream()
                .forEach(el -> el.find("[role=\"gridcell\"]:nth-child(2) a").click());

    }
}
