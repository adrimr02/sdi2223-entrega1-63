package es.uniovi.sdi63.sdi2223entrega163;

import es.uniovi.sdi63.sdi2223entrega163.pageobjects.PO_HomeView;
import es.uniovi.sdi63.sdi2223entrega163.pageobjects.PO_UserPrivateView;
import es.uniovi.sdi63.sdi2223entrega163.pageobjects.PO_Properties;
import es.uniovi.sdi63.sdi2223entrega163.pageobjects.PO_View;
import es.uniovi.sdi63.sdi2223entrega163.util.SeleniumUtils;
import org.junit.jupiter.api.*;
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
     * Inicia sesión, entra al listado de ofertas publicadas por el usuario, borra la ultima y
     * comprueba que ya no aparece.
     */
    @Test
    @Order( 9 )
    void P19() {
        // Nos logueamos
        PO_UserPrivateView.loginToPrivateView( driver, "user01@email.com", "user01" );

        // Vamos a la lista de ofertas del usuario
        PO_UserPrivateView.navigateToMyOffers(driver);

        // Guardamos el título de la primera oferta de la lista
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//*[@id=\"main-container\"]/div/div[last()]/div/div[2]/div/h5");
        String title = elements.get(0).getText();

        // Hacemos click en eliminar la primera
        elements = PO_UserPrivateView.checkElementBy(driver, "free", "//*[@id=\"main-container\"]/div/div[last()]/div/div[3]/div/a");
        elements.get(0).click();

        // Comprobamos que ya no podemos encontrar el titulo de la oferta en la página
        SeleniumUtils.textIsNotPresentOnPage( driver, title );

        //Ahora nos desconectamos y comprobamos que aparece el menú de iniciar sesion
        PO_UserPrivateView.logout( driver );

    }

    /*
     + ###################
     * Pruebas de buscar ofertas
     * ###################
     */


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
