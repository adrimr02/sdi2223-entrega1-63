package es.uniovi.sdi63.sdi2223entrega163.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_Messages extends PO_NavView {

    static public void sendMessage(WebDriver driver, String message) {
        WebElement name = driver.findElement(By.name("message"));
        name.click();
        name.clear();
        name.sendKeys(message);

        //Pulsar el boton de Alta.
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

}
