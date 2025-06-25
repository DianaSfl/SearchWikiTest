package wikipedia.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchNonFound {
    private WebDriver driver;

    public SearchNonFound(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css="#ooui-php-1")
    private WebElement searchBar;


    @Step("Проверка, что страница - страница поиска")
    public boolean isSearchNonFoundElement() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return wait.until(ExpectedConditions.visibilityOf(searchBar)) != null;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
