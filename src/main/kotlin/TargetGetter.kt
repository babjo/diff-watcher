import io.reactivex.Single
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

@FunctionalInterface
interface TargetGetter {
    fun get(): Single<String>
}

class HtmlPageGetter(private val url: String) : TargetGetter {

    init {
        val os = when (System.getProperty("os.name")) {
            "Mac OS X" -> "mac"
            else -> "linux"
        }
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver_$os")
    }

    override fun get(): Single<String> =
        Single.fromCallable {
            val driver = ChromeDriver(ChromeOptions().setHeadless(true))
            driver.get(url)
            val page = driver.pageSource
            driver.close()
            driver.quit()
            page
        }
}