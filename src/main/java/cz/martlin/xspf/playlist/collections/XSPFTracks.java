package cz.martlin.xspf.playlist.collections;

import java.net.URI;
import java.time.Duration;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFCollection;
import cz.martlin.xspf.playlist.elements.XSPFTrack;
import cz.martlin.xspf.util.XSPFException;

public class XSPFTracks extends XSPFCollection<XSPFTrack> {

	public XSPFTracks(Element trackList) {
		super(trackList);
	}

	@Override
	protected String elemName() {
		return "track";
	}

	@Override
	protected XSPFTrack create(Element track) {
		return new XSPFTrack(track);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public XSPFTrack createTrack(URI location) throws XSPFException {
		XSPFTrack track = createNew();
		track.setLocation(location);
		return track;
	}

	public XSPFTrack createTrack(URI location, String title) throws XSPFException {
		XSPFTrack track = createNew();
		track.setLocation(location);
		track.setTitle(title);
		return track;
	}

	public XSPFTrack createTrack(URI location, String title, int trackNum, Duration duration) throws XSPFException {
		XSPFTrack track = createNew();
		track.setLocation(location);
		track.setTitle(title);
		track.setTrackNum(trackNum);
		track.setDuration(duration);
		return track;
	}

	public XSPFTrack createTrack(URI location, String creator, String album, String title, int trackNum,
			Duration duration) throws XSPFException {
		
		XSPFTrack track = createNew();
		track.setLocation(location);
		track.setCreator(creator);
		track.setAlbum(album);
		track.setTitle(title);
		track.setTrackNum(trackNum);
		track.setDuration(duration);
		return track;
	}

}
