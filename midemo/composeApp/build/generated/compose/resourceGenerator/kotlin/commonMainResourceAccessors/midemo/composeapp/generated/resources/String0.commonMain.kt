@file:OptIn(InternalResourceApi::class)

package midemo.composeapp.generated.resources

import kotlin.OptIn
import kotlin.String
import kotlin.collections.MutableMap
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.ResourceItem
import org.jetbrains.compose.resources.StringResource

private const val MD: String = "composeResources/midemo.composeapp.generated.resources/"

internal val Res.string.app_name: StringResource by lazy {
      StringResource("string:app_name", "app_name", setOf(
        ResourceItem(setOf(), "${MD}values/strings.commonMain.cvr", 10, 36),
      ))
    }

internal val Res.string.greeting: StringResource by lazy {
      StringResource("string:greeting", "greeting", setOf(
        ResourceItem(setOf(), "${MD}values/strings.commonMain.cvr", 47, 36),
      ))
    }

internal val Res.string.welcome_message: StringResource by lazy {
      StringResource("string:welcome_message", "welcome_message", setOf(
        ResourceItem(setOf(), "${MD}values/strings.commonMain.cvr", 84, 43),
      ))
    }

@InternalResourceApi
internal fun _collectCommonMainString0Resources(map: MutableMap<String, StringResource>) {
  map.put("app_name", Res.string.app_name)
  map.put("greeting", Res.string.greeting)
  map.put("welcome_message", Res.string.welcome_message)
}
