package il.org.spartan.ispartanizer.auxilary_layer;

import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO @orenafek comment this class
 * @author Oren Afek
 * @since 01-12-2016
 */
public enum step {
    ;

    public static List<PsiParameter> parameters(PsiMethod ¢) {
        return ¢ == null ? new ArrayList<>() : parameters(¢.getParameterList());
    }

    public static List<PsiParameter> parameters(PsiParameterList method) {
        return method == null ? new ArrayList<>() : Arrays.asList(method.getParameters());
    }

    public static PsiParameter firstParameter(PsiParameterList ¢) {
        return parameters(¢).isEmpty() ? null : ¢.getParameters()[0];
    }

    public static PsiParameter firstParameter(PsiMethod ¢) {
        return ¢ == null ? null : firstParameter(¢.getParameterList());
    }

    public static PsiExpression firstParamterExpression(PsiMethodCallExpression ¢) {
        return ¢ == null || ¢.getArgumentList().getExpressions().length < 1 ? null : ¢.getArgumentList().getExpressions()[0];
    }

    public static PsiStatement firstStatement(PsiCodeBlock ¢) {
        return ¢ == null || statements(¢) == null || statements(¢).isEmpty() ? null : statements(¢).get(0);
    }

    public static String name(PsiNamedElement ¢) {
        return ¢ == null ? null : ¢.getName();
    }

    public static List<PsiStatement> statements(PsiCodeBlock ¢) {
        return ¢ == null ? new ArrayList<>() : Arrays.asList(¢.getStatements());
    }

    public static PsiCodeBlock blockBody(PsiLambdaExpression ¢) {
        return !iz.block(¢.getBody()) ? null : (PsiCodeBlock) ¢.getBody();
    }

    public static PsiExpression expression(PsiReturnStatement ¢) {
        return ¢ == null ? null : ¢.getReturnValue();
    }

    public static PsiExpression expression(PsiExpressionStatement ¢) {
        return ¢ == null ? null : ¢.getExpression();
    }

    public static PsiType returnType(PsiMethod ¢) {
        return ¢ == null ? null : ¢.getReturnType();
    }

    public static List<PsiField> fields(PsiClass clazz) {
        return Arrays.asList(clazz.getFields());
    }


    public static PsiExpression conditionExpression(PsiConditionalExpression ¢) {
        return ¢ == null ? null : ¢.getCondition();
    }

    public static IElementType operator(PsiBinaryExpression ¢) {
        return ¢ == null ? null : ¢.getOperationTokenType();
    }

    public static PsiExpression leftOperand(PsiBinaryExpression ¢) {

        return ¢ == null ? null : ¢.getLOperand();
    }

    public static PsiExpression rightOperand(PsiBinaryExpression ¢) {
        return ¢ == null ? null : ¢.getROperand();
    }

    public static PsiExpression thenExpression(PsiConditionalExpression ¢) {
        return ¢ == null ? null : ¢.getThenExpression();
    }

    public static PsiExpression elseExpression(PsiConditionalExpression ¢) {
        return ¢ == null ? null : ¢.getElseExpression();
    }

    public static PsiElement nextSibling(PsiElement ¢) {
        PsiElement $ = ¢.getNextSibling();
        while ($ != null && iz.whiteSpace($))
            $ = $.getNextSibling();
        return $;
    }

    @NotNull
    public static String docCommentString(@NotNull PsiJavaDocumentedElement e) {
        PsiDocComment doc = e.getDocComment();
        return doc == null ? "" : doc.getText().substring(3, doc.getText().length() - 2);
    }
}
