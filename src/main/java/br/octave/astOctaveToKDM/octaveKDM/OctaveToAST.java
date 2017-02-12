//package br.octave.astOctaveToKDM.octaveKDM;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import org.eclipse.dltk.ast.ASTNode;
//import org.eclipse.dltk.ast.declarations.Argument;
//
//import br.octave.ASTOctave.ast.OctaveBinaryExpression;
//import br.octave.ASTOctave.ast.OctaveConstant;
//import br.octave.ASTOctave.ast.OctaveFunctionDeclaration;
//import br.octave.ASTOctave.ast.OctaveFunctionStatement;
//import br.octave.ASTOctave.ast.OctaveIdentifier;
//import br.octave.ASTOctave.ast.OctaveMatrixExpression;
//import br.octave.ASTOctave.ast.OctaveString;
//import br.octave.ASTOctave.ast.OctaveSymbolReference;
//import br.octave.ASTOctave.ast.OctaveVariableDeclaration;
//import br.octave.ASTOctave.util.OctaveAST;
//import br.octave.structureKDM.CompilationUnit;
//import br.octave.structureKDM.KDMSegment;
//import br.octave.structureKDM.ParameterUnit;
//import br.octave.structureKDM.Signature;
//import br.octave.structureKDM.codeElement.AbstractCodeElement;
//import br.octave.structureKDM.codeElement.ActionElement;
//import br.octave.structureKDM.codeElement.BlockUnit;
//import br.octave.structureKDM.codeElement.CallableUnit;
//import br.octave.structureKDM.codeElement.StorableUnit;
//import br.octave.structureKDM.inventoryElement.Directory;
//import br.octave.structureKDM.inventoryElement.SourceFile;
//import br.octave.structureKDM.model.CodeModel;
//import br.octave.structureKDM.model.InventoryModel;
//
//public class OctaveToAST {
//
//	private KDMSegment segment;
//	private InventoryModel inventory;
//	private CodeModel code;
//	private CompilationUnit compilation;
//	private List<String> filesName; 
//	private String kindBlock;
//	private int qtdParsedFiles = 0;
//	
//	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException, IOException{
//		OctaveToAST oast = new OctaveToAST();
//		File f = new File("/home/thiago/matlab2tikz-master");
//		OctaveElementsKDM elements = oast.loadProject(f, "matlab2tikz");
//		System.out.println(elements.getMessage());
//	}
//	
//	public OctaveElementsKDM loadProject(File directory, String nameProject) throws IllegalArgumentException, IllegalAccessException,
//			NoSuchFieldException, SecurityException, ClassNotFoundException, IOException {
//		String messageError = "", messageParsedFiles = "";
//		if(nameProject.trim().equals("")){
//			segment = new KDMSegment(directory.getName());
//		}else{
//			segment = new KDMSegment(nameProject);
//		}
//		this.inventory = new InventoryModel("source references");
//		this.code = new CodeModel(nameProject);
//		this.segment.addSegment(code);
//		this.segment.addSegment(inventory);
//		loadFile(directory);
//		if(this.qtdParsedFiles > 0){
//			messageParsedFiles = this.qtdParsedFiles+" Octave files were parsed.";
//		}
//		if(this.filesName != null){
//			messageError = "Can't was possible to parse "+this.filesName.size()+" Octave files:\n\n";
//			Iterator<String> itr = this.filesName.iterator();
//			while(itr.hasNext()){
//				messageError = messageError + itr.next()+"\n";
//			}
//		}
//		return new OctaveElementsKDM(this.segment,messageParsedFiles+"\n\n"+messageError);
//	}
//
//	private void loadFile(File directory) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException, IOException{
//        Directory dir = new Directory(directory.getPath());
//        this.inventory.addInventoryElement(dir);
//		File[] files = directory.listFiles();
//		for (File file : files) {
//			if (file.isDirectory()) {
//				loadFile(file);
//			} else {
//				if(file.getName().substring(file.getName().length()-2, file.getName().length()).equals(".m")){
//					OctaveAST ast = new OctaveAST();
//					List<ASTNode> nodes = ast.create(file);
//					if (nodes == null) {
//						if (filesName == null){
//							filesName = new ArrayList<String>();
//						}
//						filesName.add(file.getAbsolutePath());
//					} else {
//						this.qtdParsedFiles++;
//						SourceFile source = new SourceFile(file.getName(), file.getPath());
//						this.inventory.addInventoryElement(source);
//						this.compilation = new CompilationUnit(file.getName());
//						this.code.addModule(this.compilation);
//						this.kindBlock = "block principal";
//						if(file.getName().equals("caedown.m")){
//							System.out.println("");
//						}
//						sweepAST((ASTNode)nodes.get(0),null,this.compilation);
//					}
//				}
//			}
//		}
//	}
//	
//	private void sweepAST(ASTNode astNode, ASTNode nodeParent, AbstractCodeElement codeElement) throws IOException, IllegalArgumentException,
//			IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException {
//		AbstractCodeElement element = parseToken(astNode,nodeParent,codeElement);
//		if(astNode instanceof OctaveVariableDeclaration){
//			OctaveVariableDeclaration variable = (OctaveVariableDeclaration)astNode;
//			ASTNode variableAST = variable.getExpression();
//			if(variableAST != null)
//			    element = parseToken(variableAST,astNode,element);
//		}
//		List<ASTNode> filhos = astNode.getChilds();
//		if ((filhos != null)&&(filhos.size() > 0)) {
//			for (int x = 0; x < filhos.size(); x++) {
//				ASTNode filho = (ASTNode) filhos.get(x);
//				if(!filho.isRead()){
//					if(element == null)
//						sweepAST(filho,astNode,codeElement);
//					else
//						sweepAST(filho,astNode,element);
//				}
//			}
//		}
//	}
//
//	private AbstractCodeElement parseToken(ASTNode no,ASTNode parent, AbstractCodeElement parentCodeElement) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException, IOException {
//		AbstractCodeElement codeElement = null;
//		String classe = no.getClass().getSimpleName();
//		String[] tokens = ((ASTNode) no).debugString().split("@");
//		if (tokens[0].contains("CallArgumentsList")) {
//			classe = "CallArgumentsList";
//		}
//
//		NodeOctave z = NodeOctave.valueOf(classe);
//		switch (z) {
//		case OctaveConstant: {
//			OctaveConstant token = (OctaveConstant) no;
//			codeElement = new ActionElement("constant", token.getValue());
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case OctaveAssignmentLHSExpression: {
//			codeElement = new ActionElement("assignment","assignment");
//			AbstractCodeElement storable = new StorableUnit("local");
//			codeElement.addCodeElement(storable);
//			sweepAST((ASTNode)no.getChilds().get(0),no,storable);
//			((ASTNode)no.getChilds().get(0)).read();
//			Iterator<ASTNode> itr = parent.getChilds().iterator();
//			while(itr.hasNext()){
//				ASTNode chield = itr.next();
//				if(chield != no){
//					sweepAST(chield, parent, codeElement);
//					chield.read();
//				}
//			}
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case OctaveFunctionStatement: {
//			OctaveFunctionStatement token = (OctaveFunctionStatement)no;
//			OctaveFunctionDeclaration funcDeclaration = token.getFuncDecl();
//			Signature signature = new Signature();
//			ParameterUnit parameter;
//			for(int x=0; x<funcDeclaration.getReturnList().size();x++){
//				OctaveVariableDeclaration ref = (OctaveVariableDeclaration)funcDeclaration.getReturnList().get(x);
//				parameter = new ParameterUnit(ref.getName(),"return",x+1);
//				signature.addParameter(parameter);
//			}
//			for(int x=0; x<funcDeclaration.getParamList().size();x++){
//				Argument ref = (Argument)funcDeclaration.getParamList().get(x);
//				parameter = new ParameterUnit(ref.getName(),"parameter",x+1);
//				signature.addParameter(parameter);
//			}
//			codeElement = new CallableUnit(token.getName(), signature);
//			((ASTNode)no.getChilds().get(0)).read();
//			this.kindBlock = "block callable unit";
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case OctaveSymbolReference: {
//			codeElement = new ActionElement("symbol reference", ((OctaveSymbolReference)no).getName());
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case OctaveVariableDeclaration: {
//			OctaveVariableDeclaration variable = (OctaveVariableDeclaration)no;
//			codeElement = new ActionElement("variable declaration", variable.getName());
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case OctaveBinaryExpression: {
//			OctaveBinaryExpression binaryExpression = (OctaveBinaryExpression)no;
//			if (binaryExpression.getToken().getText().equals("+")){
//				codeElement = new ActionElement("aritimetic operator", "plus");
//				parentCodeElement.addCodeElement(codeElement);
//			}
//			if (binaryExpression.getToken().getText().equals("-")){
//				codeElement = new ActionElement("aritimetic operator", "minus");
//				parentCodeElement.addCodeElement(codeElement);
//			}
//			if (binaryExpression.getToken().getText().equals("*")){
//				codeElement = new ActionElement("aritimetic operator", "multiply");
//				parentCodeElement.addCodeElement(codeElement);
//			}
//			if (binaryExpression.getToken().getText().equals("/")){
//				codeElement = new ActionElement("aritimetic operator", "slice");
//				parentCodeElement.addCodeElement(codeElement);
//			}
//			if (binaryExpression.getToken().getText().equals("&&")){
//				codeElement = new ActionElement("logical operator", "and");
//				parentCodeElement.addCodeElement(codeElement);
//			}
//			if (binaryExpression.getToken().getText().equals("&")){
//				codeElement = new ActionElement("logical operator", "and");
//				parentCodeElement.addCodeElement(codeElement);
//			}
//			if (binaryExpression.getToken().getText().equals("||")){
//				codeElement = new ActionElement("logical operator", "or");
//				parentCodeElement.addCodeElement(codeElement);
//			}
//			if (binaryExpression.getToken().getText().equals("|")){
//				codeElement = new ActionElement("logical operator", "or");
//				parentCodeElement.addCodeElement(codeElement);
//			}
//			if (binaryExpression.getToken().getText().equals("|")){
//				codeElement = new ActionElement("logical operator", "not");
//				parentCodeElement.addCodeElement(codeElement);
//			}
//			if (binaryExpression.getToken().getText().equals(">")){
//				codeElement = new ActionElement("relational operator", "greater");
//				parentCodeElement.addCodeElement(codeElement);
//			}
//			if (binaryExpression.getToken().getText().equals("<")){
//				codeElement = new ActionElement("relational operator", "less");
//				parentCodeElement.addCodeElement(codeElement);
//			}
//			if (binaryExpression.getToken().getText().equals(">=")){
//				codeElement = new ActionElement("relational operator", "greater equals");
//				parentCodeElement.addCodeElement(codeElement);
//			}
//			if (binaryExpression.getToken().getText().equals("<=")){
//				codeElement = new ActionElement("relational operator", "less equals");
//				parentCodeElement.addCodeElement(codeElement);
//			}
//			if (binaryExpression.getToken().getText().equals("==")){
//				codeElement = new ActionElement("relational operator", "equals");
//				parentCodeElement.addCodeElement(codeElement);
//			}
//			if (binaryExpression.getToken().getText().equals("!=")){
//				codeElement = new ActionElement("relational operator", "not equals");
//				parentCodeElement.addCodeElement(codeElement);
//			}
//			if (binaryExpression.getToken().getText().equals("~=")){
//				codeElement = new ActionElement("relational operator", "not equals");
//				parentCodeElement.addCodeElement(codeElement);
//			}
//			if (binaryExpression.getToken().getText().equals(":")){
//				codeElement = new ActionElement("until", "until");
//				parentCodeElement.addCodeElement(codeElement);
//			}
//		}
//			break;
//		case OctaveForStatement: {
//			codeElement = new ActionElement("for","for");
//			List<ASTNode> filhos = no.getChilds();
//			sweepAST(filhos.get(0), no, codeElement);
//			filhos.get(0).read();
//			sweepAST(filhos.get(1), no, codeElement);
//			filhos.get(1).read();
//			AbstractCodeElement codeElementBlock = new BlockUnit("block for");
//			codeElement.addCodeElement(codeElementBlock);
//			parentCodeElement.addCodeElement(codeElement);
//			codeElement = codeElementBlock;
//		}
//			break;
//		case OctaveGlobalStatement: {
//			codeElement = new ActionElement("global variable", "global variable");
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case OctaveSymbolCallExpression: {
//			codeElement = new ActionElement("call", "call");
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case OctaveMatrixExpression: {
//			codeElement = new ActionElement("matriz expression", "matriz expression");
//			OctaveMatrixExpression matriz = (OctaveMatrixExpression)no;
//			for(int y = 0; y < matriz.getChilds().size(); y++){
//				ASTNode filho = (ASTNode)matriz.getChilds().get(y);
//				sweepAST(filho,no,codeElement);
//				filho.read();
//			}
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case OctavePersistentStatement: {
//			codeElement = new ActionElement("persistent variable", "persistent variable");
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case OctaveExpressionStatement: {
//			codeElement = new ActionElement("expression statement", "expression statement");
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case OctaveIfStatement: {
//			codeElement = new ActionElement("if", "if");
//			parentCodeElement.addCodeElement(codeElement);
//			ASTNode conditional = (ASTNode)no.getChilds().get(0);
//			sweepAST(conditional,no,codeElement);
//			conditional.read();
//			AbstractCodeElement codeElementBlock = new BlockUnit("block if");
//			codeElement.addCodeElement(codeElementBlock);
//			codeElement = codeElementBlock;
//		}
//			break;
//		case OctaveElseIfClause: {
//			codeElement = new ActionElement("elseif", "elseif");
//			parentCodeElement.addCodeElement(codeElement);
//			ASTNode conditional = (ASTNode)no.getChilds().get(0);
//			sweepAST(conditional,no,codeElement);
//			conditional.read();
//			AbstractCodeElement codeElementBlock = new BlockUnit("block elseif");
//			codeElement.addCodeElement(codeElementBlock);
//			codeElement = codeElementBlock;
//		}
//			break;
//		case OctaveElseClause: {
//			codeElement = new ActionElement("else", "else");
//			parentCodeElement.addCodeElement(codeElement);
//			AbstractCodeElement codeElementBlock = new BlockUnit("block else");
//			codeElement.addCodeElement(codeElementBlock);
//			codeElement = codeElementBlock;
//		}
//			break;
//		case OctaveWhileStatement: {
//			codeElement = new ActionElement("while", "while");
//			parentCodeElement.addCodeElement(codeElement);
//			ASTNode conditional = (ASTNode)no.getChilds().get(0);
//			sweepAST(conditional,no,codeElement);
//			conditional.read();
//			AbstractCodeElement codeElementBlock = new BlockUnit("block while");
//			codeElement.addCodeElement(codeElementBlock);
//			codeElement = codeElementBlock;
//		}
//			break;
//		case OctaveDoStatement: {
//			codeElement = new ActionElement("do", "do");
//			parentCodeElement.addCodeElement(codeElement);
//			ASTNode conditional = (ASTNode)no.getChilds().get(0);
//			sweepAST(conditional,no,codeElement);
//			conditional.read();
//			AbstractCodeElement codeElementBlock = new BlockUnit("block do");
//			codeElement.addCodeElement(codeElementBlock);
//			codeElement = codeElementBlock;
//		}
//			break;
//		case OctaveTryStatement: {
//			codeElement = new ActionElement("try", "try");
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case OctaveUnwindStatement: {
//			codeElement = new ActionElement("unwind", "unwind");
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;	
//		case OctaveSwitchStatement: {
//			codeElement = new ActionElement("switch", "switch");
//			parentCodeElement.addCodeElement(codeElement);
//			ASTNode conditional = (ASTNode)no.getChilds().get(0);
//			sweepAST(conditional,no,codeElement);
//			conditional.read();
//			AbstractCodeElement codeElementBlock = new BlockUnit("block switch");
//			codeElement.addCodeElement(codeElementBlock);
//			codeElement = codeElementBlock;
//		}
//			break;
//		case OctaveUnaryExpression: {
//			codeElement = new ActionElement("unary", "unary");
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case OctaveJumpStatement: {
//			codeElement = new ActionElement("break", "break");
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case Block: {
//			codeElement = new BlockUnit(this.kindBlock);
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case CallArgumentsList: {
//			codeElement = new ActionElement("call parameters","call parameters");
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case OctaveString: {
////			OctaveString text = (OctaveString)no;
////			codeElement = new ActionElement("text", text.getValue());
////			parentCodeElement.addCodeElement(codeElement);
////			Thiago Mariano
//		}
//			break;
//		case OctaveIdentifier: {
//			OctaveIdentifier identifier = (OctaveIdentifier)no;
//			codeElement = new ActionElement("identifier", identifier.getToken().getText());
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case OctaveIndirectExpression: {
//			codeElement = new ActionElement("indirect expression", "indirect expression");
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case OctaveCellExpression: {
//			codeElement = new ActionElement("cell expression", "cell expression");
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case OctaveFunctionHandleExpression: {
//			codeElement = new ActionElement("function handle", "function handle");
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case OctaveAnonFunctionHandleExpression: {
//			codeElement = new ActionElement("function anonymous handle", "function anonymous handle");
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case OctaveColonExpression: {
//			codeElement = new ActionElement("colon expression", "colon expression");
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case OctavePostfixExpression: {
//			codeElement = new ActionElement("post fix", "post fix");
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case OctaveASTConstants: {
//			codeElement = new ActionElement("ast constant", "ast constant");
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case OctaveVariableKind: {
//			codeElement = new ActionElement("variable kind", "variable kind");
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case OctaveTranslationUnit: {
//			codeElement = new ActionElement("translation unit", "translation unit");
//			parentCodeElement.addCodeElement(codeElement);
//		}
//			break;
//		case OctaveDotCallExpression: {
//			codeElement = new ActionElement("DotCallExpression", "DotCallExpression");
//			parentCodeElement.addCodeElement(codeElement);
//			break;
//		}
//		case OctaveExceptionStatement: {
//			System.out.println("OctaveExceptionStatement");
//		}
//			break;
//		case OctaveExpression: {
//			System.out.println("OctaveExpression");
//		}
//			break;
//		case OctaveStatement: {
//			System.out.println("OctaveStatement");
//		}
//			break;
//		case OctaveASTListNode: {
//			System.out.println("OctaveASTListNode");
//		}
//			break;
//		case SimpleReference: {
//			System.out.println("SimpleReference");
//		}
//			break;
//		case Argument: {
//			System.out.println("Argument");
//		}
//			break;
//		case OctaveToken: {
//			System.out.println("OctaveToken");
//		}
//			break;
//		default: {
//			if(!classe.equals("OctaveParenthesisExpression")){
//				System.out.println(classe);
//			}
//		}
//			break;
//		}
//		return codeElement;
//	}
////	private String createValueCondicionalFor(ASTNode node){
////		String value = "";
////		if(node instanceof OctaveSymbolCallExpression){
////			OctaveSymbolCallExpression call = (OctaveSymbolCallExpression)node;
////			value=value+call.getName()+"(";
////			for(int x = 0; x < call.getArgs().getChilds().size(); x++){
////				ASTNode arg = (ASTNode)call.getArgs().getChilds().get(x);
////				value = value + createValueCondicionalFor(arg)+", ";
////			}
////			value = value.substring(0, value.length()-2)+")";
////		}else{
////			value = value + node.toString();
////		}
////		return value;
////	}
//	
////	private void createElementSymbolCallExpression(ASTNode node, AbstractCodeElement element){
////		String value = "";
////		if(node instanceof OctaveSymbolCallExpression){
////			OctaveSymbolCallExpression call = (OctaveSymbolCallExpression)node;
////			ActionElement inter = new ActionElement("call",call.getName());
////			element.addCodeElement(inter);
////			for(int x = 0; x < call.getArgs().getChilds().size(); x++){
////				ASTNode arg = (ASTNode)call.getArgs().getChilds().get(x);
////				createElementSymbolCallExpression(arg,inter);
////			}
////		}else{
////			ActionElement inter = new ActionElement("parameter",node.toString());
////			element.addCodeElement(inter);
////		}
////	}
//}