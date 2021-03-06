package br.pucrio.inf.les.ese.dianalyzer.diast.logic;

import br.pucrio.inf.les.ese.dianalyzer.diast.identification.ConstructorInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.FieldDeclarationInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.MethodInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.identification.SetMethodInjectionIdentificator;
import br.pucrio.inf.les.ese.dianalyzer.diast.model.AbstractElement;
import com.github.javaparser.ast.CompilationUnit;

import java.util.List;

public class InjectionBusiness {

    public static List<AbstractElement> getInjectedElementsFromClass(CompilationUnit cu){

        FieldDeclarationInjectionIdentificator fieldId = new FieldDeclarationInjectionIdentificator();
        ConstructorInjectionIdentificator constructorId = new ConstructorInjectionIdentificator();
        MethodInjectionIdentificator methodId = new MethodInjectionIdentificator();
        SetMethodInjectionIdentificator setMethodId = new SetMethodInjectionIdentificator();

        List<AbstractElement> elements = fieldId.identify( cu );
        elements.addAll(constructorId.identify( cu ) );
        elements.addAll(methodId.identify( cu ) );
        elements.addAll(setMethodId.identify( cu ) );

        return  elements;

    }



}
