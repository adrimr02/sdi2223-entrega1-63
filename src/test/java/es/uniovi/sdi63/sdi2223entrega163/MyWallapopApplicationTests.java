package es.uniovi.sdi63.sdi2223entrega163;

import es.uniovi.sdi63.sdi2223entrega163.pageobjects.*;
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
    //static String Geckodriver = "C:\\Users\\adria\\OneDrive\\Documentos\\Uniovi\\3er Curso\\SDI\\Practicas\\Sesion 6\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    static String Geckodriver = "C:\\Users\\larry\\Desktop\\UNI\\SDI\\PL-SDI-Sesio╠ün5-material\\geckodriver-v0.30.0-win64.exe";

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
        PO_UserPrivateView.logout(driver);
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
    @Order( 16 )
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
    @Order( 17 )
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
    @Order( 18 )
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
    @Order( 19 )
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
        // Nos logueamos con un usuario que tiene 200€ en su cartera
        PO_UserPrivateView.loginToPrivateView( driver, "user02@email.com",
                "user02" );

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

    /*
     + ###################
     * Pruebas de listado de ofertas compradas
     * ###################
     */

    /**
     * Inicia sesión, entra a la página de ofertas compradas, con un usuario
     * que no haya comprado nada y comprueba que aparezca el mensaje
     * correspondiente
     */
    @Test
    @Order( 15 )
    void P22A() {
        // Nos logueamos con un usuario que no ha comprado nada
        PO_UserPrivateView.loginToPrivateView( driver, "user01@email.com",
                "user01" );

        // Vamos a la lista de ofertas compradas
        PO_UserPrivateView.navigateToBoughtOffers(driver);

        // Comprobamos que no aparecen ofertas en la lista
        List<WebElement> result = PO_UserPrivateView.checkElementByKey(driver,
                "offer.bought.empty", PO_Properties.getSPANISH() );

        //Comprobamos el mensaje de lista vacia
        String checkText = PO_HomeView.getP().getString("offer.bought.empty",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());

        // Ahora nos desconectamos y comprobamos que aparece el menú de
        // iniciar sesion
        PO_UserPrivateView.logout( driver );
    }

    /**
     * Inicia sesión, entra a la página de ofertas compradas, con un usuario
     * que haya comprado una oferta y comprueba que aparezca en la lista
     */
    @Test
    @Order( 16 )
    void P22B() {
        // Nos logueamos con un usuario que ha comprado una oferta, en este
        // caso, una television vendida por user02@email.com
        PO_UserPrivateView.loginToPrivateView( driver, "user12@email.com",
                "user12" );

        // Vamos a la lista de ofertas compradas
        PO_UserPrivateView.navigateToBoughtOffers(driver);

        // Comprobamos que la oferta de la televisión esta en la lista
        List<WebElement> elements = PO_UserPrivateView.checkElementBy( driver,
                "free", "//*[text()='Television']");
        Assertions.assertTrue( elements.size() >= 1 );

        // Ahora nos desconectamos y comprobamos que aparece el menú de
        // iniciar sesion
        PO_UserPrivateView.logout( driver );

    }

    /*
     * ###################
     * Pruebas de crear ofertas con imagenes
     * ###################
     */

    /**
     * Inicia sesión, entra a la página de crear oferta, crea una oferta
     * con una imagen y comprueba que se crea correctamente
     */
    @Test
    @Order( 17 )
    void P40() {
        // Nos logueamos con un usuario que no ha comprado nada
        PO_UserPrivateView.loginToPrivateView( driver, "user01@email.com",
                "user01" );

        // Vamos a la pagina de crear oferta
        PO_UserPrivateView.navigateToNewOfferForm(driver);

        String name = "coche verde";

        // Añadimos la oferta con la imagen
        PO_UserPrivateView.fillFormAddOffer(driver, name,
                "7200", "un coche grande y bonito",
                "src/test/resources/testImages/coche.jpg");

        //Buscamos la oferta "coche verde" y comprobamos que existe
        List<WebElement> elements = PO_UserPrivateView.checkElementBy( driver,
                "free",
                "//*[@id=\"main-container\"]/div/div[1]/div/div[2]/div/h5" );
        Assertions.assertEquals( name, elements.get( 0 ).getText()
                .toLowerCase() );

        // Comprobamos que la imagen es la correcta y contiene el nombre de la
        // imagen original en su nombre
        elements = PO_UserPrivateView.checkElementBy( driver,
                "free", "//*[@id=\"main-container\"]/div/div[1]/div/div[1]/img" );
        Assertions.assertTrue( elements.get( 0 ).getAttribute( "src" )
                .contains( "coche" ) );


        // Ahora nos desconectamos y comprobamos que aparece el menú de
        // iniciar sesion
        PO_UserPrivateView.logout( driver );
    }

    /**
     * Inicia sesión, entra a la página de crear oferta, crea una oferta
     * sin una imagen y comprueba que se crea correctamente usando la imagen
     * por defecto
     */
    @Test
    @Order( 17 )
    void P41() {
        // Nos logueamos con un usuario que no ha comprado nada
        PO_UserPrivateView.loginToPrivateView( driver, "user01@email.com",
                "user01" );

        // Vamos a la pagina de crear oferta
        PO_UserPrivateView.navigateToNewOfferForm(driver);

        String name = "coche azul";

        // Añadimos la oferta con la imagen
        PO_UserPrivateView.fillFormAddOffer(driver, name,
                "7200", "un coche que no puedes ver");

        //Buscamos la oferta "coche verde" y comprobamos que existe
        List<WebElement> elements = PO_UserPrivateView.checkElementBy( driver,
                "free",
                "//*[@id=\"main-container\"]/div/div[1]/div/div[2]/div/h5" );
        Assertions.assertEquals( name, elements.get( 0 ).getText()
                .toLowerCase() );

        // Comprobamos que la imagen es la correcta y contiene el nombre de la
        // imagen original en su nombre
        elements = PO_UserPrivateView.checkElementBy( driver,
                "free", "//*[@id=\"main-container\"]/div/div[1]/div/div[1]/img" );
        Assertions.assertEquals( URL+"/images/defaultImg.jpg",
                elements.get( 0 ).getAttribute( "src" ));


        // Ahora nos desconectamos y comprobamos que aparece el menú de
        // iniciar sesion
        PO_UserPrivateView.logout( driver );
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
