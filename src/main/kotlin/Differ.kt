import com.github.difflib.DiffUtils
import java.util.*

@FunctionalInterface
interface Differ {
    fun findDiff(original: String, revised: String): Optional<String>
}

class TextDiffer : Differ {
    override fun findDiff(original: String, revised: String): Optional<String> =
        DiffUtils
            .diff(original.split("\n"), revised.split("\n"))
            .deltas
            .ifEmpty { return Optional.empty() }
            .joinToString { it.toString() }
            .let { Optional.of(it) }
}
