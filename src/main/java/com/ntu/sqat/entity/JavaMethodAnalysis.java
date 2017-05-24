package com.ntu.sqat.entity;

import java.util.ArrayList;
import java.util.List;

public class JavaMethodAnalysis {

	private String methodName;
	private List<String> methodViolationImprovement;
	
	
	public JavaMethodAnalysis(){
		methodName = "";
		methodViolationImprovement = new ArrayList<String>();
	}


	public String getMethodName() {
		return methodName;
	}


	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}


	public List<String> getMethodViolationImprovement() {
		return methodViolationImprovement;
	}


	public void setMethodViolationImprovement(List<String> methodViolationImprovement) {
		this.methodViolationImprovement = methodViolationImprovement;
	}
	
	public void addMethodViolationImprovement(String methodViolationImprovement) {
		this.methodViolationImprovement.add(methodViolationImprovement);
	}
	
}
