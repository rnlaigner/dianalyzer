package br.pucrio.inf.les.ese.dianalyzer.diast_test.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.practices.AbstractPractice;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeTen;
import br.pucrio.inf.les.ese.dianalyzer.diast_test.annotation.ResourceFolder;

@ResourceFolder(value="src//test//resources//BadPracticeTen")
public class BadPracticeTenTest extends AbstractBadPracticeTest {
	
	@Override
	public Class<? extends AbstractPractice> getConcretePractice() {
		return BadPracticeTen.class;
	}

}
