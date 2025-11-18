package com.example.persistenciaynetworkingmultiplatform

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.Long
import kotlin.String

public class UserQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> selectAllUsers(mapper: (
    id: Long,
    name: String,
    email: String,
  ) -> T): Query<T> = Query(-319_402_680, arrayOf("user"), driver, "User.sq", "selectAllUsers",
      "SELECT user.id, user.name, user.email FROM user") { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!
    )
  }

  public fun selectAllUsers(): Query<User> = selectAllUsers { id, name, email ->
    User(
      id,
      name,
      email
    )
  }

  /**
   * @return The number of rows updated.
   */
  public fun insertUser(name: String, email: String): QueryResult<Long> {
    val result = driver.execute(-186_320_919, """INSERT INTO user(name, email) VALUES (?, ?)""", 2)
        {
          bindString(0, name)
          bindString(1, email)
        }
    notifyQueries(-186_320_919) { emit ->
      emit("user")
    }
    return result
  }
}
