package br.pucrio.inf.les.ese.dianalyzer.diast_test.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeEleven;
import br.pucrio.inf.les.ese.dianalyzer.diast_test.annotation.BadPracticeApplied;

import java.util.List;

@BadPracticeApplied(BadPracticeEleven.class)
public class BadPracticeElevenTest extends AbstractBadPracticeTest {

    @Override
    public List<String> setUp() {
        return super.getClassesToParse();
    }
}
