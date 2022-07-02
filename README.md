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

## More complex sample
````
    // Load the playlist
    File f = /* ... */ ;
    XSPFFile file = XSPFFile.load(f);
    XSPFPlaylist playlist = file.playlist();
    
    // Get playlist data
    if (!"me".equals(playlist.getCreator())) {
        throw new IllegalArgumentException("It's not me!");
    }

    // Set playlist data
    playlist.setTitle("Not the best music ever");
    playlist.setInfo(URI.create("https://github.com/martlin2cz/xspf-playlist/wiki"));
    
    XSPFMetas metas = playlist.metas();
    metas.add(metas.createMeta(URI.create("author"), "martlin2cz"));
    
    // Pick some track
    XSPFTrack lorem = playlist.tracks().createTrack();
    
    // Get some track data
    System.out.println("Track " + lorem.getTitle() + " links:");
    for (XSPFLink link: lorem.links().iterate()) {
        System.out.println(link.getRel() + " -> " + link.getContent());
    }
    
    // Set some track data
    lorem.setTrackNum(1);
    lorem.setAlbum("The foobar");
    lorem.setDuration(Duration.ofSeconds(42));
    
    // Save the playlist
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

