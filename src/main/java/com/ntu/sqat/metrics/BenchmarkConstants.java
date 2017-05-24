package com.ntu.sqat.metrics;

/**
 * This contains all the benchmark that the score will be calculated upon
 * @author Daryl Lau
 *
 */
public class BenchmarkConstants {
	
	public static final int EFFECTIVE_CODE_LINES_CLASS = 1000;			//lower than 1001	severity 2
	public static final int CYCLOMATIC_COMPLEXITY = 12; 				//lower than 13		severity 2
	public static final int NESTED_BLOCK_DEPTH = 4; 					//lower than 5		severity 2
	public static final int PRIVATE_FIELD_COUNT = 15;					//lower than 16		severity 2
	public static final int PRIVATE_METHOD_COUNT = 15;					//lower than 16		severity 2
	public static final int PROTECTED_FIELD_COUNT = 10;					//lower than 11		severity 2
	public static final int PROTECTED_METHOD_COUNT = 10;				//lower than 11		severity 2
	public static final int PUBLIC_FIELD_COUNT = 3;						//lower than 4		severity 2
	public static final int PUBLIC_METHOD_COUNT = 35;					//lower than 36		severity 2
	public static final int NUMBER_OF_CONSTRUCTOR = 5;					//lower than 6		severity 3
	public static final int EFFECTIVE_CODE_LINES_METHOD = 50;			//lower than 51		severity 3
	public static final int NUMBER_OF_FIELDS = 15;						//lower than 16		severity 2
	public static final int NUMBER_OF_PARAMETERS = 5;					//lower than 6		severity 2
	public static final int RETURN_STATEMENT_METHOD = 3;				//lower than 4		severity 2
	public static final int NUMBER_OF_STATIC_ATTRIBUTE = 9;				//lower than 10		severity 3
	public static final int PACKAGE_PRIVATE_FIELD_COUNT = 0;			//lower than 1		severity 2
	public static final int PACKAGE_PRIVATE_METHOD_COUNT = 3;			//lower than 4		severity 2
	
}
