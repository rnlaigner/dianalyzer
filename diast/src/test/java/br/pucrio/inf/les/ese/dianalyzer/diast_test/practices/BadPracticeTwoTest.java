package br.pucrio.inf.les.ese.dianalyzer.diast_test.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.practices.AbstractPractice;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeTwo;
import br.pucrio.inf.les.ese.dianalyzer.diast_test.annotation.ResourceFolder;

@ResourceFolder(value="src//test//resources//BadPracticeTwo")
public class BadPracticeTwoTest extends AbstractBadPracticeTest {
	
	@Override
	public Class<? extends AbstractPractice> getConcretePractice() {
		return BadPracticeTwo.class;
	}

}
