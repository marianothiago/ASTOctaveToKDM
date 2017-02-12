package br.octave.ASTOctave.ast;

public class OctaveProperties extends ElementoOctave{

	private OctaveExpressao expression;
	
	public OctaveProperties(OctaveExpressao expression){
		this.expression = expression;
	}
	public OctaveExpressao getExpression(){
		return this.expression;
	}
}