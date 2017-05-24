package com.ntu.sqat.utility;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.ntu.sqat.constant.ClassErrorCode;
import com.ntu.sqat.constant.MethodErrorCode;
import com.ntu.sqat.constant.SQATConstants;
import com.ntu.sqat.entity.JavaClassAnalysis;
import com.ntu.sqat.entity.JavaClassTracker;
import com.ntu.sqat.entity.JavaClassViolation;
import com.ntu.sqat.entity.JavaFileTracker;
import com.ntu.sqat.entity.JavaMethodAnalysis;
import com.ntu.sqat.entity.JavaMethodTracker;
import com.ntu.sqat.entity.JavaMethodViolation;
import com.ntu.sqat.entity.JavaProjectTracker;
import com.ntu.sqat.entity.JavaReportDto;
import com.ntu.sqat.metrics.BenchmarkConstants;

public class PdfWriterUtil {

	private static Font mainTitle = new Font (Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font textBold = new Font (Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private static Font textNormal = new Font (Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
	private static Font subHeader = new Font (Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static DecimalFormat df2 = new DecimalFormat(".##");
	
	public void GenerateAnalysisReport(JavaProjectTracker javaProjectTracker, String destinationPath){
		Document document = new Document();
		try
		{
			String outputFileName = destinationPath + "\\SampleReport.pdf";
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputFileName));
			document.open();
			//document.add(new Paragraph("A Hello World PDF document.", mainTitle));
			JavaReportDto javaReportDto = convertJavaProjectTrackerToDto(javaProjectTracker);
			SummaryPage(document, javaReportDto);
			addInDepthDetails(document, javaReportDto);
			document.close();
			writer.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	private void SummaryPage(Document document, JavaReportDto javaReportDto) throws DocumentException{
		Paragraph heading = new Paragraph();
		addEmptyLine(heading, 1);
		Paragraph titleParagraph = new Paragraph();
		titleParagraph.add(new Paragraph("SQAT Quality Analysis Report", mainTitle));
		titleParagraph.setAlignment(Element.ALIGN_CENTER);
		heading.add(titleParagraph);

		addEmptyLine(heading, 1);
		heading.add(new Paragraph("Report Id: " + generateJobId(), textBold));
		heading.add(new Paragraph("Time of Report generated: " + new Date(), textBold));
		addEmptyLine(heading, 1);
		
		heading.add(new Paragraph("Summary", subHeader));
		addEmptyLine(heading, 1);
		heading.add(new Paragraph("Project Name : " + javaReportDto.getProjectName(), textNormal));
		heading.add(new Paragraph("Score             : " + df2.format(javaReportDto.getScore()) ,textNormal));
		heading.add(new Paragraph("Grade             : " + calculateGrade(javaReportDto.getScore()) , textNormal));
		addEmptyLine(heading ,1);
		
		heading.add(new Paragraph("Statistic Overview", subHeader));
		addEmptyLine(heading, 1);
		heading.add(new Paragraph("Total number of Java files analysed : " + javaReportDto.getTotalNumberOfFiles(), textNormal));
		heading.add(new Paragraph("Total number of classes found : " + javaReportDto.getTotalNumberOfClass(), textNormal));
		heading.add(new Paragraph("Total number of methods found : " + javaReportDto.getTotalNumberOfMethod() , textNormal));
		
		heading.add(new Paragraph(javaReportDto.getTotalDiffTypeOfViolation() + " Out of 16 metrics violations found ", textNormal));
		heading.add(new Paragraph(" "));
		heading.add(new Paragraph("Total violations found : " + javaReportDto.getTotalViolationCount(), textNormal));
		addClassViolationCount(heading, javaReportDto);
		addMethodViolationCount(heading, javaReportDto);
		
		document.add(heading);
		document.newPage();
	}
	
	private void addMethodViolationCount(Paragraph heading, JavaReportDto javaReportDto){
		JavaMethodViolation javaMethodViolation = javaReportDto.getJavaMethodViolation();
		if(javaMethodViolation.getCyclomaticComplexityCount() > 0){
			String msg = "[ " +  javaMethodViolation.getCyclomaticComplexityCount() + " ] not following the limit for cyclomatic complexity value in a java method";
			heading.add(new Paragraph(msg, textNormal));
		}
		
		if(javaMethodViolation.getEffectiveCodeLinesMethodCount() > 0){
			String msg = "[ " +  javaMethodViolation.getEffectiveCodeLinesMethodCount() + " ] not following the limit for number of lines in a java method";
			heading.add(new Paragraph(msg, textNormal));
		}
		
		if(javaMethodViolation.getNestedBlockDepthCount() > 0){
			String msg = "[ " +  javaMethodViolation.getNestedBlockDepthCount() + " ] not following the limit for nested block depth in a java method";
			heading.add(new Paragraph(msg, textNormal));
		}
		
		if(javaMethodViolation.getNumberOfParametersCount() > 0){
			String msg = "[ " +  javaMethodViolation.getNumberOfParametersCount() + " ] not following the limit for number of parameters in a java method";
			heading.add(new Paragraph(msg, textNormal));
		}
		
		if(javaMethodViolation.getReturnStatementMethodCount() > 0){
			String msg = "[ " +  javaMethodViolation.getReturnStatementMethodCount() + " ] not following the limit for number of return statement in a java method";
			heading.add(new Paragraph(msg, textNormal));
		}
	}
	
	private void addClassViolationCount(Paragraph heading, JavaReportDto javaReportDto){
		JavaClassViolation javaClassViolation = javaReportDto.getJavaClassViolation();
		if(javaClassViolation.getEffectiveCodeLinesClassCount() > 0){
			String msg = "[ " +  javaClassViolation.getEffectiveCodeLinesClassCount() + " ] not following the limit for number of lines in a java class";
			heading.add(new Paragraph(msg, textNormal));
		}
		
		if(javaClassViolation.getNoOfconstructorCount() > 0){
			String msg = "[ " +  javaClassViolation.getNoOfconstructorCount() + " ] not following the limit for number of constructor in a java class";
			heading.add(new Paragraph(msg, textNormal));
		}
		
		if(javaClassViolation.getNumberOfStaticAttributeCount() > 0){
			String msg = "[ " +  javaClassViolation.getNumberOfStaticAttributeCount() + " ] not following the limit for number of static attribute in a java class";
			heading.add(new Paragraph(msg, textNormal));
		}
		
		if(javaClassViolation.getPackagePrivateFieldCount() > 0){
			String msg = "[ " +  javaClassViolation.getPackagePrivateFieldCount() + " ] not following the limit for number of package private fields in a java class";
			heading.add(new Paragraph(msg, textNormal));
		}
		
		if(javaClassViolation.getPackagePrivateMethodCount() > 0){
			String msg = "[ " +  javaClassViolation.getPackagePrivateMethodCount() + " ] not following the limit for number of package private methods in a java class";
			heading.add(new Paragraph(msg, textNormal));
		}
		
		if(javaClassViolation.getPrivateFieldCount() > 0){
			String msg = "[ " +  javaClassViolation.getPrivateFieldCount() + " ] not following the limit for number of private fields in a java class";
			heading.add(new Paragraph(msg, textNormal));
		}
		
		if(javaClassViolation.getPrivateMethodCount() > 0){
			String msg = "[ " +  javaClassViolation.getPrivateMethodCount() + " ] not following the limit for number of private methods in a java class";
			heading.add(new Paragraph(msg, textNormal));
		}
		
		if(javaClassViolation.getProtectedFieldCount() > 0){
			String msg = "[ " +  javaClassViolation.getProtectedFieldCount() + " ] not following the limit for number of protected fields in a java class";
			heading.add(new Paragraph(msg, textNormal));
		}
		
		if(javaClassViolation.getProtectedMethodCount() > 0){
			String msg = "[ " +  javaClassViolation.getProtectedMethodCount() + " ] not following the limit for number of protected methods in a java class";
			heading.add(new Paragraph(msg, textNormal));
		}
		
		if(javaClassViolation.getPublicFieldCount() > 0){
			String msg = "[ " +  javaClassViolation.getPublicFieldCount() + " ] not following the limit for number of public fields in a java class";
			heading.add(new Paragraph(msg, textNormal));
		}
		
		if(javaClassViolation.getPublicMethodCount() > 0){
			String msg = "[ " +  javaClassViolation.getPublicMethodCount() + " ] not following the limit for number of public methods in a java class";
			heading.add(new Paragraph(msg, textNormal));
		}
	}
	
	private void addInDepthDetails(Document document, JavaReportDto javaReportDto) throws DocumentException{
		Paragraph content = new Paragraph();
		
		if(javaReportDto.getNonCompilableFiles().size() > 0){
			addEmptyLine(content, 1);
			content.add(new Paragraph("Fatal Error", subHeader));
			for(String msg: javaReportDto.getNonCompilableFiles()){
				content.add(new Paragraph(msg, textNormal));
			}
		}
		
		addEmptyLine(content, 1);
		content.add(new Paragraph("Area for improvements", subHeader));
		addEmptyLine(content, 1);
		
		List<JavaClassAnalysis> javaClassAnalysisList = javaReportDto.getJavaClassAnalysis();
		for(JavaClassAnalysis javaClassAnalysis : javaClassAnalysisList){
			boolean areaToImprove = false;
			
			if(javaClassAnalysis.getClassViolationImprovement().size() > 0){
				areaToImprove = true;
				String classTitle = "In Class " + javaClassAnalysis.getClassName();
				content.add(new Paragraph(classTitle, textBold));
				content.add(new Paragraph(new String(new char[classTitle.length()]).replace("\0", "="), textNormal));
				for(String msg : javaClassAnalysis.getClassViolationImprovement()){
					content.add(new Paragraph(msg, textNormal));
				}
				addEmptyLine(content,1);
			}
			
			for(JavaMethodAnalysis javaMethodAnalysis : javaClassAnalysis.getJavaMethodAnalysis()){
				if(javaMethodAnalysis.getMethodViolationImprovement().size()>0){
					String methodTitle = "In Method " + javaMethodAnalysis.getMethodName();
					content.add(new Paragraph(methodTitle, textBold));
					content.add(new Paragraph(new String(new char[methodTitle.length()]).replace("\0", "="), textNormal));
					for(String msg : javaMethodAnalysis.getMethodViolationImprovement()){
						content.add(new Paragraph(msg, textNormal));
					}
					addEmptyLine(content,1);
				}
			}
		}
		
		document.add(content);
	}
	
	private void addEmptyLine(Paragraph paragraph, int number){
		for(int i = 0 ; i < number ; i++){
			paragraph.add(new Paragraph(" "));
		}
	}
	
	private String generateJobId(){
		LocalDate date = LocalDate.now();
		String text = date.format(DateTimeFormatter.BASIC_ISO_DATE);
		text += "/00001";
		
		return text;
	}
	
	private JavaReportDto convertJavaProjectTrackerToDto(JavaProjectTracker javaProjectTracker){
		JavaReportDto javaReportDto = new JavaReportDto();
		javaReportDto.setProjectName(javaProjectTracker.getProjectName());
		javaReportDto.setScore(javaProjectTracker.getScore());
		//get the number of files
		javaReportDto.setTotalNumberOfFiles(javaProjectTracker.getJavaFileTrackerList().size());
		
		//now loop through the file
		for(JavaFileTracker javaFileTracker : javaProjectTracker.getJavaFileTrackerList()){
			
			if(!javaFileTracker.isCompilable()){
				String filename = javaFileTracker.getJavaFileName();
				javaReportDto.AddNonCompilableFiles(filename + " cannot be compiled");
			}
			
			//now loop through the class
			for(JavaClassTracker javaClassTracker : javaFileTracker.getJavaClassTrackerList()){
				
				javaReportDto.addTotalNumberOfClass();
				javaReportDto = tallyClassViolation(javaReportDto, javaClassTracker);
				javaReportDto = tallyMethodViolation(javaReportDto, javaClassTracker);
			}
		}
		
		javaReportDto.calculateTotalViolation();
		javaReportDto.caculateDiffTypeOfViolation();
		
		return javaReportDto;
	}
	
	
	private JavaReportDto tallyMethodViolation(JavaReportDto javaReportDto, JavaClassTracker javaClassTracker){
		
		JavaClassAnalysis javaClassAnalysis = javaReportDto.getJavaClassAnalysisByClassName(javaClassTracker.getClassName());
		
		for(JavaMethodTracker javaMethodTracker : javaClassTracker.getJavaMethodTrackerList()){
			
			javaReportDto.addTotalNumberOfMethod();
			JavaMethodAnalysis javaMethodAnalysis = new JavaMethodAnalysis();
			javaMethodAnalysis.setMethodName(javaMethodTracker.getMethodName());
			
			if(javaMethodTracker.getNoOfLinesInMethod() > BenchmarkConstants.EFFECTIVE_CODE_LINES_METHOD){
				javaReportDto.getJavaMethodViolation().addEffectiveCodeLinesMethodCount();
				String violationMsg = createMethodErrMsg(javaMethodTracker.getNoOfLinesInMethod(), MethodErrorCode.EFFECTIVE_CODE_LINES_METHOD);
				javaMethodAnalysis.addMethodViolationImprovement(violationMsg);
			}
			
			if(javaMethodTracker.getNestedDepth() > BenchmarkConstants.NESTED_BLOCK_DEPTH){
				javaReportDto.getJavaMethodViolation().addNestedBlockDepthCount();
				String violationMsg = createMethodErrMsg(javaMethodTracker.getNestedDepth(), MethodErrorCode.NESTED_BLOCK_DEPTH);
				javaMethodAnalysis.addMethodViolationImprovement(violationMsg);
			}
			
			if(javaMethodTracker.getNoOfParameters() > BenchmarkConstants.NUMBER_OF_PARAMETERS){
				javaReportDto.getJavaMethodViolation().addNumberOfParametersCount();
				String violationMsg = createMethodErrMsg(javaMethodTracker.getNoOfParameters(), MethodErrorCode.NUMBER_OF_PARAMETERS);
				javaMethodAnalysis.addMethodViolationImprovement(violationMsg);
			}
			
			if(javaMethodTracker.getCyclomaticComplexity() > BenchmarkConstants.CYCLOMATIC_COMPLEXITY){
				javaReportDto.getJavaMethodViolation().addCyclomaticComplexityCount();
				String violationMsg = createMethodErrMsg(javaMethodTracker.getCyclomaticComplexity(), MethodErrorCode.CYCLOMATIC_COMPLEXITY);
				javaMethodAnalysis.addMethodViolationImprovement(violationMsg);
			}
			
			if(javaMethodTracker.getNoOfReturnStmt() > BenchmarkConstants.RETURN_STATEMENT_METHOD){
				javaReportDto.getJavaMethodViolation().addReturnStatementMethodCount();
				String violationMsg = createMethodErrMsg(javaMethodTracker.getNoOfReturnStmt(), MethodErrorCode.RETURN_STATEMENT_METHOD);
				javaMethodAnalysis.addMethodViolationImprovement(violationMsg);
			}
			javaClassAnalysis.addJavaMethodAnalysis(javaMethodAnalysis);
		}
		
		return javaReportDto;
		
	}
	
	private JavaReportDto tallyClassViolation(JavaReportDto javaReportDto, JavaClassTracker javaClassTracker){
		
		JavaClassAnalysis javaClassAnalysis = new JavaClassAnalysis();
		javaClassAnalysis.setClassName(javaClassTracker.getClassName());
		
		if(javaClassTracker.getNoOfLinesInClass() > BenchmarkConstants.EFFECTIVE_CODE_LINES_CLASS){
			javaReportDto.getJavaClassViolation().addEffectiveCodeLinesClassCount();
			String violationMsg = createClassErrMsg(javaClassTracker.getNoOfLinesInClass(), ClassErrorCode.EFFECTIVE_CODE_LINES_CLASS);
			javaClassAnalysis.addClassViolationImprovement(violationMsg);
		}
		
		if(javaClassTracker.getNoOfConstructor() > BenchmarkConstants.NUMBER_OF_CONSTRUCTOR){
			javaReportDto.getJavaClassViolation().addNoOfconstructorCount();
			String violationMsg = createClassErrMsg(javaClassTracker.getNoOfConstructor(), ClassErrorCode.NUMBER_OF_CONSTRUCTOR);
			javaClassAnalysis.addClassViolationImprovement(violationMsg);
		}
		
		if(javaClassTracker.getNoOfPrivateFields() > BenchmarkConstants.PRIVATE_FIELD_COUNT){
			javaReportDto.getJavaClassViolation().addPrivateFieldCount();
			String violationMsg = createClassErrMsg(javaClassTracker.getNoOfPrivateFields(), ClassErrorCode.PRIVATE_FIELD_COUNT);
			javaClassAnalysis.addClassViolationImprovement(violationMsg);
		}
		
		if(javaClassTracker.getNoOfPrivateMethods() > BenchmarkConstants.PRIVATE_METHOD_COUNT){
			javaReportDto.getJavaClassViolation().addPrivateMethodCount();
			String violationMsg = createClassErrMsg(javaClassTracker.getNoOfPrivateMethods(), ClassErrorCode.PRIVATE_METHOD_COUNT);
			javaClassAnalysis.addClassViolationImprovement(violationMsg);
		}
		
		if(javaClassTracker.getNoOfProtectedFields() > BenchmarkConstants.PROTECTED_FIELD_COUNT){
			javaReportDto.getJavaClassViolation().addProtectedFieldCount();
			String violationMsg = createClassErrMsg(javaClassTracker.getNoOfProtectedFields(), ClassErrorCode.PROTECTED_FIELD_COUNT);
			javaClassAnalysis.addClassViolationImprovement(violationMsg);
		}
		
		if(javaClassTracker.getNoOfProtectedMethods() > BenchmarkConstants.PROTECTED_METHOD_COUNT){
			javaReportDto.getJavaClassViolation().addProtectedMethodCount();
			String violationMsg = createClassErrMsg(javaClassTracker.getNoOfProtectedMethods(), ClassErrorCode.PROTECTED_METHOD_COUNT);
			javaClassAnalysis.addClassViolationImprovement(violationMsg);
		}
		
		if(javaClassTracker.getNoOfPublicFields() > BenchmarkConstants.PUBLIC_FIELD_COUNT){
			javaReportDto.getJavaClassViolation().addPublicFieldCount();
			String violationMsg = createClassErrMsg(javaClassTracker.getNoOfPublicFields(), ClassErrorCode.PUBLIC_FIELD_COUNT);
			javaClassAnalysis.addClassViolationImprovement(violationMsg);
		}
		
		if(javaClassTracker.getNoOfPublicMethods() > BenchmarkConstants.PUBLIC_METHOD_COUNT) {
			javaReportDto.getJavaClassViolation().addPublicMethodCount();
			String violationMsg = createClassErrMsg(javaClassTracker.getNoOfPublicMethods(), ClassErrorCode.PUBLIC_METHOD_COUNT);
			javaClassAnalysis.addClassViolationImprovement(violationMsg);
		}
		
		if(javaClassTracker.getNoOfPackagePrivateFields() > BenchmarkConstants.PACKAGE_PRIVATE_FIELD_COUNT){
			javaReportDto.getJavaClassViolation().addPackagePrivateFieldCount();
			String violationMsg = createClassErrMsg(javaClassTracker.getNoOfPackagePrivateFields(), ClassErrorCode.PACKAGE_PRIVATE_FIELD_COUNT);
			javaClassAnalysis.addClassViolationImprovement(violationMsg);
		}
		
		if(javaClassTracker.getNoOfPackagePrivateMethods() > BenchmarkConstants.PACKAGE_PRIVATE_METHOD_COUNT){
			javaReportDto.getJavaClassViolation().addPackagePrivateMethodCount();
			String violationMsg = createClassErrMsg(javaClassTracker.getNoOfPackagePrivateMethods(), ClassErrorCode.PACKAGE_PRIVATE_METHOD_COUNT);
			javaClassAnalysis.addClassViolationImprovement(violationMsg);
		}
		
		if(javaClassTracker.getNoOfStaticAttribute() > BenchmarkConstants.NUMBER_OF_STATIC_ATTRIBUTE){
			javaReportDto.getJavaClassViolation().addNumberOfStaticAttributeCount();
			String violationMsg = createClassErrMsg(javaClassTracker.getNoOfStaticAttribute(), ClassErrorCode.NUMBER_OF_STATIC_ATTRIBUTE);
			javaClassAnalysis.addClassViolationImprovement(violationMsg);
		}
		
		javaReportDto.addJavaClassAnalysis(javaClassAnalysis);
		
		return javaReportDto;
	}
	
	private String calculateGrade(double score){
		
		String grade = "";
		if(score > 85){
			grade = "A+";
		} else if (score > 80){
			grade = "A";
		} else if ( score > 75) {
			grade = "A-";
		} else if ( score > 70) {
			grade = "B+";
		} else if (score > 65) {
			grade = "B";
		} else if (score > 60) {
			grade = "B-";
		} else if (score > 55) {
			grade = "C+";
		} else if (score > 50) {
			grade = "C";
		} else if (score > 45) {
			grade = "D+";
		} else if (score > 40) {
			grade = "D";
		} else {
			grade = "F";
		}
		return grade;
	}
	
	private String createClassErrMsg(int value, int errCode){
		String violationMsg = "";
		if(errCode == ClassErrorCode.EFFECTIVE_CODE_LINES_CLASS){
			violationMsg = "The number of effective code lines in class is " + value + ", not lower than " + 
								BenchmarkConstants.EFFECTIVE_CODE_LINES_CLASS + SQATConstants.SEVERITY_LEVEL_TWO ;
		}
		
		if(errCode == ClassErrorCode.NUMBER_OF_CONSTRUCTOR){
			violationMsg = "The number of constructor in class is " + value + ", not lower than " + 
								BenchmarkConstants.NUMBER_OF_CONSTRUCTOR + SQATConstants.SEVERITY_LEVEL_THREE;
		}
		
		if(errCode == ClassErrorCode.PRIVATE_FIELD_COUNT){
			violationMsg = "The number of private fields in class is " + value + ", not lower than " + 
								BenchmarkConstants.PRIVATE_FIELD_COUNT + SQATConstants.SEVERITY_LEVEL_TWO;
		}
		
		if(errCode == ClassErrorCode.PRIVATE_METHOD_COUNT){
			violationMsg = "The number of private methods in class is " + value + ", not lower than " + 
								BenchmarkConstants.PRIVATE_METHOD_COUNT + SQATConstants.SEVERITY_LEVEL_TWO;
		}
		
		if(errCode == ClassErrorCode.PROTECTED_FIELD_COUNT){
			violationMsg = "The number of protected fields in class is " + value + ", not lower than " + 
								BenchmarkConstants.PROTECTED_FIELD_COUNT + SQATConstants.SEVERITY_LEVEL_TWO;
		}
		
		if(errCode == ClassErrorCode.PROTECTED_METHOD_COUNT){
			violationMsg = "The number of protected method in class is " + value + ", not lower than " + 
								BenchmarkConstants.PROTECTED_METHOD_COUNT + SQATConstants.SEVERITY_LEVEL_TWO;
		}
		
		if(errCode == ClassErrorCode.PUBLIC_FIELD_COUNT){
			violationMsg = "The number of public fields in class is " + value + ", not lower than " + 
								BenchmarkConstants.PUBLIC_FIELD_COUNT + SQATConstants.SEVERITY_LEVEL_TWO;
		}
		
		if(errCode == ClassErrorCode.PUBLIC_METHOD_COUNT) {
			violationMsg = "The number of public method in class is " + value + ", not lower than " + 
								BenchmarkConstants.PUBLIC_METHOD_COUNT + SQATConstants.SEVERITY_LEVEL_TWO;
		}
		
		if(errCode == ClassErrorCode.PACKAGE_PRIVATE_FIELD_COUNT){
			violationMsg = "The number of package private field in class is " + value + ", not lower than " + 
								BenchmarkConstants.PACKAGE_PRIVATE_FIELD_COUNT + SQATConstants.SEVERITY_LEVEL_TWO;
		}
		
		if(errCode == ClassErrorCode.PACKAGE_PRIVATE_METHOD_COUNT){
			violationMsg = "The number of package private method in class is " + value + ", not lower than " + 
								BenchmarkConstants.PACKAGE_PRIVATE_METHOD_COUNT + SQATConstants.SEVERITY_LEVEL_TWO;
		}
		
		if(errCode == ClassErrorCode.NUMBER_OF_STATIC_ATTRIBUTE){
			violationMsg = "The number of static field in class is " + value + ", not lower than " + 
								BenchmarkConstants.NUMBER_OF_STATIC_ATTRIBUTE + SQATConstants.SEVERITY_LEVEL_THREE;
		}
		
		return violationMsg;
	}
	
	private String createMethodErrMsg(int value, int errCode){
		String violationMsg = "";
		if(errCode == MethodErrorCode.EFFECTIVE_CODE_LINES_METHOD){
			violationMsg = "The number of effective code line in method is " + value + ", not lower than " + 
					BenchmarkConstants.EFFECTIVE_CODE_LINES_METHOD + SQATConstants.SEVERITY_LEVEL_THREE;
		}
		
		if(errCode == MethodErrorCode.NESTED_BLOCK_DEPTH){
			violationMsg = "The number of nested block depth in method is " + value + ", not lower than " + 
					BenchmarkConstants.NESTED_BLOCK_DEPTH + SQATConstants.SEVERITY_LEVEL_TWO;

		}
		
		if(errCode == MethodErrorCode.NUMBER_OF_PARAMETERS){
			violationMsg = "The number of parameters in method is " + value + ", not lower than " + 
					BenchmarkConstants.NUMBER_OF_PARAMETERS + SQATConstants.SEVERITY_LEVEL_TWO;

		}
		
		if(errCode == MethodErrorCode.CYCLOMATIC_COMPLEXITY){
			violationMsg = "The cyclomatic complexity in method is " + value + ", not lower than " + 
					BenchmarkConstants.CYCLOMATIC_COMPLEXITY + SQATConstants.SEVERITY_LEVEL_TWO;

		}
		
		if(errCode == MethodErrorCode.RETURN_STATEMENT_METHOD){
			violationMsg = "The number of return statement in method is " + value + ", not lower than " + 
					BenchmarkConstants.RETURN_STATEMENT_METHOD + SQATConstants.SEVERITY_LEVEL_TWO;

		}
		
		return violationMsg;
	}
	
}
