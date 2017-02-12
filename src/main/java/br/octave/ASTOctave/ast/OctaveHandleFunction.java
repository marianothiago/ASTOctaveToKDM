package br.octave.ASTOctave.ast;

public class OctaveHandleFunction extends ElementoOctave{

	private OctaveIdentificador identificador;
	
	public OctaveHandleFunction(OctaveIdentificador identificdor){
		this.identificador = identificdor;
	}
	
	public OctaveIdentificador getIdentificador(){
		return this.identificador;
	}
}
