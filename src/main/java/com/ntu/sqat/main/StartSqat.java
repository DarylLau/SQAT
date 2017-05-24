package com.ntu.sqat.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.github.javaparser.JavaParser;
import com.github.javaparser.Position;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.ntu.sqat.entity.JavaClassTracker;
import com.ntu.sqat.entity.JavaMethodTracker;
import com.ntu.sqat.entity.JavaProjectTracker;
import com.ntu.sqat.metrics.MethodVisitor;
import com.ntu.sqat.service.AnalysisService;
import com.ntu.sqat.service.IAnalysisService;
import com.ntu.sqat.utility.DirExplorer;
import com.ntu.sqat.utility.PdfWriterUtil;
import com.ntu.sqat.utility.UnzipUtil;

/**
 * The main of the program
 * @author Daryl Lau
 *
 */
public class StartSqat {

	private static Logger logger = Logger.getLogger(StartSqat.class);
	
	public static void main(String[] args) throws FileNotFoundException {
		String zipFilePath = "C:\\Users\\daryl\\workspace\\SQATCore19052017.zip";
		//String zipFilePath = "C:\\Users\\daryl\\workspace\\SQATCoreBCD.zip";
		String destinationPath = "C:\\Users\\daryl\\Desktop\\version1";
		
		if(args.length > 0){
			zipFilePath = args[0];
			destinationPath = args[1];
		}
		
		IAnalysisService analysisService = new AnalysisService();
		JavaProjectTracker javaProjectTracker = analysisService.analyseJavaSourceCode(zipFilePath, destinationPath);
		double finalScore = analysisService.calculateScore(javaProjectTracker);
		javaProjectTracker.setScore(finalScore);
		System.out.println("The final Score is : " + finalScore);
		analysisService.generateReport(javaProjectTracker, destinationPath);
		
		logger.info("COMPLETED");
	}
	
	
	
}
