import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

@FunctionalInterface
interface TargetGetter {
    fun get(): String
}

class HtmlPageGetter(private val url: String) : TargetGetter {

    private var driver: ChromeDriver

    init {
        val os = when (System.getProperty("os.name")) {
            "Mac OS X" -> "mac"
            else -> "linux"
        }

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver_$os")
        driver = ChromeDriver(ChromeOptions().apply { setHeadless(true) })
    }

    override fun get(): String =
        driver.get(url)
            .let {
                val page = driver.pageSource
                driver.close()
                driver.quit()
                page
            }
}