import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

@FunctionalInterface
interface TargetGetter {
    fun get(): String
}

class HtmlPageGetter(private val url: String) : TargetGetter {

    init {
        val os = when (System.getProperty("os.name")) {
            "Mac OS X" -> "mac"
            else -> "linux"
        }
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver_$os")
    }

    override fun get(): String =
        ChromeDriver(ChromeOptions().apply { setHeadless(true) })
            .also { it.get(url) }
            .let {
                val page = it.pageSource
                it.close()
                it.quit()
                page
            }
}