package com.ntu.sqat.metrics;

/**
 * This class contain the scoring calculation 
 * @author Daryl Lau
 *
 */
public class ScoreCalculator {

	public static double calculateScore(int value, int benchmark){
		double score = 0; 
		
		if (value <= benchmark) {
			score = 100;
	    } else if (value <= 4f * benchmark) {
	    	score = (((-1f / (3f * benchmark)) * value) + (4f / 3f)) * 100f;
	    } else {
	      score = 0;
	    }
	
		return score;
	}
	
	
	public static double calculateScore(int value, int benchmark, int severity){
		double score = 0; 
		
		if (value <= benchmark) {
			score = 100;
	    } else if (value <= severity * benchmark) {
	    	score = (((-1f / (severity * benchmark)) * value) + ((severity + 1) / severity * 1f)) * 100f;
	    } else {
	    	score = 0;
	    }
				
		return score;
	}
	
	
	
}
