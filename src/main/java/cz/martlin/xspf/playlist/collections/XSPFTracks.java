package cz.martlin.xspf.playlist.collections;

import java.net.URI;
import java.time.Duration;
import java.util.Objects;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFCollection;
import cz.martlin.xspf.playlist.elements.XSPFTrack;
import cz.martlin.xspf.util.ExceptionWrapper;
import cz.martlin.xspf.util.Names;
import cz.martlin.xspf.util.XSPFException;

/**
 * An collection of the {@link XSPFTrack}s.
 * 
 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14
 * @author martin
 *
 */
public class XSPFTracks extends XSPFCollection<XSPFTrack> {

	/**
	 * Creates instance.
	 * 
	 * @param trackList an trackList element.
	 */
	public XSPFTracks(Element trackList) {
		super(trackList);
	}

	@Override
	protected String elemName() {
		return Names.TRACK;
	}

	@Override
	protected XSPFTrack create(Element track) {
		return new XSPFTrack(track);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates (empty) track.
	 * 
	 * @return
	 * @throws XSPFException
	 */
	public XSPFTrack createTrack() throws XSPFException {
		return createNew();
	}

	/**
	 * Creates track with given location.
	 * 
	 * @param location
	 * @return
	 * @throws XSPFException
	 */
	public XSPFTrack createTrack(URI location) throws XSPFException {
		XSPFTrack track = createNew();
		track.setLocation(location);
		return track;
	}

	/**
	 * Creates track with given location and title.
	 * 
	 * @param location
	 * @param title
	 * @return
	 * @throws XSPFException
	 */
	public XSPFTrack createTrack(URI location, String title) throws XSPFException {
		XSPFTrack track = createNew();
		track.setLocation(location);
		track.setTitle(title);
		return track;
	}

	/**
	 * Creates track with given location, title trackNum and duration.
	 * 
	 * @param location
	 * @param title
	 * @param trackNum
	 * @param duration
	 * @return
	 * @throws XSPFException
	 */
	public XSPFTrack createTrack(URI location, String title, int trackNum, Duration duration) throws XSPFException {
		XSPFTrack track = createNew();
		track.setLocation(location);
		track.setTitle(title);
		track.setTrackNum(trackNum);
		track.setDuration(duration);
		return track;
	}

	/**
	 * Creates track with given location, creator, album, title, trackNum and
	 * duration.
	 * 
	 * @param location
	 * @param creator
	 * @param album
	 * @param title
	 * @param trackNum
	 * @param duration
	 * @return
	 * @throws XSPFException
	 */
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
/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns track having the given trackNum. Null if no such.
	 * 
	 * @param trackNum
	 * @return
	 * @throws XSPFException
	 */
	public XSPFTrack track(int trackNum) throws XSPFException {
		return list() //
				.filter(ExceptionWrapper.wrapPredicate( //
						t -> Objects.equals(trackNum, t.getTrackNum()))) //
				.findAny().orElse(null);
	}
	
	/**
	 * Returns track having the given title. Null if no such.
	 * 
	 * @param title
	 * @return
	 * @throws XSPFException
	 */
	public XSPFTrack track(String title) throws XSPFException {
		return list() //
				.filter(ExceptionWrapper.wrapPredicate( //
						t -> Objects.equals(title, t.getTitle()))) //
				.findAny().orElse(null);
	}

}
