package br.pucrio.inf.les.ese.dianalyzer.diast_test.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeFour;
import br.pucrio.inf.les.ese.dianalyzer.diast_test.annotation.BadPracticeApplied;

import java.util.List;

@BadPracticeApplied(BadPracticeFour.class)
public class BadPracticeFourTest extends AbstractBadPracticeTest {

    @Override
    public List<String> setUp() {
        return super.getClassesToParse();
    }
}
