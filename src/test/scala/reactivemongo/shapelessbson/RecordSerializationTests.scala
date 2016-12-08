package reactivemongo.shapelessbson

import reactivemongo.bson._
import shapeless._
import shapeless.syntax.singleton._
import shapeless.record.Record

import org.scalatest.{FunSuite, Matchers}

class RecordSerializationTests extends FunSuite with Matchers {
  test("Serialize HNil") {
    implicitly[BSONDocumentWriter[HNil]].write(HNil) shouldBe BSONDocument.empty
  }

  type Book = Record.`'author -> String, 'title -> String, 'id -> Int, 'price -> Double`.T

  test("Serialize record instance") {
    val book =
      ('author ->> "Benjamin Pierce") ::
      ('title  ->> "Types and Programming Languages") ::
      ('id     ->>  262162091) ::
      ('price  ->>  44.11) ::
      HNil


    implicitly[BSONDocumentWriter[Book]].write(book) shouldBe BSONDocument(
      "author" -> BSONString("Benjamin Pierce"),
      "title" -> BSONString("Types and Programming Languages"),
      "id" -> BSONInteger(262162091),
      "price" -> BSONDouble(44.11)
    )
  }
}
