package com.ntu.sqat.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.Position;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.ntu.sqat.entity.JavaClassTracker;
import com.ntu.sqat.entity.JavaFileTracker;
import com.ntu.sqat.entity.JavaMethodTracker;
import com.ntu.sqat.entity.JavaProjectTracker;
import com.ntu.sqat.metrics.BenchmarkConstants;
import com.ntu.sqat.metrics.MethodVisitor;
import com.ntu.sqat.metrics.ScoreCalculator;
import com.ntu.sqat.utility.DirExplorer;
import com.ntu.sqat.utility.PdfWriterUtil;
import com.ntu.sqat.utility.UnzipUtil;

/**
 * This class contains the implementation of the API call that is exposed to other api
 * @author Daryl Lau
 *
 */
public class AnalysisService implements IAnalysisService {
	
	public JavaProjectTracker analyseJavaSourceCode(String zipFilePath, String destinationPath){
		unzipSrcCode(zipFilePath, destinationPath);
		//put the list of class into the java file tracker
		int idx = zipFilePath.lastIndexOf('\\');
		String projectName=(zipFilePath.substring(idx +1));
		System.out.println("Project name is " + projectName);
		JavaProjectTracker javaProjectTracker = analyseFiles(destinationPath);
		javaProjectTracker.setProjectName(projectName);
		return javaProjectTracker;
	}
	
