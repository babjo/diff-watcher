import io.reactivex.Completable
import java.util.*

class DiffWatcher(
    private val targetGetter: TargetGetter,
    private val differ: Differ,
    private val notifier: Notifier
) {
    internal var last: Optional<String> = Optional.empty()

    fun watchOnce() =
        targetGetter
            .get()
            .map(::getDiff)
            .flatMapCompletable(::notify)!!


    private fun getDiff(current: String) =
        last.flatMap { differ.findDiff(it, current) }
            .also { last = Optional.of(current) }

    private fun notify(optDiff: Optional<String>) =
        optDiff
            .map(notifier::notify)
            .orElseGet(Completable::complete)
}