package auxilary_layer;

import com.intellij.psi.*;


/** An empty <code><b>interface</b></code> for fluent programming. The name
 * should say it all: The name, followed by a dot, followed by a method name,
 * should read like a sentence phrase.
 * @author Amir Sagiv
 * @since 2016-11-24 */

public interface iz {
    static boolean abstract¢(final PsiMember ¢) {
        return (¢.getModifierList().hasModifierProperty(PsiModifier.ABSTRACT));
    }

    static boolean synchronized¢(final PsiMember ¢) {
        return (¢.getModifierList().hasModifierProperty(PsiModifier.SYNCHRONIZED));
    }

    static boolean native¢(final PsiMember ¢) {
        return (¢.getModifierList().hasModifierProperty(PsiModifier.NATIVE));
    }

    static boolean default¢(final PsiMember ¢) {
        return (¢.getModifierList().hasModifierProperty(PsiModifier.DEFAULT));
    }

    /**
     *
     * @param ¢
     * @return whether the Element is a class, enum, interface or annotation type
     */
    static boolean abstractTypeDeclaration(final PsiElement ¢) {
        return ¢ != null && (¢ instanceof PsiClass);
    }

    static boolean annotation(final PsiElement ¢) {
        return ¢ != null && ¢ instanceof PsiAnnotation;
    }

    static boolean anonymousClassDeclaration(final PsiElement ¢) {
        return ¢ != null && ¢ instanceof PsiAnonymousClass;
    }

    static boolean arrayInitializer(final PsiElement ¢) {
        return ¢ != null && ¢ instanceof PsiArrayInitializerExpression;
    }

    /** @param  ¢
     * pattern the statement or block to check if it is an assignment
     * @return <code><b>true</b></code> if the parameter an assignment or false if
     *         the parameter not or if the block Contains more than one
     *         statement */
    static boolean assignment(final PsiElement ¢) {
        return ¢ != null && ¢ instanceof PsiAssignmentExpression;
    }

    static boolean astNode(final Object ¢) {
        return ¢ != null && ¢ instanceof PsiElement;
    }

    /** Determine whether a node is a Block
     * @param ¢
     * pattern JD
     * @return <code><b>true</b></code> <i>iff</i> the parameter is a block
     *         statement */
    static boolean block(final PsiElement ¢) {
        return ¢ != null && ¢ instanceof PsiBlockStatement;
    }

    /** Determine whether the curly brackets of an {@link IfStatement} are
     * vacuous.
     * @param s JD
     * @return <code><b>true</b></code> <i>iff</i> the curly brackets are
     *         essential */
//    static boolean blockEssential(final PsiIfStatement s) { //TODO: CHECK
//        if (s == null)
//            return false;
//        final PsiBlockStatement b = az.block(step.parent(s));
//        if (b == null)
//            return false;
//        final PsiIfStatement parent = az.ifStatement(step.parent(b));
//        return parent != null && (elze(parent) == null || wizard.recursiveElze(s) == null)
//                && (elze(parent) != null || wizard.recursiveElze(s) != null || blockRequiredInReplacement(parent, s));
//    }

    /** @param ¢
     * subject JD
     * @return <code><b>true</b></code> <em>iff</em>the parameter is an essential
     *         block or false otherwise */
//    static boolean blockEssential(final PsiStatement ¢) {
//        return blockEssential(az.ifStatement(¢));
//    }

    /** @param ¢
     * subject JD
     * @return */
//    static boolean blockRequired(final PsiIfStatement ¢) {
//        return blockRequiredInReplacement(¢, ¢);
//    }

//    static boolean blockRequired(final PsiStatement ¢) {
//        return blockRequired(az.ifStatement(¢));
//    }

//    static boolean blockRequiredInReplacement(final PsiIfStatement old, final PsiIfStatement newIf) {
//        if (newIf == null || old != newIf && elze(old) == null == (elze(newIf) == null))
//            return false;
//        final PsiIfStatement parent = az.ifStatement(step.parent(old));
//        return parent != null && then(parent) == old && (elze(parent) == null || elze(newIf) == null)
//                && (elze(parent) != null || elze(newIf) != null || blockRequiredInReplacement(parent, newIf));
//    }

    static boolean bodyDeclaration(final PsiElement ¢) {
        return ¢ != null && ¢ instanceof PsiMember;
    }

    /** Determine whether a node is a boolean literal
     * @param ¢
     * pattern JD
     * @return <code><b>true</b></code> <i>iff</i> the parameter is a boolean
     *         literal */
    static boolean booleanLiteral(final PsiElement ¢) {
        if(¢ instanceof PsiLiteral){
            if(((PsiLiteral) ¢).getValue() instanceof Boolean){
                return true;
            }
        }
        return false;

    }

    /** @param ¢ node to check
     * @return <code><b>true</b></code> <em>iff</em>the given node is a boolean or
     *         null literal or false otherwise */
    static boolean booleanOrNullLiteral(final PsiElement ¢) {
        if(booleanLiteral(¢)){
            return true;
        }
        if(¢ instanceof PsiPrimitiveType){
            if(((PsiPrimitiveType) ¢).getPresentableText().equals(PsiType.NULL.getPresentableText())){
                return true;
            }
        }
        return false;

    }

    /** @param ¢ JD
     * @return */
    static boolean booleanType(final PsiType ¢) {
        return ¢ != null && ¢ instanceof PsiPrimitiveType && ((PsiPrimitiveType) ¢).getPresentableText().equals(PsiType.BOOLEAN.getPresentableText());
    }

