# xspf-playlist

An simple Java library implementing the manipulation (reading, but also creating and modifing) of the XSPF playlist files.

Usage:
````
	File f = ...;
	XSPFFile file = XSPFFile.load(f);
	//            = XSPFFile.create();
	
	XSPFPlaylist playlist = file.playlist();

	// do whatever you desire
	
	file.save(f);
````

Use the getters and setters on the `XSPFPlaylist` instance as desired. The API is strongly typed (based on the specification):
````
	String title = playlist.getTitle();
	playlist.setTitle("Unnamed playlist");
	
	LocalDateTime created = playlist.getDate();

	playlist.setLicense(URI.create("https://some.uri/to/the/license.txt"));
````

More info later.
	
