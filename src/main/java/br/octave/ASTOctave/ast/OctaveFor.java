package br.octave.ASTOctave.ast;

public class OctaveFor extends ElementoOctave{

	private OctaveExpressao expressao;
	private OctaveExpressao variavel;
	
	public OctaveFor(OctaveExpressao variavel, OctaveExpressao expressao){
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