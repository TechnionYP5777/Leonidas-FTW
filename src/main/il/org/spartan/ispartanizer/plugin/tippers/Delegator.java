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
    protected boolean prerequisites(PsiMethod psiMethod) {
        return delegation(psiMethod);
    }

    protected boolean hasOneStatement(PsiCodeBlock codeBlock) {
        return codeBlock.getStatements().length == 1;
    }

    protected boolean hasOnlyMethodCall(PsiCodeBlock codeBlock) {
        return getMethodCallExpression(codeBlock) != null;
    }

    protected PsiMethodCallExpression getMethodCallExpression(PsiCodeBlock codeBlock) {
        if (!hasOneStatement(codeBlock)) {
            return null;
        }
        PsiStatement statement = codeBlock.getStatements()[0];
        if (iz.returnStatement(statement) && iz.methodCallExpression(az.returnStatement(statement).getReturnValue())) {
            return az.methodCallExpression(az.returnStatement(statement).getReturnValue());
        }
        if (iz.expressionStatement(statement) && iz.methodCallExpression(az.expressionStatement(statement).getExpression())) {
            return az.methodCallExpression(az.expressionStatement(statement).getExpression());
        }
        return null;
    }

    protected boolean argumentsParametersMatch(PsiParameterList parameterList, PsiExpressionList argumentList) {
        for (PsiExpression expression : argumentList.getExpressions()) {
            if (iz.literal(expression)) {
                continue;
            }
            if (!iz.referenceExpression(expression)) {
                return false;
            }
            PsiElement c = az.referenceExpression(expression).getLastChild();
            if (!iz.identifier(c)) {
                return false;
            }
            PsiIdentifier identifier = az.identifier(c);
            boolean foundMatchingParameter = false;
            for (PsiParameter parameter : parameterList.getParameters()) {
                if (parameter.getNameIdentifier().getText().equals(identifier.getText())) {
                    foundMatchingParameter = true;
                    break;
                }
            }
            if (!foundMatchingParameter) {
                return false;
            }
        }
        return true;
    }

    protected boolean delegation(PsiMethod psiMethod) {
        if (!hasOnlyMethodCall(psiMethod.getBody())) {
            return false;
        }
        PsiMethodCallExpression methodCallExpression = getMethodCallExpression(psiMethod.getBody());
        if (!argumentsParametersMatch(psiMethod.getParameterList(), methodCallExpression.getArgumentList())) {
            return false;
        }
        PsiReferenceExpression referenceExpression = methodCallExpression.getMethodExpression();
        return referenceExpression.getQualifierExpression() == null;
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
