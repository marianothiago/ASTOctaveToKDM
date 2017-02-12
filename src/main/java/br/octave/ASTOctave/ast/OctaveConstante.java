package br.octave.ASTOctave.ast;

public class OctaveConstante extends ElementoOctave{

    private String valor;
	
	public OctaveConstante(String valor){
		this.valor = valor;
	}
	public String getValue(){
		return this.valor;
	}
}