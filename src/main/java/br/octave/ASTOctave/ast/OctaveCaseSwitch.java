package br.octave.ASTOctave.ast;

public class OctaveCaseSwitch extends ElementoOctave{

	private OctaveExpressao expressao;
	
	public OctaveCaseSwitch(OctaveExpressao expressao){
		this.expressao = expressao;
	}
	public OctaveExpressao getOctaveExpressao(){
		return this.expressao;
	}
}