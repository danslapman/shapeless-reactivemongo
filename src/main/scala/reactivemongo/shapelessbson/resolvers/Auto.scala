package reactivemongo.shapelessbson.resolvers

import reactivemongo.shapelessbson.KeyResolver

object Auto {
  implicit val autoKeyResolver: KeyResolver = (key: Symbol) => key.name
}
