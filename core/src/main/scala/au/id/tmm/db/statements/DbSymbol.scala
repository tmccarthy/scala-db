package au.id.tmm.db.statements

sealed trait DbSymbol

final case class ColumnName(asString: String)                        extends DbSymbol
final case class TableName(asString: String)                         extends DbSymbol
final case class ColumnOnTable(table: TableName, column: ColumnName) extends DbSymbol

object ColumnName {
  implicit val fromResultSetColumn: FromResultSetColumn[ColumnName] = FromResultSetColumn[String].map(ColumnName.apply)
}

object TableName {
  implicit val fromResultSetColumn: FromResultSetColumn[TableName] = FromResultSetColumn[String].map(TableName.apply)
}
