import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.long
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate


class Cil : CliktCommand() {
    private val token: String by argument(help = "Line notify token, see https://notify-bot.line.me/en")
    private val url: String by argument(help = "Url path you want to watch")
    private val differClass: String by option(help = "Class to find diff, default: TextDiffer").default("TextDiffer")
    private val delay: Long by option(help = "Delay, default: 10 seconds").long().default(1000 * 10) // 10 seconds
    private val period: Long by option(help = "Period, default: 10 seconds").long().default(1000 * 10)

    override fun run() {
        val cls = Class.forName(differClass)
        val notifier = LineNotifier(token)

        val diffWatcher = DiffWatcher(
            HtmlPageGetter(url),
            cls.getDeclaredConstructor().newInstance() as Differ,
            notifier
        )

        Timer().scheduleAtFixedRate(delay, period) {
            diffWatcher.watchOnce().doOnError {
                notifier.notify("Raised an exception: $it")
            }
        }
    }
}

fun main(args: Array<String>) = Cil().main(args)
