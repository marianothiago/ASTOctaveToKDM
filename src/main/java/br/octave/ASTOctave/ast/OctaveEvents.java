package br.octave.ASTOctave.ast;

public class OctaveEvents extends ElementoOctave{

	private OctaveExpressao expression;
	
	public OctaveEvents(OctaveExpressao expression){
		this.expression = expression;
	}
	public OctaveExpressao getExpression(){
		return this.expression;
	}
}