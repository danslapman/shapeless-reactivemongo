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

  test("Serialize with custom KeyResolver") {
    implicit val keyRes = new resolvers.MapBasedResolver(Map('title -> 'caption))

    val book =
      ('author ->> "Benjamin Pierce") ::
        ('title  ->> "Types and Programming Languages") ::
        ('id     ->>  262162091) ::
        ('price  ->>  44.11) ::
        HNil


    implicitly[BSONDocumentWriter[Book]].write(book) shouldBe BSONDocument(
      "author" -> BSONString("Benjamin Pierce"),
      "caption" -> BSONString("Types and Programming Languages"),
      "id" -> BSONInteger(262162091),
      "price" -> BSONDouble(44.11)
    )
  }

  test("Serialize record with some optional value") {
    type Rec = Record.`'from -> Option[Int], 'to -> Option[Int]`.T

    val rec = Record(from = Some(1), to = Some(2))

    implicitly[BSONDocumentWriter[Rec]].write(rec) shouldBe BSONDocument(
      "from" -> BSONArray(BSONInteger(1)),
      "to" -> BSONArray(BSONInteger(2))
    )
  }

  test("Serialize record with none optional value") {
    type Rec = Record.`'from -> Option[Int], 'to -> Option[Int]`.T

    val rec = Record(from = None: Option[Int], to = None: Option[Int])

    implicitly[BSONDocumentWriter[Rec]].write(rec) shouldBe BSONDocument(
      "from" -> BSONArray(),
      "to" -> BSONArray()
    )
  }
}
