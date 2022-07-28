package au.id.tmm.db.statements

trait SqlCompanionVersionSpecific {

  type ConvertsToSqlComponent[A] = scala.Conversion[A, Sql.Component]

}