	private void unzipSrcCode(String zipFilePath, String destinationPath){
		UnzipUtil unzipUtil = new UnzipUtil();
		try {
			unzipUtil.unzip(zipFilePath, destinationPath);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public double calculateScore(JavaProjectTracker javaProjectTracker){
		
		List<Double> totalScoreList = new ArrayList<Double>();
		for(JavaFileTracker javaFileTracker : javaProjectTracker.getJavaFileTrackerList()) {
			
			if(!javaFileTracker.isCompilable()){
				return 0;
			}
			
			List<Double> classTotalScore = getClassTotalScore(javaFileTracker);
			
			double fileScore = averageScore(classTotalScore);
			totalScoreList.add(fileScore);
		}
		
		return averageScore(totalScoreList);
	}
	
	private List<Double> getClassTotalScore(JavaFileTracker javaFileTracker){
		List<Double> classTotalScore = new ArrayList<Double>();
		for(JavaClassTracker javaClassTracker : javaFileTracker.getJavaClassTrackerList()){
			
			double classAvgScore = calculateScoreForClassAttribute(javaClassTracker);
			
			if(javaClassTracker.getJavaMethodTrackerList().size() > 0){
				List<Double> scoreList = new ArrayList<Double>();
				for(JavaMethodTracker javaMethodTracker : javaClassTracker.getJavaMethodTrackerList()){
					double methodScore = calculateScoreForMethod(javaMethodTracker);
					System.out.println("method avg score is: " + methodScore);
					scoreList.add(methodScore);
				}
				double methodAvgScore = averageScore(scoreList);
				double classScore = (classAvgScore + methodAvgScore)/ 2;
				classTotalScore.add(classScore);
			} else {
				classTotalScore.add(classAvgScore);
			}
		}
		return classTotalScore;
	}
	
	public void generateReport(JavaProjectTracker javaProjectTracker, String destinationPath){
		PdfWriterUtil writerUtil = new PdfWriterUtil();
		writerUtil.GenerateAnalysisReport(javaProjectTracker,destinationPath);
	}
	
	private double calculateScoreForClassAttribute(JavaClassTracker javaClassTracker){
		List<Double> scoreList = new ArrayList<Double>();
		
		int noOfConstructor = javaClassTracker.getNoOfConstructor();
		double constructorScore = ScoreCalculator.calculateScore(noOfConstructor, BenchmarkConstants.NUMBER_OF_CONSTRUCTOR, 3);
		scoreList.add(constructorScore);
		
		int noOfLines = javaClassTracker.getNoOfLinesInClass(); //2
		double lineScore = ScoreCalculator.calculateScore(noOfLines, BenchmarkConstants.EFFECTIVE_CODE_LINES_CLASS, 2);
		scoreList.add(lineScore);
		
		int noOfPackagePrivateFields = javaClassTracker.getNoOfPackagePrivateFields();
		double packagePrivateFieldScore = ScoreCalculator.calculateScore(noOfPackagePrivateFields, BenchmarkConstants.PACKAGE_PRIVATE_FIELD_COUNT, 2);
		scoreList.add(packagePrivateFieldScore);
		
		int noOfPackegePrivateMethods = javaClassTracker.getNoOfPackagePrivateMethods();
		double packagePrivateMethodScore = ScoreCalculator.calculateScore(noOfPackegePrivateMethods, BenchmarkConstants.PACKAGE_PRIVATE_METHOD_COUNT, 2);
		scoreList.add(packagePrivateMethodScore);
		
		int noOfPrivateFields = javaClassTracker.getNoOfPrivateFields();
		double privateFieldScore = ScoreCalculator.calculateScore(noOfPrivateFields, BenchmarkConstants.PRIVATE_FIELD_COUNT, 2);
		scoreList.add(privateFieldScore);
		
		int noOfPrivateMethods = javaClassTracker.getNoOfPrivateMethods();
		double privateMethodScore = ScoreCalculator.calculateScore(noOfPrivateMethods, BenchmarkConstants.PRIVATE_METHOD_COUNT, 2);
		scoreList.add(privateMethodScore);
		
		int noOfProtectedFields = javaClassTracker.getNoOfProtectedFields();
		double protectedFieldCount = ScoreCalculator.calculateScore(noOfProtectedFields, BenchmarkConstants.PROTECTED_FIELD_COUNT, 2);
		scoreList.add(protectedFieldCount);
		
		int noOfProtectedMethods = javaClassTracker.getNoOfProtectedMethods();
		double protectedMethodCount = ScoreCalculator.calculateScore(noOfProtectedMethods, BenchmarkConstants.PROTECTED_METHOD_COUNT, 2);
		scoreList.add(protectedMethodCount);
		
		int noOfPublicFields = javaClassTracker.getNoOfPublicFields();
		double publicFieldScore = ScoreCalculator.calculateScore(noOfPublicFields, BenchmarkConstants.PUBLIC_FIELD_COUNT, 2);
		scoreList.add(publicFieldScore);
		
		int noOfPublicMethods = javaClassTracker.getNoOfPublicMethods();
		double publicMethodScore = ScoreCalculator.calculateScore(noOfPublicMethods, BenchmarkConstants.PUBLIC_METHOD_COUNT, 2);
		scoreList.add(publicMethodScore);
		
		int noOfStaticAttribute = javaClassTracker.getNoOfStaticAttribute();
		double staticAttributeScore = ScoreCalculator.calculateScore(noOfStaticAttribute, BenchmarkConstants.NUMBER_OF_STATIC_ATTRIBUTE, 3);
		scoreList.add(staticAttributeScore);
		
		return averageScore(scoreList);
	}
	
	private double calculateScoreForMethod(JavaMethodTracker javaMethodTracker){
		List<Double> scoreList = new ArrayList<Double>();
		
		int cyclomaticComplexity = javaMethodTracker.getCyclomaticComplexity();
		double cyclomaticScore = ScoreCalculator.calculateScore(cyclomaticComplexity, BenchmarkConstants.CYCLOMATIC_COMPLEXITY, 2);
		scoreList.add(cyclomaticScore);
		
		int nestedDepth = javaMethodTracker.getNestedDepth();
		double nestedDepthScore = ScoreCalculator.calculateScore(nestedDepth, BenchmarkConstants.NESTED_BLOCK_DEPTH, 2);
		scoreList.add(nestedDepthScore);
		
		int noOfLinesInMethod = javaMethodTracker.getNoOfLinesInMethod();
		double linesInMethodScore = ScoreCalculator.calculateScore(noOfLinesInMethod, BenchmarkConstants.EFFECTIVE_CODE_LINES_METHOD, 2);
		scoreList.add(linesInMethodScore);
		
		int noOfParameters = javaMethodTracker.getNoOfParameters();
		double parametersScore = ScoreCalculator.calculateScore(noOfParameters, BenchmarkConstants.NUMBER_OF_PARAMETERS, 2);
		scoreList.add(parametersScore);
		
		int noOfReturnStmt = javaMethodTracker.getNoOfReturnStmt();
		double returnStmtScore = ScoreCalculator.calculateScore(noOfReturnStmt, BenchmarkConstants.RETURN_STATEMENT_METHOD, 2);
		scoreList.add(returnStmtScore);
		
		return averageScore(scoreList);
	}
	
	private double averageScore(List<Double> scoreList){
		
		double value = 0;
		for(Double score : scoreList){
			value += score;
		}
		double avgScore = value / scoreList.size();
		
		return avgScore;
	}
	
	
	public JavaProjectTracker analyseFiles(String destinationPath){
		
		JavaProjectTracker javaProjectTracker = new JavaProjectTracker();
		System.out.println("dest path: " + destinationPath);
		File projectDir = new File(destinationPath);
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			String filePath = destinationPath + path;
			System.out.println(filePath);

			JavaFileTracker javaFileTracker = new JavaFileTracker();
			try {
				FileInputStream in = new FileInputStream(filePath);
				javaFileTracker.setJavaFileName(path);
				CompilationUnit cu = JavaParser.parse(in); 
				MethodVisitor methodVisitor = new MethodVisitor();
				methodVisitor.visit(cu, null);
				List<JavaClassTracker> classTrackerList = methodVisitor.getJavaClassTrackerList();
				
				for(JavaClassTracker classTracker : classTrackerList){
					javaFileTracker.addJavaClassTracker(classTracker);
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseProblemException e){
				System.out.println("Fatal Error : cannot be compiled");
				javaFileTracker.setCompilable(false);
			}
			
			javaProjectTracker.addJavaFileTracker(javaFileTracker);
	           
		}).explore(projectDir);
		
		return javaProjectTracker;
	}

}