    static boolean breakStatement(final PsiStatement ¢) {
        return ¢ != null && ¢ instanceof PsiBreakStatement;
    }

    /** @param ¢ JD
     * @return */
    static boolean castExpression(final PsiElement ¢) {
        return ¢ != null && ¢ instanceof PsiTypeCastExpression;
    }

    /** @param ¢
     * @return */
    static boolean classInstanceCreation(final PsiElement ¢) {
        return ¢ != null && ¢ instanceof PsiNewExpression;
    }

//    static boolean comparison(final PsiExpression ¢) {
//        return iz.infixExpression(¢) && iz.comparison(az.infixExpression(¢));
//    }

    /** @param ¢ JD
     * @return <code><b>true</b></code> <i>iff</i> the parameter is a comparison
     *         expression. */
//    static boolean comparison(final PsiBinaryExpression ¢) {
//        return ¢ != null && in(¢.getOperationSign(), JavaTokenType.EQ, JavaTokenType.GT,
//                JavaTokenType.GT, JavaTokenType.LT, JavaTokenType.LE, JavaTokenType.NE);
//    }
//
//    static boolean comparison(final JavaTokenType ¢) {
//        return in(¢, JavaTokenType.EQ, JavaTokenType.GT,
//                JavaTokenType.GT, JavaTokenType.LT, JavaTokenType.LE, JavaTokenType.NE);
//    }

    /** Check whether an expression is a "conditional and" (&&)
     * @param ¢ JD
     * @return <code><b>true</b></code> <i>iff</i> the parameter is an expression
     *         whose operator is AND
     */
    static boolean conditionalAnd(final PsiBinaryExpression ¢) {
        return ¢.getOperationSign() == JavaTokenType.AND;
    }

    /** @param xs JD
     * @return <code><b>true</b></code> <i>iff</i> one of the parameters is a
     *         conditional or parenthesized conditional expression */
    static boolean conditionalExpression(final PsiExpression... xs) {
        for (final PsiExpression ¢ : xs)
            if (¢ instanceof PsiConditionalExpression)
                return true;
        return false;
    }
//
//    /** Check whether an expression is a "conditional or" (||)
//     * @param ¢  JD
//     * @return <code><b>true</b></code> <i>iff</i> the parameter is an expression
//     *         whose operator is OR */
//    static boolean conditionalOr(final PsiExpression ¢) {
//        return conditionalOr(az.infixExpression(¢));
//    }

    /** Check whether an expression is a "conditional or" (||)
     * @param ¢ JD
     * @return <code><b>true</b></code> <i>iff</i> the parameter is an expression
     *         whose operator is OR */
    static boolean conditionalOr(final PsiBinaryExpression ¢) {
        return ¢.getOperationSign() == JavaTokenType.OR;
    }

    /** Determine whether a node is a "specific", i.e., <code><b>null</b></code>
     * or <code><b>this</b></code> or literal.
     * @param ¢  JD
     * @return <code><b>true</b></code> <i>iff</i> the parameter is a
     *         "specific" */
//    static boolean constant(final Expression ¢) {
//        return iz.nodeTypeIn(¢, CHARACTER_LITERAL, NUMBER_LITERAL, NULL_LITERAL, THIS_EXPRESSION)
//                || nodeTypeEquals(¢, PREFIX_EXPRESSION) && iz.constant(extract.core(((PrefixExpression) ¢).getOperand()));
//    }

    /** Determine whether an PsiElement contains as a children a
     * continue
     * @param ¢ JD
     * @return <code> true </code> iff ¢ contains any continue statement
     */
//    @SuppressWarnings("boxing") static boolean containsContinueStatement(final ASTNode ¢) {
//        return ¢ != null && new Recurser<>(¢, 0).postVisit((x) -> {
//            return x.getRoot().getNodeType() != ASTNode.CONTINUE_STATEMENT ? x.getCurrent() : x.getCurrent() + 1;
//        }) > 0;
//    }

    static boolean containsOperator(final PsiElement ¢) {
        return ¢ instanceof PsiPrefixExpression || ¢ instanceof PsiPostfixExpression || ¢ instanceof PsiBinaryExpression
                || ¢ instanceof PsiAssignmentExpression;
    }

    /** Check whether the operator of an expression is susceptible for applying
     * one of the two de Morgan laws.
     * @param ¢  InfixExpression
     * @return <code><b>true</b></code> <i>iff</i> the parameter is an operator on
     *         which the de Morgan laws apply. */
    static boolean deMorgan(final PsiBinaryExpression ¢) {
        return ¢ != null && iz.deMorgan(¢.getOperationSign());
    }

    /** Check whether an operator is susceptible for applying one of the two de
     * Morgan laws.
     * @param ¢ o JD
     * @return <code><b>true</b></code> <i>iff</i> the parameter is an operator on
     *         which the de Morgan laws apply. */
    static boolean deMorgan(final PsiJavaToken ¢) {
        return ¢ == JavaTokenType.AND || ¢ == JavaTokenType.OR;
    }

//    static boolean doubleType(final Expression ¢) {
//        return type.of(¢) == DOUBLE;
//    }

