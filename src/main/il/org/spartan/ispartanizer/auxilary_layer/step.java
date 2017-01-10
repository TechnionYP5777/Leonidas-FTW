package il.org.spartan.ispartanizer.auxilary_layer;

import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

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

    public static PsiExpression firstParamterExpression(PsiMethodCallExpression expression) {
        return expression != null && expression.getArgumentList().getExpressions().length >= 1
                ? expression.getArgumentList().getExpressions()[0] : null;
    }

    public static PsiStatement firstStatement(PsiCodeBlock block) {
        return block != null && statements(block) != null && statements(block).size() >= 1 ?
                statements(block).get(0) : null;
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

    public static PsiExpression expression(PsiExpressionStatement s) {
        return s != null ? s.getExpression() : null;
    }

    public static PsiType returnType(PsiMethod m) {
        return m != null ? m.getReturnType() : null;
    }

    public static List<PsiField> fields(PsiClass clazz) {
        return Arrays.asList(clazz.getFields());
    }


    public static PsiExpression conditionExpression(PsiConditionalExpression expr) {
        return expr != null ? expr.getCondition() : null;
    }

    public static IElementType operator(PsiBinaryExpression expr) {
        return expr != null ? expr.getOperationTokenType() : null;
    }

    public static PsiExpression leftOperand(PsiBinaryExpression expr) {

        return expr != null ? expr.getLOperand() : null;
    }

    public static PsiExpression rightOperand(PsiBinaryExpression expr) {
        return expr != null ? expr.getROperand() : null;
    }

    public static PsiExpression thenExpression(PsiConditionalExpression expr) {
        return expr != null ? expr.getThenExpression() : null;
    }

    public static PsiExpression elseExpression(PsiConditionalExpression expr) {
        return expr != null ? expr.getElseExpression() : null;
    }

    public static PsiElement nextSibling(PsiElement e) {
        PsiElement b = e.getNextSibling();
        while (b != null && iz.whiteSpace(b)) {
            b = b.getNextSibling();
        }
        return b;
    }

    public static
    @NotNull
    String docCommentString(@NotNull PsiJavaDocumentedElement e) {
        PsiDocComment doc = e.getDocComment();
        if (doc == null) {
            return "";
        }
        return doc.getText().substring(3, doc.getText().length() - 2);
    }
}
