package br.pucrio.inf.les.ese.dianalyzer.diast.identification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.stmt.Statement;

import br.pucrio.inf.les.ese.dianalyzer.diast.model.AssignmentBusiness;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectedElement;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.InjectionType;
/*
 * It serves Constructor and Method injection identifier
 */
public abstract class AbstractMethodInjectionIdentificator extends AbstractInjectionIdentificator {
	
	public AbstractMethodInjectionIdentificator(InjectionType injectionType) {
		super(injectionType);
	}

	protected List<InjectedElement> identifyFromParameters( CallableDeclaration<?> f ) throws Exception {
		
		List<InjectedElement> elements = new ArrayList<InjectedElement>();
		
		NodeList<Parameter> parameters = f.getParameters();
		
		NodeList<Statement> statements = getStatementsFromBody(f);
		
		//search body for assignment of element
		List<AssignExpr> assignments = AssignmentBusiness.getAssignmentsFromStatements(statements);
		
		for (Parameter parameter : parameters) {
			
			//get assignments from current parameter
			List<AssignExpr> assignmentsFromCurrentParameter = assignments
										.stream()
										.filter(a -> a
												.getValue()
												.toString()
												.equals(parameter.getName().toString())
												)
										.collect(Collectors.toList());
			
			for ( AssignExpr assignment : assignmentsFromCurrentParameter ) {
				
				InjectedElement elem = new InjectedElement();
				
				elem.setType(parameter.getType().asString());
				
				try {
					elem.setClassType( getObjectTypeFromString
										(parameter.getChildNodes().
												get(0).
												getClass().
												getSimpleName() ) );
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				String annotation = f.getAnnotations()
						.stream()
						.distinct()
						.map(e -> e.getName().asString())
						.collect( Collectors.toList() )
						.get(0);
				
				try {
					elem.setAnnotation(getInjectionAnnotationFromString(annotation));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				elem.setInjectionType(getInjectionType());
				
				FieldAccessExpr fieldAccessExpr = (FieldAccessExpr) assignment.getTarget();
				
				elem.setName(fieldAccessExpr.getNameAsString());
				
				elements.add( elem );
			}
		}
		
		return elements;
	}
	
	private NodeList<Statement> getStatementsFromBody( CallableDeclaration<?> f ) throws Exception {

		if (f instanceof MethodDeclaration) {
			return ( (MethodDeclaration) f).getBody().get().getStatements();
		}
		
		if (f instanceof ConstructorDeclaration) {
			return ( (ConstructorDeclaration) f).getBody().getStatements();
		}
		
		throw new Exception("Tipo nao reconhecido");
		
	}
	
	//FIXME based on assumption for Java language
	protected boolean isSetMethod(MethodDeclaration f){
		return f.getName().getIdentifier().startsWith("set");
	}

}
