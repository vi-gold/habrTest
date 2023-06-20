package com.example.habrtest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPageTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.habr.com/");


    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @RepeatedTest(3)
    public void checkHeadText() {

        WebElement administrationLink = driver.findElement(By.xpath("//a[contains(text(), 'Администрирование')]"));
        String admLinkText = administrationLink.getText();
        administrationLink.click();

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(By.cssSelector("h1.tm-section-name__text"), "Все потоки"));

        WebElement header = driver.findElement(By.cssSelector("h1.tm-section-name__text"));
        assertEquals(admLinkText, header.getText(), "Названия ссылки и заголовка не совпадают");
    }

    @Test
    @RepeatedTest(3)
    public void checkTextInSearch() {

        WebElement searchIcon = driver.findElement(By.cssSelector("svg.tm-header-user-menu__icon_search"));
        searchIcon.click();

        assertTrue(driver.findElement(By.cssSelector("h2.tm-block__title")).isDisplayed(),
                "Заголовок \"Минуточку внимания\" отсутствует");
    }

}
