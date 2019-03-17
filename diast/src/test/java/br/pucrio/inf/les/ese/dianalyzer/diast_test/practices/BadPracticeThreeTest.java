package br.pucrio.inf.les.ese.dianalyzer.diast_test.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeThree;
import br.pucrio.inf.les.ese.dianalyzer.diast_test.annotation.BadPracticeApplied;

import java.util.List;

@BadPracticeApplied(BadPracticeThree.class)
public class BadPracticeThreeTest extends AbstractBadPracticeTest {

	@Override
	public List<String> setUp() {
		return super.getClassesToParse();
	}
}
