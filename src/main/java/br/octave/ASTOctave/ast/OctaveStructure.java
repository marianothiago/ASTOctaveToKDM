package br.octave.ASTOctave.ast;

public class OctaveStructure extends ElementoOctave{

	private String valor;
	
	public OctaveStructure(String valor){
		this.valor = valor;
	}
	
	public String getValor(){
		return this.valor;
	}
}