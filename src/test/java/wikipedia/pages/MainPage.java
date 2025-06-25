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
import java.util.List;

public class MainPage {
    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="searchInput")
    private WebElement searchInput;

    @FindBy(id="searchButton")
    private WebElement searchButton;

    @FindBy(css=".suggestions-results .suggestions-result")
    private List<WebElement> suggestionsResult;

    @FindBy(css="div.special-query")
    private WebElement specialQuery;

    @Step("Ввод поискового запроса: {query}")
    public void enterSearchQuery(String query){
        searchInput.sendKeys(query); //необходимо, чтобы появлялось поле с подсказкой,
        // иначе появляются только саджесты и тест на проверку подсказки ложно падает
        searchInput.clear();
        searchInput.sendKeys(query);
    }

    @Step("Нажатие кнопки поиск (лупа)")
    public void clickSearchBtn(){
        searchButton.click();
    }

    @Step ("Проверка, что существует саджест 'Поиск страниц, cодержащих'")
    public boolean isSpecialQuery() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return wait.until(ExpectedConditions.visibilityOf(specialQuery)) != null;
        } catch (TimeoutException e) {
            return false;
        }
    }


    @Step("Нажатие на 'Поиск страниц, содержащих'")
    public void clickSpecialQuery(){
        specialQuery.click();
    }

    @Step("Получение списка саджестов")
    public List<WebElement> getSuggestionResult(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfAllElements(suggestionsResult));
        return suggestionsResult;
    }

    @Step("Получение текста первого саджеста")
    public String textFirstSuggestion(){
        List<WebElement> suggestions = getSuggestionResult();
        return suggestions.get(0).getText();
    }

    @Step("Переход по первому саджесту")
    public void selectFirstSuggestion(){
        getSuggestionResult().get(0).click();
    }
}
