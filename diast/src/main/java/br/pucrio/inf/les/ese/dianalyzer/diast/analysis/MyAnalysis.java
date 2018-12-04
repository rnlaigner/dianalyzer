package br.pucrio.inf.les.ese.dianalyzer.diast.analysis;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeEight;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeEleven;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeSeven;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeTen;
import br.pucrio.inf.les.ese.dianalyzer.diast.practices.BadPracticeThree;

/**
 * Some code that uses JavaSymbolSolver.
 */
@SuppressWarnings("rawtypes")
public class MyAnalysis extends VoidVisitorAdapter
{
	
	private List<String> injectAnnotations = Arrays.asList( "Inject", "Autowired" );
	
	private Map<String,Integer> map = new HashMap<String,Integer>();

    @SuppressWarnings("unchecked")
	public void execute(String[] args) 
    {
        // Set up a minimal type solver that only looks at the classes used to run this sample.
        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        combinedTypeSolver.add(new ReflectionTypeSolver());

        // Configure JavaParser to use type resolution
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedTypeSolver);
        JavaParser.getStaticConfiguration().setSymbolResolver(symbolSolver);
        
        //String exampleClass = "class X { int x() { return 1 + 1.0 - 5; } }";
        
        String exampleClass1 = "class BusinessExample { "
        							+ "private IDAO exampleDAO; "
        							+ "private IDAO genericDAO; "
        							+ "@Inject "
									+ "public void setExampleDAO(ExampleDAO exampleDAO) { "
									+ "this.genericDAO = exampleDAO; "
									+ "this.exampleDAO = exampleDAO; "
									+ "} "
        							+ "@Inject IExample1 one;"
        							+ "@Autowired "
        							+ "private ABusiness business; "
        							+ "@Autowired "
        							+ "private AnotherBusiness business1; "
        							+ "private AThirdBusiness business2; "
        							+ "@Autowired "
        							+ "private AFifthBusiness business4; "
        							+ "@Autowired "
        							+ "public BusinessExample( AThirdBusiness business2 ){"
        							+ "	this.business2 = business2; "
        							+ "}"
        							+ "public void execute() { "
        							+ "	business.execute(business4); "
        							+ "	business1.execute(); "
        							+ "} "
        							+ " "
        							+ "public ExampleBean containerCall() {"
        							+ "	ApplicationContext ctx = ApplicationContextHolder.getApplicationContext(); "
        							+ " return (ExampleBean) ctx.getBean(\" ExampleBean \");"
        							+ "} "
        							+ " "
        							+ "@Produces "
        							+ "public AFourthBusiness getProducerMethod() { "
        							+ "	String variab; "
        							+ " return new AFourthBusiness(); "
        							+ "} "
        							+ "public AFifthBusiness getAFifthBusiness(){"
        							+ " return business4; "
        							+ "} "
        							+ "public void setOne(IExample1 one) { "
									+ "this.one = one; "
									+ "} "
        							+ "} ";
        
        //ClassOrInterfaceDeclaration classDeclaration = new ClassOrInterfaceDeclaration();
        
        /*
        String exampleClass2 = "class BusinessExample { private "
				+ "@Autowired "
				+ "AnotherBusiness business; }"; 
        */
        // Parse some code
        CompilationUnit cu;
        
        try 
        {
        	cu = JavaParser.parse(exampleClass1);
        }
        catch(Exception e)
        {
        	System.out.println("Excecao");
        	System.out.println(e.getMessage());
        	return;
        }
        

        
        //BadPracticeOne bdOne = new BadPracticeOne(cu);
        //BadPracticeThree bdTwo = new BadPracticeThree(cu);
        //BadPracticeSeven bdSeven = new BadPracticeSeven(cu);
        //BadPracticeEight bdEight = new BadPracticeEight(cu);
        //BadPracticeTen bdTen = new BadPracticeTen(cu);
        BadPracticeEleven bdEleven = new BadPracticeEleven(cu);
        
        //bdOne.process();
        //bdTwo.process();
        //bdSeven.process();
        //bdEight.process();
        //bdTen.process();
        bdEleven.process();

