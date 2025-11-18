package com.example.persistenciaynetworkingmultiplatform.sqldelight.composeApp

import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.AfterVersion
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import com.example.persistenciaynetworkingmultiplatform.UserQueries
import com.example.persistenciaynetworkingmultiplatform.sqldelight.AppDatabaseExample
import kotlin.Long
import kotlin.Unit
import kotlin.reflect.KClass

internal val KClass<AppDatabaseExample>.schema: SqlSchema<QueryResult.Value<Unit>>
  get() = AppDatabaseExampleImpl.Schema

internal fun KClass<AppDatabaseExample>.newInstance(driver: SqlDriver): AppDatabaseExample =
    AppDatabaseExampleImpl(driver)

private class AppDatabaseExampleImpl(
  driver: SqlDriver,
) : TransacterImpl(driver),
    AppDatabaseExample {
  override val userQueries: UserQueries = UserQueries(driver)

  public object Schema : SqlSchema<QueryResult.Value<Unit>> {
    override val version: Long
      get() = 1

    override fun create(driver: SqlDriver): QueryResult.Value<Unit> {
      driver.execute(null, """
          |CREATE TABLE user (
          |    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
          |    name TEXT NOT NULL,
          |    email TEXT NOT NULL
          |)
          """.trimMargin(), 0)
      return QueryResult.Unit
    }

    override fun migrate(
      driver: SqlDriver,
      oldVersion: Long,
      newVersion: Long,
      vararg callbacks: AfterVersion,
    ): QueryResult.Value<Unit> = QueryResult.Unit
  }
}
