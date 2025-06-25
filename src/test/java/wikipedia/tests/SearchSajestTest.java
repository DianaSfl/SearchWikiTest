package wikipedia.tests;


import io.qameta.allure.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import wikipedia.pages.SearchNonFound;
import wikipedia.pages.SearchQueryPage;
import wikipedia.utils.TestDataProperties;

import java.util.List;

@DisplayName("Проверка саджестов поиска в Википедии")
public class SearchSajestTest extends BaseTest{
    public SearchQueryPage searchQueryPage;


    @Test
    @DisplayName("Проверка соответствия начала саджестов поисковому запросу")
    @Description(value = "Тест проверяет, что начало саджестов соответствует поисковому запросу")
    public void SajestStartWithSearchQueryTest() {
        String searchQuery = TestDataProperties.getProperty("existSearchQuery");

        mainPage.enterSearchQuery(searchQuery);
        List<WebElement> suggestions = mainPage.getSuggestionResult();

        Assertions.assertFalse(suggestions.isEmpty(), "Саджесты не отобразились");

        for (WebElement suggestion : suggestions) {
            String suggestionText = suggestion.getText();
            Assertions.assertTrue(suggestionText.startsWith(searchQuery),
                    "Саджест '" + suggestionText +
                            "' не начинается с '" + searchQuery + "'");
        }
    }

    @Test
    @DisplayName("Проверка выделения жирным начала саджестов, соответствующего поисковому запросу")
    @Description(value = "Тест проверяет, что содержание поискового запроса в саджестах выделено жирным ")
    public void SearchQueryInSajestInBoldTest() {
        String searchQuery = TestDataProperties.getProperty("existSearchQuery");

        mainPage.enterSearchQuery(searchQuery);
        List<WebElement> suggestions = mainPage.getSuggestionResult();

        Assertions.assertFalse(suggestions.isEmpty(), "Саджесты не отобразились");

        for (WebElement suggestion : suggestions) {
            String innerHTML = suggestion.getAttribute("innerHTML");
            Assertions.assertNotNull(innerHTML);
            Assertions.assertTrue(innerHTML.contains("<span class=\"highlight\">" + searchQuery + "</span>"),
                    "Поисковый запрос внутри саджеста не выделяется жирным");
        }
    }

    @Test
    @DisplayName("Проверка соответствия названия статьи содержанию саджеста")
    @Description(value = "Тест проверяет, что название страницы статьи соответствует выбранному саджесту")
    public void ClickSajestOpenCorrectPageTest() {
        String searchQuery = TestDataProperties.getProperty("existSearchQuery");
        mainPage.enterSearchQuery(searchQuery);

        String expectedTitle = mainPage.textFirstSuggestion();
        mainPage.selectFirstSuggestion();

        SearchQueryPage searchQueryPage = new SearchQueryPage(driver);
        String actualTitle = searchQueryPage.getPageTitle();

        Assertions.assertEquals(expectedTitle, actualTitle, "Название саджеста не соответствует названию страницы");
    }

    @Test
    @DisplayName("Проверка существования подсказки 'Поиск страниц, cодержащих'")
    @Description(value = "Тест проверяет, что при вводе поискового запроса существует подсказка 'Поиск страниц, cодержащих'")
    public void ExistsHintTest(){
        String searchQuery = TestDataProperties.getProperty("existSearchQuery");

        mainPage.enterSearchQuery(searchQuery);

        boolean isHint = mainPage.isSpecialQuery();
        Assertions.assertTrue(isHint, "Поле с подсказкой 'Поиск страниц, cодержащих' не появилось");

    }

    @Test
    @DisplayName("Проверка перехода на страницу поиска при нажатии подсказки 'Поиск страниц, cодержащих'")
    @Description(value = "Тест проверяет, что при нажатии подсказки 'Поиск страниц, cодержащих' происходит переход на страницу поиска")
    public void ClicksHintTest(){
        String searchQuery = TestDataProperties.getProperty("existSearchQuery");

        mainPage.enterSearchQuery(searchQuery);

        mainPage.clickSpecialQuery();
        SearchNonFound searchNonFound = new SearchNonFound(driver);
        boolean isSearch = searchNonFound.isSearchNonFoundElement();
        Assertions.assertTrue(isSearch, "При нажатии подсказки 'Поиск страниц, cодержащих' не произошло перехода на страницу поиска");
    }
}
