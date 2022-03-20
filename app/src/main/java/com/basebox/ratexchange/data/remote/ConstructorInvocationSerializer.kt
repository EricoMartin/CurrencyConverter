package com.basebox.ratexchange.data.remote

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ConstructorInvocationDeserializer<Usd>(
    private val fields: Array<String>
) : JsonDeserializer<Usd> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Usd {
        val clazz = TypeToken.get(typeOfT).rawType
        var args: Array<Any?> = arrayOf()
        val appropriateConstructor = clazz.constructors
            .filter { it.parameterTypes.size == fields.size }
            .find {
                try {
                    args = deserializeEach(json.asJsonObject, it.parameterTypes, context)
                    true
                } catch (e: JsonSyntaxException) {
                    false
                }
            }
        return appropriateConstructor!!.newInstance(*args) as Usd
    }

    @Throws(JsonSyntaxException::class)
    private fun deserializeEach(
        json: JsonObject,
        fieldTypes: Array<Class<*>>,
        context: JsonDeserializationContext
    ): Array<Any?> {
        val result = Array<Any?>(fields.size, { null })
        fields.forEachIndexed { index, name ->
            result[index] = context.deserialize(json.get(name), fieldTypes[index])
        }
        return result
    }

}