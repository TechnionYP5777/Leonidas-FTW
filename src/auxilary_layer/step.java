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

    public static List<PsiStatement> statements(PsiCodeBlock block) {
        return block != null ? Arrays.asList(block.getStatements()) : new ArrayList<>();
    }

    public static PsiCodeBlock blockBody(PsiLambdaExpression lambdaExpression) {
        return iz.block(lambdaExpression.getBody()) ? (PsiCodeBlock) lambdaExpression.getBody() : null;
    }

    public static PsiExpression expression(PsiReturnStatement s) {
        return s != null ? s.getReturnValue() : null;
    }


}
