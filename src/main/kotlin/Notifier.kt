import mu.KotlinLogging
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


@FunctionalInterface
interface Notifier {
    fun notify(message: String)
}

class LineNotifier(
    private val token: String,
    private val client: OkHttpClient = OkHttpClient()
) : Notifier {

    private val logger = KotlinLogging.logger {}

    override fun notify(message: String) {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("message", message)
            .build()

        val request = Request.Builder()
            .addHeader("Authorization", "Bearer $token")
            .url("https://notify-api.line.me/api/notify")
            .post(requestBody)
            .build()

        try {
            client.newCall(request).execute()
        } catch (e: IOException) {
            logger.error { "Failed to notify: error=$e" }
        }
    }
}