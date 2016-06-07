package com.slalom.aws.avs.sutr.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.PsiReference;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.util.IncorrectOperationException;
import com.slalom.aws.avs.sutr.SutrElementFactory;
import com.slalom.aws.avs.sutr.parser.SutrPsiCompositeElement;
import com.slalom.aws.avs.sutr.psi.*;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stryder on 1/17/2016.
 */
public class SutrSlotReferenceImpl extends SutrReferenceImpl implements PsiScopeProcessor, SutrPsiCompositeElement, PsiNameIdentifierOwner {
    private static final Logger log = Logger.getInstance(SutrSlotReferenceImpl.class);

    public SutrSlotReferenceImpl(@NotNull final ASTNode astNode) {
        super(astNode);
    }

    @NotNull
    @Override
    public String getCanonicalText() {
        if(this.getTokenType().equals(SutrTypes.SutrSLOT)){
            final SutrSlotName slotName = ((SutrSlotImpl) this).getSlotName();
            if (slotName != null) {
                return slotName.getText();
            }
        }
        return this.getText();
    }

    @Override
    public PsiReference getReference() {
       return this;
    }

    @Nullable
    @Override
    public PsiElement getNameIdentifier() {
        if(this.getTokenType().equals(SutrTypes.SutrSLOT)){
            return ((SutrSlotImpl)this).getSlotName();
        }
        return this;
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        SutrObject thisSutrObject = getParentSutr();

        for (final SutrParam sutrParam : thisSutrObject.getSutrParams().getSutrParamList()) {
            if (sutrParam.getParamName().getText().equals(this.getCanonicalText())) {
                return sutrParam.getParamName();
            }
        }

        return null;
    }

    private SutrObject getParentSutr() {
        return (SutrObject)this.getParent().getParent().getParent();
    }

    @Override
    public PsiElement setName(@NonNls @NotNull final String name) throws IncorrectOperationException {
        return handleElementRename(name);
    }

    @Override
    public boolean isReferenceTo(final PsiElement psiElement) {
        if(psiElement instanceof SutrParamName){
            final SutrObject elementSutr = ((SutrParamName) psiElement).getParentSutr();
            final SutrObject parentSutr = getParentSutr();


            final boolean textMatches = this.getCanonicalText().equals(psiElement.getText());
            if (elementSutr.equals(parentSutr) && textMatches) {
                return true;
            }
        }

        return false;
    }

    @Override
    public PsiElement handleElementRename(final String s) throws IncorrectOperationException {
        SutrSlotImpl newNode = SutrElementFactory.createSlotElement(getProject(), s);
        final PsiElement nameIdentifier = getNameIdentifier();
        final PsiElement nameIdentifier1 = newNode.getNameIdentifier();
        if(nameIdentifier != null && nameIdentifier1 != null){
            this.getParent().getNode().replaceChild(getNode(), newNode.getNode());
        }

        return this;
    }

    @Override
    public boolean execute(@NotNull final PsiElement element, @NotNull final ResolveState state) {
        return false;
    }

    @Nullable
    @Override
    public <T> T getHint(@NotNull final Key<T> hintKey) {
        return null;
    }

    @Override
    public void handleEvent(@NotNull final Event event, @Nullable final Object associated) {
        log.error("handling random event...");
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        List<Object> variants = new ArrayList<>();
        final List<SutrParam> sutrParamList = this.getParentSutr().getSutrParams().getSutrParamList();

        for (final SutrParam sutrParam : sutrParamList) {
            if(notInUtterance(sutrParam)){
                variants.add(new SlotLookupElement(sutrParam.getParamName().getText()));
            }
        }

        final Object[] objects = variants.toArray(new Object[sutrParamList.size()]);

        return objects;
    }

    private boolean notInUtterance(final SutrParam sutrParam) {
        SutrUtteranceImpl utterance = (SutrUtteranceImpl) this.getParent();
        for (final SutrSlot sutrSlot : utterance.getSlotList()) {
            final SutrSlotName slotName = sutrSlot.getSlotName();

            if(slotName != null && slotName.getText().equals(sutrParam.getParamName().getText())){
                return false;
            }
        }
        return true;
    }

    @Override
    public TextRange getRangeInElement() {
        final TextRange textRange = getTextRange();
        return new TextRange(1, textRange.getEndOffset() - textRange.getStartOffset());

    }
}