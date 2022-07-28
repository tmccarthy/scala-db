package au.id.tmm.db.statements

import au.id.tmm.db.statements.{Argument, ArgumentBinder, DbSymbol, Sql}
import au.id.tmm.db.statements.Sql.Component
import au.id.tmm.db.statements.Sql.Component.{OfArgument, OfSql, OfSymbol}
import scala.Conversion

trait SqlComponentCompanionVersionSpecific {
  implicit val argumentConversion: Conversion[Argument[_], Component] =
    a => OfArgument(a)

  // TODO this isn't completed yet I think?
  implicit def argumentConversionUsingBinder[A : ArgumentBinder](a: A): Component =
    OfArgument(Argument(a))

  implicit val dbSymbolConversion: Conversion[DbSymbol, Component] =
    s => OfSymbol(s)

  implicit val sqlConversion: Conversion[Sql, Component] =
    s => OfSql(s)

  implicit val componentConversion: Conversion[Component, Component] =
    c => c
}
