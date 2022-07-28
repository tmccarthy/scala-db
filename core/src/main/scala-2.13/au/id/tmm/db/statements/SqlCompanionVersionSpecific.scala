package au.id.tmm.db.statements

trait SqlCompanionVersionSpecific {

  type ConvertsToSqlComponent[A] = A => Sql.Component

}
