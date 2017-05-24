package com.ntu.sqat.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the number of violation found per metrics
 * @author daryl
 *
 */
public class JavaReportDto {
	
	private int totalViolationCount;
	private int totalDiffTypeOfViolation;
	private int totalNumberOfFiles;
	private int totalNumberOfClass;
	private int totalNumberOfMethod;
	private double score;
	private String projectName;
	private JavaClassViolation javaClassViolation;
	private JavaMethodViolation javaMethodViolation;
	private List<String> nonCompilableFiles;
	private List<JavaClassAnalysis> javaClassAnalysis;
	
	public JavaReportDto(){
		totalViolationCount = 0;
		totalDiffTypeOfViolation = 0;
		totalNumberOfFiles = 0;
		totalNumberOfClass = 0;
		totalNumberOfMethod = 0;
		score = 0;
		projectName ="";
		javaClassViolation = new JavaClassViolation();
		javaMethodViolation = new JavaMethodViolation();
		nonCompilableFiles = new ArrayList<String>();
		javaClassAnalysis = new ArrayList<JavaClassAnalysis>();
	}
	
	public int getTotalViolationCount() {
		return totalViolationCount;
	}

	public void setTotalViolationCount(int totalViolationCount) {
		this.totalViolationCount = totalViolationCount;
	}
	
	public void addTotalViolationCount(){
		this.totalViolationCount ++;
	}

	public int getTotalDiffTypeOfViolation() {
		return totalDiffTypeOfViolation;
	}

	public void setTotalDiffTypeOfViolation(int totalDiffTypeOfViolation) {
		this.totalDiffTypeOfViolation = totalDiffTypeOfViolation;
	}

	public int getTotalNumberOfFiles() {
		return totalNumberOfFiles;
	}

	public void setTotalNumberOfFiles(int totalNumberOfFiles) {
		this.totalNumberOfFiles = totalNumberOfFiles;
	}

	public int getTotalNumberOfClass() {
		return totalNumberOfClass;
	}

	public void setTotalNumberOfClass(int totalNumberOfClass) {
		this.totalNumberOfClass = totalNumberOfClass;
	}
	
	public void addTotalNumberOfClass() {
		this.totalNumberOfClass++;
	}

	public int getTotalNumberOfMethod() {
		return totalNumberOfMethod;
	}

	public void setTotalNumberOfMethod(int totalNumberOfMethod) {
		this.totalNumberOfMethod = totalNumberOfMethod;
	}
	
	public void addTotalNumberOfMethod() {
		this.totalNumberOfMethod++;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public JavaClassViolation getJavaClassViolation() {
		return javaClassViolation;
	}

	public void setJavaClassViolation(JavaClassViolation javaClassViolation) {
		this.javaClassViolation = javaClassViolation;
	}

	public JavaMethodViolation getJavaMethodViolation() {
		return javaMethodViolation;
	}

	public void setJavaMethodViolation(JavaMethodViolation javaMethodViolation) {
		this.javaMethodViolation = javaMethodViolation;
	}

	public void caculateDiffTypeOfViolation(){
		int noOfClassViolation = javaClassViolation.noOfTypeOfViolation();
		int noOfMethodViolation = javaMethodViolation.noOfTypeOfViolation();
		
		int totalTypes = noOfClassViolation + noOfMethodViolation;
		this.totalDiffTypeOfViolation = totalTypes;
	}
	
	public void calculateTotalViolation(){
		int noOfViolationInClass = javaClassViolation.totalNumberOfViolation();
		int noOfViolationInMethod = javaMethodViolation.totalNumberOfViolation();
		
		int total = noOfViolationInClass + noOfViolationInMethod;
		
		this.totalViolationCount = total;
	}
	
	public List<JavaClassAnalysis> getJavaClassAnalysis(){
		return this.javaClassAnalysis;
	}
	
	public void addJavaClassAnalysis(JavaClassAnalysis javaClassAnalysis){
		this.javaClassAnalysis.add(javaClassAnalysis);
	}
	
	public JavaClassAnalysis getJavaClassAnalysisByClassName(String className){
		for(JavaClassAnalysis javaClass : javaClassAnalysis){
			if(javaClass.getClassName().equals(className)){
				return javaClass;
			}
		}
		
		return null;
	}
	
	public List<String> getNonCompilableFiles(){
		return this.nonCompilableFiles;
	}
	
	public void SetNonCompilableFiles(List<String> nonCompilableFiles){
		this.nonCompilableFiles = nonCompilableFiles;
	}
	
	public void AddNonCompilableFiles(String nonCompilableFiles){
		this.nonCompilableFiles.add(nonCompilableFiles);
	}
}
