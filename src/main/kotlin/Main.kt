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
    private val delay: Long by option(help = "Delay, default: 10 seconds").long().default(1000 * 10) // 10 seconds
    private val period: Long by option(help = "Period, default: 10 seconds").long().default(1000 * 10)

    override fun run() {
        val diffWatcher = DiffWatcher(
            HtmlPageGetter(url),
            TextDiffer(),
            LineNotifier(token)
        )
        Timer(false).scheduleAtFixedRate(delay, period) {
            diffWatcher.watchOnce()
        }
    }
}

fun main(args: Array<String>) = Cil().main(args)
