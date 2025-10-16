package ru.tinkoff.load.myhttpservice.cases

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object Actions {
// Open browser
  val webTours: HttpRequestBuilder =
    http("WebTours").get("/WebTours/").check(status is 200)

  val header: HttpRequestBuilder =
    http("header").get("/WebTours/header.html").check(status is 200)

  val welcome: HttpRequestBuilder =
    http("welcome").get("/cgi-bin/welcome.pl")
      .queryParam("signOff", "true")
      .check(status is 200)

  val nav: HttpRequestBuilder =
    http("nav").get("/cgi-bin/nav.pl").queryParam("in", "home")
      .check(status.is(200))
      .check(css("input[type='hidden'][name='userSession']", "value").saveAs("userSession"))

  val home: HttpRequestBuilder =
    http("home").get("/WebTours/home.html").check(status.is(200))

// Login

  val login: HttpRequestBuilder =
    http("login").post("/cgi-bin/login.pl")
      .asFormUrlEncoded
      .formParam("userSession","#{userSession}")
      .formParam("username","#{username}")
      .formParam("password","#{password}")
      .formParam("login.x","62")
      .formParam("login.y","12")
      .formParam("JSFormSubmit","off")
      .check(status.is(200))

  val nav_1: HttpRequestBuilder =
    http("nav_1").get("/cgi-bin/nav.pl")
      .queryParam("page", "menu")
      .queryParam("in", "home")
      .check(status.is(200))

  val login_1: HttpRequestBuilder =
    http("login_1").get("/cgi-bin/login.pl")
      .queryParam("intro", "true")
      .check(status.is(200))
      .check(bodyString.saveAs("RESPONSE_BODY"))

// Click Flights
  val welcome_1: HttpRequestBuilder =
    http("welcome_1").get("/cgi-bin/welcome.pl").
      queryParam("page", "search").check(status.is(200))

  val nav_2: HttpRequestBuilder =
    http("nav_2").get("/cgi-bin/nav.pl")
      .queryParam("page", "menu").queryParam("in", "flights")

  val reservations: HttpRequestBuilder =
    http("reservations").get("/cgi-bin/reservations.pl")
      .queryParam("page", "welcome")
      .check(status.is(200))
      .check(css("select[name='depart']>option", "value")
        .findRandom.saveAs("depart"))
      .check(css("select[name='arrive']>option", "value")
        .findRandom.saveAs("arrive"))
      .check(css("input[type='radio'][name='seatPref']", "value")
        .findRandom.saveAs("seatPref"))
      .check(css("input[type='radio'][name='seatType']", "value")
        .findRandom.saveAs("seatType"))

  //Input flight data
  val reservations_1: HttpRequestBuilder =
    http("reservations_1").post("/cgi-bin/reservations.pl")
      .asFormUrlEncoded
      .formParam("advanceDiscount","0")
      .formParam("depart","#{depart}")
      .formParam("departDate","#{departDate}")
      .formParam("arrive","#{arrive}")
      .formParam("returnDate","#{arriveDate}")
      .formParam("numPassengers","1")
      .formParam("seatPref","#{seatPref}")
      .formParam("seatType","#{seatType}")
      .formParam("findFlights.x","40")
      .formParam("findFlights.y","10")
      .formParam(".cgifields","roundtrip")
      .formParam(".cgifields","seatType")
      .formParam(".cgifields","seatPref")
      .check(status.is(200))
      .check(css("input[type='radio'][name='outboundFlight']", "value")
        .findRandom.saveAs("outboundFlight"))

  //Select flight

  val reservations_2: HttpRequestBuilder =
    http("reservations_2").post("/cgi-bin/reservations.pl")
      .asFormUrlEncoded
      .formParam("outboundFlight","#{outboundFlight}")
      .formParam("numPassengers","1")
      .formParam("advanceDiscount","0")
      .formParam("seatType","#{seatType}")
      .formParam("seatPref","#{seatPref}")
      .formParam("reserveFlights.x","43")
      .formParam("reserveFlights.y","9")
      .check(status.is(200))
      //.check(bodyString.saveAs("RESPONSE_BODY"))
      .check(css("input[type='text'][name='firstName']", "value").saveAs("firstName"))
      .check(regex("<input type=\"text\" name=\"lastName\" value=\"([a-zA-Z]+)").saveAs("lastName"))
      .check(css("input[type='text'][name='address1']", "value").saveAs("address1"))
      .check(css("input[type='text'][name='address2']", "value").saveAs("address2"))

  //Input payment data
  val reservations_3: HttpRequestBuilder =
    http("reservations_3").post("/cgi-bin/reservations.pl")
      .asFormUrlEncoded
      .formParam(".cgifields","saveCC")
      .formParam("address1","#{address1}")
      .formParam("address2","#{address2}")
      .formParam("advanceDiscount","0")
      .formParam("buyFlights.x","43")
      .formParam("buyFlights.y","8")
      .formParam("creditCard","#{card1}-#{card2}-#{card3}-#{card4}")
      .formParam("expDate","2040")
      .formParam("firstName","#{firstName}")
      .formParam("lastName","#{lastName}")
      .formParam("JSFormSubmit","off")
      .formParam("numPassengers","1")
      .formParam("oldCCOption","")
      .formParam("outboundFlight","#{outboundFlight}")
      .formParam("pass1","#{firstName} #{lastName}")
      .formParam("returnFlight","")
      .formParam("seatPref","#{seatPref}")
      .formParam("seatType","#{seatType}")
      .check(status.is(200))
      .check(regex("<small><B>([a-zA-Z\\. ]+)</B></small>").is("Thank you for booking through Web Tours."))

  //Go to home page
  val login_2: HttpRequestBuilder =
    http("login_2").get("/cgi-bin/login.pl")
      .queryParam("intro", "true")
      .check(status.is(200))

  val nav_3: HttpRequestBuilder =
    http("nav_3").get("/cgi-bin/nav.pl")
      .queryParam("page", "menu")
      .queryParam("in", "home")
      .check(status.is(200))

  val welcome_2: HttpRequestBuilder =
    http("welcome_2").get("/cgi-bin/welcome.pl")
      .queryParam("page", "menus")
      .check(status.is(200))
  //--------------------------------------------------------------------------------------

  //--------------------------------------------------------------------------------------


  /*val openMainPage: HttpRequestBuilder =
    // Указываем имя запроса
    http("Open main page")
    // Указываем тип запроса (метод) и эндпоинт
      .get("/")
      .check(
        // Проверяем, что в ответе пришел ОК
        // (по умолчанию gatling проверяет ответ на все успешные коды ответа: 2xx, 3xx)
        status is 200,
      )

  val pressButtonAddNewComputer: HttpRequestBuilder =
    http("pressButtonAddNewComputer")
      .get("/computers/new")
      .check(
        status is 200,
      )
      .check(
        // Забираем из ответа случайную компанию и сохраняем значение в переменной 'company'
        regex("""<option value="(.+?)"""").findRandom.saveAs("company"),
      )

  val createNewComputer: HttpRequestBuilder =
    http("createNewComputer")
      .post("/computers")
      // Указываем параметры запроса. Обратите внимание на значение, таким образом мы можем параметризовать запросы.
      .formParam("name", "#{randomComputerName}")
      .formParam("introduced", "#{introduced}")
      .formParam("discontinued", "#{discontinued}")
      // Тут вместо #{company} подставится значение из предыдущего запроса
      .formParam("company", "#{company}")*/
}
