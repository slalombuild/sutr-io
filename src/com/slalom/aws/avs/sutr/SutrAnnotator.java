package com.slalom.aws.avs.sutr;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.slalom.aws.avs.sutr.actions.ActionUtil;
import com.slalom.aws.avs.sutr.parser.SutrPsiCompositeElement;
import com.slalom.aws.avs.sutr.psi.*;
import com.slalom.aws.avs.sutr.psi.impl.SutrLiteralPhrasesImpl;
import com.slalom.aws.avs.sutr.psi.impl.SutrSlotImpl;
import com.slalom.aws.avs.sutr.psi.impl.SutrTypeNameImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Created by Stryder on 1/17/2016.
 */
public class SutrAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull final PsiElement psiElement, @NotNull final AnnotationHolder annotationHolder) {

        if (!(psiElement.getContext() instanceof SutrPsiCompositeElement)) return;

        final SutrPsiCompositeElement psiElementContext = (SutrPsiCompositeElement) psiElement.getContext();
        final IElementType tokenType = psiElementContext.getTokenType();


        if (tokenType.equals(SutrTypes.SutrTYPE_NAME)) {
            validateTypeName(psiElement, annotationHolder);
        } else if (tokenType.equals(SutrTypes.SutrSLOT)) {
            validateSlot(psiElement, annotationHolder);
        } else if (tokenType.equals(SutrTypes.SutrCUSTOM_TYPE_ITEM)) {
            validateCustomTypeItem(psiElement, annotationHolder);
        } else if (tokenType.equals(SutrTypes.SutrLITERAL_PHRASE)) {
            validateLiteralPhrase(psiElement, annotationHolder);
        } else if (tokenType.equals(SutrTypes.SutrSUTR_NAME)) {
            validateSutrPhrase(psiElement, annotationHolder);
        } else if (tokenType.equals(SutrTypes.SutrFUNCTION_NAME)) {
            validateFunctionName(psiElement, annotationHolder);
        } else if (tokenType.equals(SutrTypes.SutrUTTERANCE_PART)) {
            validateUtterancePart(psiElement, annotationHolder);
        } else if (tokenType.equals(SutrTypes.SutrSUTR_PARAM)) {
            validateSutrParamPart(psiElement, annotationHolder);
        }
    }

    private void validateSutrImport(PsiElement psiElement, AnnotationHolder annotationHolder) {
        if (((SutrImportStmt) psiElement).resolveReference() == null) {
            annotationHolder.createErrorAnnotation(psiElement, "Unable to resolve import statement.");
        }
    }

    private void validateSutrParamPart(final PsiElement psiElement, final AnnotationHolder annotationHolder) {
        if (psiElement instanceof SutrTypeName) {

            final boolean isBuiltInType = ActionUtil.BUILT_IN_TYPES.containsKey(psiElement.getText());

            if (!isBuiltInType && (((SutrTypeNameImpl) psiElement).resolve() == null)) {
                annotationHolder.createErrorAnnotation(psiElement, "Not a built in slotType and not declared as literal or custom slotType");
            }
        }

    }

    private void validateUtterancePart(final @NotNull PsiElement psiElement, final @NotNull AnnotationHolder annotationHolder) {
        annotationHolder.createInfoAnnotation(psiElement, null).setTextAttributes(SutrSyntaxHighlighter.UTTERANCE_PART);
    }

    private void validateFunctionName(final @NotNull PsiElement psiElement, final @NotNull AnnotationHolder annotationHolder) {
        annotationHolder.createInfoAnnotation(psiElement, null).setTextAttributes(SutrSyntaxHighlighter.FUNCTION_NAME);
    }

    private void validateSutrPhrase(final @NotNull PsiElement psiElement, final @NotNull AnnotationHolder annotationHolder) {
        final SutrObject[] sutrObjects = ((SutrFile) psiElement.getContainingFile()).findChildrenByClass(SutrObject.class);
        for (final SutrObject sutrObject : sutrObjects) {
            if (sutrObject.getSutrName().getText().equals(psiElement.getText()) && !sutrObject.getSutrName().getNode().equals(psiElement.getParent().getNode())) {
                annotationHolder.createErrorAnnotation(psiElement, "Duplicate Sutr found");
            }
        }

        annotationHolder.createInfoAnnotation(psiElement, null).setTextAttributes(SutrSyntaxHighlighter.SUTR_NAME);
    }

    private void validateLiteralPhrase(final @NotNull PsiElement psiElement, final @NotNull AnnotationHolder annotationHolder) {

        final SutrLiteralPhrases phrases = ((SutrLiteralPhrasesImpl) psiElement.getParent().getParent());
        for (final SutrLiteralPhrase sutrLiteralPhrase : phrases.getLiteralPhraseList()) {
            final boolean textMatch = sutrLiteralPhrase.getText().equals(psiElement.getText());
            final boolean nodeMatch = sutrLiteralPhrase.getNode().equals(psiElement.getParent().getNode());
            if (textMatch && !nodeMatch) {
                annotationHolder.createErrorAnnotation(psiElement, "Duplicate Literal Phrase");
            }
        }

        annotationHolder.createInfoAnnotation(psiElement, null).setTextAttributes(SutrSyntaxHighlighter.LITERAL_PHRASE);
    }

    private void validateCustomTypeItem(final @NotNull PsiElement psiElement, final @NotNull AnnotationHolder annotationHolder) {

        annotationHolder.createInfoAnnotation(psiElement, null).setTextAttributes(SutrSyntaxHighlighter.CUSTOM_TYPE_ITEM);
    }

    private void validateTypeDeclaration(final @NotNull SutrTypeDefinitionReference psiElement, final @NotNull AnnotationHolder annotationHolder) {

        for (final SutrTypeDefinitionReference customType : ((SutrFile) psiElement.getContainingFile()).findChildrenByClass(SutrTypeDefinitionReference.class)) {
            final boolean textMatch = customType.getTypeName().getText().equals(psiElement.getTypeName().getText());
            final boolean nodeMatch = customType.getTypeName().getNode().equals(psiElement.getTypeName().getNode());
            if (textMatch && !nodeMatch) {
                annotationHolder.createErrorAnnotation(psiElement, "Duplicate slotType definition found.");
            }
        }
    }

    private void validateSlot(final @NotNull PsiElement psiElement, final @NotNull AnnotationHolder annotationHolder) {

        if (psiElement instanceof SutrSlotName) {

            if (!hasParam(psiElement)) {
                annotationHolder.createErrorAnnotation(psiElement, "Slot slotName not defined in Sutr params.");
            }
            if (isDuplicatedSlot(psiElement)) {
                annotationHolder.createErrorAnnotation(psiElement, "Slot used more than once in utterance.");
            }
        }

        annotationHolder.createInfoAnnotation(psiElement, null).setTextAttributes(SutrSyntaxHighlighter.SLOT);
    }

    private boolean hasParam(final @NotNull PsiElement psiElement) {
        final SutrObject parent = ((SutrSlotImpl) psiElement.getParent()).getParentSutr();

        for (final SutrParam sutrSutrParam : parent.getSutrParams().getSutrParamList()) {
            if (((SutrSlotImpl) psiElement.getParent()).isReferenceTo(sutrSutrParam.getParamName())) {
                return true;
            }
        }

        return false;
    }

    private boolean isDuplicatedSlot(final @NotNull PsiElement psiElement) {
        final SutrUtterance curUtterance = (SutrUtterance) psiElement.getParent().getParent();

        for (final SutrSlot sutrSlot : curUtterance.getSlotList()) {
            final SutrSlotName slotName = sutrSlot.getSlotName();

            if (slotName != null && slotName.getText().equals(psiElement.getText()) && !slotName.getNode().equals(psiElement.getNode())) {
                return true;
            }
        }
        return false;
    }

    private void validateTypeName(final @NotNull PsiElement psiElement, final AnnotationHolder annotationHolder) {

        final PsiElement parent = psiElement.getParent().getParent();

        if (parent instanceof SutrImportStmt && ((SutrImportStmt) parent).resolveReference() == null) {
            annotationHolder.createErrorAnnotation(psiElement, "Unable to resolve import statement.");
            return;
        }

        if (parent instanceof SutrTypeDefinitionReference) {
            validateTypeDeclaration((SutrTypeDefinitionReference) parent, annotationHolder);
        }

        annotationHolder.createInfoAnnotation(psiElement, null).setTextAttributes(SutrSyntaxHighlighter.TYPE_NAME);
    }

    private boolean checkDupes(final @NotNull PsiElement psiElement, final AnnotationHolder annotationHolder, final Map<String, PsiElement> elements, final String typeName) {
        if (elements.containsKey(typeName)) {

            return true;
        }

        return false;
    }
}