    /** Determine whether a node is an EmpyStatement
     * @param  ¢ pattern JD
     * @return <code><b>true</b></code> <i>iff</i> the parameter is an
     *         empty statment*/
    static boolean emptyStatement(final PsiElement ¢) {
        return ¢ != null && ¢ instanceof PsiEmptyStatement;
    }

//    static boolean emptyStringLiteral(final PsiElement ¢) {
//        return emptyStringLiteral(az.stringLiteral(¢));
//    }

    static boolean emptyStringLiteral(final PsiLiteral ¢) {
        String s = (String)¢.getValue();
        return ¢ != null && s.length()==0;
    }

    static boolean enhancedFor(final PsiElement ¢) {
        return ¢ != null && ¢ instanceof PsiForeachStatement;
    }

    static boolean enumConstantDeclaration(final PsiElement ¢) {
        return ¢ != null && ¢ instanceof PsiEnumConstant;
    }

    static boolean enumDeclaration(final PsiElement ¢) {
        return ¢ != null && ¢ instanceof PsiClass && ((PsiClass) ¢).isEnum();
    }

    /** Determine whether a node is an "expression statement"
     * @param ¢ pattern JD
     * @return <code><b>true</b></code> <i>iff</i> the parameter is an
     *        expression statement statement */
    static boolean expression(final PsiElement ¢) {
        return (¢ != null) && (¢ instanceof PsiExpression);
    }

//    static boolean expressionOfEnhancedFor(final PsiElement child, final PsiElement parent) {
//        if (child == null || parent == null || !iz.enhancedFor(parent))
//            return false;
//        final PsiForeachStatement parent1 = az.enhancedFor(parent);
//        assert parent1 != null;
//        assert step.expression(parent1) != null;
//        return step.expression(parent1) == child;
//    }

    /** Determine whether a node is an "expression statement"
     * @param ¢ pattern JD
     * @return <code><b>true</b></code> <i>iff</i> the parameter is an
     *         expression statement */
    static boolean expressionStatement(final PsiElement ¢) {
        return ¢ != null && ¢ instanceof PsiExpressionStatement;
    }

    /** @param ¢ JD
     * @return */
//    static boolean fieldAccess(final PsiExpression ¢) {
//        return iz.nodeTypeEquals(¢, FIELD_ACCESS);
//    }

    static boolean fieldDeclaration(final PsiMember ¢) {
        return ¢ != null && ¢ instanceof PsiField;
    }

    /** Determine whether a declaration is final or not
     * @param ¢ JD
     * @return <code><b>true</b></code> <em>iff</em>declaration is final */
    static boolean final¢(final PsiMember ¢) {
        return (¢.getModifierList().hasModifierProperty(PsiModifier.FINAL));
    }

    /** Determine whether a variable declaration is final or not
     * @param ¢ JD
     * @return <code><b>true</b></code> <i>iff</i> the variable is declared as
     *         final */
    static boolean final¢(final PsiVariable ¢) {
        return ¢ != null && ¢.getModifierList().hasModifierProperty(PsiModifier.FINAL);
    }

    /** @param o The operator to check
     * @return True - if the operator have opposite one in terms of operands
     *         swap. */ //TODO: ASTERISK IS TIMES?
//    static boolean flipable(final PsiJavaToken ¢) {
//        return in(¢, JavaTokenType.AND, JavaTokenType.EQ, JavaTokenType.GT,
//                JavaTokenType.GE, JavaTokenType.LE, JavaTokenType.LT,
//                JavaTokenType.NE, JavaTokenType.OR, JavaTokenType.PLUS,
//                JavaTokenType.ASTERISK, JavaTokenType.XOR, null);
//    }

    /** @param ¢
     *          pattern the statement or block to check if it is an for statement
     * @return <code><b>true</b></code> if the parameter an for statement or false
     *         if the parameter not or if the block Contains more than one
     *         statement */
    static boolean forStatement(final PsiElement ¢) {
        return ¢ != null && ¢ instanceof PsiForStatement;
    }

//    static boolean identifier(final String identifier, final Name typeName) {
//        return typeName.isQualifiedName() ? identifier(identifier, ((QualifiedName) typeName).getName())
//                : iz.simpleName(typeName) && identifier(identifier, az.simpleName(typeName));
//    }

//    static boolean identifier(final String identifier, final SimpleName n) { //TODO: what is simple name psi equivalent?
//        return identifier.equals(n.getIdentifier());
//    }

    static boolean ifStatement(final PsiStatement ¢) {
        return ¢ != null && ¢ instanceof PsiIfStatement;
    }

//    /** @param pattern JD
//     * @return <code><b>true</b></code> <i>iff</i> the node is an Expression
//     *         Statement of type Post or Pre Expression with ++ or -- operator
//     *         false if node is not an Expression Statement or its a Post or Pre
//     *         fix expression that its operator is not ++ or -- */
//    static boolean incrementOrDecrement(final PsiElement ¢) {
//        if (¢ == null)
//            return false;
//        switch (¢.getNodeType()) {
//            case EXPRESSION_STATEMENT:
//                return incrementOrDecrement(step.expression(¢));
//            case POSTFIX_EXPRESSION:
//                return in(az.postfixExpression(¢).getOperator(), PostfixExpression.Operator.INCREMENT, PostfixExpression.Operator.DECREMENT);
//            case PREFIX_EXPRESSION:
//                return in(az.prefixExpression(¢).getOperator(), PrefixExpression.Operator.INCREMENT, PrefixExpression.Operator.DECREMENT);
//            case ASSIGNMENT:
//                return in(az.assignment(¢).getOperator(), PLUS_ASSIGN, MINUS_ASSIGN, TIMES_ASSIGN, DIVIDE_ASSIGN, BIT_AND_ASSIGN, BIT_OR_ASSIGN,
//                        BIT_XOR_ASSIGN, REMAINDER_ASSIGN, LEFT_SHIFT_ASSIGN, RIGHT_SHIFT_SIGNED_ASSIGN, RIGHT_SHIFT_UNSIGNED_ASSIGN);
//            default:
//                return false;
//        }
//    }
//
//    // TODO Yossi: Move to lisp
//    static int index(final int i, final int... is) {
//        for (int $ = 0; $ < is.length; ++$)
//            if (is[$] == i)
//                return $;
//        return -1;
//    }
//
    /** @param ¢ JD
     * @return <code><b>true</b></code> <em>iff</em>the given node is an infix
     *         expression or false otherwise */
    static boolean infix(final PsiElement ¢) {
        return ¢ != null && ¢ instanceof PsiBinaryExpression;
    }

