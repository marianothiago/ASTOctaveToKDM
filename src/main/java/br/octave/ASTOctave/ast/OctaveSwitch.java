package br.octave.ASTOctave.ast;

public class OctaveSwitch extends ElementoOctave{

	private OctaveExpressao expressao;
	
	public OctaveSwitch(OctaveExpressao expressao){
		this.expressao = expressao;
	}
	public OctaveExpressao getOctaveExpressao(){
	    return this.expressao;
	}
}