package br.octave.ASTOctave.ast;

public class OctaveImport extends ElementoOctave{

	private String valor;
	
	public OctaveImport(String tokenTemp) {
		this.valor = tokenTemp;
	}
	public String getValor(){
		return this.valor;
	}
}