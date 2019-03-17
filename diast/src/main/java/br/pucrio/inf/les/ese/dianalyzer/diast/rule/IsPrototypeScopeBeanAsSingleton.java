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
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;

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

		AnnotationExpr annotation;

		try {
			annotation = cu.getType(0).getAnnotationByName("Scope").get();
		}
		catch(NoSuchElementException e){
			ElementResult result = new ElementResult();
			result.setElement(null);
			result.setResult(false);
			return result;
		}

		String annotationAsString = ((StringLiteralExpr) ((SingleMemberAnnotationExpr) annotation).getMemberValue()).asString();

		// if it is not prototype, return
		if(annotation != null && !annotationAsString.equalsIgnoreCase("prototype")){
			ElementResult result = new ElementResult();
			result.setElement(null);
			result.setResult(false);
			return result;
		}

		// now I need to find at least one singleton bean that requires cu as injection
		for(Map.Entry entry : entries){

			CompilationUnit currentCu = (CompilationUnit) entry.getValue();

			// se por acaso forem os mesmos, nao ha motivo para analisar
			if( currentCu.getType(0).getNameAsString().equals(cu.getType(0).getNameAsString()) ){
				continue;
			}

			// need to find all injected elements
			FieldDeclarationInjectionIdentificator fieldId = new FieldDeclarationInjectionIdentificator();
			ConstructorInjectionIdentificator constructorId = new ConstructorInjectionIdentificator();
			MethodInjectionIdentificator methodId = new MethodInjectionIdentificator();
			SetMethodInjectionIdentificator setMethodId = new SetMethodInjectionIdentificator();

			List<AbstractElement> elements = fieldId.identify( currentCu );
			elements.addAll(constructorId.identify( currentCu ) );
			elements.addAll(methodId.identify( currentCu ) );
			elements.addAll(setMethodId.identify( currentCu ) );

			AbstractElement element;

			String cuType = cu.getType(0).getNameAsString();

			 try {
				 element = elements.stream().filter(elem -> elem.getName().equalsIgnoreCase(cuType)).findFirst().get();
			 }catch (NoSuchElementException e){
			 	continue;
			 }

			 try{

				 AnnotationExpr annotation_ = currentCu.getType(0).getAnnotationByName("Scope").get();

				 String annotation_AsString = ((StringLiteralExpr) ((SingleMemberAnnotationExpr) annotation_).getMemberValue()).asString();

				 if(annotation_ != null && !annotation_AsString.equalsIgnoreCase("prototype")){
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
