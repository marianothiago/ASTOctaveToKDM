package br.octave.ASTOctave.ast;

public class OctaveIdentificador extends ElementoOctave{

	private String nome;
	
	public OctaveIdentificador(String nome) {
		this.nome = nome;
	}
	public String getNome(){
		return this.nome;
	}
}