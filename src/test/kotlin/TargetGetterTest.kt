class TargetGetterTest {

    // @Test
    fun `should get page`() {
        println(HtmlPageGetter("http://google.com").get().blockingGet())
    }
}