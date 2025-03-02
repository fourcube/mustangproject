
/** **********************************************************************
 *
 * Copyright 2019 Jochen Staerk
 *
 * Use is subject to license terms.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *********************************************************************** */
package org.mustangproject.ZUGFeRD;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.mustangproject.CII.CIIToUBL;

import javax.xml.transform.TransformerException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VisualizationTest extends ResourceCase {

	public void testVisualizationBasic() {

		// the writing part
		CIIToUBL c2u = new CIIToUBL();
		String sourceFilename = "factur-x.xml";
		File input = getResourceAsFile(sourceFilename);

		String expected = null;
		String result = null;
		try {
			ZUGFeRDVisualizer zvi = new ZUGFeRDVisualizer();
			/* remove file endings so that tests can also pass after checking
			   out from git with arbitrary options (which may include CSRF changes)
			 */
			result = zvi.visualize(input.getAbsolutePath()).replace("\r","").replace("\n","");

			File expectedResult=getResourceAsFile("factur-x.html");
			expected = new String(Files.readAllBytes(expectedResult.toPath()), StandardCharsets.UTF_8).replace("\r","").replace("\n","");
			// remove linebreaks as well...

		} catch (TransformerException e) {
			fail("Exception should not happen: "+e.getMessage());
		} catch (IOException e) {
			fail("Exception should not happen: "+e.getMessage());
		}



		assertNotNull(result);
		// Reading ZUGFeRD
		assertEquals(expected, result);
	}
}
