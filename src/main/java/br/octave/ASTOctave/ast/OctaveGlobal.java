package br.octave.ASTOctave.ast;

public class OctaveGlobal extends ElementoOctave{

	private OctaveExpressao expressao;
	
	public OctaveGlobal(OctaveExpressao expressao){
		this.expressao = expressao;
	}
	public OctaveExpressao getOctaveExpressao(){
		return this.expressao;
	}
}