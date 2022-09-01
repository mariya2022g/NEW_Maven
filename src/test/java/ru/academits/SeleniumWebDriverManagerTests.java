package ru.academits;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Configuration.browser;
import static com.codeborne.selenide.Configuration.clickViaJs;

public class SeleniumWebDriverManagerTests {
    private WebDriver driver;


    @BeforeEach
    public void setUp() {
        String browser = System.getProperty("browser");

        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equals("opera")) {
            WebDriverManager.operadriver().setup();
            driver = new OperaDriver();
        } else if (Objects.equals(browser, "edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }


        driver.get("https://demoqa.com/automation-practice-form");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void authenticationFormsTest() throws InterruptedException {

        String firstName = "Ivan";
        String lastName = "Ivanov";
        String email = "Ivanov@yandex.ru";
        String mobile = "9135555555";
        String dateBirthday = "21 January,2000";
        String adress = "Novosibirsk, Dimitrova 6";
        String state = "Uttar Pradesh";
        String city = "Agra";
        String subject = "English";
        File file = new File("src/test/resources/img.png");

        WebElement inputFirstName = driver.findElement(By.id("firstName"));
        inputFirstName.sendKeys(firstName);

        WebElement inputLastName = driver.findElement(By.id("lastName"));
        inputLastName.sendKeys(lastName);

        WebElement inputEmail = driver.findElement(By.id("userEmail"));
        inputEmail.sendKeys(email);

        WebElement inputGender = driver.findElement(By.xpath("//*[@class='custom-control-label']"));
        inputGender.click();

        WebElement inputMobile = driver.findElement(By.xpath("//*[@id='userNumber']"));
        inputMobile.sendKeys(mobile);

        driver.findElement(By.id("dateOfBirthInput")).click();
        driver.findElement(By.cssSelector(".react-datepicker__month-select [value='0']")).click();
        driver.findElement(By.cssSelector(".react-datepicker__year-select [value='2000']")).click();
        driver.findElement(By.cssSelector("div[aria-label='Choose Friday, January 21st, 2000']")).click();

        WebElement subjectAutocomplete = driver.findElement(By.id("subjectsInput"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subjectAutocomplete);
        subjectAutocomplete.sendKeys(subject);
        subjectAutocomplete.sendKeys(Keys.ENTER);

        WebElement sportCheckBox = driver.findElement(By.cssSelector("div.custom-control.custom-checkbox.custom-control-inline:nth-child(1)"));
        sportCheckBox.click();
        WebElement readingCheckBox = driver.findElement(By.cssSelector("div.custom-control.custom-checkbox.custom-control-inline:nth-child(2)"));
        readingCheckBox.click();

        WebElement selectPictureButton = driver.findElement(By.id("uploadPicture"));
        selectPictureButton.sendKeys(file.getAbsolutePath());

        WebElement inputCurrentAddress = driver.findElement(By.cssSelector("div textarea"));
        inputCurrentAddress.sendKeys(adress);

        WebElement stateAutocomplete = driver.findElement(By.id("react-select-3-input"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", stateAutocomplete);
        stateAutocomplete.sendKeys(state);
        stateAutocomplete.sendKeys(Keys.ENTER);

        WebElement sityAutocomplete = driver.findElement(By.id("react-select-4-input"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", stateAutocomplete);
        sityAutocomplete.sendKeys(city);
        sityAutocomplete.sendKeys(Keys.ENTER);

        WebElement button = driver.findElement(By.id("submit"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        button.sendKeys(Keys.ENTER);

        String successfulMessage = driver.findElement(By.id("example-modal-sizes-title-lg")).getText();
        Assertions.assertEquals("Thanks for submitting the form", successfulMessage);

        WebElement StudentName = driver.findElement(By.xpath("//div[@class='modal-body']//table/tbody/tr[1]/td[2]"));
        WebElement StudentEmail = driver.findElement(By.xpath("//div[@class='modal-body']//table/tbody/tr[2]/td[2]"));
        WebElement StudentGender = driver.findElement(By.xpath("//div[@class='modal-body']//table/tbody/tr[3]/td[2]"));
        WebElement StudentMobile = driver.findElement(By.xpath("//div[@class='modal-body']//table/tbody/tr[4]/td[2]"));
        WebElement StudentDateBirth = driver.findElement(By.xpath("//div[@class='modal-body']//table/tbody/tr[5]/td[2]"));
        WebElement StudentSubjects = driver.findElement(By.xpath("//div[@class='modal-body']//table/tbody/tr[6]/td[2]"));
        WebElement StudentHobbies = driver.findElement(By.xpath("//div[@class='modal-body']//table/tbody/tr[7]/td[2]"));
        WebElement StudentPicture = driver.findElement(By.xpath("//div[@class='modal-body']//table/tbody/tr[8]/td[2]"));
        WebElement StudentAdress = driver.findElement(By.xpath("//div[@class='modal-body']//table/tbody/tr[9]/td[2]"));
        WebElement StudentStateAndCity = driver.findElement(By.xpath("//div[@class='modal-body']//table/tbody/tr[10]/td[2]"));

        Assertions.assertEquals(firstName + " " + lastName, StudentName.getText());
        Assertions.assertEquals(email, StudentEmail.getText());
        Assertions.assertEquals("Male", StudentGender.getText());
        Assertions.assertEquals(mobile, StudentMobile.getText());
        Assertions.assertEquals(dateBirthday, StudentDateBirth.getText());
        Assertions.assertEquals(subject, StudentSubjects.getText());
        Assertions.assertEquals("Sports, Reading", StudentHobbies.getText());
        Assertions.assertEquals("img.png", StudentPicture.getText());
        Assertions.assertEquals(adress, StudentAdress.getText());
        Assertions.assertEquals(state + " " + city, StudentStateAndCity.getText());

        Thread.sleep(6000);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}