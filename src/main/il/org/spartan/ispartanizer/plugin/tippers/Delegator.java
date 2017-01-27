package il.org.spartan.ispartanizer.plugin.tippers;

import com.intellij.psi.*;
import il.org.spartan.ispartanizer.auxilary_layer.az;
import il.org.spartan.ispartanizer.auxilary_layer.iz;
import il.org.spartan.ispartanizer.plugin.tipping.Tip;

/**
 * @author RoeiRaz
 * @since 03-01-2017
 */
public class Delegator extends JavadocMarkerNanoPattern {
    @Override
    protected boolean prerequisites(PsiMethod ¢) {
        return delegation(¢);
    }

    protected boolean hasOneStatement(PsiCodeBlock ¢) {
        return (¢ != null) && ¢.getStatements().length == 1;
    }

    protected boolean hasOnlyMethodCall(PsiCodeBlock ¢) {
        return getMethodCallExpression(¢) != null;
    }

    protected PsiMethodCallExpression getMethodCallExpression(PsiCodeBlock b) {
        if (!hasOneStatement(b))
            return null;
        PsiStatement statement = b.getStatements()[0];
        return iz.returnStatement(statement) && iz.methodCallExpression(az.returnStatement(statement).getReturnValue())
                ? az.methodCallExpression(az.returnStatement(statement).getReturnValue())
                : iz.expressionStatement(statement)
                && iz.methodCallExpression(az.expressionStatement(statement).getExpression())
                ? az.methodCallExpression(az.expressionStatement(statement).getExpression()) : null;
    }

    protected boolean argumentsParametersMatch(PsiParameterList l, PsiExpressionList argumentList) {
        for (PsiExpression expression : argumentList.getExpressions()) {
            if (iz.literal(expression))
                continue;
            if (!iz.referenceExpression(expression))
                return false;
            PsiElement c = az.referenceExpression(expression).getLastChild();
            if (!iz.identifier(c))
                return false;
            PsiIdentifier identifier = az.identifier(c);
            boolean foundMatchingParameter = false;
            for (PsiParameter ¢ : l.getParameters())
                if (¢.getNameIdentifier().getText().equals(identifier.getText())) {
                    foundMatchingParameter = true;
                    break;
                }
            if (!foundMatchingParameter)
                return false;
        }
        return true;
    }

    protected boolean delegation(PsiMethod m) {
        if (!hasOnlyMethodCall(m.getBody()))
            return false;
        PsiMethodCallExpression methodCallExpression = getMethodCallExpression(m.getBody());
        return argumentsParametersMatch(m.getParameterList(), methodCallExpression.getArgumentList())
                && methodCallExpression.getMethodExpression().getQualifierExpression() == null;
    }

    @Override
    protected Tip pattern(PsiMethod ¢) {
        return null;
    }

    @Override
    public Class<PsiMethod> getPsiClass() {
        return PsiMethod.class;
    }
}
