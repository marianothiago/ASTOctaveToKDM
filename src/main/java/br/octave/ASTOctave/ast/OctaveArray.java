package br.octave.ASTOctave.ast;

public class OctaveArray extends ElementoOctave{

	private String valor;
	private OctaveIdentificador identificador;
	
	public OctaveArray(String valor){
		this.valor = valor;
	}
	
	public String getValor(){
		return this.valor;
	}

	public OctaveIdentificador getIdentificador() {
		return identificador;
	}

	public void setIdentificador(OctaveIdentificador identificador) {
		this.identificador = identificador;
	}
}