package br.octave.ASTOctave.ast;

public class OctaveExpressaoAutoDecremento  extends OctaveExpressao{

	private OctaveVariavel var;
	
	public OctaveExpressaoAutoDecremento(OctaveVariavel var){
		this.var = var;
	}
	public OctaveVariavel getOctaveVariavel(){
		return this.var;
	}
}