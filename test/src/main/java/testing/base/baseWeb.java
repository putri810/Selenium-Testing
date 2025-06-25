package testing.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class baseWeb {

    public WebDriver driver;
    public void setup(){
         // Setup WebDriver
        System.setProperty("webdriver.chrome.drive", "D:\\Garuda\\Sertifikasi-Selenium\\Selenium-TestingAutomation\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/v1/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
    }
    
    public void tearDown() {
        // Close the browser after the test
        if (driver != null) {
            driver.quit();
        }
    }
}