    //    static boolean infixDivide(final PsiExpression ¢) {
//        return operator(az.infixExpression(¢)) == DIVIDE;
//    }
//
    static boolean infixExpression(final PsiElement ¢) {
        return ¢ != null && ¢ instanceof PsiBinaryExpression;
    }
//
//    static boolean infixMinus(final ASTNode ¢) {
//        return operator(az.infixExpression(¢)) == wizard.MINUS2;
//    }
//
//    static boolean infixPlus(final ASTNode ¢) {
//        return operator(az.infixExpression(¢)) == wizard.PLUS2;
//    }
//
//    static boolean infixTimes(final Expression ¢) {
//        return operator(az.infixExpression(¢)) == TIMES;
//    }
//
//    static boolean infixEquals(final Expression ¢) {
//        return operator(az.infixExpression(¢)) == EQUALS;
//    }
//
//    static boolean infixLess(final Expression ¢) {
//        return operator(az.infixExpression(¢)) == LESS;
//    }
//
//    static boolean infixGreater(final Expression ¢) {
//        return operator(az.infixExpression(¢)) == GREATER;
//    }
//
    /** @param ¢ JD
     * @return */
    static boolean instanceofExpression(final PsiExpression ¢) {
        return ¢ != null && ¢ instanceof PsiInstanceOfExpression;
    }

    /** @param ¢ JD
     * @return <code><b>true</b></code> <em>iff</em>the given node is an interface
     *         or false otherwise */
    static boolean interface¢(final PsiElement ¢) {
        return ¢ != null && ¢ instanceof PsiClass && ((PsiClass) ¢).isInterface();
    }
//
//    static boolean intType(final Expression ¢) {
//        return ¢ != null && type.of(¢) == INT;
//    }
//
    /** @param ¢ JD
     * @return <code><b>true</b></code> <em>iff</em>the given node is a method
     *         decleration or false otherwise */
    static boolean isMethodDeclaration(final PsiElement ¢) {
        return ¢ != null && ¢ instanceof PsiMethod;
    }

    /** @param ¢ node to check
     * @return <code><b>true</b></code> <em>iff</em>the given node is a method
     *         invocation or false otherwise */
    static boolean isMethodInvocation(final PsiElement ¢) {
        return ¢ != null && ¢ instanceof PsiMethodCallExpression;
    }

    /** @param ¢ the assignment whose operator we want to check
     * @return <code><b>true</b></code> <em>iff</em> the assignment'¢ operator is
     *         plus assign */
    static boolean isMinusAssignment(final PsiAssignmentExpression ¢) {
        return ¢ != null && ¢.getOperationSign() == JavaTokenType.MINUSEQ;
    }

