package reactivemongo.shapelessbson.resolvers

import reactivemongo.shapelessbson.{ReadKeyResolver, WriteKeyResolver}

object Auto {
  implicit val autoReadKeyResolver: ReadKeyResolver = (key: String) => key
  implicit val autoWriteKeyResolver: WriteKeyResolver = (key: String) => key
}
