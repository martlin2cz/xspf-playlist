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

## Usage
There are elements (subclasses of `XSPFElement`) (attribution) collections (subclasses of `XSPFCollection`) (metas, links, extensions, tracks) ad finally the general nodes (direct subclasses of `XSPFNode`) (the whole xspf file). 

The elements are more likely just the POJOs, the collections are similar to java collections.

There do is one exception. To allow more flexibility, the elements or collections fields of elements have triple encapsulation. The `getX()` methods returns the copy of the actual X (for instance, the list of metas), thus its modification won't affect the actual playlist. To do so, call the `setX(X)` method with that X instnace. However, to modify the playlist "in-place" (without the get+set mechanism), there do is the `x()` method. This returns "view" to the particular part of the object, and modifiing that modifies directly the actual playlist instance.

The `XSPFile` is the main entry point.

### Get instance
You can either load or create (empty) the `XSPFFile`:
````
	File f = ...;
	XSPFFile file = XSPFFile.load(f);
	//            = XSPFFile.create();
````

### Get playlist
Nextly, you can obtain the `XSPFPlaylist` instance. Either by get+set mechanism or by the "view" mechanism:
````
    // get+set approach
    XSPFPlaylist playlist = file.getPlaylist();
    // ... modify the playlist ...
    file.setPlaylist(playlist);

    // or
	XSPFPlaylist playlist = file.playlist();
    // ... modify the playlist directly ...
````

### Modify the element properties

Use the getters and setters on the any element instance as desired. The API is strongly typed (based on the specification):
````
	String title = playlist.getTitle();
	playlist.setTitle("Unnamed playlist");

	LocalDateTime created = playlist.getDate();

	playlist.setLicense(URI.create("https://some.uri/to/the/license.txt"));
````

### Working with the collections
Following explains how to get and modify some meta field:
````
	XSPFMetas metas = playlist.metas();
    XSPFMeta meta = metas.list().findFirst().get(); // simply pick one
    newMeta.setContent("Sample meta for the README purposes");
````

Here is demonstration of add/remove operations on the links:
````
    XSPFLinks links = playlist.links();
    XSPFLink linkToRemove = metas.list().findFirst().get(); // simply pick one
    links.remove(linkToRemove);

    XSPFLink linkToAdd = linksView.createLink(URI.create("Hello"), URI.create("world"));
    links.add(linkToAdd);
````

You can iterate over the collection following way:
````
	XSPFMetas tracks = playlist.tracks();
    for (XSPFTrack track: tracks.iterate()) {
        System.out.println(track.getTitle());
    }
````

If you want to create brand new collection, use the file instace:
````
	XSPFExtensions extensions = file.newExtensions();
    // ... initialize ...
    track.setExtensions(extensions);
````

### Working with atttribution
The attribution element is not well supported. Simple usage:
````
    XSPFAttribution attribution = playlist.attribution();

	attribution.add("location", "whatever-location");
	attribution.add("identifier", "whatever-identifier");

    for (XSPFAttributionItem item: attribution.list()) {
        System.out.println(item.element + ": " + item.identifier);
    }
````

### Working with extensions
The extensions are specific since they contain custom XML content. Furthermore, the namespace of the nested XML may differ from the xspf's default namespace. Thus, an instance of the `XMLDocumentUtility` is recomended to be used:
````
	XSPFExtensions extensions = playlist.extensions();
	XSPFExtension extension = extensions.createExtension(URI.create("myapp.com"));

    XMLDocumentUtility extensionUtil = extension.getUtility("ma", "myapp.com/xmlns");
	Element extensionElem = extension.getElement();

    // set value
    Genre genre = Genre.ROCK; // application specific enum
    extensionUtil.setElementValue(extensionElem, genre, g -> g.name());

    // get value
    Genre genre = extensionUtil.getElementValue(extensionElem, v -> Genre.valueOf(v));
````

### Save
Finally save the xspf file.
````
    File f = ...;
	file.save(f);
````

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



	
