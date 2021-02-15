package cz.martlin.xspf.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import cz.martlin.xspf.playlist.TestingFiles;
import cz.martlin.xspf.playlist.elements.XSPFFile;

class PrinterTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@ParameterizedTest
	@ValueSource(strings = { //
			"full.xspf", //
//			"minimal.xspf" //
	})
	void test(String name) throws XSPFException {
		File fileToRead = TestingFiles.fileToReadAssumed("playlist", name);
		XSPFFile file = XSPFFile.load(fileToRead);
		
		Printer.print(1, "The " + name + " playlist file", file);
	}

}
