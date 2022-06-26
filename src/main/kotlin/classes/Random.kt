package classes

import java.security.SecureRandom
import java.io.IOException
import org.json.JSONObject
import okhttp3.*

open class Random {
    private val secureRandom = SecureRandom.getInstance("SHA1PRNG")
    private val client = OkHttpClient()

    fun getNumber(bound: Int): Int {
        secureRandom.setSeed((waterLevel() * 100).toLong())
        return secureRandom.nextInt(bound + 1)
    }

    private fun waterLevel(): Double {
        var msg = ""
        val request = Request.Builder()
            .url("https://app.city-monitor.com/public/sensors/7dc03848-8846-47e6-9209-c8c06a306c55/last_event")
            .build()
        try {
            client.newCall(request).execute().use { response ->
                msg += response.body!!.string()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return JSONObject(msg).get("level").toString().toDouble()
    }
}
