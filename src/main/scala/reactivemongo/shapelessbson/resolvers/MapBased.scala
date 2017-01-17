package reactivemongo.shapelessbson.resolvers

import reactivemongo.shapelessbson.{ReadKeyResolver, WriteKeyResolver}

object MapBased {
  def readResolver(keyMap: Map[String, String]): ReadKeyResolver = (key: String) => keyMap.getOrElse(key, key)
  def writeResolver(keyMap: Map[String, String]): WriteKeyResolver = (key: String) => keyMap.getOrElse(key, key)
}
