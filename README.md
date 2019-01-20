# diff-watcher

Please watch a web page and let me know, if the web page is changed!  


### Getting Started
1. Generate LINE notify token!: https://notify-bot.line.me/en
2. Build and Enjoy
```bash
❯ ./gradlew installDist
❯ ./build/install/diff-watcher/bin/diff-watcher -h


Usage: cil [OPTIONS] TOKEN URL

Options:
  --delay INT   Delay
  --period INT  Period
  -h, --help    Show this message and exit

Arguments:
  TOKEN  Line notify token, see https://notify-bot.line.me/en
  URL    Url path you want to watch

❯ ./build/install/diff-watcher/bin/diff-watcher (YOUR_TOKEN) "http://google.com"
```