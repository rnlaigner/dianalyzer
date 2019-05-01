package br.pucrio.inf.les.ese.dianalyzer.diast.rule;

import br.pucrio.inf.les.ese.dianalyzer.diast.identification.ConstructorInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.FieldDeclarationInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.MethodInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.SetMethodInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.ElementResult;
import br.pucrio.inf.les.ese.dianalyzer.repository.locator.ServiceLocator;
import br.pucrio.inf.les.ese.dianalyzer.repository.source.IDataSource;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
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

		AnnotationExpr annotation = null;

		try {
			annotation = cu.getType(0).getAnnotationByName("Scope").get();
		}
		catch(NoSuchElementException | IndexOutOfBoundsException e){
			ElementResult result = new ElementResult();
			result.setElement(null);
			result.setResult(false);
			return result;
		}

		String annotationAsString = extractAnnotationAsString( (SingleMemberAnnotationExpr) annotation );

		if(annotationAsString == null) {
            ElementResult result = new ElementResult();
            result.setElement(null);
            result.setResult(false);
            return result;
        }

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

				 // String annotation_AsString = ((StringLiteralExpr) ((SingleMemberAnnotationExpr) annotation_).getMemberValue()).asString();

                 String annotation_AsString = extractAnnotationAsString( (SingleMemberAnnotationExpr) annotation_ );

                 if(annotation_AsString == null) {
                     ElementResult result = new ElementResult();
                     result.setElement(null);
                     result.setResult(false);
                     return result;
                 }

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

    private String extractAnnotationAsString(SingleMemberAnnotationExpr annotation) {

	    String annotationAsString = null;
        try {
            annotationAsString = ((StringLiteralExpr) annotation.getMemberValue()).asString();
        } catch (ClassCastException e) {
            log.info("It is not a string literal");
        }

        if (annotationAsString == null) {
            try {
                annotationAsString = ((FieldAccessExpr) annotation.getMemberValue()).getNameAsString();
            } catch (Exception e) {
                log.info("It is not a field access expr");
                return null;
            }
        }

        if(annotationAsString.equals("SCOPE_PROTOTYPE")){
            annotationAsString = "prototype";
        }

        return annotationAsString;
    }
}
