import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.*;

public class SelenideTests {

    public SelenideTests() {
        baseUrl = "http://the-internet.herokuapp.com";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        Configuration.browserCapabilities = options;
        Configuration.browserSize = null;
        holdBrowserOpen = false;
        timeout = 10000;
    }

    @Test
    public void checkBoxTest(){
        open("/checkboxes");
        ElementsCollection checkboxes = $$("input[type=checkbox]");
        checkboxes.get(0).click();
        for (SelenideElement checkbox :
                checkboxes) {
            checkbox.shouldHave(type("checkbox"));
        }
    }

    @Test
    public void dropDownTest(){
        open("/dropdown");
        $("#dropdown").getSelectedOption().shouldHave(matchText("Please select an option"));
        $("#dropdown").selectOptionContainingText("Option 2");
        $("#dropdown").getSelectedOption().shouldHave(matchText("Option 2"),value("2"));
    }

    @Test
    public void textBoxTest(){
        open("https://demoqa.com/text-box");
        Faker faker = new Faker();
        String fullName = faker.name().fullName();
        $("#userName").setValue(fullName);
        String email = faker.internet().emailAddress();
        $("[type='email']").setValue(email);
        String currentAddress = faker.address().secondaryAddress();
        $("form#userForm").$("#currentAddress").setValue(currentAddress);
        String permanentAddress = faker.address().fullAddress();
        $(By.xpath("//textarea[@id=\"permanentAddress\"]")).setValue(permanentAddress);
        $("#submit").click();
        $$("#output p").shouldHave(
                exactTexts(
                        "Name:" + fullName,
                        "Email:" + email,
                        "Current Address :" + currentAddress,
                        "Permananet Address :" + permanentAddress
                )
        );
    }
}
