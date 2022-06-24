# `scala-db`

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/au.id.tmm.scala-db/scala-db-core_2.13/badge.svg)](https://maven-badges.herokuapp.com/maven-central/au.id.tmm.scala-db/scala-db-core_2.13)

A simple jdbc-based library for SQL interaction written in frustration with what's on offer in the Scala ecosystem.

This library uses [`cats-effect`](https://github.com/typelevel/cats-effect/) and [`fs2`](https://github.com/typelevel/fs2/).
It's intended to be a relatively thin wrapper over the top of jdbc, emphasising writing SQL queries directly rather than
using any sort of DSL.

See [the tests](core/src/test/scala/au/id/tmm/db/DatabaseTest.scala) for examples until I've added more documentation.
