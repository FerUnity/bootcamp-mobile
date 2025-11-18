package com.example.persistenciaynetworkingmultiplatform.sqldelight

import app.cash.sqldelight.Transacter
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import com.example.persistenciaynetworkingmultiplatform.UserQueries
import com.example.persistenciaynetworkingmultiplatform.sqldelight.composeApp.newInstance
import com.example.persistenciaynetworkingmultiplatform.sqldelight.composeApp.schema
import kotlin.Unit

public interface AppDatabaseExample : Transacter {
  public val userQueries: UserQueries

  public companion object {
    public val Schema: SqlSchema<QueryResult.Value<Unit>>
      get() = AppDatabaseExample::class.schema

    public operator fun invoke(driver: SqlDriver): AppDatabaseExample =
        AppDatabaseExample::class.newInstance(driver)
  }
}
