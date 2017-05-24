package com.ntu.sqat.metrics;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BinaryExpr.Operator;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.ForeachStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.ntu.sqat.constant.SQATConstants;
import com.ntu.sqat.entity.JavaClassTracker;
import com.ntu.sqat.entity.JavaMethodTracker;

/**
 * This class tracks all the metrics that is included in this project
 * It makes use of JavaParser which break the class down into its AST
 * Information is extracted from the AST for analysis
 * @author Daryl Lau
 *
 */
public class MethodVisitor extends VoidVisitorAdapter<Void> {
    
	private Logger logger = Logger.getLogger(this.getClass());
	
	private List<JavaClassTracker> javaClassTrackerList;
	private JavaClassTracker javaClassTracker;
	
	public MethodVisitor()
	{
		javaClassTrackerList = new ArrayList<JavaClassTracker>();
		javaClassTracker = new JavaClassTracker();
	}
	
	@Override
	public void visit(ClassOrInterfaceDeclaration n, Void arg){
		javaClassTracker = new JavaClassTracker();
		javaClassTracker.setClassName(n.getNameAsString());
		int numberOfMethods = 0;
		int numberOfLineInClass = 0;
		int numberOfConstructor = 0;
		int numberOfPrivateFields = 0;
		int numberOfPrivateMethods = 0;
		int numberOfProtectedFields = 0;
		int numberOfProtectedMethods = 0;
		int numberOfPublicFields = 0;
		int numberOfPublicMethods = 0;
		int numberOfPackagePrivateFields = 0;
		int numberOfPackagePrivateMethods = 0;
		int numberOfStaticAttribute = 0;
		for(Node node : n.getChildNodes()){
			if(node instanceof FieldDeclaration){
				//System.out.println("Field Declaration " + node);
				int fieldType = getFieldType((FieldDeclaration)node);
				if(fieldType == 1){
					numberOfPrivateFields ++;
				} else if (fieldType == 2){
					numberOfProtectedFields ++;
				} else if (fieldType == 3){
					numberOfStaticAttribute ++;
				} else if (fieldType == 4){
					numberOfStaticAttribute ++;
				} else if (fieldType == 5){
					numberOfPackagePrivateFields++;
				}
				numberOfLineInClass++;
			}
			
			if(node instanceof MethodDeclaration){
				//System.out.println("Method Declaration " + node);
				EnumSet<Modifier> modifierSet = ((MethodDeclaration) node).getModifiers();
				if(modifierSet.size() > 0){
					for(Modifier modifier : ((MethodDeclaration) node).getModifiers()){
						if(modifier.asString().equalsIgnoreCase(SQATConstants.PRIVATE_STRING)){
							numberOfPrivateMethods ++;
						} else if (modifier.asString().equalsIgnoreCase(SQATConstants.PROTECTED_STRING)){
							numberOfProtectedMethods ++;
						} else if (modifier.asString().equalsIgnoreCase(SQATConstants.PUBLIC_STRING)) {
							numberOfPublicMethods ++;
						} 
					}
				} else {
					numberOfPackagePrivateMethods++;
				}
				numberOfLineInClass++;
				numberOfMethods ++;
				
				JavaMethodTracker javaMethodTracker = visitMethod((MethodDeclaration)node);
				numberOfLineInClass += javaMethodTracker.getNoOfLinesInMethod();
				javaClassTracker.addJavaMethodTracker(javaMethodTracker);
			}
			
			if(node instanceof ConstructorDeclaration){
				numberOfConstructor++;
			}
		}
		javaClassTracker.setNoOfMethods(numberOfMethods);
		javaClassTracker.setNoOfConstructor(numberOfConstructor);
		javaClassTracker.setNoOfPackagePrivateFields(numberOfPackagePrivateFields);
		javaClassTracker.setNoOfPackagePrivateMethods(numberOfPackagePrivateMethods);
		javaClassTracker.setNoOfPrivateFields(numberOfPrivateFields);
		javaClassTracker.setNoOfPrivateMethods(numberOfPrivateMethods);
		javaClassTracker.setNoOfProtectedFields(numberOfProtectedFields);
		javaClassTracker.setNoOfProtectedMethods(numberOfProtectedMethods);
		javaClassTracker.setNoOfPublicFields(numberOfPublicFields);
		javaClassTracker.setNoOfPublicMethods(numberOfPublicMethods);
		javaClassTracker.setNoOfStaticAttribute(numberOfStaticAttribute);
		
		super.visit(n, arg);
		javaClassTracker.setNoOfLinesInClass(numberOfLineInClass);
		javaClassTrackerList.add(javaClassTracker);
	}
	
	private int getFieldType(FieldDeclaration node){
		EnumSet<Modifier> modifierSet = node.getModifiers();
		int fieldType = -1;
		if(modifierSet.size() > 0){
			boolean containStatic = false;
			for(Modifier modifier : node.getModifiers()){
				if(modifier.asString().equalsIgnoreCase(SQATConstants.STATIC_STRING)){
					containStatic = true;
				}
				
				if(modifier.asString().equalsIgnoreCase(SQATConstants.PRIVATE_STRING)){
					fieldType = 1;
				} else if (modifier.asString().equalsIgnoreCase(SQATConstants.PROTECTED_STRING)){
					fieldType = 2;
				} else if (modifier.asString().equalsIgnoreCase(SQATConstants.PUBLIC_STRING)) {
					fieldType = 3;
				} 
			}
			if(containStatic){
				fieldType = 4;
			}
		} else {
			System.out.println("Empty modifier");
			fieldType = 5;
		}
		return fieldType;
	}
	
