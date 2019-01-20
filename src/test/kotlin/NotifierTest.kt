import org.junit.jupiter.api.Test

class NotifierTest {

    @Test
    fun `should do nothing with invalid token`() {
        // GIVEN
        val token = ""

        // WHEN, THEN
        LineNotifier(token).notify("Hi")
    }
}