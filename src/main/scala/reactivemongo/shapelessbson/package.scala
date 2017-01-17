package reactivemongo

import reactivemongo.bson._
import reactivemongo.bson.exceptions._
import shapeless._
import shapeless.labelled._

package object shapelessbson {
  trait KeyResolver {
    def resolve(field: Symbol): String
  }

  implicit val hNilWriter: BSONDocumentWriter[HNil] = new BSONDocumentWriter[HNil] {
    override def write(t: HNil) = BSONDocument.empty
  }

  implicit def recordWriter[K <: Symbol, H, T <: HList](implicit
    witness: Witness.Aux[K],
    hWriter: Lazy[BSONWriter[H, _ <: BSONValue]],
    tWriter: BSONDocumentWriter[T],
    resolver: KeyResolver
  ): BSONDocumentWriter[FieldType[K, H] :: T] = new BSONDocumentWriter[FieldType[K, H] :: T] {
    override def write(hl: FieldType[K, H] :: T) = {
      BSONDocument(resolver.resolve(witness.value) -> hWriter.value.write(hl.head)) ++ tWriter.write(hl.tail)
    }
  }

  implicit val hNilReader: BSONDocumentReader[HNil] = new BSONDocumentReader[HNil] {
    override def read(bson: BSONDocument) = HNil
  }

  implicit def recordReader[K <: Symbol, H, T <: HList](implicit
    witness: Witness.Aux[K],
    hReader: Lazy[BSONReader[_ <: BSONValue, H]],
    tReader: BSONDocumentReader[T],
    resolver: KeyResolver
  ): BSONDocumentReader[FieldType[K, H] :: T] = new BSONDocumentReader[FieldType[K, H] :: T] {
    override def read(bson: BSONDocument) = {
      val fieldName = resolver.resolve(witness.value)
      bson.get(fieldName) match {
        case Some(bsv) =>
          val hv = hReader.value.widenReader.readTry(bsv).get
          val tv = tReader.read(bson)
          field[K](hv) :: tv
        case None =>
          throw DocumentKeyNotFound(fieldName)
      }
    }
  }
}
