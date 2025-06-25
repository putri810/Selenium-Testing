package testing.objectPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import testing.abstractComponent.abstractComponents;

public class loginPage extends abstractComponents {

     WebDriver driver;

    public loginPage(WebDriver driver) {
        // Constructor for LoginPage
        super(driver); // Call the constructor of AbstractComponent
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "user-name")
    private WebElement userName;

    @FindBy(name = "password")
    private WebElement userPassword;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    By usernameErrorMessage = By.xpath("//*[@id=\"login_button_container\"]/div/form/h3");
    By passwordErrorMessage = By.xpath("//*[@id=\"login_button_container\"]/div/form/h3");

    public void loginApplication(String username, String password){
        userName.sendKeys(null == username ? "" : username);
        userPassword.sendKeys(null == password ? "" : password);
        loginButton.click();
    }

    public Boolean isUsernameErrorMessageVisible(){
       return isElementPresent(usernameErrorMessage);
    }

    public Boolean isPasswordErrorMessageVisible(){
       return isElementPresent(passwordErrorMessage);
    }

    public String getUsernameErrorMessage() {
        return driver.findElement(usernameErrorMessage).getText();
    }

    public String getPasswordErrorMessage() {
        return driver.findElement(passwordErrorMessage).getText();
    }
}