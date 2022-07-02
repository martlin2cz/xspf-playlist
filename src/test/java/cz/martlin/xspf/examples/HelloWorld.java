package cz.martlin.xspf.examples;

import java.io.File;
import java.net.URI;

import cz.martlin.xspf.playlist.collections.XSPFTracks;
import cz.martlin.xspf.playlist.elements.XSPFFile;
import cz.martlin.xspf.playlist.elements.XSPFTrack;
import cz.martlin.xspf.util.XSPFException;

/**
 * The hello world, the most simpliest sample. Creates empty playlist, adds one
 * track, saves.
 * 
 * @author martin
 *
 */
public class HelloWorld {
	/**
	 * The main.
	 * 
	 * @param args
	 * @throws XSPFException
	 */
	public static void main(String[] args) throws XSPFException {
		if (args.length < 1) {
			System.err.println("Usage: FILE");
			System.exit(1);
		}

		String fileStr = args[0];
		File file = new File(fileStr);
		runHelloWorld(file);
	}

	/**
	 * Creates empty playlist, adds one track to it, saves to given file.
	 * 
	 * @param f
	 * @throws XSPFException
	 */
	protected static void runHelloWorld(File f) throws XSPFException {
		XSPFFile file = XSPFFile.create();
		XSPFTracks tracks = file.playlist().tracks();

		XSPFTrack track = tracks.createTrack(URI.create("file://hello-world.mp3"));
		tracks.add(track);

		file.save(f);
	}
}
