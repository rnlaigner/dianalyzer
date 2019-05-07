package br.pucrio.inf.les.ese.dianalyzer.worker.logic;

import br.pucrio.inf.les.ese.dianalyzer.diast.practices.*;

import java.util.List;

@BadPracticesApplied(values={
		BadPracticeOne.class,
//		BadPracticeTwo.class,
		BadPracticeThree.class,
//		BadPracticeFour.class,
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
