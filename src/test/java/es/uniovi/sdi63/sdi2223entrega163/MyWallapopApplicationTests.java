package es.uniovi.sdi63.sdi2223entrega163;

import es.uniovi.sdi63.sdi2223entrega163.pageobjects.PO_HomeView;
import es.uniovi.sdi63.sdi2223entrega163.pageobjects.PO_PrivateView;
import es.uniovi.sdi63.sdi2223entrega163.pageobjects.PO_Properties;
import es.uniovi.sdi63.sdi2223entrega163.pageobjects.PO_View;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

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
        PO_PrivateView.loginToPrivateView( driver, "user01@email.com", "user01" );

        // Vamos a la pagina de crear oferta
        PO_PrivateView.navigateToNewOfferForm(driver);

        // Rellenamos el formulario de oferta
        String checkText = "Coche";
        PO_PrivateView.fillFormAddOffer(driver, checkText, "7800", "Un buen coche con pocos " +
                "kilometros");

        // Comprobamos que la oferta aparece en la lista
        List<WebElement> elements = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, elements.get(0).getText());

        //Ahora nos desconectamos y comprobamos que aparece el menú de registrarse
        PO_PrivateView.logout( driver );

    }

    /**
     * Inicia sesión, crea una oferta con un nombre vacio y comprueba que aparece
     * el mensaje de error correspondiente.
     */
    @Test
    @Order( 2 )
    void P16A() {
        // Nos logueamos
        PO_PrivateView.loginToPrivateView( driver, "user01@email.com", "user01" );

        // Vamos a la pagina de crear oferta
        PO_PrivateView.navigateToNewOfferForm(driver);

        // Rellenamos el formulario de oferta
        PO_PrivateView.fillFormAddOffer(driver, "", "7800", "Un buen coche con pocos " +
                "kilometros");

        List<WebElement> result = PO_PrivateView.checkElementByKey(driver, "error.offer.title.missing",
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
        PO_PrivateView.loginToPrivateView( driver, "user01@email.com", "user01" );

        // Vamos a la pagina de crear oferta
        PO_PrivateView.navigateToNewOfferForm(driver);

        // Rellenamos el formulario de oferta
        PO_PrivateView.fillFormAddOffer(driver, "pen", "7800", "");

        List<WebElement> result = PO_PrivateView.checkElementByKey(driver, "error.offer.title.short",
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
        PO_PrivateView.loginToPrivateView( driver, "user01@email.com", "user01" );

        // Vamos a la pagina de crear oferta
        PO_PrivateView.navigateToNewOfferForm(driver);

        // Rellenamos el formulario de oferta
        PO_PrivateView.fillFormAddOffer(driver, "Mueble", "150", "");

        List<WebElement> result = PO_PrivateView.checkElementByKey(driver, "error.offer.title.duplicate",
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
        PO_PrivateView.loginToPrivateView( driver, "user01@email.com", "user01" );

        // Vamos a la pagina de crear oferta
        PO_PrivateView.navigateToNewOfferForm(driver);

        // Rellenamos el formulario de oferta
        PO_PrivateView.fillFormAddOffer(driver, "Lapiz", "-2", "");

        List<WebElement> result = PO_PrivateView.checkElementByKey(driver, "error.offer.price.negative",
                PO_Properties.getSPANISH() );
        //Comprobamos el error nombre vacio
        String checkText = PO_HomeView.getP().getString("error.offer.price.negative",
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
