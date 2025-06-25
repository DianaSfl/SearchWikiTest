package wikipedia.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import wikipedia.pages.MainPage;
import wikipedia.utils.ConfProperties;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected MainPage mainPage;

    @BeforeEach
    public void setUp()
    {
       System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
       driver = new ChromeDriver();

       driver.manage().window().maximize();
       driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
       driver.get(ConfProperties.getProperty("linkMainPage"));

       this.mainPage = new MainPage(driver);
    }

    @AfterEach
    public void tearDown()
    {
        driver.quit();
    }
}
