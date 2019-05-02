package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.identification.ConstructorInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.FieldDeclarationInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.MethodInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.SetMethodInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.logic.InjectionBusiness;
import br.pucrio.inf.les.ese.dianalyzer.diast.logic.ScopeBusiness;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.repository.locator.ServiceLocator;
import br.pucrio.inf.les.ese.dianalyzer.repository.source.IDataSource;
import com.github.javaparser.ast.CompilationUnit;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class IsPrototypeScopeBeanAsSingleton extends AbstractRuleWithNoElement {

	private Iterator entries;

	public IsPrototypeScopeBeanAsSingleton(){
		IDataSource dataSource = (IDataSource) ServiceLocator.getInstance().getBeanInstance("IDataSource");
		// TODO nao precisa fazer mais isso. so dar find em associated data
		entries = dataSource.findAll();
	}

	@Override
	public ElementResult processRule(CompilationUnit cu) {

		// default is singleton on spring

		String annotationAsString = ScopeBusiness.extractScopeAnnotationValueAsString(cu);

		// if it is not prototype, return
		if(annotationAsString != null && !annotationAsString.equalsIgnoreCase("prototype")){
			ElementResult result = new ElementResult();
			result.setElement(null);
			result.setResult(false);
			return result;
		}

		// now I need to find at least one singleton bean that requires cu as injection
		while(entries.hasNext()){

			// TODO change to tuple
			CompilationUnit currentCu = (CompilationUnit) entries.next();

			String currentCuNameAsString = null;
			try{
			    currentCuNameAsString = currentCu.getType(0).getNameAsString();
            } catch(IndexOutOfBoundsException e){
			    continue;
            }

			// se por acaso forem os mesmos, nao ha motivo para analisar
			if( currentCuNameAsString.equals(cu.getType(0).getNameAsString()) ){
				continue;
			}

			List<AbstractElement> elements = InjectionBusiness.
					getInjectedElementsFromClass( cu );


			AbstractElement element = null;

			String cuType = cu.getType(0).getNameAsString();

			 try {
				 element = elements.stream().filter(elem -> elem.getName().equalsIgnoreCase(cuType)).findFirst().get();
			 }catch (NoSuchElementException e){
			 	continue;
			 }

			 String annotation_AsString = ScopeBusiness.extractScopeAnnotationValueAsString(cu);

			 if(annotation_AsString != null && !annotation_AsString.equalsIgnoreCase("prototype")){
				 ElementResult result = new ElementResult();
				 result.setElement(element);
				 result.setResult(true);
				 return result;
			 }

		}

		ElementResult result = new ElementResult();
		result.setElement(null);
		result.setResult(false);
		return result;

	}


}
