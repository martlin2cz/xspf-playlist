# xspf-playlist

An simple Java library implementing the manipulation (reading, but also creating and modifing) of the XSPF playlist files. The implementation is valid agains the XSPF file format specification, version 1: [https://xspf.org/](https://xspf.org/xspf-v1.html). 

## Hello world
The basic usage (create empty playlist, add one track, save to file) can be acchieved by following code snippet:
````
	XSPFFile file = XSPFFile.create();
	XSPFTracks tracks = file.playlist().tracks();

	XSPFTrack track = tracks.createTrack(URI.create("file://hello-world.mp3"));
	tracks.add(track);

	file.save(f);
````

Further usage can be found in the Wiki: [Usage](https://github.com/martlin2cz/xspf-playlist/wiki/Usage) 


### Examples
There are some sample programs in the `cz.martlin.xspf.examples` package in `src/test/java`. Like the `TracksFinder`:
````
$ TracksFinder "Kraftwerk - Computer World.xspf" "Compute"
Computer World
Computer World..2
Computer Love
Home Computer
It's More Fun To Compute
````

### Further notes
 - If some element or attribute is missing in the document, the corresponding method returns null.
 - In some rare cases, the `XSPFException` can be thrown. Think about that as like the `IOException`.
 - After you `set` some element or collection, it's recomended to re-obtain all the corresponding view instances (the `x()` methods).
 - Some more sample use cases can be found in the JUnit tests.
 - The code is documented and quite well structured.

### Changelog
#### Version 1.0
First oficial version. The basic features may be working correctly.

#### Version 2.0
Small changes, including:
 - `Printer` methods made public
 - when node has not the attribute present, null gets returned instead of empty string
 - `XSPFRuntimeException` replaces `RuntimeException` where it used to happen
 - added the "find by" methods to some collection types
 - examples moved from production _sources_ to _tests_

#### Version 2.0.1
Closes #1 (tons of blank spaces beeing generated in the produced xspf file).

#### Version 3.0
Closes #2 (nullable vs. non-nullable converters).

