package br.com.user.management.chains;

import javax.enterprise.util.AnnotationLiteral;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
public class ChainQualifier extends AnnotationLiteral<Chain> implements Chain {
    private String name;

    public ChainQualifier(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }
}
