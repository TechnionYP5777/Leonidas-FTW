package plugin.tippers;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiForStatement;
import plugin.tipping.Tipper;
import plugin.tipping.Tip;
import plugin.tipping.TipperCategory;
import auxilary_layer.PsiRewrite;
import java.util.Collection;

/**
 * Created by maorroey on 12/26/2016.
 */
public class AnyMatch extends NanoPatternTipper<PsiForStatement>{


    public boolean canTip(PsiElement ¢){
        return false;
    }

    @Override
    public String description(PsiForStatement ¢) {
        return "Replaces anyMatch-for statement with the appropriate .stream().AnyMatch Java Collection function call";
    }

    @Override
    protected Tip pattern(final PsiForStatement ¢){
        return tip(¢);
    }

    @Override
    public Class<PsiForStatement> getPsiClass() {
        return PsiForStatement.class;
    }
}
