shapeless-reactivemongo [ ![Download](https://api.bintray.com/packages/danslapman/maven/shapeless-reactivemongo/images/download.svg) ](https://bintray.com/danslapman/maven/shapeless-reactivemongo/_latestVersion)
=======================
**reactivemongo-shapeless** provides support for [shapeless](https://github.com/milessabin/shapeless)'s extensible records for ReactiveMongo

Using **shapeless-reactivemongo** is pretty simple:
```scala
import reactivemongo.bson._
import shapeless._
import shapeless.record._
import reactivemongo.shapelessbson._

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
import resolvers.MapBasedResolver

type Book = Record.`'author -> String, 'title -> String, 'id -> Int, 'price -> Double`.T

implicit val resolver = new MapBasedResolver(Map('title -> 'caption))

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

shapeless-reactivemongo is available via bintray:
```
    resolvers += Resolver.bintrayRepo("danslapman", "maven")

    libraryDependencies += "danslapman" %% "shapeless-reactivemongo" % "{version}"
```