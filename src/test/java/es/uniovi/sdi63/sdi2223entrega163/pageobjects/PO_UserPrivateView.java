package es.uniovi.sdi63.sdi2223entrega163.pageobjects;

import es.uniovi.sdi63.sdi2223entrega163.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.List;

public class PO_UserPrivateView extends PO_NavView {

    static public void fillFormAddOffer(WebDriver driver, String namep,
                                        String pricep, String descriptionp) {
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

    /**
     * @param driver
     * @param namep
     * @param pricep
     * @param descriptionp
     * @param imgPath relative to project folder
     */
    static public void fillFormAddOffer(WebDriver driver, String namep,
                                        String pricep, String descriptionp, String imgPath) {
        File file = new File(imgPath);
        String absolutePath = file.getAbsolutePath();

        //Rellenemos el campo de imagen
        WebElement title = driver.findElement( By.name("image"));
        title.clear();
        title.sendKeys(absolutePath);

        fillFormAddOffer( driver, namep, pricep, descriptionp );
    }

    static public void loginToPrivateView(WebDriver driver, String email,
                                          String password) {
        // Vamos al formulario de logueo
        PO_HomeView.clickOption(driver, "login", "class",
                "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, email, password);

        //Comprobamos que entramos en la pagina privada del usuario
        List<WebElement> elements = PO_View.checkElementBy(driver,
                "free", "//*[@id=\"accountDropdown\"]");
        elements.get(0).click();
        PO_View.checkElementBy(driver, "text", email);
    }

    public static double getCurrentUserWallet(WebDriver driver) {
        List<WebElement> elements = PO_View.checkElementBy(driver,
                "free", "//*[@id=\"accountDropdown\"]");

        // Solo hace click en el boton del dropdown si este esta cerrado
        if ( elements.get( 0 ).getAttribute( "aria-expanded" ).equals( "false" ) ) {
            elements.get(0).click();
        }
        var element = By.xpath(
                "//a[@id='accountDropdown']/following-sibling::div/span[2]" );
        var money = driver.findElement(element).getText();
        String numString = money.replaceAll("[^\\d.]", "");
        return Double.parseDouble(numString);
    }

    public static void navigateToNewOfferForm(WebDriver driver) {
        // Pinchamos en la opción de menú de Notas
        List<WebElement> elements = PO_View.checkElementBy(driver, "free",
                "//*[@id=\"offersDropdown\"]");
        elements.get(0).click();
        // Esperamos a que aparezca la opción de añadir oferta:
        elements = PO_View.checkElementBy(driver, "free",
                "//a[contains(@href, 'offer/new')]");
        // Pinchamos en agregar Nota.
        elements.get(0).click();
    }

    public static void navigateToMyOffers(WebDriver driver) {
        // Pinchamos en la opción de menú de Notas
        List<WebElement> elements = PO_View.checkElementBy(driver, "free",
                "//*[@id=\"offersDropdown\"]");

        // Solo hace click en el boton del dropdown si este esta cerrado
        if ( elements.get( 0 ).getAttribute( "aria-expanded" ).equals( "false" ) ) {
            elements.get(0).click();
        }
        // Esperamos a que aparezca la opción de añadir oferta:
        elements = PO_View.checkElementBy(driver, "free",
                "//a[contains(@href, 'offer/my-offers')]");
        // Pinchamos en agregar Nota.
        elements.get(0).click();
    }

    public static void navigateToMyConversations(WebDriver driver) {
        // Pinchamos en la opción de menú de Conversaciones
        List<WebElement> elements = PO_View.checkElementBy(driver, "free",
                "//*[@id=\"conversationsDropdown\"]");

        // Solo hace click en el boton del dropdown si este esta cerrado
        if ( elements.get( 0 ).getAttribute( "aria-expanded" ).equals( "false" ) ) {
            elements.get(0).click();
        }
        // Esperamos a que aparezca la opción de ver conversaciones:
        elements = PO_View.checkElementBy(driver, "free",
                "//a[contains(@href, 'conversations/list')]");
        // Pinchamos en ver conversaciones.
        elements.get(0).click();
    }

    public static void navigateToSearchOffers(WebDriver driver) {
        // Esperamos a que aparezca la opción de añadir oferta:
        var elements = PO_View.checkElementBy(driver, "free",
                "//a[contains(@href, '/')]");
        // Pinchamos en agregar Nota.
        elements.get(0).click();
    }

    public static void navigateToBoughtOffers(WebDriver driver) {
        // Pinchamos en la opción de menú de Notas
        List<WebElement> elements = PO_View.checkElementBy(driver, "free",
                "//*[@id=\"offersDropdown\"]");

        // Solo hace click en el boton del dropdown si este esta cerrado
        if ( elements.get( 0 ).getAttribute( "aria-expanded" ).equals( "false" ) ) {
            elements.get(0).click();
        }
        // Esperamos a que aparezca la opción de añadir oferta:
        elements = PO_View.checkElementBy(driver, "free",
                "//a[contains(@href, 'offer/bought')]");
        // Pinchamos en agregar Nota.
        elements.get(0).click();
    }

    public static void searchInOffersList(WebDriver driver, String searchText) {
        WebElement searchField = driver.findElement( By.name("search"));
        searchField.click();
        searchField.clear();
        searchField.sendKeys(searchText);
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

    public static void logout(WebDriver driver) {
        String loginText = PO_HomeView.getP().getString("login.title",
                PO_Properties.getSPANISH());
        List<WebElement> elements = PO_View.checkElementBy(driver, "free",
                "//*[@id=\"accountDropdown\"]");

        // Solo hace click en el boton del dropdown si este esta cerrado
        if ( elements.get( 0 ).getAttribute( "aria-expanded" ).equals( "false" ) ) {
            elements.get(0).click();
        }

        PO_UserPrivateView.clickOption(driver, "logout", "text",
                loginText);
    }

    static public void checkInternMyOffers(WebDriver driver, int language) {
        //Esperamos a que se cargue el saludo de bienvenida en Español
        SeleniumUtils.waitLoadElementsBy(driver, "text", p.getString("nav.offers", language),
                getTimeout());
    }

    static public void checkChangeLanguage(WebDriver driver, String textLanguage1, String textLanguage,
                                           int locale1, int locale2) {
        //Esperamos a que se cargue el saludo de bienvenida en Español
        PO_UserPrivateView.checkInternMyOffers(driver, locale1);
        //Cambiamos a segundo idioma
        PO_HomeView.changeLanguage(driver, textLanguage);
        //Comprobamos que el texto de bienvenida haya cambiado a segundo idioma
        PO_UserPrivateView.checkInternMyOffers(driver, locale2);
        //Volvemos a Español.
        PO_HomeView.changeLanguage(driver, textLanguage1);
        //Esperamos a que se cargue el saludo de bienvenida en Español
        PO_UserPrivateView.checkInternMyOffers(driver, locale1);
    }

}
