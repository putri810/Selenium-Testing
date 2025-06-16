package selenium.selenium_scenario;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class Login {
    private WebDriver driver;

    @BeforeMethod
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "D:\\Testing-Selenium\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
    }

    @Test
    public void validCredentials() throws InterruptedException {
        System.out.println("Valid credentials test is running.");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        Thread.sleep(2000);

        // Cek kembali apakah berhasil login ke halaman produk
        String title = driver.findElement(By.xpath("//span[@class='title']")).getText();
        Assert.assertEquals(title, "Products", "Title Home text does not match!");
    }

    @Test(dataProvider = "invalidCredentialsData")
    public void invalidCredentials(String username, String password, String expectedError) throws InterruptedException {
        System.out.println("Invalid credentials test is running.");

        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();

        Thread.sleep(1000);

        if (isElementPresent(By.xpath("//h3[@data-test='error']"))) {
            String actualError = driver.findElement(By.xpath("//h3[@data-test='error']")).getText();
            Assert.assertEquals(actualError, expectedError, "Error message does not match!");
        } else {
            Assert.fail("Error message not displayed!");
        }
    }

    @DataProvider(name = "invalidCredentialsData")
    public Object[][] invalidCredentialsData() {
        return new Object[][] {
            {"standard_user", "wrong_password", "Epic sadface: Username and password do not match any user in this service"},
            {"wrong_user", "secret_sauce", "Epic sadface: Username and password do not match any user in this service"},
            {"standard_user", "", "Epic sadface: Password is required"},
            {"", "secret_sauce", "Epic sadface: Username is required"},
            {"", "", "Epic sadface: Username is required"}
        };
    }

    public Boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
