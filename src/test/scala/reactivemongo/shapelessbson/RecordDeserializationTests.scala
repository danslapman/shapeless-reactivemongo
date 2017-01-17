package reactivemongo.shapelessbson

import reactivemongo.bson._
import shapeless._
import shapeless.record._
import resolvers.Auto.autoReadKeyResolver

import org.scalatest.{FunSuite, Matchers}

class RecordDeserializationTests extends FunSuite with Matchers {
  test("Deserialize HNil") {
    implicitly[BSONDocumentReader[HNil]].read(BSONDocument.empty) shouldBe HNil
  }

  type Book = Record.`'author -> String, 'title -> String, 'id -> Int, 'price -> Double`.T

  test("Deserialize record type") {
    val bso = BSONDocument(
      "author" -> BSONString("Benjamin Pierce"),
      "title" -> BSONString("Types and Programming Languages"),
      "id" -> BSONInteger(262162091),
      "price" -> BSONDouble(44.11)
    )

    val book: Book = implicitly[BSONDocumentReader[Book]].read(bso)
    book('author) shouldBe "Benjamin Pierce"
    book('title) shouldBe "Types and Programming Languages"
    book('id) shouldBe 262162091
    book('price) shouldBe 44.11
  }
}