    static boolean isOneOf(final int i, final int... is) {
        for (final int j : is)
            if (i == j)
                return true;
        return false;
    }
//
//    /** @param a the assignment whose operator we want to check
//     * @return <code><b>true</b></code> <em>iff</em> the assignment'¢ operator is
//     *         assign */
//    static boolean isPlainAssignment(final Assignment ¢) {
//        return ¢ != null && ¢.getOperator() == ASSIGN;
//    }
//
//    /** @param a the assignment whose operator we want to check
//     * @return <code><b>true</b></code> <em>iff</em> the assignment'¢ operator is
//     *         plus assign */
//    static boolean isPlusAssignment(final Assignment ¢) {
//        return ¢ != null && ¢.getOperator() == PLUS_ASSIGN;
//    }
//
//    /** @param ¢ node to check
//     * @return <code><b>true</b></code> <em>iff</em>the given node is a variable
//     *         declaration statement or false otherwise */
//    static boolean isVariableDeclarationStatement(final ASTNode ¢) {
//        return iz.nodeTypeEquals(¢, VARIABLE_DECLARATION_STATEMENT);
//    }
//
//    static iz izParser(final String name) {
//        return new iz() {
//            @Override public String toString() {
//                return name;
//            }
//        };
//    }
//
//    static iz izParser(final Throwable ¢) {
//        return new iz() {
//            @Override public String toString() {
//                return ¢.getStackTrace() + "";
//            }
//        };
//    }
//
//    /** Determine whether an item is the last one in a list
//     * @param tipper a list item
//     * @param ts a list
//     * @return <code><b>true</b></code> <i>iff</i> the item is found in the list
//     *         and it is the last one in it. */
//    static <T> boolean last(final T t, final List<T> ts) {
//        return ts.indexOf(t) == ts.size() - 1;
//    }
//
//    /** Determines whether a statement is last statement in its containing method
//     * @param s JD
//     * @return <code><b>true</b></code> <em>iff</em>the parameter is a statement
//     *         which is last in its method */
//    static boolean lastInMethod(final Statement s) {
//        final Block b = az.block(parent(s));
//        return last(s, statements(b)) && iz.methodDeclaration(parent(b));
//    }
//
//    static boolean leftOfAssignment(final Expression ¢) {
//        return left(az.assignment(¢.getParent())).equals(¢);
//    }
//
//    /** @param pattern Expression node
//     * @return <code><b>true</b></code> <i>iff</i> the Expression is literal */
//    static boolean literal(final ASTNode ¢) {
//        return ¢ != null && intIsIn(¢.getNodeType(), NULL_LITERAL, CHARACTER_LITERAL, NUMBER_LITERAL, STRING_LITERAL, BOOLEAN_LITERAL);
//    }
//
//    static boolean literal(final ASTNode ¢, final boolean b) {
//        return ¢ != null && literal(az.booleanLiteral(¢), b);
//    }
//
//    static boolean literal(final ASTNode ¢, final double d) {
//        final NumberLiteral numberLiteral = az.numberLiteral(¢);
//        if (numberLiteral == null)
//            return false;
//        final String token = numberLiteral.getToken();
//        return NumericLiteralClassifier.of(token) == type.Primitive.Certain.DOUBLE && izParser("Searching for double").parsesTo(token, d);
//    }
//
//    static boolean literal(final ASTNode ¢, final int i) {
//        final NumberLiteral numberLiteral = az.numberLiteral(¢);
//        if (numberLiteral == null)
//            return false;
//        final String token = numberLiteral.getToken();
//        return NumericLiteralClassifier.of(token) == type.Primitive.Certain.INT && izParser("Searching for int").parsesTo(token, i);
//    }
//
//    static boolean literal(final ASTNode ¢, final long l) {
//        final NumberLiteral numberLiteral = az.numberLiteral(¢);
//        if (numberLiteral == null)
//            return false;
//        final String token = numberLiteral.getToken();
//        return NumericLiteralClassifier.of(token) == type.Primitive.Certain.LONG && izParser("Seaching for LONG").parsesTo(token, l);
//    }
//
//    static boolean literal(final BooleanLiteral ¢, final boolean b) {
//        return ¢ != null && ¢.booleanValue() == b;
//    }
//
//    /** @param subject JD
//     * @return <code><b>true</b></code> <i>iff</i> the parameter return a
//     *         literal */
//    static boolean literal(final ReturnStatement ¢) {
//        return ¢ != null && literal(¢.getExpression());
//    }
//
//    static boolean literal(final String literal, final ASTNode ¢) {
//        return literal(literal, az.stringLiteral(¢));
//    }
//
//    static boolean literal(final String literal, final StringLiteral ¢) {
//        return ¢ != null && ¢.getLiteralValue().equals(literal);
//    }
//
//    static boolean literal(final StringLiteral ¢, final String s) {
//        return ¢ != null && ¢.getLiteralValue().equals(s);
//    }
//
//    /** @param ¢ JD
//     * @return <code><b>true</b></code> <em>iff</em>the given node is a literal 0
//     *         or false otherwise */
//    static boolean literal0(final ASTNode ¢) {
//        return literal(¢, 0);
//    }
//
//    /** @param ¢ JD
//     * @return <code><b>true</b></code> <em>iff</em>the given node is a literal 1
//     *         or false otherwise */
//    static boolean literal1(final ASTNode ¢) {
//        return literal(¢, 1);
//    }
//
//    static boolean longType(final Expression ¢) {
//        return type.of(¢) == LONG;
//    }
//
//    static boolean memberRef(final ASTNode ¢) {
//        return iz.nodeTypeEquals(¢, MEMBER_REF);
//    }
//
//    /** Determine whether a node is a {@link MethodDeclaration}
//     * @param pattern JD
//     * @return <code><b>true</b></code> <i>iff</i> the parameter is a method
//     *         invocation. */
//    static boolean methodDeclaration(final ASTNode ¢) {
//        return iz.nodeTypeEquals(¢, METHOD_DECLARATION);
//    }
//
//    /** Determine whether a node is a {@link MethodInvocation}
//     * @param pattern JD
//     * @return <code><b>true</b></code> <i>iff</i> the parameter is a method
//     *         invocation. */
//    static boolean methodInvocation(final ASTNode ¢) {
//        return iz.nodeTypeEquals(¢, METHOD_INVOCATION);
//    }
//
//    static boolean modifier(final ASTNode ¢) {
//        return iz.nodeTypeEquals(¢, MODIFIER);
//    }
//
//    static boolean name(final ASTNode ¢) {
//        return ¢ instanceof Name;
//    }
//
//    static boolean negative(final Expression ¢) {
//        return negative(az.prefixExpression(¢)) || negative(az.numberLiteral(¢));
//    }
//
//    static boolean negative(final NumberLiteral ¢) {
//        return ¢ != null && ¢.getToken().startsWith("-");
//    }
//
//    static boolean negative(final PrefixExpression ¢) {
//        return ¢ != null && ¢.getOperator() == PrefixExpression.Operator.MINUS;
//    }
//
//    static boolean nodeTypeEquals(final ASTNode n, final int type) {
//        return n != null && type == n.getNodeType();
//    }
//
//    /** Determine whether the type of an {@link ASTNode} node is one of given list
//     * @param n a node
//     * @param types a list of types
//     * @return <code><b>true</b></code> <i>iff</i> function #ASTNode.getNodeType
//     *         returns one of the types provided as parameters */
//    static boolean nodeTypeIn(final ASTNode n, final int... types) {
//        return n != null && intIsIn(n.getNodeType(), types);
//    }
//
//    /** Determine whether an {@link Expression} is so basic that it never needs to
//     * be placed in parenthesis.
//     * @param x JD
//     * @return <code><b>true</b></code> <i>iff</i> the parameter is so basic that
//     *         it never needs to be placed in parenthesis. */
//    static boolean noParenthesisRequired(final Expression ¢) {
//        return iz.nodeTypeIn(¢, ARRAY_ACCESS, ARRAY_CREATION, BOOLEAN_LITERAL, CAST_EXPRESSION, CHARACTER_LITERAL, CLASS_INSTANCE_CREATION, FIELD_ACCESS,
//                INSTANCEOF_EXPRESSION, METHOD_INVOCATION, NULL_LITERAL, NUMBER_LITERAL, PARAMETERIZED_TYPE, PARENTHESIZED_EXPRESSION, QUALIFIED_NAME,
//                SIMPLE_NAME, STRING_LITERAL, SUPER_CONSTRUCTOR_INVOCATION, SUPER_FIELD_ACCESS, SUPER_METHOD_INVOCATION, THIS_EXPRESSION, TYPE_LITERAL);
//    }
//
//    static boolean normalAnnotations(final ASTNode ¢) {
//        return iz.nodeTypeEquals(¢, NORMAL_ANNOTATION);
//    }
//
//    /** Determine whether a node is the <code><b>null</b></code> keyword
//     * @param pattern JD
//     * @return <code><b>true</b></code> <i>iff</i>is thee <code><b>null</b></code>
//     *         literal */
//    static boolean nullLiteral(final ASTNode ¢) {
//        return iz.nodeTypeEquals(¢, NULL_LITERAL);
//    }
//
//    static boolean number(final Expression ¢) {
//        return iz.numberLiteral(¢) && (type.isInt(¢) || type.isDouble(¢) || type.isLong(¢));
//    }
//
//    static boolean numberLiteral(final ASTNode ¢) {
//        return iz.nodeTypeEquals(¢, NUMBER_LITERAL);
//    }
//
//    /** Determine whether a node is <code><b>this</b></code> or
//     * <code><b>null</b></code>
//     * @param x JD
//     * @return <code><b>true</b></code> <i>iff</i> the parameter is a block
//     *         statement */
//    static boolean numericLiteral(final Expression ¢) {
//        return iz.nodeTypeIn(¢, new int[] { CHARACTER_LITERAL, NUMBER_LITERAL });
//    }
//
//    static boolean parenthesizedExpression(final ASTNode ¢) {
//        return iz.nodeTypeEquals(¢, PARENTHESIZED_EXPRESSION);
//    }
//
//    /** @param a the assignment who's operator we want to check
//     * @return true is the assignment's operator is assign */
//    static boolean plainAssignment(final Assignment ¢) {
//        return ¢ != null && ¢.getOperator() == ASSIGN;
//    }
//
//    static boolean postfixExpression(final ASTNode ¢) {
//        return iz.nodeTypeEquals(¢, POSTFIX_EXPRESSION);
//    }
//
//    /** @param pattern JD
//     * @return <code><b>true</b></code> <i>iff</i> the parameter is a prefix
//     *         expression. */
//    static boolean prefixExpression(final ASTNode ¢) {
//        return iz.nodeTypeEquals(¢, PREFIX_EXPRESSION);
//    }
//
//    static boolean prefixMinus(final Expression ¢) {
//        return iz.prefixExpression(¢) && az.prefixExpression(¢).getOperator() == wizard.MINUS1;
//    }
//
//    /** @param ¢ JD
//     * @return */
//    static boolean primitiveType(final Type ¢) {
//        return ¢ != null && ¢ instanceof PrimitiveType;
//    }
//
//    /** Determine whether a declaration is private
//     * @param ¢ JD
//     * @return <code><b>true</b></code> <em>iff</em>declaration is private */
//    static boolean private¢(final BodyDeclaration ¢) {
//        return (Modifier.PRIVATE & ¢.getModifiers()) != 0;
//    }
//
//    static boolean protected¢(final BodyDeclaration ¢) {
//        return (¢.getModifiers() & Modifier.PROTECTED) != 0;
//    }
//
//    static boolean pseudoNumber(final Expression ¢) {
//        return number(¢) || iz.prefixMinus(¢) && iz.number(az.prefixExpression(¢).getOperand());
//    }
//
//    static boolean public¢(final BodyDeclaration ¢) {
//        return (Modifier.PUBLIC & ¢.getModifiers()) != 0;
//    }
//
//    /** Determine whether a node is a qualified name
//     * @param pattern JD
//     * @return <code><b>true</b></code> <i>iff</i> the parameter is a qualified
//     *         name */
//    static boolean qualifiedName(final ASTNode ¢) {
//        return iz.nodeTypeEquals(¢, QUALIFIED_NAME);
//    }
//
//    /** Determine whether a node is a return statement
//     * @param pattern JD
//     * @return <code><b>true</b></code> <i>iff</i> the parameter is a return
//     *         statement. */
//    static boolean returnStatement(final ASTNode ¢) {
//        return iz.nodeTypeEquals(¢, RETURN_STATEMENT);
//    }
//
//    static boolean rightOfAssignment(final Expression ¢) {
//        return ¢ != null && right(az.assignment(¢.getParent())).equals(¢);
//    }
//
//    /** Determine whether a node is a "sequencer", i.e.,
//     * <code><b>return</b></code> , <code><b>break</b></code>,
//     * <code><b>continue</b></code> or <code><b>throw</b></code>
//     * @param pattern JD
//     * @return <code><b>true</b></code> <i>iff</i> the parameter is a sequencer */
//    static boolean sequencer(final ASTNode ¢) {
//        return iz.nodeTypeIn(¢, new int[] { RETURN_STATEMENT, BREAK_STATEMENT, CONTINUE_STATEMENT, THROW_STATEMENT });
//    }
//
//    /** Checks if expression is simple.
//     * @param x an expression
//     * @return <code><b>true</b></code> <em>iff</em> argument is simple */
//    static boolean simple(final Expression ¢) {
//        return iz.nodeTypeIn(¢, BOOLEAN_LITERAL, CHARACTER_LITERAL, NULL_LITERAL, NUMBER_LITERAL, QUALIFIED_NAME, SIMPLE_NAME, STRING_LITERAL,
//                THIS_EXPRESSION, TYPE_LITERAL);
//    }
//
//    /** Determine whether a node is a simple name
//     * @param pattern JD
//     * @return <code><b>true</b></code> <i>iff</i> the parameter is a simple
//     *         name */
//    static boolean simpleName(final ASTNode ¢) {
//        return iz.nodeTypeEquals(¢, SIMPLE_NAME);
//    }
//
//    static boolean singleMemberAnnotation(final ASTNode ¢) {
//        return iz.nodeTypeEquals(¢, SINGLE_MEMBER_ANNOTATION);
//    }
//
//    /** Determine whether a node is a singleton statement, i.e., not a block.
//     * @param pattern JD
//     * @return <code><b>true</b></code> <i>iff</i> the parameter is a singleton
//     *         statement. */
//    static boolean singletonStatement(final ASTNode ¢) {
//        return extract.statements(¢).size() == 1;
//    }
//
//    /** Determine whether the "then" branch of an {@link Statement} is a single
//     * statement.
//     * @param subject JD
//     * @return <code><b>true</b></code> <i>iff</i> the parameter is a statement */
//    static boolean singletonThen(final IfStatement ¢) {
//        return ¢ != null && iz.singletonStatement(then(¢));
//    }
//
//    static boolean singleVariableDeclaration(final ASTNode ¢) {
//        return iz.nodeTypeEquals(¢, SINGLE_VARIABLE_DECLARATION);
//    }
//
//    /** @param ¢ JD
//     * @return <code><b>true</b></code> <em>iff</em>the given node is a statement
//     *         or false otherwise */
//    static boolean statement(final ASTNode ¢) {
//        return ¢ instanceof Statement;
//    }
//
//    /** Determine whether a declaration is static or not
//     * @param ¢ JD
//     * @return <code><b>true</b></code> <em>iff</em>declaration is static */
//    static boolean static¢(final BodyDeclaration ¢) {
//        return (Modifier.STATIC & ¢.getModifiers()) != 0;
//    }
//
//    /** @param pattern JD
//     * @return <code><b>true</b></code> <i>iff</i> the parameter is a string
//     *         literal */
//    static boolean stringLiteral(final ASTNode ¢) {
//        return ¢ != null && ¢.getNodeType() == STRING_LITERAL;
//    }
//
//    /** Determine whether a node is the <code><b>this</b></code> keyword
//     * @param pattern JD
//     * @return <code><b>true</b></code> <i>iff</i> is the <code><b>this</b></code>
//     *         keyword */
//    static boolean thisLiteral(final ASTNode ¢) {
//        return iz.nodeTypeEquals(¢, THIS_EXPRESSION);
//    }
//
//    /** Determine whether a node is <code><b>this</b></code> or
//     * <code><b>null</b></code>
//     * @param x JD
//     * @return <code><b>true</b></code> <i>iff</i> the parameter is a block
//     *         statement */
//    static boolean thisOrNull(final Expression ¢) {
//        return iz.nodeTypeIn(¢, new int[] { NULL_LITERAL, THIS_EXPRESSION });
//    }
//
//    static boolean tryStatement(final ASTNode ¢) {
//        return iz.nodeTypeEquals(¢, TRY_STATEMENT);
//    }
//
//    static boolean typeDeclaration(final ASTNode ¢) {
//        return ¢ != null && iz.nodeTypeEquals(¢, TYPE_DECLARATION);
//    }
//
//    /** @param ¢ JD
//     * @return <code><b>true</b></code> <em>iff</em> the statement is side effect
//     *         and updating an initializer from the for initializers. returns
//     *         false if the parent is not a for loop. */
//    static boolean usingForInitializer(final Statement ¢) {
//        return az.forStatement(¢.getParent()) != null;
//    }
//
//    /** Determine whether a given {@link Statement} is an {@link EmptyStatement}
//     * or has nothing but empty sideEffects in it.
//     * @param subject JD
//     * @return <code><b>true</b></code> <i>iff</i> there are no non-empty
//     *         sideEffects in the parameter */
//    static boolean vacuous(final Statement ¢) {
//        return extract.statements(¢).isEmpty();
//    }
//
//    /** Determine whether the 'else' part of an {@link IfStatement} is vacuous.
//     * @param subject JD
//     * @return <code><b>true</b></code> <i>iff</i> there are no non-empty
//     *         sideEffects in the 'else' part of the parameter */
//    static boolean vacuousElse(final IfStatement ¢) {
//        return vacuous(elze(¢));
//    }
//
//    /** Determine whether a statement is an {@link EmptyStatement} or has nothing
//     * but empty sideEffects in it.
//     * @param subject JD
//     * @return <code><b>true</b></code> <i>iff</i> there are no non-empty
//     *         sideEffects in the parameter */
//    static boolean vacuousThen(final IfStatement ¢) {
//        return vacuous(then(¢));
//    }
//
//    static boolean validForEvaluation(final InfixExpression x) {
//        for (final Expression ¢ : extract.allOperands(x))
//            if (!iz.pseudoNumber(¢))
//                return false;
//        return true;
//    }
//
//    static boolean variableDeclarationExpression(final ASTNode $) {
//        return iz.nodeTypeEquals($, VARIABLE_DECLARATION_EXPRESSION);
//    }
//
//    /** @param $
//     * @return */
//    static boolean variableDeclarationFragment(final ASTNode $) {
//        return iz.nodeTypeEquals($, VARIABLE_DECLARATION_FRAGMENT);
//    }
//
//    /** @param pattern JD
//     * @return <code><b>true</b></code> <i>iff</i> the parameter is a variable
//     *         declaration statement. */
//    static boolean variableDeclarationStatement(final ASTNode ¢) {
//        return iz.nodeTypeEquals(¢, VARIABLE_DECLARATION_STATEMENT);
//    }
//
//    static boolean variableNotUsedAfterStatement(final Statement s, final SimpleName n) {
//        final Block b = az.block(s.getParent());
//        assert b != null : "For loop's parent is not a block";
//        final List<Statement> statements = step.statements(b);
//        boolean passedFor = false;
//        for (final Statement ¢ : statements) {
//            if (passedFor && !Collect.usesOf(n).in(¢).isEmpty())
//                return false;
//            if (¢.equals(s))
//                passedFor = true;
//        }
//        return true;
//    }
//
//    /** Determines whether a specific SimpleName was used in a
//     * {@link ForStatement}.
//     * @param s JD
//     * @param n JD
//     * @return <code><b>true</b></code> <em>iff</em> the SimpleName is used in a
//     *         ForStatement's condition, updaters, or body. */
//    static boolean variableUsedInFor(final ForStatement s, final SimpleName n) {
//        return !Collect.usesOf(n).in(step.condition(s), step.body(s)).isEmpty() || !Collect.usesOf(n).in(step.updaters(s)).isEmpty();
//    }
//
//    /** @param ¢ JD
//     * @return */
//    static boolean voidType(final Type ¢) {
//        return primitiveType(¢) && az.primitiveType(¢).getPrimitiveTypeCode().equals(PrimitiveType.VOID);
//    }
//
//    static boolean whileStatement(final ASTNode x) {
//        return iz.nodeTypeEquals(x, WHILE_STATEMENT);
//    }
//
//    static boolean wildcardType(final ASTNode ¢) {
//        return iz.nodeTypeEquals(¢, WILDCARD_TYPE);
//    }
//
//    /** @param ¢ JD
//     * @return <code><b>true</b></code> <em>iff</em>the given node is a literal or
//     *         false otherwise */
//    default boolean parsesTo(final String token, final double d) {
//        try {
//            return Double.parseDouble(token) == d;
//        } catch (final IllegalArgumentException x) {
//            monitor.logEvaluationError(this, x);
//            return false;
//        }
//    }
//
//    default boolean parsesTo(final String token, final int i) {
//        try {
//            return Integer.parseInt(token) == i;
//        } catch (final IllegalArgumentException x) {
//            monitor.logEvaluationError(this, x);
//            return false;
//        }
//    }
//
//    default boolean parsesTo(final String token, final long l) {
//        try {
//            return Long.parseLong(token) == l;
//        } catch (final IllegalArgumentException x) {
//            monitor.logEvaluationError(box(l), x);
//            return false;
//        }
//    }
//
//    interface literal {
//        /** @param ¢ JD
//         * @return */
//        static boolean classInstanceCreation(final ASTNode ¢) {
//            return ¢ != null && nodeTypeEquals(¢, CLASS_INSTANCE_CREATION);
//        }
//
//        /** @param ¢ JD
//         * @return <code><b>true</b></code> <em>iff</em>the given node is a literal
//         *         false or false otherwise */
//        static boolean false¢(final ASTNode ¢) {
//            return iz.literal(¢, false);
//        }
//
//        /** @param ¢ JD
//         * @return */
//        static boolean fieldAccess(final Expression ¢) {
//            return ¢ != null && nodeTypeEquals(¢, FIELD_ACCESS);
//        }
//
//        /** @param ¢ JD
//         * @return <code><b>true</b></code> <em>iff</em>the given node is a literal
//         *         true or false otherwise */
//        static boolean true¢(final ASTNode ¢) {
//            return iz.literal(¢, true);
//        }
//
//        static boolean xliteral(final String s, final ASTNode ¢) {
//            return literal(az.stringLiteral(¢), s);
//        }
//    }
}