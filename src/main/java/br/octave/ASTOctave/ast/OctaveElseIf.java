package br.octave.ASTOctave.ast;

public class OctaveElseIf extends ElementoOctave{

	private OctaveExpressao expressao;
	
	public OctaveElseIf(OctaveExpressao expressao){
		this.expressao = expressao;
	}
	
	public OctaveExpressao getOctaveExpressao(){
		return this.expressao;
	}
}