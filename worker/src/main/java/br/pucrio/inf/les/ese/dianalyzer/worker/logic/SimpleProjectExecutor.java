package br.pucrio.inf.les.ese.dianalyzer.worker.logic;

import br.pucrio.inf.les.ese.dianalyzer.diast.environment.ParseException;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.CompilationUnitResult;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.*;
import br.pucrio.inf.les.ese.dianalyzer.worker.report.IWorkbookCreator;
import br.pucrio.inf.les.ese.dianalyzer.worker.report.Report;
import br.pucrio.inf.les.ese.dianalyzer.worker.report.WorkbookCreator;
import com.github.javaparser.ast.CompilationUnit;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@BadPracticesApplied(values={
		BadPracticeOne.class,
		BadPracticeTwo.class,
		BadPracticeThree.class,
		/*BadPracticeFour.class,*/
		BadPracticeFive.class,
		BadPracticeSix.class,
		BadPracticeSeven.class,
		BadPracticeEight.class,
		BadPracticeNine.class,
		BadPracticeTen.class,
		BadPracticeEleven.class,
		BadPracticeTwelve.class,
		// BadPracticeThirteen.class
})
public class SimpleProjectExecutor extends AbstractProjectExecutor {

	public SimpleProjectExecutor(){
		super();
		buildBadPracticesApplied();
	}

	@Override
	public void execute(String projectPath, String outputPath) throws Exception {
		
		List<String> files = env.readFilesFromFolder(projectPath,false);

		process(projectPath, outputPath, files);

	}




}
