package au.id.tmm.db

import java.sql.SQLException

import au.id.tmm.db.FluentDbOps.UnexpectedResultSizeException
import au.id.tmm.db.data.{DbId, NumRowsAffected}
import au.id.tmm.db.operations.streaming.{BatchInsert, StreamingQuery}
import au.id.tmm.db.operations.{DatabaseOp, Insert, Query, Update}
import au.id.tmm.db.statements.{InsertStatement, QueryStatement, UpdateStatement}
import cats.effect.IO

import scala.collection.immutable.ArraySeq

private[db] trait FluentDbOps {

  def run[F[_], A](dbOp: DatabaseOp[F, A]): F[A]

  final def query[A](queryStatement: QueryStatement[A]): IO[ArraySeq[A]] =
    run(Query(queryStatement))

  // TODO this needs a test
  final def queryFirstElement[A](queryStatement: QueryStatement[A]): IO[Option[A]] =
    run(Query(queryStatement)).map(_.headOption)

  // TODO this needs a test
  final def queryOnlyElement[A](queryStatement: QueryStatement[A]): IO[A] =
    run(Query(queryStatement)).flatMap { results =>
      results.size match {
        case 1       => IO.pure(results.head)
        case badSize => IO.raiseError(new UnexpectedResultSizeException(expectedSize = 1, actualSize = badSize))
      }
    }

  // TODO this needs a test
  /**
    * Differs from `queryFirstElement` in that an error is thrown if more than one element is returned from the DB
    */
  final def queryOneOrNoElement[A](queryStatement: QueryStatement[A]): IO[Option[A]] =
    run(Query(queryStatement)).flatMap { results =>
      results.size match {
        case 0 | 1   => IO.pure(results.headOption)
        case badSize => IO.raiseError(new UnexpectedResultSizeException(expectedSize = Set(0, 1), actualSize = badSize))
      }
    }

  final def streamingQuery[A](queryStatement: QueryStatement[A]): fs2.Stream[IO, A] =
    run(StreamingQuery(queryStatement))

  final def update(updateStatement: UpdateStatement): IO[NumRowsAffected] =
    run(Update(updateStatement))

  final def insert[A](insertStatement: InsertStatement[A])(a: A): IO[ArraySeq[DbId[A]]] =
    run(Insert.using(insertStatement)(a))

  final def insert(updateStatement: UpdateStatement): IO[ArraySeq[DbId[Any]]] =
    run(Insert(updateStatement))

  final def batchInsert[A](insertStatement: InsertStatement[A], stream: fs2.Stream[IO, A]): IO[NumRowsAffected] =
    run(BatchInsert(insertStatement, stream))

}

object FluentDbOps {

  final class UnexpectedResultSizeException(expectedSize: Set[Int], actualSize: Int)
      extends SQLException(s"Expected exactly $expectedSize elements, but was $actualSize") {
    def this(expectedSize: Int, actualSize: Int) = this(Set(expectedSize), actualSize)
  }

}
