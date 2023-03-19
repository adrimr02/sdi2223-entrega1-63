package es.uniovi.sdi63.sdi2223entrega163.pageobjects;

import es.uniovi.sdi63.sdi2223entrega163.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_SignupView extends PO_NavView {
    static public void fillForm(WebDriver driver, String namep, String lastnamep, String emailp, String
            passwordp, String passwordconfp) {
        WebElement name = driver.findElement(By.name("name"));
        name.click();
        name.clear();
        name.sendKeys(namep);
        WebElement lastname = driver.findElement(By.name("lastname"));
        lastname.click();
        lastname.clear();
        lastname.sendKeys(lastnamep);
        WebElement email = driver.findElement(By.name("email"));
        email.click();
        email.clear();
        email.sendKeys(emailp);
        WebElement password = driver.findElement(By.name("password"));
        password.click();
        password.clear();
        password.sendKeys(passwordp);
        WebElement passwordConfirm = driver.findElement(By.name("passwordConfirm"));
        passwordConfirm.click();
        passwordConfirm.clear();
        passwordConfirm.sendKeys(passwordconfp);
        //Pulsar el boton de Alta.
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

    static public void checkIntern(WebDriver driver, int language) {
        //Esperamos a que se cargue el saludo de bienvenida en Español
        SeleniumUtils.waitLoadElementsBy(driver, "text", p.getString("signup.title", language),
                getTimeout());
    }

    static public void checkChangeLanguage(WebDriver driver, String textLanguage1, String textLanguage,
                                           int locale1, int locale2) {
        //Esperamos a que se cargue el saludo de bienvenida en Español
        PO_SignupView.checkIntern(driver, locale1);
        //Cambiamos a segundo idioma
        PO_HomeView.changeLanguage(driver, textLanguage);
        //Comprobamos que el texto de bienvenida haya cambiado a segundo idioma
        PO_SignupView.checkIntern(driver, locale2);
        //Volvemos a Español.
        PO_HomeView.changeLanguage(driver, textLanguage1);
        //Esperamos a que se cargue el saludo de bienvenida en Español
        PO_SignupView.checkIntern(driver, locale1);
    }
}