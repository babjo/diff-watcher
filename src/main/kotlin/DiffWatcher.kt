class DiffWatcher(
    private val targetGetter: TargetGetter,
    private val differ: Differ,
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
        differ
            .findDiff(last, current)
            ?.also { last = current }
}