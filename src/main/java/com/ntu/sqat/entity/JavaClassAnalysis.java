package com.ntu.sqat.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the number of violation found per metrics
 * @author daryl
 *
 */
public class JavaClassAnalysis {
	
	private String className;
	private List<String> classViolationImprovement;
	private List<JavaMethodAnalysis> javaMethodAnalysis;
	
	public JavaClassAnalysis(){
		className ="";
		classViolationImprovement = new ArrayList<String>();
		javaMethodAnalysis = new ArrayList<JavaMethodAnalysis>();
	}
	
	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}


	public List<String> getClassViolationImprovement() {
		return classViolationImprovement;
	}


	public void setClassViolationImprovement(List<String> classViolationImprovement) {
		this.classViolationImprovement = classViolationImprovement;
	}
	
	public void addClassViolationImprovement(String classViolationImprovement) {
		this.classViolationImprovement.add(classViolationImprovement);
	}

	public List<JavaMethodAnalysis> getJavaMethodAnalysis() {
		return javaMethodAnalysis;
	}


	public void setJavaMethodAnalysis(List<JavaMethodAnalysis> javaMethodAnalysis) {
		this.javaMethodAnalysis = javaMethodAnalysis;
	}
	
	public void addJavaMethodAnalysis(JavaMethodAnalysis javaMethodAnalysis) {
		this.javaMethodAnalysis.add(javaMethodAnalysis);
	}


	
	

}
