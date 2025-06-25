package wikipedia.tests;


import io.qameta.allure.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import wikipedia.pages.MainPage;
import wikipedia.pages.SearchNonFound;
import wikipedia.pages.SearchQueryPage;
import wikipedia.utils.TestDataProperties;

@DisplayName("Проверка корректной реакции поиска на запросы в Википедии")
public class SearchNavigationTest extends BaseTest{

    public SearchQueryPage searchQueryPage;
    public SearchNonFound searchNonFound;


    @Test
    @DisplayName("Проверка перехода на страницу статьи при существующем запросе")
    @Description(value = "Тест проверяет, что при выполнении поиска (нажатие лупы) по существующему запросу появляется статья")
    public void ValidSearchQueryOpenArticleTest() {
        mainPage = new MainPage(driver);
        String searchQuery = TestDataProperties.getProperty("existSearchQuery");

        mainPage.enterSearchQuery(searchQuery);
        String expectedTitle = mainPage.textFirstSuggestion();
        mainPage.clickSearchBtn();

        SearchQueryPage searchQueryPage = new SearchQueryPage(driver);
        String actualTitle = searchQueryPage.getPageTitle();

        Assertions.assertEquals(expectedTitle, actualTitle,"Переход по валидному поисковому запросу не привел на страницу со статьей");
    }

    @Test
    @DisplayName("Проверка перехода на страницу поиска при несуществующем запросе")
    @Description(value = "Тест проверяет, что при выполнении поиска (нажатие лупы) по несуществующему запросу открывается страница поиска")
    public void InvalidSearchOpenNonFoundPageTest() {
        mainPage = new MainPage(driver);
        String searchQuery = TestDataProperties.getProperty("noExistSearchQuery");

        mainPage.enterSearchQuery(searchQuery);
        mainPage.clickSearchBtn();

        SearchNonFound searchNonFound = new SearchNonFound(driver);
        boolean actualTitle = searchNonFound.isSearchNonFoundElement();
        Assertions.assertTrue(actualTitle, "Переход по невалидному поисковому запросу не привел на страницу с поиском");
    }
}
