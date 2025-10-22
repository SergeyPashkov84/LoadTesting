package ru.tinkoff.load.myhttpservice

import io.gatling.core.Predef._
import ru.tinkoff.gatling.config.SimulationConfig._
import ru.tinkoff.gatling.influxdb.Annotations
import ru.tinkoff.load.myhttpservice.scenarios.CommonScenario

class Stability extends Simulation with Annotations {
  val rampDuration = 120
  val stageDuration = 3600
  val intensity = 16
  val testDuration = 4000

  setUp(
    CommonScenario().inject(
      // Плавное увеличение нагрузки до целевого значения
      rampUsersPerSec(0) to intensity during rampDuration,
      // Длительность полки
      constantUsersPerSec(intensity) during stageDuration
    )
  ).protocols(httpProtocol)
    // Общая длительность теста
    .maxDuration(testDuration)

}
