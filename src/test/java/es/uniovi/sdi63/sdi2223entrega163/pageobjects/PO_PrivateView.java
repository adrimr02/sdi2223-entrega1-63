package es.uniovi.sdi63.sdi2223entrega163.pageobjects;

import es.uniovi.sdi63.sdi2223entrega163.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class PO_PrivateView extends PO_NavView {

    static public void fillFormAddOffer(WebDriver driver, String namep, String pricep, String descriptionp)
    {
        //Rellenemos el campo de nombre
        WebElement title = driver.findElement( By.name("title"));
        title.click();
        title.clear();
        title.sendKeys(namep);
        //Rellenemos el campo de precio
        WebElement price = driver.findElement( By.name("price"));
        price.click();
        price.clear();
        price.sendKeys(pricep);
        WebElement details = driver.findElement(By.name("details"));
        details.click();
        details.clear();
        details.sendKeys(descriptionp);
        By boton = By.cssSelector("button[type=submit]");
        driver.findElement(boton).click();
    }

    static public void loginToPrivateView(WebDriver driver, String email, String password) {
        // Vamos al formulario de logueo
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, email, password);

        //Comprobamos que entramos en la pagina privada del usuario
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//*[@id=\"accountDropdown\"]");
        elements.get(0).click();
        PO_View.checkElementBy(driver, "text", email);
    }

    public static void navigateToNewOfferForm(WebDriver driver) {
        // Pinchamos en la opción de menú de Notas
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//*[@id=\"offersDropdown\"]");
        elements.get(0).click();
        // Esperamos a que aparezca la opción de añadir oferta:
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'offer/new')]");
        // Pinchamos en agregar Nota.
        elements.get(0).click();
    }

    public static void logout(WebDriver driver) {
        String loginText = PO_HomeView.getP().getString("login.title", PO_Properties.getSPANISH());
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//*[@id=\"accountDropdown\"]");
        elements.get(0).click();
        PO_PrivateView.clickOption(driver, "logout", "text", loginText);
    }

}
