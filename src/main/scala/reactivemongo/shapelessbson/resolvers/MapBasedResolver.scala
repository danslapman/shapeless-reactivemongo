package reactivemongo.shapelessbson.resolvers

import reactivemongo.shapelessbson.KeyResolver

class MapBasedResolver(fieldMap: Map[Symbol, Symbol]) extends KeyResolver {
  override def resolve(field: Symbol): String = fieldMap.getOrElse(field, field).name
}