	public JavaMethodTracker visitMethod(MethodDeclaration n){
		//System.out.print("From [" + n.getBegin() + "] to [" + n.getEnd() + "] is method: ");
		//System.out.println(n.getNameAsString());
		logger.info(n.getNameAsString());
		//System.out.println("new class " + n.getNameAsString());
		
		JavaMethodTracker javaMethodTracker = new JavaMethodTracker();
		
		String methodName = n.getNameAsString();
		javaMethodTracker.setMethodName(methodName);
		int parameterCounter = 0;
		int noOfLines =0;
		for(Node node : n.getChildNodes()){
			if(node instanceof Parameter){
				parameterCounter ++;
			}
			
			if(node instanceof Statement){
				noOfLines = noOfLinesInMethod(node);
			}
		}
		int depthCounter = getTreeNestedDepth(n);
		javaMethodTracker.setNestedDepth(depthCounter);
		
		int cyclomaticComplexity = findingCyclomaticComplexity(n);
		javaMethodTracker.setCyclomaticComplexity(cyclomaticComplexity);
		int noOfReturnStmt = noOfReturnStatementInMethod(n);
		javaMethodTracker.setNoOfReturnStmt(noOfReturnStmt);
		javaMethodTracker.setNoOfLinesInMethod(noOfLines);
		javaMethodTracker.setNoOfParameters(parameterCounter);
		
		return javaMethodTracker;
	}
	
	private int getTreeNestedDepth(Node node){
		
		String[] methodString = node.toString().split("\n");
        int counter = 0;
        int nestedDepth = 0;
        boolean cmtFlag = false;
        for(String line : methodString){
        	if(line.contains("/*")){
        		cmtFlag = true;
        	}
        	
        	if(line.contains("*/")){
        		cmtFlag = false;
        	}
        	
        	if(!cmtFlag){
        		
        		//System.out.println(line);
        		if(line.contains("{")){
        			counter ++;
        			//System.out.println("ADD " + counter);
        		}
        		
        		if(nestedDepth < counter){
        			nestedDepth = counter;
        		}
        		
        		if(line.contains("}")){
        			counter --;
        			//System.out.println("MINUS" + counter);
        		}
        	}        	
        }
        return nestedDepth;
	}
		
	
	/**
	 * find the cyclomatic complexity of the function
	 * @param n
	 * @return cyclomaticComplexityValue
	 */
	public int findingCyclomaticComplexity(Node n){
		int counter = 0;
		
		if(n instanceof MethodDeclaration){
			counter ++;
		}
		
		for(Node node : n.getChildNodes()){
			
			//logger.info("node class "+ node.getClass() + " " + node);
			//Selection : if, else, case, default
			if(node instanceof IfStmt){
				//System.out.println("Added 1 to if " + node);
				counter ++;
				
			}
			
			if(node instanceof SwitchStmt){
				counter += ((SwitchStmt) node).getEntries().size();
			}
			
			//Returns Each return that isn't the last statement of a method
//			if(node instanceof ReturnStmt){
//				//System.out.println("Added 1 to returns" + node);
//				counter ++;
//			}
			
			//Loops :for, while, do-while, break, and continue
			//do stmt is taken away, cause while will be counted in the do-while loop
			if(node instanceof ForeachStmt || node instanceof ForStmt ||
				 node instanceof WhileStmt){
				//System.out.println("Added 1 to loops" + node);
				counter ++;
			}
			
			//Operators :&&, ||
			if(node instanceof BinaryExpr){
				Operator operator = ((BinaryExpr) node).getOperator();
				if(operator.compareTo(Operator.AND) == 0 || operator.compareTo(Operator.OR) == 0){
					//System.out.println("Added 1 to BinaryExpr" + node);
					counter ++;
				}
			}
			
			//Operators ?, and :
			if(node instanceof ConditionalExpr){
				counter ++;
			}
			
			//Exceptions : catch, finally, throw, or throws clause
			if(node instanceof TryStmt || node instanceof CatchClause){
				//System.out.println("Added 1 to operators" + node);
				counter ++;
			}
			
			counter += findingCyclomaticComplexity(node);
			
		}
		return counter;
	}
	
	public int noOfReturnStatementInMethod(Node n){
		int counter = 0;
		
		for(Node node : n.getChildNodes()){
			if(node instanceof ReturnStmt){
				counter ++;
			}
			counter += noOfReturnStatementInMethod(node);
		}
		return counter;
	}
	
	/**
	 * find the number of effective java line number in a method ECLM
	 * ECLM = total lines - comment lines - blank lines - lines containing a lone '{' . '}', '(' or ')'
	 * the line number exclude comments, blank lines and lines with "}" or "{"
	 * @param node
	 * @return
	 */
	public int noOfLinesInMethod(Node node){
		String[] methodString = node.toString().split("\n");
        int counter = 0;
        boolean cmtFlag = false;
        for(String line : methodString){
        	if(line.contains("/*")){
        		cmtFlag = true;
        	}
        	
        	if(line.contains("*/")){
        		cmtFlag = false;
        	}
        	
        	if(!cmtFlag){
        		int length = line.length();
        		char c;
        		if(length > 1){
        			c = line.charAt(length-2);
        		} else {
        			c = line.charAt(length-1);
        		}
        		if(!line.contains("//") && !line.startsWith("{") && c != '}' && !line.contains("*/")){
        			//System.out.println(line);
        			counter ++;
        		}
        	}
        	
        }
        return counter;
	}
	
	public JavaClassTracker getJavaClassTracker(){
		return javaClassTracker;
	}
	
	public List<JavaClassTracker> getJavaClassTrackerList(){
		return javaClassTrackerList;
	}
	
}