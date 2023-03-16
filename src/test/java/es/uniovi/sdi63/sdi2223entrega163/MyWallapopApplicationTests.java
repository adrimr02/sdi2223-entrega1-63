package es.uniovi.sdi63.sdi2223entrega163;

import es.uniovi.sdi63.sdi2223entrega163.pageobjects.PO_HomeView;
import es.uniovi.sdi63.sdi2223entrega163.pageobjects.PO_UserPrivateView;
import es.uniovi.sdi63.sdi2223entrega163.pageobjects.PO_Properties;
import es.uniovi.sdi63.sdi2223entrega163.pageobjects.PO_View;
import es.uniovi.sdi63.sdi2223entrega163.util.SeleniumUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestMethodOrder( MethodOrderer.OrderAnnotation.class )
class MyWallapopApplicationTests {

    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    static String Geckodriver = "C:\\Users\\adria\\OneDrive\\Documentos\\Uniovi\\3er Curso\\SDI\\Practicas\\Sesion 6\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";

    //static String Geckodriver = "C:\\Dev\\tools\\selenium\\geckodriver-v0.30.0-win64.exe";
    //static String PathFirefox = "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
    //static String Geckodriver = "/Users/USUARIO/selenium/geckodriver-v0.30.0-macos";
    //Común a Windows y a MACOSX
    static WebDriver driver = getDriver(PathFirefox, Geckodriver);
    static String URL = "http://localhost:8080";
    public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckodriver);
        driver = new FirefoxDriver();
        return driver;
    }

    //Antes de la primera prueba
    @BeforeAll
    static public void begin() {}

    @BeforeEach
    public void setUp(){
        driver.navigate().to(URL);
    }


    /*
     + ###################
     * Pruebas de crear ofertas
     * ###################
     */

    /**
     * Inicia sesión, crea una oferta con datos validos y comprueba que aparece
     * en la lista de ofertas del usuario.
     */
    @Test
    @Order( 1 )
    void P15() {
        // Nos logueamos
        PO_UserPrivateView.loginToPrivateView( driver, "user01@email.com", "user01" );

        // Vamos a la pagina de crear oferta
        PO_UserPrivateView.navigateToNewOfferForm(driver);

        // Rellenamos el formulario de oferta
        String checkText = "Coche";
        PO_UserPrivateView.fillFormAddOffer(driver, checkText, "7800", "Un buen coche con pocos " +
                "kilometros");

        // Comprobamos que la oferta aparece en la lista
        List<WebElement> elements = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, elements.get(0).getText());

        //Ahora nos desconectamos y comprobamos que aparece el menú de iniciar sesion
        PO_UserPrivateView.logout( driver );

    }

    /**
     * Inicia sesión, crea una oferta con un nombre vacio y comprueba que aparece
     * el mensaje de error correspondiente.
     */
    @Test
    @Order( 2 )
    void P16A() {
        // Nos logueamos
        PO_UserPrivateView.loginToPrivateView( driver, "user01@email.com", "user01" );

        // Vamos a la pagina de crear oferta
        PO_UserPrivateView.navigateToNewOfferForm(driver);

        // Rellenamos el formulario de oferta
        PO_UserPrivateView.fillFormAddOffer(driver, "", "7800", "Un buen coche con pocos " +
                "kilometros");

        List<WebElement> result = PO_UserPrivateView.checkElementByKey(driver, "error.offer.title.missing",
                PO_Properties.getSPANISH() );
        //Comprobamos el error nombre vacio
        String checkText = PO_HomeView.getP().getString("error.offer.title.missing",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());

    }

    /**
     * Inicia sesión, crea una oferta con un nombre corto y comprueba que aparece
     * el mensaje de error correspondiente.
     */
    @Test
    @Order( 3 )
    void P16B() {
        // Nos logueamos
        PO_UserPrivateView.loginToPrivateView( driver, "user01@email.com", "user01" );

        // Vamos a la pagina de crear oferta
        PO_UserPrivateView.navigateToNewOfferForm(driver);

        // Rellenamos el formulario de oferta
        PO_UserPrivateView.fillFormAddOffer(driver, "pen", "7800", "");

        List<WebElement> result = PO_UserPrivateView.checkElementByKey(driver, "error.offer.title.short",
                PO_Properties.getSPANISH() );
        //Comprobamos el error nombre vacio
        String checkText = PO_HomeView.getP().getString("error.offer.title.short",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());

    }

    /**
     * Inicia sesión, crea una oferta con un nombre repetido para ese usuario y comprueba que aparece
     * el mensaje de error correspondiente.
     */
    @Test
    @Order( 4 )
    void P16C() {
        // Nos logueamos
        PO_UserPrivateView.loginToPrivateView( driver, "user01@email.com", "user01" );

        // Vamos a la pagina de crear oferta
        PO_UserPrivateView.navigateToNewOfferForm(driver);

        // Rellenamos el formulario de oferta
        PO_UserPrivateView.fillFormAddOffer(driver, "Mueble", "150", "");

        List<WebElement> result = PO_UserPrivateView.checkElementByKey(driver, "error.offer.title.duplicate",
                PO_Properties.getSPANISH() );
        //Comprobamos el error nombre vacio
        String checkText = PO_HomeView.getP().getString("error.offer.title.duplicate",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());

    }

    /**
     * Inicia sesión, crea una oferta con un precio negativo y comprueba que aparece
     * el mensaje de error correspondiente.
     */
    @Test
    @Order( 5 )
    void P16D() {
        // Nos logueamos
        PO_UserPrivateView.loginToPrivateView( driver, "user01@email.com", "user01" );

        // Vamos a la pagina de crear oferta
        PO_UserPrivateView.navigateToNewOfferForm(driver);

        // Rellenamos el formulario de oferta
        PO_UserPrivateView.fillFormAddOffer(driver, "Lapiz", "-2", "");

        List<WebElement> result = PO_UserPrivateView.checkElementByKey(driver, "error.offer.price.negative",
                PO_Properties.getSPANISH() );
        //Comprobamos el error nombre vacio
        String checkText = PO_HomeView.getP().getString("error.offer.price.negative",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());

    }

    /*
     + ###################
     * Pruebas de listar ofertas creadas
     * ###################
     */

    /**
     * Inicia sesión, entra al listado de ofertas publicadas por el usuario y
     * comprueba que están todas.
     */
    @Test
    @Order( 6 )
    void P17A() {
        // Nos logueamos
        PO_UserPrivateView.loginToPrivateView( driver, "user01@email.com", "user01" );

        // Vamos a la lista de ofertas del usuario
        PO_UserPrivateView.navigateToMyOffers(driver);

        // Lista de ofertas generadas al iniciar la aplicación
        List<String> offerTitles = new ArrayList<>() {
            {
                add( "Mueble" );
                add( "Sofá" );
                add( "Silla" );
                add( "Mesa" );
            }
        };

        // Comprobamos que las ofertas aparecen en la lista
        for (String title : offerTitles) {
            List<WebElement> elements = PO_View.checkElementBy(driver, "text", title);
            Assertions.assertEquals(title, elements.get(0).getText());
        }

        //Ahora nos desconectamos y comprobamos que aparece el menú de iniciar sesion
        PO_UserPrivateView.logout( driver );

    }

    /**
     * Inicia sesión, entra al listado de ofertas publicadas por un usuario que no ha publicado ninguna y
     * comprueba que aparezca el mensaje correspondiente.
     */
    @Test
    @Order( 7 )
    void P17B() {
        // Nos logueamos
        PO_UserPrivateView.loginToPrivateView( driver, "user03@email.com", "user03" );

        // Vamos a la lista de ofertas del usuario
        PO_UserPrivateView.navigateToMyOffers(driver);

        // Comprobamos que no aparecen ofertas en la lista
        List<WebElement> result = PO_UserPrivateView.checkElementByKey(driver, "offer.list.empty",
                PO_Properties.getSPANISH() );

        //Comprobamos el mensaje de lista vacia
        String checkText = PO_HomeView.getP().getString("offer.list.empty",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());

        //Ahora nos desconectamos y comprobamos que aparece el menú de iniciar sesion
        PO_UserPrivateView.logout( driver );

    }

    /*
     + ###################
     * Pruebas de eliminar ofertas
     * ###################
     */

    /**
     * Inicia sesión, entra al listado de ofertas publicadas por el usuario, borra la primera y
     * comprueba que ya no aparece.
     */
    @Test
    @Order( 8 )
    void P18() {
        // Nos logueamos
        PO_UserPrivateView.loginToPrivateView( driver, "user01@email.com", "user01" );

        // Vamos a la lista de ofertas del usuario
        PO_UserPrivateView.navigateToMyOffers(driver);

        // Guardamos el título de la primera oferta de la lista
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//*[@id=\"main-container\"]/div/div[1]/div/div[2]/div/h5");
        String title = elements.get(0).getText();

        // Hacemos click en eliminar la primera
        elements = PO_UserPrivateView.checkElementBy(driver, "free", "//*[@id=\"main-container\"]/div/div[1]/div/div[3]/div/a");
        elements.get(0).click();

        // Comprobamos que ya no podemos encontrar el titulo de la oferta en la página
        SeleniumUtils.textIsNotPresentOnPage( driver, title );

        //*[@id="main-container"]/div/div[last()]/div/div[2]/div/h5
        //*[@id="main-container"]/div/div[last()]/div/div[3]/div/a

        //Ahora nos desconectamos y comprobamos que aparece el menú de iniciar sesion
        PO_UserPrivateView.logout( driver );

    }

    /**
     * Inicia sesión, entra al listado de ofertas publicadas por el usuario,
     * borra la ultima y comprueba que ya no aparece.
     */
    @Test
    @Order( 9 )
    void P19() {
        // Nos logueamos
        PO_UserPrivateView.loginToPrivateView( driver, "user01@email.com",
                "user01" );

        // Vamos a la lista de ofertas del usuario
        PO_UserPrivateView.navigateToMyOffers(driver);

        // Guardamos el título de la primera oferta de la lista
        List<WebElement> elements = PO_View.checkElementBy(driver, "free",
                "//*[@id=\"main-container\"]/div/div[last()]/div/div[2]/div/h5");
        String title = elements.get(0).getText();

        // Hacemos click en eliminar la primera
        elements = PO_UserPrivateView.checkElementBy(driver, "free",
                "//*[@id=\"main-container\"]/div/div[last()]/div/div[3]/div/a");
        elements.get(0).click();

        // Comprobamos que ya no podemos encontrar el titulo de la oferta en
        // la página
        SeleniumUtils.textIsNotPresentOnPage( driver, title );

        //Ahora nos desconectamos y comprobamos que aparece el menú de
        // iniciar sesion
        PO_UserPrivateView.logout( driver );

    }

    /*
     + ###################
     * Pruebas de buscar ofertas
     * ###################
     */

    /**
     * Inicia sesión, entra a la página de buscar ofertas, y comprueba que
     * aparecen las 5 ofertas por página
     */
    @Test
    @Order( 10 )
    void P20() {
        // Nos logueamos
        PO_UserPrivateView.loginToPrivateView( driver, "user01@email.com",
                "user01" );

        // Vamos a la lista de buscar ofertas
        PO_UserPrivateView.navigateToSearchOffers(driver);

        // Comprobamos que aparecen las 5 ofertas que deben aparecer por página
        List<WebElement> markList = SeleniumUtils.waitLoadElementsBy(driver,
                "free", "//*[@id=\"list\"]/div",
                PO_View.getTimeout());
        Assertions.assertEquals(5, markList.size());

        // Ahora nos desconectamos y comprobamos que aparece el menú de
        // iniciar sesion
        PO_UserPrivateView.logout( driver );

    }

    /**
     * Inicia sesión, entra a la página de buscar ofertas, busca por un titulo
     * que no existe y comprueba que se muestra el mensaje correcto.
     */
    @Test
    @Order( 11 )
    void P21() {
        // Nos logueamos
        PO_UserPrivateView.loginToPrivateView( driver, "user01@email.com",
                "user01" );

        // Vamos a la lista de buscar ofertas
        PO_UserPrivateView.navigateToSearchOffers(driver);

        // Buscamos una cadena que no coincida con ningún título
        WebElement searchField = driver.findElement( By.name("search"));
        searchField.click();
        searchField.clear();
        searchField.sendKeys("asdfasd");
        By boton = By.className("btn");
        driver.findElement(boton).click();

        // Comprobamos que no aparecen ofertas en la lista
        List<WebElement> result = PO_UserPrivateView.checkElementByKey(driver,
                "home.user.empty", PO_Properties.getSPANISH() );

        // Comprobamos el mensaje de lista vacia
        String checkText = PO_UserPrivateView.getP().getString(
                "home.user.empty", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());

        // Ahora nos desconectamos y comprobamos que aparece el menú de
        // iniciar sesion
        PO_UserPrivateView.logout( driver );

    }

    /*
     + ###################
     * Pruebas de comprar ofertas
     * ###################
     */

    /**
     * Inicia sesión, entra a la página de buscar ofertas, compra 1 oferta que
     * cueste menos que el dinero que tenga el usuario en su cartera y comprueba
     * que el dinero se resta de la cartera del comprador.
     */
    @Test
    @Order( 12 )
    void P22() {
        // Nos logueamos con un usuario que tiene 100€ en su cartera
        PO_UserPrivateView.loginToPrivateView( driver, "user01@email.com",
                "user01" );

        // Vamos a la lista de buscar ofertas
        PO_UserPrivateView.navigateToSearchOffers(driver);

        // Guardamos el dinero del usuario antes de comprar
        double initialMoney = PO_UserPrivateView.getCurrentUserWallet(driver);

        // Buscamos una oferta que cuesta menos de 100€ y guardamos su precio
        // exacto
        PO_UserPrivateView.searchInOffersList( driver, "pantalon" );
        var element = By.xpath(
                "//*[@id=\"list\"]/div/div/div[2]/div/p[2]" );
        var money = driver.findElement(element).getText();
        String numString = money.replaceAll("[^\\d.]", "");
        double price = Double.parseDouble(numString);

        // Hacemos click en comprar
        List<WebElement> result = PO_UserPrivateView.checkElementByKey(driver,
                "home.user.offer.buy", PO_Properties.getSPANISH() );
        result.get( 0 ).click();

        // Comprobamos que se ha restado el dinero de la cartera del usuario
        Assertions.assertEquals(initialMoney - price ,
                PO_UserPrivateView.getCurrentUserWallet( driver ));

        // Ahora nos desconectamos y comprobamos que aparece el menú de
        // iniciar sesion
        PO_UserPrivateView.logout( driver );

    }

    /**
     * Inicia sesión, entra a la página de buscar ofertas, compra 1 oferta que
     * cueste el mismo dinero que el que tenga el usuario en su cartera y
     * comprueba que el dinero se resta de la cartera del comprador.
     */
    @Test
    @Order( 13 )
    void P23() {
        // Nos logueamos con un usuario que tiene 350€ en su cartera
        PO_UserPrivateView.loginToPrivateView( driver, "user15@email.com",
                "user15" );

        // Vamos a la lista de buscar ofertas
        PO_UserPrivateView.navigateToSearchOffers(driver);

        // Guardamos el dinero del usuario antes de comprar
        double initialMoney = PO_UserPrivateView.getCurrentUserWallet(driver);

        // Buscamos una oferta que cuesta exactamente 350€ y guardamos su precio
        PO_UserPrivateView.searchInOffersList( driver, "IPhone 11" );
        var element = By.xpath(
                "//*[@id=\"list\"]/div/div/div[2]/div/p[2]" );
        var money = driver.findElement(element).getText();
        String numString = money.replaceAll("[^\\d.]", "");
        double price = Double.parseDouble(numString);

        // Hacemos click en comprar
        List<WebElement> result = PO_UserPrivateView.checkElementByKey(driver,
                "home.user.offer.buy", PO_Properties.getSPANISH() );
        result.get( 0 ).click();

        // Comprobamos que se ha restado el dinero de la cartera del usuario
        Assertions.assertEquals(initialMoney - price ,
                PO_UserPrivateView.getCurrentUserWallet( driver ));
        Assertions.assertEquals( 0,
                PO_UserPrivateView.getCurrentUserWallet( driver ) );

        // Ahora nos desconectamos y comprobamos que aparece el menú de
        // iniciar sesion
        PO_UserPrivateView.logout( driver );
    }

    /**
     * Inicia sesión, entra a la página de buscar ofertas, compra 1 que cueste
     * más que el dinero que tenga el usuario en su cartera y comprueba que
     * aparezca el mensaje de error correspondiente.
     */
    @Test
    @Order( 14 )
    void P24() {
        // Nos logueamos con un usuario que tiene 0€ en su cartera
        PO_UserPrivateView.loginToPrivateView( driver, "user07@email.com",
                "user07" );

        // Vamos a la lista de buscar ofertas
        PO_UserPrivateView.navigateToSearchOffers(driver);

        // Buscamos una oferta
        PO_UserPrivateView.searchInOffersList( driver, "Portatil" );

        // Hacemos click en comprar
        List<WebElement> result = PO_UserPrivateView.checkElementByKey(driver,
                "home.user.offer.buy", PO_Properties.getSPANISH() );
        result.get( 0 ).click();

        // Comprobamos que aparece el mensaje de error
        result = PO_UserPrivateView.checkElementByKey(driver,
                "error.offer.buy.not_enough_money",
                PO_Properties.getSPANISH() );
        //Comprobamos el error nombre vacio
        String checkText = PO_HomeView.getP().getString(
                "error.offer.buy.not_enough_money",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());

    }

    @AfterEach
    public void tearDown(){
        driver.manage().deleteAllCookies();
    }

    //Al finalizar la última prueba
    @AfterAll
    static public void end() {
        //Cerramos el navegador al finalizar las pruebas
        driver.quit();
    }

}
