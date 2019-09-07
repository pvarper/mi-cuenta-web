Feature: Iniciar Sesion a la pagina web "http://localhost:8080/mi-cuenta-web-0.0.1-SNAPSHOT/"

  Scenario: Validacion de credenciales de la pagina web
    Given Yo abro el navegador Firefox
    When Yo ingreso a la pagina web
    And Ingreso las siguientes credenciales
    Then Hago click en el boton Login
