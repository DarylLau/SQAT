package com.ntu.sqat.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.Position;

/**
 * This class contains all the metrics tracked on a method level
 * @author Daryl Lau
 *
 */
public class JavaMethodTracker {
	//name of method
	private String methodName;
	private int noOfLinesInMethod;
	private int nestedDepth;
	private int noOfParameters;
	private int cyclomaticComplexity;
	private int noOfReturnStmt;
	
	public JavaMethodTracker(){
		methodName = "";
		noOfLinesInMethod = 0;
		nestedDepth = 0;
		noOfParameters = 0;
		cyclomaticComplexity = 0;
		noOfReturnStmt = 0;
	}
	
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public int getNoOfLinesInMethod() {
		return noOfLinesInMethod;
	}
	public void setNoOfLinesInMethod(int noOfLinesInMethod) {
		this.noOfLinesInMethod = noOfLinesInMethod;
	}
	public int getNestedDepth() {
		return nestedDepth;
	}
	public void setNestedDepth(int nestedDepth) {
		this.nestedDepth = nestedDepth;
	}
	public int getNoOfParameters() {
		return noOfParameters;
	}
	public void setNoOfParameters(int noOfParameters) {
		this.noOfParameters = noOfParameters;
	}
	public int getCyclomaticComplexity() {
		return cyclomaticComplexity;
	}
	public void setCyclomaticComplexity(int cyclomaticComplexity) {
		this.cyclomaticComplexity = cyclomaticComplexity;
	}
	public int getNoOfReturnStmt() {
		return noOfReturnStmt;
	}
	public void setNoOfReturnStmt(int noOfReturnStmt) {
		this.noOfReturnStmt = noOfReturnStmt;
	}
}
