package br.octave.ASTOctave.ast;

public class OctaveClassDef extends ElementoOctave{

	private OctaveExpressao expression;
	
	public OctaveClassDef(OctaveExpressao expression){
		this.expression = expression;
	}
	public OctaveExpressao getExpression(){
		return this.expression;
	}
}