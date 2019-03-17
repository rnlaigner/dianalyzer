package br.pucrio.inf.les.ese.dianalyzer.diast_test.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeTwelve;
import br.pucrio.inf.les.ese.dianalyzer.diast_test.annotation.BadPracticeApplied;

import java.util.List;

@BadPracticeApplied(BadPracticeTwelve.class)
public class BadPracticeTwelveTest extends AbstractBadPracticeTest {

	@Override
	public List<String> setUp() {
		return super.getClassesToParse();
	}
}
