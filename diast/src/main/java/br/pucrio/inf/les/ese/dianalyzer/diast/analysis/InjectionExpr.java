package br.pucrio.inf.les.ese.dianalyzer.diast.analysis;


import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.printer.Printable;

public class InjectionExpr extends AnnotationExpr 
{
	
	public enum Qualifier implements Printable {

        INJECT("@Inject"),
        AUTOWIRED("@Autowired");

        private final String codeRepresentation;

        Qualifier(String codeRepresentation) {
            this.codeRepresentation = codeRepresentation;
        }

        public String asString() {
            return codeRepresentation;
        }
    }

	@Override
	public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <A> void accept(VoidVisitor<A> v, A arg) {
		// TODO Auto-generated method stub
		
	}

	
	

}