        // Find all the calculations with two sides:
        cu.findAll(AnnotationExpr.class).forEach(be -> {
            // Find out what type it has:
            //ResolvedType resolvedType = be.calculateResolvedType();
        	
        	//AnnotationExpr resolvedType = be.asAnnotationExpr();
        	
        	//Optional<Node> parent = be.getParentNode();
        	
        	//parent.get().findAll(FieldDeclaration.class).forEach(ac -> { ac.asFieldDeclaration().toString(); });
        	
        	//System.out.println( parent.get().toString() );

            // Show that it's "double" in every case:
            //System.out.println(be.toString() + " is a: " + resolvedType.getNameAsString());
        });
        
        // Find all the calculations with two sides:
        /*
        cu.findAll(FieldDeclaration.class).stream()
                .filter(f -> f.isAnnotationPresent("Autowired"))
                .forEach(f -> System.out.println( f.getElementType().toString() + " " + f.getVariable(0).toString() ) );
        */
        
        List<String> injectedVariables = new ArrayList<String>();
        
        cu.findAll(FieldDeclaration.class).stream()
        		.filter(f -> containsInjectionAnnotation(f))
        		.forEach(f -> {
        		
        		//System.out.println( f.getVariable(0).toString() );
        		//Store fields in a list, in order to find its occurrence later
        		injectedVariables.add( f.getVariable(0).toString() );
        });
        
        //fazer a classe myanalysis estender voidvisitoradapter e criar map com os atributos que sao injetados
        //new MethodVisitor().visit(cu, injectedVariables);
        
        //Init map
        for (String variable : injectedVariables)
        {
        	map.put(variable, 0);
        }
        
        visit(cu, injectedVariables);
        
        //Iterate through map
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            //it.remove(); // avoids a ConcurrentModificationException
        }


    }
    
    private boolean containsInjectionAnnotation(NodeWithAnnotations node)
    {
    	List<String> annotations = new ArrayList<String>();
    	
    	@SuppressWarnings("unchecked")
		NodeList<AnnotationExpr> nodeAnnotations = node.getAnnotations();
    	
    	nodeAnnotations.forEach(action -> { annotations.add( action.getNameAsString() ); });
    	
    	annotations.retainAll(getInjectAnnotations());
    	
    	if (annotations.size() > 0) {
    		return true;
    	}
    	return false;
    }

    @SuppressWarnings("unchecked")
	@Override
    public void visit(MethodCallExpr methodCall, Object arg)
    {
        //System.out.print("Method call: " + methodCall.getName() + "\n");
        List<Expression> args = methodCall.getArguments();
        if (args != null)
        {
        	if (args instanceof List)
        	{
        		Integer numberOfAppearances = 
        				handleInjectedVariables( methodCall, (List<String>) arg );
        		//System.out.println( methodCall.getName() + " \n" );
        		//System.out.println(numberOfAppearances);
        		
        		String nodeName = methodCall.getChildNodes().get(0).toString();
        		
        		map.put(nodeName, numberOfAppearances);
        	}
        	else
        	{
        		//handleExpressions(args);
        	}
        	
        }
            
    }
    
    private int handleInjectedVariables(MethodCallExpr methodCall, List<String> variables)
    {
    	int i = 0;
    	//Optional<Node> node = methodCall.getParentNode();
    	String nodeName = methodCall.getChildNodes().get(0).toString();
    	
    	//System.out.println(node.get().getParentNode().get().toString());
    	
    	/*
    	System.out.println( methodCall.getName() + " \n" 
    						//+ methodCall.getNameAsString() + " \n"
    						//+ methodCall.findRootNode().toString() + " "
    						+ methodCall.getParentNodeForChildren().toString() + " \n"
    						//+ methodCall.get
    					   );
    	*/
    	
    	//System.out.println(nodeName);
    	
        for (String variable : variables)
        {
        	if(variable.equals(nodeName))
        	{
        		i++;
        	}
            
        }
        return i;
    }
    
	public List<String> getInjectAnnotations() {
		return injectAnnotations;
	}


	public void setInjectAnnotations(List<String> injectAnnotations) {
		this.injectAnnotations = injectAnnotations;
	}

	public Map<String,Integer> getMap() {
		return map;
	}

	public void setMap(Map<String,Integer> map) {
		this.map = map;
	}
}
