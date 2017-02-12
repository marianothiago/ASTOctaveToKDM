package br.octave.ASTOctave.ast;

public class OctaveVariavelStructure extends ElementoOctave{

	private String valor;
	
	public OctaveVariavelStructure(String valor){
		this.valor = valor;
	}
	
	public String getValor(){
		return this.valor;
	}
}