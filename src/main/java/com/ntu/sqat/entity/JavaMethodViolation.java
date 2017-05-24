package com.ntu.sqat.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the number of violation found per metrics
 * @author daryl
 *
 */
public class JavaMethodViolation {
	
	private int cyclomaticComplexityCount;
	private int nestedBlockDepthCount;
	private int effectiveCodeLinesMethodCount;
	private int numberOfParametersCount;
	private int returnStatementMethodCount;
	
	public JavaMethodViolation(){
		cyclomaticComplexityCount = 0;
		nestedBlockDepthCount = 0;
		effectiveCodeLinesMethodCount = 0;
		numberOfParametersCount = 0;
		returnStatementMethodCount = 0;
	}
	
	public int getCyclomaticComplexityCount() {
		return cyclomaticComplexityCount;
	}

	public void setCyclomaticComplexityCount(int cyclomaticComplexityCount) {
		this.cyclomaticComplexityCount = cyclomaticComplexityCount;
	}
	
	public void addCyclomaticComplexityCount() {
		this.cyclomaticComplexityCount++;
	}

	public int getNestedBlockDepthCount() {
		return nestedBlockDepthCount;
	}

	public void setNestedBlockDepthCount(int nestedBlockDepthCount) {
		this.nestedBlockDepthCount = nestedBlockDepthCount;
	}

	public void addNestedBlockDepthCount() {
		this.nestedBlockDepthCount++;
	}
	

	public int getEffectiveCodeLinesMethodCount() {
		return effectiveCodeLinesMethodCount;
	}

	public void setEffectiveCodeLinesMethodCount(int effectiveCodeLinesMethodCount) {
		this.effectiveCodeLinesMethodCount = effectiveCodeLinesMethodCount;
	}
	
	public void addEffectiveCodeLinesMethodCount() {
		this.effectiveCodeLinesMethodCount++;
	}

	public int getNumberOfParametersCount() {
		return numberOfParametersCount;
	}

	public void setNumberOfParametersCount(int numberOfParametersCount) {
		this.numberOfParametersCount = numberOfParametersCount;
	}

	public void addNumberOfParametersCount() {
		this.numberOfParametersCount++;
	}
	
	public int getReturnStatementMethodCount() {
		return returnStatementMethodCount;
	}

	public void setReturnStatementMethodCount(int returnStatementMethodCount) {
		this.returnStatementMethodCount = returnStatementMethodCount;
	}
	
	public void addReturnStatementMethodCount() {
		this.returnStatementMethodCount++;
	}
	
	public int noOfTypeOfViolation(){
		
		int count = 0;
		if (cyclomaticComplexityCount > 0) {
			count ++;
		}
		
		if (nestedBlockDepthCount > 0) {
			count ++;
		}
		
		if (effectiveCodeLinesMethodCount > 0) {
			count ++;
		}
		
		if (numberOfParametersCount > 0) {
			count ++;
		}
		
		if (returnStatementMethodCount > 0) {
			count ++;
		}
		
		return count;
	}

	public int totalNumberOfViolation(){
		int totalNumber = getCyclomaticComplexityCount() +getNestedBlockDepthCount() + getEffectiveCodeLinesMethodCount() + 
				getNumberOfParametersCount() + getReturnStatementMethodCount();
		
		return totalNumber;
		
	}
	
}
