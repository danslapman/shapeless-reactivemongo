package reactivemongo

import reactivemongo.bson._
import reactivemongo.bson.exceptions._
import shapeless._
import shapeless.labelled._

package object shapelessbson {
  trait KeyResolver {
    def resolve(field: Symbol): String
  }

  implicit val hNilWriter: BSONDocumentWriter[HNil] = (t: HNil) => BSONDocument.empty

  implicit def recordWriter[K <: Symbol, H, T <: HList](implicit
    witness: Witness.Aux[K],
    hWriter: Lazy[BSONWriter[H, _ <: BSONValue]],
    tWriter: BSONDocumentWriter[T],
    resolver: KeyResolver = resolvers.AutoResolver
  ): BSONDocumentWriter[FieldType[K, H] :: T] = (hl: FieldType[K, H] :: T) => {
    BSONDocument(resolver.resolve(witness.value) -> hWriter.value.write(hl.head)) ++ tWriter.write(hl.tail)
  }

  implicit val hNilReader: BSONDocumentReader[HNil] = (bson: BSONDocument) => HNil

  implicit def recordReader[K <: Symbol, H, T <: HList](implicit
    witness: Witness.Aux[K],
    hReader: Lazy[BSONReader[_ <: BSONValue, H]],
    tReader: BSONDocumentReader[T],
    resolver: KeyResolver = resolvers.AutoResolver
  ): BSONDocumentReader[FieldType[K, H] :: T] = (bson: BSONDocument) => {
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

  implicit val cNilWriter: BSONDocumentWriter[CNil] = (c: CNil) => BSONDocument.empty

  implicit def coproductWriter[H, T <: Coproduct](implicit
    hWriter: Lazy[BSONWriter[H, _ <: BSONValue]],
    tWriter: BSONWriter[T, _ <: BSONValue]
  ): BSONWriter[H :+: T, _ <: BSONValue] = {
    case Inl(h) =>
      hWriter.value.write(h)
    case Inr(t) =>
      tWriter.write(t)
  }
}
