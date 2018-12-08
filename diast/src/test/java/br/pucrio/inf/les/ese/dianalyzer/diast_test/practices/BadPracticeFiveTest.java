package br.pucrio.inf.les.ese.dianalyzer.diast_test.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.practices.AbstractPractice;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeFive;
import br.pucrio.inf.les.ese.dianalyzer.diast_test.annotation.ResourceFolder;

@ResourceFolder(value="src//test//resources//BadPracticeFive")
public class BadPracticeFiveTest extends AbstractBadPracticeTest {
	
	@Override
	public Class<? extends AbstractPractice> getConcretePractice() {
		return BadPracticeFive.class;
	}

}
