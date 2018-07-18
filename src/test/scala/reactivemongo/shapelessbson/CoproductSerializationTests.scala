package reactivemongo.shapelessbson

import reactivemongo.bson._
import shapeless._

import org.scalatest.{FunSuite, Matchers}

class CoproductSerializationTests extends FunSuite with Matchers {
  type ISB = Int :+: String :+: Boolean :+: CNil

  test("Serialize coproduct cases") {
    implicitly[BSONWriter[ISB, _ <: BSONValue]].write(Coproduct[ISB](42)) shouldBe BSONInteger(42)
    implicitly[BSONWriter[ISB, _ <: BSONValue]].write(Coproduct[ISB]("test")) shouldBe BSONString("test")
    implicitly[BSONWriter[ISB, _ <: BSONValue]].write(Coproduct[ISB](true)) shouldBe BSONBoolean(true)
  }
}
