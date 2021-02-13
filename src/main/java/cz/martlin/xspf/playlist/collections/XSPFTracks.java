package cz.martlin.xspf.playlist.collections;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFCollection;
import cz.martlin.xspf.playlist.elements.XSPFTrack;

public class XSPFTracks extends XSPFCollection<XSPFTrack> {

	public XSPFTracks(Element trackList) {
		super("track", trackList);
	}

	@Override
	protected XSPFTrack create(Element track) {
		return new XSPFTrack(track);
	}
	
	//TODO create track

}
