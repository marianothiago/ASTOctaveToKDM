package br.octave.ASTOctave.ast;

public class OctaveMatriz extends ElementoOctave{

	private String valor;
	private OctaveIdentificador identificador;
	
	public OctaveMatriz(String valor){
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
