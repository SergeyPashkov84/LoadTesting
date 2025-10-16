package ru.tinkoff.load.myhttpservice.scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.core.structure.ChainBuilder
import ru.tinkoff.load.myhttpservice.cases.Actions._
import ru.tinkoff.load.myhttpservice.feeders.Feeders._
import ru.tinkoff.load.myhttpservice.cases.Actions

/*
Объект-компаньон для класса CommonScenario,
по сути синтаксический сахар, чтобы можно было вызвать сценарий
таким образом CommonScenario(), вместо new CommonScenario().scn
*/
object CommonScenario {
  def apply(): ScenarioBuilder = new CommonScenario().scn
}

class CommonScenario {

  val openBrowser: ChainBuilder = group("OpenBrowser")(
    exec(Actions.webTours)
    exec(Actions.header)
    exec(Actions.welcome)
    exec(Actions.nav)
    exec(Actions.home)
  )

  val login: ChainBuilder = group("Login")(
    exec(Actions.login)
    exec(Actions.nav_1)
    exec(Actions.login_1)
  )

  val clickFlights: ChainBuilder = group("Click Flights")(
    exec(Actions.welcome_1)
    exec(Actions.nav_2)
    exec(Actions.reservations)
  )

  val inputFlightData: ChainBuilder = group("Input flight data")(
    exec(Actions.reservations_1)
  )

  val selectFlight: ChainBuilder = group("Select flight")(
    exec(Actions.reservations_2)
  )

  val inputPaymentData: ChainBuilder = group("Input payment data")(
    exec(Actions.reservations_3)
  )

  val goToHomePage: ChainBuilder = group("Go to home page")(
    exec(Actions.login_2)
    exec(Actions.nav_3)
    exec(Actions.welcome_2)
  )

  // Создаем сценарий и его имя
  val scn: ScenarioBuilder = scenario("CommonScenario")
    // Подключаем наши фидеры
    .feed(feeders)
    // Подключаем наши запросы
    .exec(openBrowser)
    .exec(login)
    .exec(clickFlights)
    .exec(inputFlightData)
    .exec(selectFlight)
    .exec(inputPaymentData)
    .exec(goToHomePage)
    /*.exec( session => {
      println("Some Restful Service:")
      println( session("RESPONSE_BODY").as[String] )
      session
    })*/
}