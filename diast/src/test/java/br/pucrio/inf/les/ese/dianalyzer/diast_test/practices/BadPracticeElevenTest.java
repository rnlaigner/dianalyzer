package br.pucrio.inf.les.ese.dianalyzer.diast_test.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.practices.AbstractPractice;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeEleven;
import br.pucrio.inf.les.ese.dianalyzer.diast_test.annotation.ResourceFolder;

@ResourceFolder(value="src//test//resources//BadPracticeEleven")
public class BadPracticeElevenTest extends AbstractBadPracticeTest {
	
	@Override
	public Class<? extends AbstractPractice> getConcretePractice() {
		return BadPracticeEleven.class;
	}

}