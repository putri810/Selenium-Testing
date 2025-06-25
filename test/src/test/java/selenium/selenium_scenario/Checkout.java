package selenium.selenium_scenario;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import testing.objectPage.loginPage;

public class Checkout {
    WebDriver driver;

    @BeforeClass
    public void setup() throws InterruptedException{
         // Setup WebDriver
        System.setProperty("webdriver.chrome.drive", "D:\\Garuda\\Sertifikasi-Selenium\\Selenium-TestingAutomation\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/v1/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
    }
    
    @Test
    public void Login(){
        // This is a placeholder for the actual test implementation
        // You can add your test logic here
        System.out.println("Valid credentials test is running.");

        loginPage loginPage = new loginPage(driver);
        loginPage.loginApplication("standard_user", "secret_sauce");

        String homepage = driver.findElement(By.xpath("//div[@class = 'product_label']")).getText();

        Assert.assertEquals(homepage, "Products", "Tittle Home text does not match!");
    }

    @Test(dependsOnMethods = {"Login"})
    public void CheckoutScenarioTest() throws InterruptedException{
        String productName = "Sauce Labs Backpack";

        // Explicitly wait for the product to be visible
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        Thread.sleep(2000);

        driver.findElement(By.cssSelector("#inventory_container > div > div:nth-child(1) > div.pricebar > button")).click();

        // Scenario Cart Page
        driver.findElement(By.id("shopping_cart_container")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#contents_wrapper > div.subheader")));
        List<WebElement> cartProducts = driver.findElements(By.id("item_4_title_link"));
        boolean isProductInCart = cartProducts.stream().anyMatch(cartProd -> cartProd.getText().equals(productName));

        Assert.assertTrue(isProductInCart, "Product not found in cart!");
    }

    @AfterClass
    public void tearDown() {
        // Close the browser after the test
        if (driver != null) {
            driver.quit();
        }
    }

}