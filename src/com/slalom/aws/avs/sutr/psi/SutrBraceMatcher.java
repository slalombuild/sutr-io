package com.slalom.aws.avs.sutr.psi;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Stryder on 1/24/2016.
 */
public class SutrBraceMatcher implements PairedBraceMatcher {
    private static final BracePair[] BRACE_PAIRS = {
            new BracePair(SutrTypes.SutrLB, SutrTypes.SutrRB, false),
            new BracePair(SutrTypes.SutrLS, SutrTypes.SutrRS, false),
            new BracePair(SutrTypes.SutrLP, SutrTypes.SutrRP, false)
    };

    @Override
    public BracePair[] getPairs() {

        return BRACE_PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull final IElementType lbraceType, @Nullable final IElementType contextType) {
        return true;
    }

    @Override
    public int getCodeConstructStart(final PsiFile file, final int openingBraceOffset) {
        return 0;
    }
}
