# presentation-parser

Simple parser to quickly create slides for presentations.
Comes with exactly one theme and no configurable functionality.

## Build

Build with:

```
maven clean package
```

Will create a ZIP file named `pres-parser.zip` in the `target` directory.

## Usage

Via command line.

```
java -jar ./pres-parser.jar path/to/notes.txt path/to/presentation.html
```

or

```
pres-parser path/to/notes.txt path/to/presentation.html
```

## Formatting

This only supports a few formatting options.
An example file can be found in `examples\example.txt`.

```

! Title

~~~ (next slide)

# Heading
- item

## Sub-heading
- item
-- sub-item

~~~

||||||||

first column

||||||||

second column

|||||||| end

~~~

$$$ css/js/java/...
$ /* code */
$ var i = 0

$$$ html
$ <div>
$   <div class="indented">
$     <img src="example.jpg" alt="example" />
$   </div>
$   <div class="indented">description</div>
$ </div>
```

## License

MIT. See `LICENSE.txt`.

## Third-party

Contains a bundled version of Highlight.js
- https://highlightjs.org/
- https://github.com/highlightjs/highlight.js/

Uses Apache Commons IO as a runtime dependency.

See LICENSE-3RD-PARTY.txt for license information.
