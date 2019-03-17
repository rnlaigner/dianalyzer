package br.pucrio.inf.les.ese.dianalyzer.diast_test.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeNine;
import br.pucrio.inf.les.ese.dianalyzer.diast_test.annotation.BadPracticeApplied;

import java.util.List;

@BadPracticeApplied(BadPracticeNine.class)
public class BadPracticeNineTest extends AbstractBadPracticeTest {

	@Override
	public List<String> setUp() {
		return super.getClassesToParse();
	}
}
