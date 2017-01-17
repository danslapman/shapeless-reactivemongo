package reactivemongo.shapelessbson.resolvers

import reactivemongo.shapelessbson.KeyResolver

object MapBased {
  def makeResolver(keyMap: Map[Symbol, Symbol]): KeyResolver = (key: Symbol) => keyMap.getOrElse(key, key).name
}
