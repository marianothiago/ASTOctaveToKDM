package br.octave.ASTOctave.ast;

public class OctaveString extends ElementoOctave{

	private String valor;
	
	public OctaveString(String valor){
		this.valor = valor;
	}
	public String getValor(){
		return this.valor;
	}
}
