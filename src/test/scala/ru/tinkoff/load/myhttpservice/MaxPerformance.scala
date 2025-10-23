package ru.tinkoff.load.myhttpservice

import io.gatling.core.Predef._
import ru.tinkoff.gatling.config.SimulationConfig._
import ru.tinkoff.gatling.influxdb.Annotations
import ru.tinkoff.load.myhttpservice.scenarios.CommonScenario

class MaxPerformance extends Simulation with Annotations {
val stagesNumber = 20
val intensity = 3.332
val testDuration = 7200
val stageDuration = 600
val rampDuration = 60


  setUp(
    CommonScenario().inject(
      // Интенсивность на ступень
      incrementUsersPerSec(intensity / stagesNumber)
        // Количество ступеней
        .times(stagesNumber)
        // Длительность полки
        .eachLevelLasting(stageDuration)
        // Длительность разгона
        .separatedByRampsLasting(rampDuration)
        // Начало нагрузки с
        .startingFrom(0)
    )
  ).protocols(httpProtocol)
    // Общая длительность теста
    .maxDuration(testDuration)

}
