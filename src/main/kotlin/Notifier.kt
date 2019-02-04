import io.reactivex.Completable
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request


@FunctionalInterface
interface Notifier {
    fun notify(message: String): Completable
}

class LineNotifier(
    private val token: String,
    private val client: OkHttpClient = OkHttpClient()
) : Notifier {

    override fun notify(message: String) =
        Completable.fromCallable {
            client.newCall(
                Request
                    .Builder()
                    .addHeader("Authorization", "Bearer $token")
                    .url("https://notify-api.line.me/api/notify")
                    .post(
                        MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("message", message)
                            .build()
                    )
                    .build()
            ).execute()
        }!!
}