package br.octave.ASTOctave.ast;

public class OctavePersistent  extends ElementoOctave{

	private OctaveExpressao expressao;
	
	public OctavePersistent(OctaveExpressao expressao){
		this.expressao = expressao;
	}
	public OctaveExpressao getOctaveExpressao(){
		return this.expressao;
	}
}