package br.pucrio.inf.les.ese.dianalyzer.diast_test.practices;

import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeThirteen;
import br.pucrio.inf.les.ese.dianalyzer.diast_test.annotation.BadPracticeApplied;
import br.pucrio.inf.les.ese.dianalyzer.repository.locator.ServiceLocator;
import br.pucrio.inf.les.ese.dianalyzer.repository.source.IDataSource;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.util.ArrayList;
import java.util.List;

@BadPracticeApplied(BadPracticeThirteen.class)
public class BadPracticeThirteenTest extends AbstractBadPracticeTest {

	@Override
	public List<String> setUp() {
		List<String> classes = new ArrayList<>();

		//read files from resource folder
		classes = getClassesToParse();

		IDataSource dataSource = (IDataSource) ServiceLocator.getInstance().getBeanInstance("IDataSource");

		for(String class_ : classes){
			CompilationUnit cu = JavaParser.parse(class_);

			String typeName = cu.getType(0).getNameAsString();

			dataSource.insert( typeName, cu );
		}

		// only first should be analyzed. others are part of the 'database'
		classes.remove(1);

		return classes;
	}

}
