package br.octave.ASTOctave.ast;

public class OctaveExpressaoAutoIncremento extends OctaveExpressao{

	private OctaveVariavel var;
	
	public OctaveExpressaoAutoIncremento(OctaveVariavel var){
		this.var = var;
	}
	public OctaveVariavel getOctaveVariavel(){
		return this.var;
	}
}