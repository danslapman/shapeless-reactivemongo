reactivemongo-shapeless [![Release](https://jitpack.io/v/danslapman/reactivemongo-shapeless.svg)](https://jitpack.io/#danslapman/reactivemongo-shapeless)
=========
**reactivemongo-shapeless** provides support for [shapeless](https://github.com/milessabin/shapeless)'s extensible records for ReactiveMongo

Using **reactivemongo-shapeless** is pretty simple:
```scala
import reactivemongo.bson._
import shapeless._
import shapeless.record._
import reactivemongo.shapelessbson._
import resolvers.Auto._

type Book = Record.`'author -> String, 'title -> String, 'id -> Int, 'price -> Double`.T

val bso = BSONDocument(
    "author" -> BSONString("Benjamin Pierce"),
    "title" -> BSONString("Types and Programming Languages"),
    "id" -> BSONInteger(262162091),
    "price" -> BSONDouble(44.11)
)

val book: Book = implicitly[BSONDocumentReader[Book]].read(bso)
println(book)
```

If You need to customize some field names, You can use custom field name resolver:

```scala
import reactivemongo.bson._
import shapeless._
import shapeless.record._
import reactivemongo.shapelessbson._
import resolvers.MapBased.makeResolver

type Book = Record.`'author -> String, 'title -> String, 'id -> Int, 'price -> Double`.T

implicit val resolver = makeResolver(Map('title -> 'caption))

val bso = BSONDocument(
    "author" -> BSONString("Benjamin Pierce"),
    "caption" -> BSONString("Types and Programming Languages"),
    "id" -> BSONInteger(262162091),
    "price" -> BSONDouble(44.11)
)

val book: Book = implicitly[BSONDocumentReader[Book]].read(bso)
println(book)
```

Of course, it all work with serializers as well.

reactivemongo-shapeless is available via jitpack:
```
    resolvers += "jitpack" at "https://jitpack.io"

    libraryDependencies += "com.github.danslapman" %% "reactivemongo-shapeless" % "{version}"
```