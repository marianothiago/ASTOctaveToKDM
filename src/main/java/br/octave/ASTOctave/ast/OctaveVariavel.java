package br.octave.ASTOctave.ast;

public class OctaveVariavel extends ElementoOctave{
	
	private OctaveIdentificador identificador;
	
	public OctaveVariavel(OctaveIdentificador identificador){
		this.identificador = identificador;
	}
	
	public OctaveIdentificador getIdentificador(){
		return this.identificador;
	}
}