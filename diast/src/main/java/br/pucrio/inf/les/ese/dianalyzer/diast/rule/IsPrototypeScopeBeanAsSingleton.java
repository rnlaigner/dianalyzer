package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.identification.ConstructorInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.FieldDeclarationInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.MethodInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.SetMethodInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.repository.locator.ServiceLocator;
import br.pucrio.inf.les.ese.dianalyzer.repository.model.IDataSource;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.AnnotationDeclaration;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class IsPrototypeScopeBeanAsSingleton extends AbstractRuleWithNoElement {

	private Collection<Map.Entry> entries;

	public IsPrototypeScopeBeanAsSingleton(){
		IDataSource dataSource = (IDataSource) ServiceLocator.getInstance().getBeanInstance("IDataSource");
		entries = dataSource.findAll();
	}

	@Override
	public ElementResult processRule(CompilationUnit cu) {

		// default is singleton on spring

		AnnotationDeclaration annotation;

		try {
			annotation = cu.getAnnotationDeclarationByName("Scope").get();
		}
		catch(NoSuchElementException e){
			ElementResult result = new ElementResult();
			result.setElement(null);
			result.setResult(false);
			return result;
		}

		// if it is not prototype, return
		if(annotation != null && !annotation.getName().getIdentifier().equalsIgnoreCase("prototype")){
			ElementResult result = new ElementResult();
			result.setElement(null);
			result.setResult(false);
			return result;
		}

		// TODO now I need to find at least one singleton bean that requires cu as injection


		for(Map.Entry entry : entries){

			// need to find all injected elements
			FieldDeclarationInjectionIdentificator fieldId = new FieldDeclarationInjectionIdentificator();
			ConstructorInjectionIdentificator constructorId = new ConstructorInjectionIdentificator();
			MethodInjectionIdentificator methodId = new MethodInjectionIdentificator();
			SetMethodInjectionIdentificator setMethodId = new SetMethodInjectionIdentificator();

			List<AbstractElement> elements = fieldId.identify(( CompilationUnit ) entry.getValue() );
			elements.addAll(constructorId.identify(( CompilationUnit ) entry.getValue()));
			elements.addAll(methodId.identify(( CompilationUnit ) entry.getValue()));
			elements.addAll(setMethodId.identify(( CompilationUnit ) entry.getValue()));

			 String cuType = cu.getPrimaryTypeName().get();

			AbstractElement element;

			 try {
				 element = elements.stream().filter(elem -> elem.getName().equalsIgnoreCase(cuType)).findFirst().get();
			 }catch (NoSuchElementException e){
			 	continue;
			 }

			 try{

				 AnnotationDeclaration annotation_ = (( CompilationUnit ) entry.getValue()).getAnnotationDeclarationByName("Scope").get();

				 if(annotation_ != null && !annotation_.getName().getIdentifier().equalsIgnoreCase("prototype")){
					 ElementResult result = new ElementResult();
					 result.setElement(element);
					 result.setResult(true);
					 return result;
				 }

			 }catch(NoSuchElementException e){
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
