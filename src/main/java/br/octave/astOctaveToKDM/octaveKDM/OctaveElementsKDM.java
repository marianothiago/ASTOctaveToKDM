package br.octave.astOctaveToKDM.octaveKDM;

import br.octave.structureKDM.KDMSegment;

public class OctaveElementsKDM {

	private KDMSegment segment;
	private String message;
	public OctaveElementsKDM(KDMSegment segment, String message) {
		super();
		this.segment = segment;
		this.message = message;
	}
	public KDMSegment getSegment() {
		return segment;
	}
	public String getMessage() {
		return message;
	}
}