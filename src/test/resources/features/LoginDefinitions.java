package stepsdef;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

import org.openqa.selenium.By;

import cucumber.api.java.en.And;

public class LoginDefinitions {
	WebDriver driver = null;

	@Given("^Yo abro el navegador Firefox$")
	public void openBrowser() throws Throwable {
		driver = new FirefoxDriver();
	}

	@When("^Yo ingreso a la pagina web \"http://localhost:8080/mi-cuenta-web-0.0.1-SNAPSHOT/\"$")
	public void goToPaginaWebAndPutCredentials() {
		driver.navigate().to("http://localhost:8080/mi-cuenta-web-0.0.1-SNAPSHOT/");

	}

	@And("^Ingreso las siguientes credenciales $")
	public void putCredencials() {
		driver.findElement(By.id("idLogin")).sendKeys("vargasped");
		driver.findElement(By.id("idPassowrd")).sendKeys("123");

	}

	@Then("^Hago click en el boton Login$")
	public void loginButton() {
		driver.findElement(By.id("idButtonLogin")).click();
		driver.close();
	}

}
