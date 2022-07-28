package au.id.tmm.db.statements

import au.id.tmm.db.statements.Sql.Component
import au.id.tmm.db.statements.Sql.Component.{OfArgument, OfSql, OfSymbol}

trait SqlComponentCompanionVersionSpecific {
  implicit def of(argument: Argument[_]): Component     = OfArgument(argument)
  implicit def ofA[A : ArgumentBinder](a: A): Component = OfArgument(Argument(a))
  implicit def of(dbSymbol: DbSymbol): Component        = OfSymbol(dbSymbol)
  implicit def of(sql: Sql): Component                  = OfSql(sql)
}
