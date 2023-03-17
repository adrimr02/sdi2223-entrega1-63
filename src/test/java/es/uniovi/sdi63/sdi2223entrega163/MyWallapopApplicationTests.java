package es.uniovi.sdi63.sdi2223entrega163;

import es.uniovi.sdi63.sdi2223entrega163.pageobjects.*;
import es.uniovi.sdi63.sdi2223entrega163.util.SeleniumUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder( MethodOrderer.OrderAnnotation.class )
class MyWallapopApplicationTests {

    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    //static String Geckodriver = "C:\\Users\\adria\\OneDrive\\Documentos\\Uniovi\\3er Curso\\SDI\\Practicas\\Sesion 6\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    static String Geckodriver = "C:\\Users\\larry\\Desktop\\UNI\\SDI\\PL-SDI-Sesio╠ün5-material\\geckodriver-v0.30.0-win64.exe";


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

    /*
     + ###################
     * Pruebas de registro de usuario
     * ###################
     */

    /**
     * Registro de Usuario con datos válidos
     */
    @Test
    @Order(1)
    void P1() {
        //Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_SignupView.fillForm(driver, "user20", "20", "user20@email.com",
                "user20", "user20");
        //Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
        String checkText = PO_HomeView.getP().getString("offer.list.title",PO_Properties.getSPANISH());
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    /**
     * Registro de Usuario con datos inválidos (email vacío, nombre vacío, apellidos vacíos)
     */
    @Test
    @Order(2)
    void P2() {
        //Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Pulsamos el botón para enviar el formulario sin haberlo rellenado
        By boton = By.className("btn");
        driver.findElement(boton).click();
        List<WebElement> result = PO_SignupView.checkElementByKey(driver, "Error.empty",
                PO_Properties.getSPANISH() );
        //Comprobamos que se muestra mensaje de elementos vacíos.
        String checkText = PO_HomeView.getP().getString("Error.empty",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());
    }

    /**
     * Registro de Usuario con datos inválidos (repetición de contraseña inválida)
     */
    @Test
    @Order(3)
    void P3() {
        //Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_SignupView.fillForm(driver, "user20", "20", "user20@email.com",
                "user20", "user21");
        List<WebElement> result = PO_SignupView.checkElementByKey(driver,
                "Error.signup.passwordConfirm.coincidence",
                PO_Properties.getSPANISH() );
        //Comprobamos el error de contraseña no coincidente.
        String checkText = PO_HomeView.getP().getString("Error.signup.passwordConfirm.coincidence",
                PO_Properties.getSPANISH());

        Assertions.assertEquals(checkText , result.get(0).getText());
    }

    /**
     * Registro de Usuario con datos inválidos (email existente).
     */
    @Test
    @Order(4)
    void P4() {
        //Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_SignupView.fillForm(driver, "user", "20", "user01@email.com",
                "user20", "user20");
        List<WebElement> result = PO_SignupView.checkElementByKey(driver, "Error.signup.email.duplicate",
                PO_Properties.getSPANISH() );
        //Comprobamos el error de email repetido.
        String checkText = PO_HomeView.getP().getString("Error.signup.email.duplicate",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());
    }

    /*
     + ###################
     * Pruebas de Login
     * ###################
     */

    /**
     * Inicio de sesión con datos válidos (administrador)
     */
    @Test
    @Order(5)
    void P5() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");
        //Comprobamos que entramos en la pagina privada del administrador
        String checkText = PO_HomeView.getP().getString("home.admin.title", PO_Properties.getSPANISH());
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    /**
     * Inicio de sesión con datos válidos (usuario estándar)
     */
    @Test
    @Order(6)
    void P6() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");
        //Comprobamos que entramos en la pagina privada del usuario
        String checkText = PO_HomeView.getP().getString("offer.list.title", PO_Properties.getSPANISH());
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    /**
     * Inicio de sesión con datos inválidos (usuario estándar, campo email y contraseña vacíos)
     */
    @Test
    @Order(7)
    void P7() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Pulsamos el botón para enviar el formulario sin haberlo rellenado
        By boton = By.className("btn");
        driver.findElement(boton).click();
        List<WebElement> result = PO_LoginView.checkElementByKey(driver, "error.login.credentials",
                PO_Properties.getSPANISH() );
        //Comprobamos que se muestra mensaje de elementos vacíos.
        String checkText = PO_HomeView.getP().getString("error.login.credentials", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    /**
     * Inicio de sesión con datos válidos (usuario estándar, email existente, pero contraseña incorrecta)
     */
    @Test
    @Order(8)
    void P8() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user02");
        List<WebElement> result = PO_LoginView.checkElementByKey(driver, "error.login.credentials",
                PO_Properties.getSPANISH() );
        //Comprobamos que se muestra mensaje de elementos vacíos.
        String checkText = PO_HomeView.getP().getString("error.login.credentials", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    /*
     + ###################
     * Pruebas de Logout
     * ###################
     */

    /**
     * Hacer clic en la opción de salir de sesión y comprobar que se redirige a la página de inicio de sesión (Login)
     */
    @Test
    @Order(9)
    void P9() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");
        //Comprobamos que entramos en la pagina privada del usuario
        String checkText = PO_HomeView.getP().getString("offer.list.title", PO_Properties.getSPANISH());
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        //Ahora nos desconectamos comprobamas que aparece el menu de login
        PO_PrivateView.logout(driver);
        String loginText = PO_HomeView.getP().getString("login.title", PO_Properties.getSPANISH());
        List<WebElement> resultlogin = PO_View.checkElementBy(driver, "text", loginText);
        Assertions.assertEquals(loginText, resultlogin.get(0).getText());
    }

    /*
     + ###################
     * Pruebas de administrador
     * ###################
     */

    /**
     * Mostrar el listado de usuarios y comprobar que se muestran todos los que existen en el sistema
     */
    @Test
    @Order(11)
    void P11() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");
        //Cpmprobamos que entramos en la pagina privada del administrador
        String checkText = PO_HomeView.getP().getString("home.admin.title", PO_Properties.getSPANISH());
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        //Contamos el número de filas de notas
        List<WebElement> markList = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout());
        Assertions.assertEquals(16, markList.size());
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
    @Order( 15 )
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
        //PO_PrivateView.logout( driver );

    }

    /**
     * Inicia sesión, crea una oferta con un nombre vacio y comprueba que aparece
     * el mensaje de error correspondiente.
     */
    @Test
    @Order( 16 )
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
    @Order( 17 )
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
    @Order( 18 )
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
    @Order( 19 )
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

}
