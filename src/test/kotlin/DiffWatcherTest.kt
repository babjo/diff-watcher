import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import io.reactivex.Single.just
import org.junit.jupiter.api.BeforeEach
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

    private lateinit var sut: DiffWatcher

    @BeforeEach
    fun setUp() {
        sut = DiffWatcher(targetGetter, differ, notifier)
    }

    @Test
    fun `should set the last, when starting to watch`() {
        // WHEN
        every { targetGetter.get() } returns just("Hi")

        // WHEN
        sut.watchOnce().blockingGet()

        // THEN
        assert(sut.last.get() == "Hi")
    }

    @Test
    fun `should not notify, when there is no diff between current target and last target`() {
        // GIVEN
        every { targetGetter.get() } returnsMany listOf("Hi", "Hi").map { just(it) }

        // WHEN
        repeat(2) { sut.watchOnce().blockingGet() }

        // THEN
        verify(exactly = 0) { notifier.notify(any()) }
        assert(sut.last.get() == "Hi")
    }

    @Test
    fun `should notify diff message, when there is a diff between current target and last target`() {
        // GIVEN
        every { targetGetter.get() } returnsMany listOf("Hi", "Hello").map { just(it) }

        // WHEN
        repeat(2) { sut.watchOnce().blockingGet() }

        // THEN
        verify { notifier.notify(any()) }
        assert(sut.last.get() == "Hello")
    }
}
