package com.ntu.sqat.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all the metrics tracked based on class level
 * @author Daryl Lau
 * 
 */
public class JavaClassTracker {

	private String className;
	private int noOfMethods;
	private int noOfLinesInClass;
	private List<JavaMethodTracker> javaMethodTrackerList;
	private int noOfConstructor;	//less than 6
	private int noOfPrivateFields; //less than 16
	private int noOfPrivateMethods; //less than 16
	private int noOfProtectedFields; //less than 11
	private int noOfProtectedMethods; //less than 11
	private int noOfPublicFields; //less than 4
	private int noOfPublicMethods; //less than 36
	private int noOfPackagePrivateFields; //less than 1
	private int noOfPackagePrivateMethods; //less than 4
	private int noOfStaticAttribute;	//less than 9
	
	public JavaClassTracker(){
		className = "";
		noOfMethods = 0;
		noOfLinesInClass = 0;
		javaMethodTrackerList = new ArrayList<JavaMethodTracker>();
		noOfConstructor = 0;
		noOfPrivateFields = 0;
		noOfPrivateMethods = 0;
		noOfProtectedFields = 0;
		noOfProtectedMethods = 0;
		noOfPublicFields = 0;
		noOfPublicMethods = 0;
		noOfPackagePrivateFields = 0;
		noOfPackagePrivateMethods = 0;
		noOfStaticAttribute = 0;
	}
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getNoOfMethods() {
		return noOfMethods;
	}

	public void setNoOfMethods(int noOfMethods) {
		this.noOfMethods = noOfMethods;
	}
	public List<JavaMethodTracker> getJavaMethodTrackerList() {
		return javaMethodTrackerList;
	}
	public void setJavaMethodTrackerList(List<JavaMethodTracker> javaMethodTrackerList) {
		this.javaMethodTrackerList = javaMethodTrackerList;
	}
	public void addJavaMethodTracker(JavaMethodTracker javaMethodTracker){
		javaMethodTrackerList.add(javaMethodTracker);
	}
	public int getNoOfLinesInClass() {
		return noOfLinesInClass;
	}
	public void setNoOfLinesInClass(int noOfLinesInClass) {
		this.noOfLinesInClass = noOfLinesInClass;
	}

	public int getNoOfConstructor() {
		return noOfConstructor;
	}

	public void setNoOfConstructor(int noOfConstructor) {
		this.noOfConstructor = noOfConstructor;
	}

	public int getNoOfPrivateFields() {
		return noOfPrivateFields;
	}

	public void setNoOfPrivateFields(int noOfPrivateFields) {
		this.noOfPrivateFields = noOfPrivateFields;
	}

	public int getNoOfPrivateMethods() {
		return noOfPrivateMethods;
	}

	public void setNoOfPrivateMethods(int noOfPrivateMethods) {
		this.noOfPrivateMethods = noOfPrivateMethods;
	}

	public int getNoOfProtectedFields() {
		return noOfProtectedFields;
	}

	public void setNoOfProtectedFields(int noOfProtectedFields) {
		this.noOfProtectedFields = noOfProtectedFields;
	}

	public int getNoOfProtectedMethods() {
		return noOfProtectedMethods;
	}

	public void setNoOfProtectedMethods(int noOfProtectedMethods) {
		this.noOfProtectedMethods = noOfProtectedMethods;
	}

	public int getNoOfPublicFields() {
		return noOfPublicFields;
	}

	public void setNoOfPublicFields(int noOfPublicFields) {
		this.noOfPublicFields = noOfPublicFields;
	}

	public int getNoOfPublicMethods() {
		return noOfPublicMethods;
	}

	public void setNoOfPublicMethods(int noOfPublicMethods) {
		this.noOfPublicMethods = noOfPublicMethods;
	}

	public int getNoOfPackagePrivateFields() {
		return noOfPackagePrivateFields;
	}

	public void setNoOfPackagePrivateFields(int noOfPackagePrivateFields) {
		this.noOfPackagePrivateFields = noOfPackagePrivateFields;
	}

	public int getNoOfPackagePrivateMethods() {
		return noOfPackagePrivateMethods;
	}

	public void setNoOfPackagePrivateMethods(int noOfPackagePrivateMethods) {
		this.noOfPackagePrivateMethods = noOfPackagePrivateMethods;
	}

	public int getNoOfStaticAttribute() {
		return noOfStaticAttribute;
	}

	public void setNoOfStaticAttribute(int noOfStaticAttribute) {
		this.noOfStaticAttribute = noOfStaticAttribute;
	}
	
}
