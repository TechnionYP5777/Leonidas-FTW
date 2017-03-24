package il.org.spartan.ispartanizer.auxilary_layer;

import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiClassImpl;
import com.intellij.psi.impl.source.PsiEnumConstantImpl;
import com.intellij.psi.impl.source.PsiFieldImpl;
import com.intellij.psi.impl.source.PsiTypeElementImpl;
import com.intellij.psi.impl.source.tree.java.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.tree.IElementType;
import il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiElementStub.StubName;
import il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiTypes.GenericPsi;
import il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiTypes.GenericPsiBlock;
import il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiTypes.GenericPsiExpression;
import il.org.spartan.ispartanizer.plugin.leonidas.GenericPsiTypes.GenericPsiStatement;

import java.util.Arrays;

import static com.intellij.psi.PsiModifier.PUBLIC;
import static com.intellij.psi.PsiModifier.STATIC;

/**
 * Utils class that helps to check if a Psi element iz of a specific type
 *
 * @author Oren Afek
 * @since 01-12-2016
 */
public enum iz {
    ;
    private static final String ABSTRACT = "abstract";

    private static boolean typeCheck(Class<? extends PsiElement> type, PsiElement element) {
        return element != null && type.isAssignableFrom(element.getClass());
    }

    public static boolean null$(PsiElement element) {
        return iz.literal(element) && az.literal(element).textMatches("null");
    }

    public static boolean notNull(PsiElement element) {
        return !null$(element);
    }

    public static boolean statement(PsiElement element) {
        return typeCheck(PsiStatement.class, element);
    }

    public static boolean block(PsiElement element) {
        return typeCheck(PsiCodeBlock.class, element);
    }

    public static boolean genereicBlock(PsiElement element) {
        return typeCheck(GenericPsiBlock.class, element);
    }

    public static boolean blockStatement(PsiElement element) {
        return typeCheck(PsiBlockStatement.class, element);
    }

    public static boolean methodCallExpression(PsiElement element) {
        return typeCheck(PsiMethodCallExpression.class, element);
    }

    public static boolean declarationStatement(PsiElement element) {
        return typeCheck(PsiDeclarationStatement.class, element);
    }

    public static boolean enumConstant(PsiElement element) {
        return typeCheck(PsiEnumConstantImpl.class, element);
    }

    public static boolean fieldDeclaration(PsiElement element) {
        return typeCheck(PsiFieldImpl.class, element);
    }

    public static boolean abstract$(PsiMethod element) {
        return element.getModifierList().hasExplicitModifier(ABSTRACT);
    }

    public static boolean static$(PsiMethod element) {
        return element.getModifierList().hasExplicitModifier(STATIC);
    }

    public static boolean singleParameterMethod(PsiMethod element) {
        return element.getParameterList().getParameters().length == 1;
    }

    public static boolean method(PsiElement element) {
        return typeCheck(PsiMethod.class, element);
    }

    public static boolean void$(PsiMethod element) {
        return PsiType.VOID.equals(step.returnType(element));
    }

    public static boolean public$(PsiMethod element) {
        return element != null && element.getModifierList().hasExplicitModifier(PUBLIC);
    }

    public static boolean main(PsiMethod element) {
        return element != null &&
                public$(element) &&
                static$(element) &&
                "main".equals(element.getName()) &&
                iz.singleParameterMethod(element) &&
                "args".equals(step.name(step.firstParameter(element)));
    }

    public static boolean returnStatement(PsiElement element) {
        return typeCheck(PsiReturnStatementImpl.class, element);
    }

    public static boolean type(PsiElement element) {
        return typeCheck(PsiTypeElementImpl.class, element);
    }

    public static boolean expressionStatement(PsiElement element) {
        return typeCheck(PsiExpressionStatementImpl.class, element);
    }

    public static boolean identifier(PsiElement element) {
        return typeCheck(PsiIdentifier.class, element);
    }

    public static boolean conditionalExpression(PsiElement element) {
        return typeCheck(PsiConditionalExpressionImpl.class, element);
    }

    public static boolean nullExpression(PsiExpression e) {
        return e.getText().equals("null");
    }

    public static boolean binaryExpression(PsiElement element) {
        return typeCheck(PsiBinaryExpressionImpl.class, element);
    }

    public static boolean referenceExpression(PsiElement element) {
        return typeCheck(PsiReferenceExpressionImpl.class, element);
    }

    public static boolean equalsOperator(IElementType operator) {
        return operator != null && operator.equals(JavaTokenType.EQEQ);
    }

