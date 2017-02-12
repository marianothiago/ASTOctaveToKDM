package br.octave.ASTOctave.ast;

public class OctaveDo extends ElementoOctave{

	private OctaveExpressao expressao;
	
	public OctaveExpressao getOctaveExpressao(){
		return this.expressao;
	}	
	public void setExpressao(OctaveExpressao expressao){
		this.expressao = expressao;
	}
}