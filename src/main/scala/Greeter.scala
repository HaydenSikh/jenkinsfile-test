package com.example.helloworld

import scala.util.Random

object Greeter extends App {
  // should generate a style warning for not having an explicit return type
  def greeting(i: Int) = (math.abs(i) % 5) match {
    case 0 => "Hello"
    case 1 => "Hola"
    case 2 => "Olá"
    case 3 => "नमस्ते"
    case 4 => "여보"
  }

  def phrase(): String = s"${greeting(Random.nextInt())} world!"

  // TODO: this comment is to test if the Task Plugin can pick it up
  println(phrase)
}
