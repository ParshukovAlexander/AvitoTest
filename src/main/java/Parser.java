import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static int searchElements(WebDriver driver) {
        int page = serchFinalPage(driver);
        List<WebElement> elementsAll = new ArrayList<>();
        for (int i = 0; i < page; i++) {
            elementsAll.addAll(driver.findElements(By.className("iva-item-body-KLUuy")));
            if (i < page - 1) searchNextButton(driver).click();
        }
        return elementsAll.size();
    }

    private static WebElement searchNextButton(WebDriver driver) {
        List<WebElement> elements = driver.findElements(By.xpath("//*[@class='pagination-item-JJq_j pagination-item_arrow-Sttbt']"));
        return elements.get(elements.size() - 1);
    }

    private static int serchFinalPage(WebDriver driver) {
        List<WebElement> elements = driver.findElements(By.className("pagination-item-JJq_j"));
        return Integer.parseInt(elements.get(elements.size() - 2).getText());
    }
}
