package br.pucrio.inf.les.ese.dianalyzer.diast_test.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeTwo;
import br.pucrio.inf.les.ese.dianalyzer.diast_test.annotation.BadPracticeApplied;

import java.util.List;

@BadPracticeApplied(BadPracticeTwo.class)
public class BadPracticeTwoTest extends AbstractBadPracticeTest {

	@Override
	public List<String> setUp() {
		return super.getClassesToParse();
	}

}
