package ru.tinkoff.load.myhttpservice.feeders

import io.gatling.core.feeder._
import ru.tinkoff.gatling.feeders._
import java.time.format.DateTimeFormatter
import java.time.ZonedDateTime
import scala.util.Random


object Feeders {

  val dateTimeFeeder = Iterator.continually(Map(
    "departDate" -> ZonedDateTime.now().plusDays(5).format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
    "arriveDate" -> ZonedDateTime.now().plusDays(6).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
  ))

  val authorizeFeeder = Iterator.continually(Map(
    "username" -> "IvanDmitriev",
    "password" -> "12345678"
  ))

  val cardNumberFeeder = Iterator.continually(Map(
    "card1" -> (new Random().nextInt(8999) + 1000),
    "card2" -> (new Random().nextInt(8999) + 1000),
    "card3" -> (new Random().nextInt(8999) + 1000),
    "card4" -> (new Random().nextInt(8999) + 1000)
  ))


  // Для имени компьютера будем использовать случайную строку с нужным алфавитом.
  /*val randomComputerName: Feeder[String] =
    RandomRangeStringFeeder("randomComputerName", alphabet = ('A' to 'Z').mkString)*/

  // Используем фидер для создания случайной даты
  //val introducedDate: Feeder[String] = RandomDateFeeder("introduced")

  // Создаем случайную дату со сдвигом относительно 'introduced' даты
  //val discontinuedDate: Feeder[String] = RandomDateRangeFeeder("introduced", "discontinued", 3)

  // Объединяем фидеры в одну переменную для удобства
  //val feeders: Feeder[Any] = randomComputerName ** introducedDate ** discontinuedDate
  val feeders: Feeder[Any] = dateTimeFeeder ** authorizeFeeder ** cardNumberFeeder
}