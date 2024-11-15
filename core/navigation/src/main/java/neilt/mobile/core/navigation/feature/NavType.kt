package neilt.mobile.core.navigation.feature

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class NavType<T : Any>(
    private val serializer: KSerializer<T>,
    override val isNullableAllowed: Boolean = false
) : NavType<T>(isNullableAllowed = isNullableAllowed) {

    @ExperimentalSerializationApi
    override val name: String get() = serializer.descriptor.serialName

    override fun get(bundle: Bundle, key: String): T? {
        return bundle.getString(key)?.let { Json.decodeFromString(serializer, it) }
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, Json.encodeToString(serializer, value))
    }

    override fun parseValue(value: String): T {
        return Json.decodeFromString(serializer, Uri.decode(value))
    }

    override fun serializeAsValue(value: T): String {
        return Uri.encode(Json.encodeToString(serializer, value))
    }
}