    public static boolean notEqualsOperator(IElementType operator) {
        return operator != null && operator.equals(JavaTokenType.NE);
    }

    public static boolean literal(PsiElement element) {
        return typeCheck(PsiLiteral.class, element);
    }

    public static boolean classDeclaration(PsiElement element) {
        return typeCheck(PsiClassImpl.class, element);
    }

    public static boolean forStatement(PsiElement element) {
        return typeCheck(PsiForStatement.class, element);
    }

    public static boolean forEachStatement(PsiElement element) {
        return typeCheck(PsiForeachStatement.class, element);
    }

    public static boolean ifStatement(PsiElement element) {
        return typeCheck(PsiIfStatement.class, element);
    }

    public static boolean importList(PsiElement element) {
        return typeCheck(PsiImportList.class, element);
    }

    public static boolean javaToken(PsiElement element) {
        return typeCheck(PsiJavaToken.class, element);
    }

    public static boolean expression(PsiElement element) {
        return typeCheck(PsiExpression.class, element);
    }

    /**
     * @return true if e1 is of e2's type or inherits from it
     */
    public static boolean theSameType(PsiElement e1, PsiElement e2) {
        return typeCheck(e2.getClass(), e1);
    }


    private static boolean literalConforms(PsiElement e1, PsiElement e2) {
        return (iz.literal(e1) && iz.literal(e2) && !iz.generic(e2) && az.literal(e1).getText().equals(az.literal(e2).getText()));
    }

    private static boolean tokenConforms(PsiElement e1, PsiElement e2) {
        return (iz.javaToken(e1) && iz.javaToken(e2) && !iz.generic(e2) && az.javaToken(e1).getText().equals(az.javaToken(e2).getText()));
    }

    private static boolean genericConforms(PsiElement e1, PsiElement e2) {
        return iz.generic(e2) && az.generic(e2).generalizes(e1);
    }

    private static boolean elseConforms(PsiElement e1, PsiElement e2) {
        return !iz.generic(e2) && !iz.literal(e1) && !iz.literal(e2) && !iz.javaToken(e1) && !iz.javaToken(e2) && iz.theSameType(e1, e2);
    }

    /**
     * e2 is the generic tree
     */
    public static boolean conforms(PsiElement e1, PsiElement e2) {
        return literalConforms(e1, e2) || tokenConforms(e1, e2) || genericConforms(e1, e2) || elseConforms(e1, e2);
    }

    public static boolean whiteSpace(PsiElement e) {
        return typeCheck(PsiWhiteSpace.class, e);
    }

    public static boolean ofType(PsiElement e, Class<? extends PsiElement> type) {
        return typeCheck(type, e);
    }

    public static boolean stubMethodCall(PsiElement element) {
        return iz.methodCallExpression(element) &&
                Arrays.stream(StubName.values())
                        .map(StubName::stubName)
                        .anyMatch(sn ->
                                sn.equals(az.methodCallExpression(element).getMethodExpression().getText()));
    }

    public static boolean documentedElement(PsiElement e) {
        return typeCheck(PsiJavaDocumentedElement.class, e);
    }

    public static boolean javadoc(PsiElement e) {
        return typeCheck(PsiDocComment.class, e);
    }

    public static boolean generic(PsiElement e) {
        return typeCheck(GenericPsi.class, e);
    }

    public static boolean genericExpression(PsiElement e) {
        return typeCheck(GenericPsiExpression.class, e);
    }

    public static boolean genericStatement(PsiElement e) {
        return typeCheck(GenericPsiStatement.class, e);
    }

    public static boolean genericBlock(PsiElement e) {
        return typeCheck(GenericPsiBlock.class, e);
    }

    public static boolean whileStatement(PsiElement e) {
        return typeCheck(PsiWhileStatement.class, e);
    }

    public static boolean switchStatement(PsiElement e) {
        return typeCheck(PsiSwitchStatement.class, e);
    }

    public static boolean loopStatement(PsiElement e) {
        return typeCheck(PsiLoopStatement.class, e);
    }

    public static boolean tryStatement(PsiElement e) {
        return typeCheck(PsiTryStatement.class, e);
    }

    public static boolean enclosingStatement(PsiElement e) {
        return ifStatement(e) || switchStatement(e) || loopStatement(e) || tryStatement(e);
    }

    public static boolean synchronized¢(final PsiMember ¢) {
        return (¢.getModifierList().hasModifierProperty(PsiModifier.SYNCHRONIZED));
    }

    public static boolean native¢(final PsiMember ¢) {
        return (¢.getModifierList().hasModifierProperty(PsiModifier.NATIVE));
    }

