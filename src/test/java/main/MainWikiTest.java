package main;

import org.junit.Test;

public class MainWikiTest {

	@Test
	public void testMain() {
		// Statement for code coverage
		new MainWiki();
		
		MainWiki.main(new String[]{"ressources/TestQuizz.txt"});
	}
	
	@Test
	public void testIncorrectParameters() {
		MainWiki.main(new String[]{"ressources/TestQuizz.txt", "pouet"});
	}

}
