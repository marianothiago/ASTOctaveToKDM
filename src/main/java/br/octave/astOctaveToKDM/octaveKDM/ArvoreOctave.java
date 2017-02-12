package br.octave.astOctaveToKDM.octaveKDM;

import java.util.List;

import br.octave.ASTOctave.ast.OctaveErrorLineParse;

public class ArvoreOctave {

	private No pai = null;
	private List<OctaveErrorLineParse> listError;
	
	public ArvoreOctave(No pai){
		this.pai = pai;
	}
	public No getPai(){
		return this.pai;
	}
	public void setListError(List<OctaveErrorLineParse> listError){
		this.listError = listError;
	}
	public List<OctaveErrorLineParse> getListError(){
		return this.listError;
	}
}