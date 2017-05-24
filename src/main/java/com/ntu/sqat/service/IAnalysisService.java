package com.ntu.sqat.service;

import com.ntu.sqat.entity.JavaProjectTracker;

/**
 * This class contains the API call for other java project to use
 * @author Daryl Lau
 *
 */
public interface IAnalysisService {
	
	/**
	 * the zip file will be unzip and placed in the destinationPath and begin analysing the source code
	 * After analysing it will be parsed into JavaProjectTracker which contains all the information
	 * @param zipFilePath
	 * @param destinationPath
	 * @return
	 */
	public JavaProjectTracker analyseJavaSourceCode(String zipFilePath, String destinationPath);
	
	/**
	 * score will be calculated which will depend on the number of violation found matched against a 
	 * benchmark and severity level of violation.
	 * Per file there are two components, the class level and method level scoring 
	 * The final score will be based on the average score per file
	 * Non compilable code will have a score of 0
	 * @param javaProjectTracker
	 * @return
	 */
	public double calculateScore(JavaProjectTracker javaProjectTracker);
	
	/**
	 * a report will be generated based on the analysed result
	 * it can be retrieve from the destinationPath after generation
	 * @param javaProjectTracker
	 * @param destinationPath
	 */
	public void generateReport(JavaProjectTracker javaProjectTracker, String destinationPath);

}
