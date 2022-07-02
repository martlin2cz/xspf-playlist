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


## Examples
There are some sample programs in the `cz.martlin.xspf.examples` package in `src/test/java`. Like the `TracksFinder`:
````
$ TracksFinder "Kraftwerk - Computer World.xspf" "Compute"
Computer World
Computer World..2
Computer Love
Home Computer
It's More Fun To Compute
````

## Changelog
Moved to Wiki: [Changelog](github.com/martlin2cz/xspf-playlist/wiki/Changelog-release-info) 

