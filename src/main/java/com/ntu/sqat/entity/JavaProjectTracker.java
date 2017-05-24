package com.ntu.sqat.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all the information tracked on project level
 * @author daryl
 *
 */
public class JavaProjectTracker {
	
	private List<JavaFileTracker> javaFileTrackerList;
	private String projectName;
	private double score;
	
	public JavaProjectTracker(){
		javaFileTrackerList = new ArrayList<JavaFileTracker>();
		projectName = "";
		score = 0.0;
	}
	
	public List<JavaFileTracker> getJavaFileTrackerList() {
		return javaFileTrackerList;
	}
	public void setJavaFileTrackerList(List<JavaFileTracker> javaFileTrackerList) {
		this.javaFileTrackerList = javaFileTrackerList;
	}
	
	public void addJavaFileTracker(JavaFileTracker javaFileTracker){
		this.javaFileTrackerList.add(javaFileTracker);
	}
	
	public String getProjectName(){
		return projectName;
	}
	
	public void setProjectName(String projectName){
		this.projectName = projectName;
	}
	
	public double getScore(){
		return score;
	}
	
	public void setScore(double score){
		this.score = score;
	}
	
}
