import com.github.difflib.DiffUtils

@FunctionalInterface
interface Differ {
    fun findDiff(original: String, revised: String): String?
}

class TextDiffer : Differ {
    override fun findDiff(original: String, revised: String): String? =
        DiffUtils
            .diff(original.split("\n"), revised.split("\n"))
            .deltas
            .ifEmpty { return null }
            .joinToString { it.toString() }
}