    public static boolean default¢(final PsiMember ¢) {
        return (¢.getModifierList().hasModifierProperty(PsiModifier.DEFAULT));
    }


    public static boolean annotation(final PsiElement ¢) {
        return typeCheck(PsiAnnotation.class, ¢);
    }

    public static boolean anonymousClassDeclaration(final PsiElement ¢) {
        return typeCheck(PsiAnonymousClass.class, ¢);
    }

    public static boolean arrayInitializer(final PsiElement ¢) {
        return typeCheck(PsiArrayInitializerExpression.class, ¢);
    }

    /**
     * @param ¢ pattern the statement or block to check if it is an assignment
     * @return <code><b>true</b></code> if the parameter an assignment or false if
     * the parameter not or if the block Contains more than one
     * statement
     */
    public static boolean assignment(final PsiElement ¢) {
        return typeCheck(PsiAssignmentExpression.class, ¢);
    }

    /**
     * Determine whether a node is a boolean literal
     *
     * @param ¢ pattern JD
     * @return <code><b>true</b></code> <i>iff</i> the parameter is a boolean
     * literal
     */
    public static boolean booleanLiteral(final PsiElement ¢) {
        return typeCheck(PsiLiteralExpression.class, ¢) && ((PsiLiteralExpression) ¢).getValue() instanceof Boolean;
    }

    /**
     * @param ¢ JD
     * @return
     */
    public static boolean booleanType(final PsiType ¢) {
        return ¢ != null && ¢ instanceof PsiPrimitiveType && ¢.getPresentableText().equals(PsiType.BOOLEAN.getPresentableText());
    }

    public static boolean breakStatement(final PsiElement ¢) {
        return typeCheck(PsiBreakStatement.class, ¢);
    }

    /**
     * @param ¢ JD
     * @return
     */
    public static boolean castExpression(final PsiElement ¢) {
        return typeCheck(PsiTypeCastExpression.class, ¢);
    }

    /**
     * @param ¢
     * @return
     */
    public static boolean classInstanceCreation(final PsiElement ¢) {
        return typeCheck(PsiNewExpression.class, ¢);
    }

    /**
     * Check whether an expression is a "conditional and" (&&)
     *
     * @param ¢ JD
     * @return <code><b>true</b></code> <i>iff</i> the parameter is an expression
     * whose operator is AND
     */
    public static boolean conditionalAnd(final PsiBinaryExpression ¢) {
        return ¢.getOperationSign().getTokenType().equals(JavaTokenType.ANDAND);
    }

    /**
     * Check whether an expression is a "conditional or" (||)
     *
     * @param ¢ JD
     * @return <code><b>true</b></code> <i>iff</i> the parameter is an expression
     * whose operator is OR
     */
    public static boolean conditionalOr(final PsiBinaryExpression ¢) {
        return ¢.getOperationSign().getTokenType().equals(JavaTokenType.OROR);
    }

    /**
     * Determine whether a node is an EmpyStatement
     *
     * @param ¢ pattern JD
     * @return <code><b>true</b></code> <i>iff</i> the parameter is an
     * empty statment
     */
    static boolean emptyStatement(final PsiElement ¢) {
        return typeCheck(PsiEmptyStatement.class, ¢);
    }


    static boolean enumDeclaration(final PsiElement ¢) {
        return typeCheck(PsiClass.class, ¢) && (az.classDeclaration(¢).isEnum());
    }

    static boolean fieldDeclaration(final PsiMember ¢) {
        return typeCheck(PsiField.class, ¢);
    }

    /**
     * Determine whether a declaration is final or not
     *
     * @param ¢ JD
     * @return <code><b>true</b></code> <em>iff</em>declaration is final
     */
    static boolean final¢(final PsiMember ¢) {
        return (¢.getModifierList().hasModifierProperty(PsiModifier.FINAL));
    }

    /**
     * Determine whether a variable declaration is final or not
     *
     * @param ¢ JD
     * @return <code><b>true</b></code> <i>iff</i> the variable is declared as
     * final
     */
    static boolean final¢(final PsiVariable ¢) {
        return ¢ != null && ¢.getModifierList().hasModifierProperty(PsiModifier.FINAL);
    }

    /**
     * @param ¢ JD
     * @return <code><b>true</b></code> <em>iff</em>the given node is an interface
     * or false otherwise
     */
    static boolean interface¢(final PsiElement ¢) {
        return typeCheck(PsiClass.class, ¢) && (az.classDeclaration(¢).isInterface());
    }

    static boolean variable(PsiElement element) {
        return typeCheck(PsiVariable.class, element);
    }
}
