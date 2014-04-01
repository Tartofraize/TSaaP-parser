package main;

import wiki.WikiQuestionService;

public class MainWiki {

	/**
	 * instantiate a WikiQuestionService
	 * @param args parameter for the TSaap-parser file
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: TSaaP-parser file");
		} else {
			WikiQuestionService me = new WikiQuestionService();
			
			me.getQuizFromWikiText(args[0]);
		}
	}

}
