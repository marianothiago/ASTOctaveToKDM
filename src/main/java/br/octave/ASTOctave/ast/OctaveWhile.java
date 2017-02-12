package br.octave.ASTOctave.ast;

public class OctaveWhile extends ElementoOctave{

	private OctaveExpressao expressao;
	
	public OctaveWhile(OctaveExpressao expressao){
		this.expressao = expressao;
	}
	
	public OctaveExpressao getOctaveExpressao(){
		return this.expressao;
	}	
}