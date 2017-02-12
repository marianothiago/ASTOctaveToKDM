package br.octave.ASTOctave.ast;

public class OctaveIf extends ElementoOctave{

	private OctaveExpressao expressao;
	
	public OctaveIf(OctaveExpressao expressao){
		this.expressao = expressao;
	}
	
	public OctaveExpressao getOctaveExpressao(){
		return this.expressao;
	}
}