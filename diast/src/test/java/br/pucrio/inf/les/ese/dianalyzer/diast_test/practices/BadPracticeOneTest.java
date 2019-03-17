package br.pucrio.inf.les.ese.dianalyzer.diast_test.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeOne;
import br.pucrio.inf.les.ese.dianalyzer.diast_test.annotation.BadPracticeApplied;

import java.util.List;

@BadPracticeApplied(BadPracticeOne.class)
public class BadPracticeOneTest extends AbstractBadPracticeTest {

	@Override
	public List<String> setUp() {
		return super.getClassesToParse();
	}
}
