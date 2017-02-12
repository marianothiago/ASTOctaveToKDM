package br.octave.ASTOctave.ast;

public class OctaveExpressaoLooping extends OctaveExpressao{

	private ElementoOctave apartirDe;
	private ElementoOctave ate;
	
	public OctaveExpressaoLooping(ElementoOctave apartirDe, ElementoOctave ate){
		this.apartirDe = apartirDe;
		this.ate = ate;
	}
	public ElementoOctave getApartirDe(){
		return this.apartirDe;
	}
	public ElementoOctave getAte(){
		return this.ate;
	}
}