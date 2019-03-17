package br.pucrio.inf.les.ese.dianalyzer.diast_test.annotation;

import br.pucrio.inf.les.ese.dianalyzer.diast.practices.AbstractPractice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BadPracticeApplied {

    Class<? extends AbstractPractice> value();

}
