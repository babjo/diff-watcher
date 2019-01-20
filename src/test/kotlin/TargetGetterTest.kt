import HtmlPageGetter

class TargetGetterTest {

    // @Test
    fun `should get page`() {
        println(HtmlPageGetter("http://google.com").get())
    }
}