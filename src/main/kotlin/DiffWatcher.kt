import com.github.difflib.DiffUtils

class DiffWatcher(
    private val targetGetter: TargetGetter,
    private val notifier: Notifier
) {
    var last: String = targetGetter.get()

    fun watchOnce() {
        try {
            targetGetter
                .get()
                .let(this::getDiff)
                ?.also(notifier::notify)
        } catch (e: Exception) {
            notifier.notify(e.toString())
        }
    }

    private fun getDiff(current: String): String? =
        DiffUtils
            .diff(last.split("\n"), current.split("\n"))
            .deltas
            .ifEmpty { return null }
            .joinToString { it.toString() }
            .also { last = current }
}
