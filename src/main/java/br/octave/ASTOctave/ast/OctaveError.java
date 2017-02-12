package br.octave.ASTOctave.ast;

public class OctaveError extends ElementoOctave{

	private OctaveExpressao expressao;
	
	public OctaveError(OctaveExpressao expressao){
		this.expressao = expressao;
	}
	public OctaveExpressao getOctaveExpressao(){
		return this.expressao;
	}
}