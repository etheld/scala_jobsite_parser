package org.scambient.parser

import akka.actor.{ActorSystem, PoisonPill, Props}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

/**
  * Created by gwelican-laptop on 2017-03-14.
  */
object StartingPoint extends App {
  val system = ActorSystem()
  val supervisor = system.actorOf(Props(new Supervisor(system)))

  supervisor ! Start("https://foat.me")

  Await.result(system.whenTerminated, 10 minutes)

  supervisor ! PoisonPill
  system.terminate
}
