package auxilary_layer;

import com.intellij.psi.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Oren Afek
 * @since 2016.12.1
 */

public enum step {
    ;

    public static List<PsiParameter> parameters(PsiMethod method) {
        return method != null ? parameters(method.getParameterList()) : new ArrayList<>();
    }

    public static List<PsiParameter> parameters(PsiParameterList method) {
        return method != null ? Arrays.asList(method.getParameters()) : new ArrayList<>();
    }

    public static PsiParameter firstParameter(PsiParameterList parameterList) {
        return !parameters(parameterList).isEmpty() ? parameterList.getParameters()[0] : null;
    }

    public static PsiParameter firstParameter(PsiMethod method) {
        return method != null ? firstParameter(method.getParameterList()) : null;
    }

    public static String name(PsiNamedElement element) {
        return element != null ? element.getName() : null;
    }

    public static List<PsiStatement> statements(PsiCodeBlock block) {
        return block != null ? Arrays.asList(block.getStatements()) : new ArrayList<>();
    }

    public static PsiCodeBlock blockBody(PsiLambdaExpression lambdaExpression) {
        return iz.block(lambdaExpression.getBody()) ? (PsiCodeBlock) lambdaExpression.getBody() : null;
    }

    public static PsiExpression expression(PsiReturnStatement s) {
        return s != null ? s.getReturnValue() : null;
    }

    public static PsiType returnType(PsiMethod m) {
        return m != null ? m.getReturnType() : null;
    }

    public static List<PsiField> fields(PsiClass clazz) {
        return Arrays.asList(clazz.getFields());
    }


}
