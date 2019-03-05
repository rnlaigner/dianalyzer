package br.pucrio.inf.les.ese.dianalyzer.diast_test.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.practices.AbstractPractice;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeThirteen;
import br.pucrio.inf.les.ese.dianalyzer.diast_test.annotation.ResourceFolder;

@ResourceFolder(value="src//test//resources//BadPracticeThirteen")
public class BadPracticeThirteenTest extends AbstractBadPracticeTest {
	
	@Override
	public Class<? extends AbstractPractice> getConcretePractice() {
		return BadPracticeThirteen.class;
	}

}
