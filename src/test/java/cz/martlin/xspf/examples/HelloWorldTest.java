package cz.martlin.xspf.examples;

import java.io.File;

import org.junit.jupiter.api.Test;

import cz.martlin.xspf.util.TestingFiles;
import cz.martlin.xspf.util.XSPFException;

/**
 * Just the test for the {@link HelloWorld}.
 * 
 * @author martin
 *
 */
class HelloWorldTest {

	@Test
	void testIt() throws XSPFException {
		File file = TestingFiles.fileToWriteAssumed("hello-world.xspf");

		HelloWorld.runHelloWorld(file);
	}

}
