package br.pucrio.inf.les.ese.dianalyzer.diast_test.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeFive;
import br.pucrio.inf.les.ese.dianalyzer.diast_test.annotation.BadPracticeApplied;

import java.util.List;

@BadPracticeApplied(BadPracticeFive.class)
public class BadPracticeFiveTest extends AbstractBadPracticeTest {

    @Override
    public List<String> setUp() {
        return super.getClassesToParse();
    }
}
