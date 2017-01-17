package reactivemongo.shapelessbson.resolvers

import reactivemongo.shapelessbson.{ReadKeyResolver, WriteKeyResolver}

object MapBased {
  def readResolver(keyMap: Map[Symbol, Symbol]): ReadKeyResolver = (key: Symbol) => keyMap.getOrElse(key, key).name
  def writeResolver(keyMap: Map[Symbol, Symbol]): WriteKeyResolver = (key: Symbol) => keyMap.getOrElse(key, key).name
}
