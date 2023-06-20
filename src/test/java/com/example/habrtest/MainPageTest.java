package com.example.habrtest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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
    public void checkHeadText() {

        WebElement administrationLink = driver.findElement(By.xpath("//a[contains(text(), 'Администрирование')]"));
        administrationLink.click();

        WebElement header = driver.findElement(By.cssSelector("h1.tm-section-name__text"));
        System.out.println(administrationLink.getText());
        System.out.println(header.getText());
        assertEquals(administrationLink.getText(), header.getText(), "Названия ссылки и заголовка не совпадают");
    }

    @Test
    public void checkComments() {

        WebElement news = driver.findElement(By.cssSelector("a[data-test-id = 'tab-link-active']"));
        news.click();

        assertTrue(driver.findElement(By.xpath("//h2[contains(text(), 'Читают сейчас')]")).isDisplayed(),
                "Блок \"Читают сейчас\" отсутствует");
    }

}
