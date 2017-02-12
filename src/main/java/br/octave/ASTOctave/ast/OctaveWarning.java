package br.octave.ASTOctave.ast;

public class OctaveWarning extends ElementoOctave{

	private OctaveExpressao expressao;
	
	public OctaveWarning(OctaveExpressao expressao){
		this.expressao = expressao;
	}
	public OctaveExpressao getOctaveExpressao(){
		return this.expressao;
	}
}