package com.ntu.sqat.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all the things tracked in a file level
 * @author daryl
 *
 */
public class JavaFileTracker {
	
	private List<JavaClassTracker> javaClassTrackerList;
	private String javaFileName;
	private double score;
	private boolean compilable;
	
	public JavaFileTracker(){
		javaClassTrackerList = new ArrayList<JavaClassTracker>();
		javaFileName = "";
		score = 0.0;
		compilable = true;
	}
	
	public String getJavaFileName(){
		return this.javaFileName;
	}
	
	public void setJavaFileName(String javaFileName){
		this.javaFileName = javaFileName;
	}
	
	public List<JavaClassTracker> getJavaClassTrackerList() {
		return javaClassTrackerList;
	}
	public void setJavaClassTrackerList(List<JavaClassTracker> javaClassTrackerList) {
		this.javaClassTrackerList = javaClassTrackerList;
	}
	
	public void addJavaClassTracker(JavaClassTracker javaClassTracker){
		this.javaClassTrackerList.add(javaClassTracker);
	}
	
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	
	public boolean isCompilable() {
		return compilable;
	}
	
	public void setCompilable(boolean compilable){
		this.compilable = compilable;
	}
	
}
