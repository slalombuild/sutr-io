package com.slalom.aws.avs.sutr.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.PsiReference;
import com.intellij.util.IncorrectOperationException;
import com.slalom.aws.avs.sutr.SutrElementFactory;
import com.slalom.aws.avs.sutr.psi.*;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Stryder on 1/17/2016.
 */
public class SutrTypeReferenceImpl extends SutrReferenceImpl implements SutrTypeReference, PsiNameIdentifierOwner {
    public SutrTypeReferenceImpl(@NotNull final ASTNode astNode) {
        super(astNode);
    }

    @Override
    public PsiReference getReference() {
        return this;
    }

    @Override
    public PsiElement setName(@NonNls @NotNull final String s) throws IncorrectOperationException {
      return handleElementRename(s);
    }

    @Override
    public PsiElement handleElementRename(final String s) throws IncorrectOperationException {
        SutrTypeNameImpl newNode = SutrElementFactory.createTypeNameElement(getProject(), s);
        final PsiElement nameIdentifier = getNameIdentifier();
        final PsiElement nameIdentifier1 = newNode.getNameIdentifier();
        if(nameIdentifier != null && nameIdentifier1 != null){
            this.getParent().getNode().replaceChild(getNode(), newNode.getNode());
//            getNode().replaceChild(nameIdentifier.getNode(), nameIdentifier1.getNode());
        }


        return this;
    }

    @Override
    public String getName() {
        return this.getText();
    }


    @Override
    public PsiElement getElement() {
        return this;
    }

    @Override
    public TextRange getRangeInElement() {
        final TextRange textRange = getTextRange();
        return new TextRange(0, textRange.getEndOffset() - textRange.getStartOffset());
//        return getTextRange();
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        SutrFile file = (SutrFile) this.getContainingFile();
        for (SutrImportStmt sutrImportStmt : file.findChildrenByClass(SutrImportStmt.class)) {
            if(sutrImportStmt.getTypeName().getText().equals(this.getText())){
                return sutrImportStmt.getTypeName();
            }
        }

        for (final SutrLiteralType sutrLiteralType : file.findChildrenByClass(SutrLiteralType.class)) {
            if(sutrLiteralType.getTypeName().getText().equals(this.getText())){
                return sutrLiteralType.getTypeName();
            }
        }

        for (final SutrCustomType sutrCustomType : file.findChildrenByClass(SutrCustomType.class)) {
            if(sutrCustomType.getTypeName().getText().equals(this.getText())){
                return sutrCustomType.getTypeName();
            }
        }

        return null;
    }


    @NotNull
    @Override
    public String getCanonicalText() {
        return getText();
    }



    @Override
    public PsiElement bindToElement(@NotNull final PsiElement psiElement) throws IncorrectOperationException {
        return null;
    }

    @Override
    public boolean isReferenceTo(final PsiElement psiElement) {

        return true;
    }

    @NotNull
    @Override
    public Object[] getVariants() {

        return  new Object[]{"foo1", "foo2", "foo3"};
    }

    @Override
    public boolean isSoft() {
        return false;
    }

    @Nullable
    @Override
    public PsiElement getNameIdentifier() {
        return this;
    }


}
