package com.ntu.sqat.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the number of violation found per metrics
 * @author daryl
 *
 */
public class JavaClassViolation {
	
	private int effectiveCodeLinesClassCount;
	private int privateFieldCount;
	private int privateMethodCount;
	private int protectedFieldCount;
	private int protectedMethodCount;
	private int publicFieldCount;
	private int publicMethodCount;
	private int noOfconstructorCount;
	private int numberOfStaticAttributeCount;
	private int packagePrivateFieldCount;
	private int packagePrivateMethodCount;
	
	public JavaClassViolation(){
		effectiveCodeLinesClassCount = 0;
		privateFieldCount = 0;
		privateMethodCount = 0;
		protectedFieldCount = 0;
		protectedMethodCount = 0;
		publicFieldCount = 0;
		publicMethodCount = 0;
		noOfconstructorCount = 0;
		numberOfStaticAttributeCount = 0;
		packagePrivateFieldCount = 0;
		packagePrivateMethodCount = 0;
	}
	

	public int getEffectiveCodeLinesClassCount() {
		return effectiveCodeLinesClassCount;
	}

	public void setEffectiveCodeLinesClassCount(int effectiveCodeLinesClassCount) {
		this.effectiveCodeLinesClassCount = effectiveCodeLinesClassCount;
	}

	public void addEffectiveCodeLinesClassCount() {
		this.effectiveCodeLinesClassCount ++;
	}
	
	public int getPrivateFieldCount() {
		return privateFieldCount;
	}

	public void setPrivateFieldCount(int privateFieldCount) {
		this.privateFieldCount = privateFieldCount;
	}

	public void addPrivateFieldCount() {
		this.privateFieldCount++;
	}
	
	public int getPrivateMethodCount() {
		return privateMethodCount;
	}

	public void setPrivateMethodCount(int privateMethodCount) {
		this.privateMethodCount = privateMethodCount;
	}
	
	public void addPrivateMethodCount() {
		this.privateMethodCount++;
	}

	public int getProtectedFieldCount() {
		return protectedFieldCount;
	}

	public void setProtectedFieldCount(int protectedFieldCount) {
		this.protectedFieldCount = protectedFieldCount;
	}
	
	public void addProtectedFieldCount() {
		this.protectedFieldCount++;
	}

	public int getProtectedMethodCount() {
		return protectedMethodCount;
	}

	public void setProtectedMethodCount(int protectedMethodCount) {
		this.protectedMethodCount = protectedMethodCount;
	}
	
	public void addProtectedMethodCount() {
		this.protectedMethodCount++;
	}

	public int getPublicFieldCount() {
		return publicFieldCount;
	}

	public void setPublicFieldCount(int publicFieldCount) {
		this.publicFieldCount = publicFieldCount;
	}
	
	public void addPublicFieldCount() {
		this.publicFieldCount++;
	}

	public int getPublicMethodCount() {
		return publicMethodCount;
	}

	public void setPublicMethodCount(int publicMethodCount) {
		this.publicMethodCount = publicMethodCount;
	}
	
	public void addPublicMethodCount() {
		this.publicMethodCount++;
	}

	public int getNoOfconstructorCount() {
		return noOfconstructorCount;
	}

	public void setNoOfconstructorCount(int noOfconstructorCount) {
		this.noOfconstructorCount = noOfconstructorCount;
	}
	
	public void addNoOfconstructorCount() {
		this.noOfconstructorCount++;
	}

	public int getNumberOfStaticAttributeCount() {
		return numberOfStaticAttributeCount;
	}

	public void setNumberOfStaticAttributeCount(int numberOfStaticAttributeCount) {
		this.numberOfStaticAttributeCount = numberOfStaticAttributeCount;
	}
	
	public void addNumberOfStaticAttributeCount() {
		this.numberOfStaticAttributeCount++;
	}

	public int getPackagePrivateFieldCount() {
		return packagePrivateFieldCount;
	}

	public void setPackagePrivateFieldCount(int packagePrivateFieldCount) {
		this.packagePrivateFieldCount = packagePrivateFieldCount;
	}
	
	public void addPackagePrivateFieldCount() {
		this.packagePrivateFieldCount++;
	}

	public int getPackagePrivateMethodCount() {
		return packagePrivateMethodCount;
	}

	public void setPackagePrivateMethodCount(int packagePrivateMethodCount) {
		this.packagePrivateMethodCount = packagePrivateMethodCount;
	}
	
	public void addPackagePrivateMethodCount() {
		this.packagePrivateMethodCount++;
	}
	
	public int noOfTypeOfViolation(){
		int count = 0;
		if (effectiveCodeLinesClassCount > 0) {
			count ++;
		}
		
		if (privateFieldCount > 0) {
			count ++;
		}
		
		if (privateMethodCount > 0) {
			count ++;
		}
		
		if (protectedFieldCount > 0) {
			count ++;
		}
		
		if (protectedMethodCount > 0) {
			count ++;
		}
		
		if (publicFieldCount > 0) {
			count ++;
		}
		
		if (publicMethodCount > 0) {
			count ++;
		}
		
		if (noOfconstructorCount > 0) {
			count ++;
		}
		
		if (numberOfStaticAttributeCount > 0) {
			count ++;
		}
		
		if (packagePrivateFieldCount > 0) {
			count ++;
		}
		
		if (packagePrivateMethodCount > 0) {
			count ++;
		}
		
		return count;
	}
	
	public int totalNumberOfViolation(){
		int totalNumber = getEffectiveCodeLinesClassCount() + getPrivateFieldCount() + getPrivateMethodCount() + 
				getProtectedFieldCount() + getProtectedMethodCount() + getPublicFieldCount() + 
				getPublicMethodCount() + getNoOfconstructorCount() + getNumberOfStaticAttributeCount() + 
				getPackagePrivateFieldCount() + getPackagePrivateMethodCount();
		
		return totalNumber;
	}
	
}
