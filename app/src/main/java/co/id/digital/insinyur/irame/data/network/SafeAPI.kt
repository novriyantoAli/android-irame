package co.id.digital.insinyur.irame.data.network

import co.id.digital.insinyur.irame.util.ApiException
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

@Suppress("IMPLICIT_NOTHING_AS_TYPE_PARAMETER")
abstract class SafeAPI {
    suspend fun <T: Any> apiRequest(call: suspend () -> Response<T>) : T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            withContext(IO){
                val error = response.errorBody()?.string()
                val message = StringBuilder()
                error?.let {
                    try {
                        message.append(JSONObject(it).getString("error"))
                    } catch (e: JSONException) { e.printStackTrace() }
                }
                message.append("\nError Code: ${response.code()}")

                throw ApiException(message.toString())
            }
        }
    }
}