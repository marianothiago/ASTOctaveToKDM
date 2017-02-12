package br.octave.ASTOctave.ast;

public class OctaveParFor extends ElementoOctave{

	private OctaveExpressao expressao;
	private OctaveExpressao variavel;
	
	public OctaveParFor(OctaveExpressao variavel, OctaveExpressao expressao){
		this.variavel = variavel;
		this.expressao = expressao;
	}
	
	public OctaveExpressao getOctaveExpressao(){
		return this.expressao;
	}
	
	public OctaveExpressao getVariavel(){
		return this.variavel;
	}
}