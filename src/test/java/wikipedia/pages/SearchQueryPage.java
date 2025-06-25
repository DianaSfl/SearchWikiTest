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

public class SearchQueryPage {
    private WebDriver driver;

    public SearchQueryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css=".mw-page-title-main")
    private WebElement titleSearchQuery;

    @Step("Получение названия статьи")
    public String getPageTitle() {
        return titleSearchQuery.getText();
    }

    @Step("Проверка, что страница - страница статьи")
    public boolean isTitleElementPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return wait.until(ExpectedConditions.visibilityOf(titleSearchQuery)) != null;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
