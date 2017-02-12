package br.octave.ASTOctave.ast;

import java.util.ArrayList;
import java.util.List;

public class OctaveChamadaFuncao extends ElementoOctave{

	private OctaveIdentificador identificador;
	private List<ElementoOctave> parametros;
	
	public OctaveChamadaFuncao(OctaveIdentificador identificador){
		this.identificador = identificador;
	}
	
	public OctaveIdentificador getIdentificador() {
		return identificador;
	}
	public void setIdentificador(OctaveIdentificador identificador) {
		this.identificador = identificador;
	}
	public List<ElementoOctave> getParametros() {
		return parametros;
	}
	public void setParametros(List<ElementoOctave> parametros) {
		this.parametros = parametros;
	}
	public void adicionaParametro(ElementoOctave parametro){
		if(this.parametros == null)
			this.parametros = new ArrayList<ElementoOctave>();
		this.parametros.add(parametro);
	}
}