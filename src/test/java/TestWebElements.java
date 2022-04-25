import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestWebElements {
    public WebDriver driver;
    @Before
    public void  setUp(){
        System.setProperty("webdriver.gecko.driver","/geckodriver.exe");
        driver = new FirefoxDriver();
        driver.get("https://www.avito.ru/permskiy_kray/avtomobili");
    }
    @Test
    public void startTest(){
        String title = driver.findElement(By.xpath("//*[@class='page-title-text-tSffu page-title-inline-zBPFx']"))
                .getText();
        Assert.assertEquals("Купить автомобиль в Пермском крае",title);
    }
    @Test
    public void PriceTest(){
//Вводим цену от 10000 до 20000 и сравниваем количество элементов с количеством в названии
        String path="/html/body/div[1]/div[4]/div[3]/div[1]/div/div[2]/div[1]/"+
                "form/div[5]/div/div[2]/div/div/div/div/div/div/";
        driver.findElement(By.xpath(path+"label[1]/input")).sendKeys("10000");
        driver.findElement(By.xpath(path+"label[2]/input")).sendKeys("50000");
        driver.findElement(By.xpath("/html/body/div[1]/div[4]/div[3]/div[1]/div/div[2]/div[2]/div/button[1]")).click();
        int res = Parser.searchElements(driver);
        int count =Integer.parseInt(driver.findElement(By.xpath("//*[@class='page-title-count-wQ7pG']")).getText()) ;
        Assert.assertEquals(res,count);
    }
    @Test
    public void incorrectNameTest(){
        //Проверка с некоректным названием авто
        String incorrectName = "@!REF$*";
        driver.findElement(By.xpath("/html/body/div[1]/div[4]/div[3]/div[1]/div/div[2]/div[1]" +
                "/form/div[2]/div/div[2]/div/div/div/div/label/input")).sendKeys(incorrectName);
        String res = driver.findElement(By.xpath("//*[@class='text-text-LurtD text-size-s-BxGpL']")).getText();
        List<WebElement> elementList=new ArrayList<>(driver.findElements(By.xpath("//*[@class='text-text-LurtD text-size-s-BxGpL']")));
        Assert.assertEquals("Ничего не найдено",elementList.get(elementList.size()-1).getText());
    }
    @Test
    public void CountTest(){
        //Подсчет и сравние каоличество авто
        driver.findElement(By.xpath("//*[@class='popular-rubricator-button-WWqUy']")).click();
        WebElement table = driver.findElement(By.xpath("/html/body/div[1]/div[4]/div[3]/div[3]/div[1]"));
        List <WebElement> elements = new ArrayList<>(table.findElements(By.xpath("//*[@class='popular-rubricator-count-CX8Mx']")));
        int i = 0;
        for (var e:elements) {
            if (!Objects.equals(e.getText(), "")) i+=Integer.parseInt(e.getText().replaceAll(" ",""));
        }
        Assert.assertEquals(i,Integer.parseInt(driver.findElement(By.xpath("//*[@class='page-title-count-wQ7pG']")).getText().replaceAll(" ","")));
    }

    @After
    public void close(){
        driver.quit();
    }
}
