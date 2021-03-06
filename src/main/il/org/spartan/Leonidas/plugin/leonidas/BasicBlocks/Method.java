package il.org.spartan.Leonidas.plugin.leonidas.BasicBlocks;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import il.org.spartan.Leonidas.auxilary_layer.*;
import il.org.spartan.Leonidas.plugin.UserControlled;
import il.org.spartan.Leonidas.plugin.leonidas.Matcher;
import il.org.spartan.Leonidas.plugin.leonidas.MatchingResult;
import il.org.spartan.Leonidas.plugin.leonidas.Pruning;
import il.org.spartan.Leonidas.plugin.leonidas.Replacer;

import java.util.*;


/**
 * A basic block representing a method. For example "int method0 (int x){...}
 * @author Sharon, michalcohen
 * @since 13.5.17
 */
public class Method extends ModifiableElement {
    private static final String TEMPLATE = "method";
    @UserControlled(name="must contain: " , templatePart = "Matcher")
    public List<String> containsList = new LinkedList<>();
    @UserControlled(name="must not contain: " , templatePart = "Matcher")
    public List<String> notContainsList = new LinkedList<>();
    private Matcher matcherReturnType, matcherParameters, matcherCodeBlock;
    private Replacer replacerReturnType, replacerParameters, replacerCodeBlock;

    public Method(Encapsulator e) {
        super(e, TEMPLATE);
    }

    /**
     * For reflection use DO NOT REMOVE!
     */
    public Method() {
        super(TEMPLATE);
    }

    @Override
    protected String getName(PsiElement e) {
        return !iz.method(e) ? null : az.method(e).getName();
    }

    @Override
    public boolean conforms(PsiElement e) {
        return iz.method(e) && super.conforms(e);
    }

    @Override
    public MatchingResult generalizes(Encapsulator e, Map<Integer, List<PsiElement>> map) {
        if (!iz.method(e.getInner()) || super.generalizes(e, map).notMatches()) return new MatchingResult(false);
        PsiMethod m = az.method(e.getInner());
        Wrapper<Integer> dummy = new Wrapper<>(0);
        return matcherReturnType.getMatchingResult(m.getReturnTypeElement(), dummy).combineWith(
                matcherParameters.getMatchingResult(m.getParameterList(), dummy)).combineWith(
                matcherCodeBlock.getMatchingResult(m.getBody(), dummy));
    }

    @Override
    protected boolean goUpwards(Encapsulator prev, Encapsulator next) {
        return next != null && iz.method(next.getInner());
    }

    @Override
    public GenericEncapsulator create(Encapsulator e, Map<Integer, List<Matcher.Constraint>> map) {
        Method m = new Method(e);
        Encapsulator returnType = Pruning
				.prune(Encapsulator.buildTreeFromPsi(az.method(e.getInner()).getReturnTypeElement()), map),
				parameters = Pruning.prune(Encapsulator.buildTreeFromPsi(az.method(e.getInner()).getParameterList()),
						map),
				codeBlock = Pruning.prune(Encapsulator.buildTreeFromPsi(az.method(e.getInner()).getBody()), map);
        m.matcherReturnType = new Matcher(Utils.wrapWithList(returnType), map);
        m.matcherParameters = new Matcher(Utils.wrapWithList(parameters), map);
        m.matcherCodeBlock = new Matcher(Utils.wrapWithList(codeBlock), map);
        m.replacerReturnType = new Replacer(Utils.wrapWithList(returnType));
        m.replacerParameters = new Replacer(Utils.wrapWithList(parameters));
        m.replacerCodeBlock = new Replacer(Utils.wrapWithList(codeBlock));
        return m;
    }

    @Override
    public List<PsiElement> replaceByRange(List<PsiElement> es, Map<Integer, List<PsiElement>> m, PsiRewrite r) {
        PsiMethod e = az.method(es.get(0)), iam = az.method(inner);
        iam.setName(e.getName());
        iam.getReturnTypeElement().replace(replacerReturnType.replaceSingleRoot(e.getReturnTypeElement(), m, r));
        iam.getParameterList().replace(replacerParameters.replaceSingleRoot(e.getParameterList(), m, r));
        iam.getBody().replace(replacerCodeBlock.replaceSingleRoot(e.getBody(), m, r));
        iam.getModifierList().replace(e.getModifierList());
        return Utils.wrapWithList(inner);
    }

    @Override
    public Map<Integer, GenericEncapsulator> getGenericElements(){
        Map<Integer,GenericEncapsulator> map = new HashMap<>();

        Set<Encapsulator> set = new HashSet<>(matcherReturnType.getAllRoots());
        set.addAll(matcherCodeBlock.getAllRoots());
        set.addAll(matcherParameters.getAllRoots());

        List<Encapsulator> l = new ArrayList<>(set);

        l.forEach(root -> root.accept(e -> {
            if (e.isGeneric()) {
                map.put(az.generic(e).getId(), (GenericEncapsulator) e);
                if (iz.quantifier(e))
					map.put(az.quantifier(e).getId(), az.generic(az.quantifier(e).getInternal()));
            }
        }));

        return map;

    }

    public void contains(String s) {
        containsList.add(s);
        addConstraint((e, m) -> containsList.stream().allMatch(cs -> az.method(e.inner).getName().contains(cs)));
    }

    public void notContains(String s) {
        notContainsList.add(s);
        addConstraint((e, m) -> notContainsList.stream().noneMatch(ncs ->az.method(e.inner).getName().contains(ncs)));
    }

    @Override
    public void copyTo(GenericEncapsulator dst) {
        if (!(dst instanceof Method)) return;
        super.copyTo(dst);
        Method castDst = (Method) dst;
        castDst.containsList = new LinkedList<>(containsList);
        castDst.notContainsList = new LinkedList<>(notContainsList);
    }
}