package reactivemongo.shapelessbson.resolvers

import reactivemongo.shapelessbson.{ReadKeyResolver, WriteKeyResolver}

object Auto {
  implicit val autoReadKeyResolver: ReadKeyResolver = (key: Symbol) => key.name
  implicit val autoWriteKeyResolver: WriteKeyResolver = (key: Symbol) => key.name
}
