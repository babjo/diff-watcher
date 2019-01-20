import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class DiffWatcherTest {

    @MockK
    lateinit var targetGetter: TargetGetter

    @MockK(relaxUnitFun = true)
    lateinit var notifier: Notifier

    @SpyK
    var differ = TextDiffer()

    @Test
    fun `should set the last, when starting to watch`() {
        // WHEN
        every { targetGetter.get() } returns "Hi"

        // GIVEN
        val sut = DiffWatcher(targetGetter, differ, notifier)

        // THEN
        assert(sut.last == "Hi")
    }

    @Test
    fun `should not notify, when there is no diff between current target and last target`() {
        // GIVEN
        every { targetGetter.get() } returnsMany listOf("Hi", "Hi")
        val sut = DiffWatcher(targetGetter, differ, notifier)

        // WHEN
        sut.watchOnce()

        // THEN
        verify(exactly = 0) { notifier.notify(any()) }
        assert(sut.last == "Hi")
    }

    @Test
    fun `should notify diff message, when there is a diff between current target and last target`() {
        // GIVEN
        every { targetGetter.get() } returnsMany listOf("Hi", "Hello")
        val sut = DiffWatcher(targetGetter, differ, notifier)

        // WHEN
        sut.watchOnce()

        // THEN
        verify { notifier.notify(any()) }
        assert(sut.last == "Hello")
    }
}
