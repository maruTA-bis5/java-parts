# Java Parts / std-class-wrap

## Contents
### net.bis5.jparts.wrapper.io.IterableBufferedReader
`Iterable<String>`を実装した`BufferedReader`。Java SE8なら素直に`BufferedReader#lines()`でStream APIを使うべきですが、
もろもろの事情でStream APIが使えない環境向けの`Iterable<String>`実装。
