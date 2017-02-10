package reactivemongo.shapelessbson.resolvers

import reactivemongo.shapelessbson.KeyResolver

object AutoResolver extends KeyResolver {
  override def resolve(field: Symbol): String = field.name
}
