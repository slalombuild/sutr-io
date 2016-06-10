package com.slalom.aws.avs.utr;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

public class UtrColorSettingsPage implements ColorSettingsPage {
    public static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
            new AttributesDescriptor("SutrIntentModel", UtrSyntaxHighlighter.INTENT),
            new AttributesDescriptor("Utterance", UtrSyntaxHighlighter.WORD),
            new AttributesDescriptor("Slot", UtrSyntaxHighlighter.SLOT),
            new AttributesDescriptor("Slot Phrase", UtrSyntaxHighlighter.LITERAL_PHRASE),
    };

    private static final Map<String, TextAttributesKey> ATTRIBUTES_KEY_MAP = ContainerUtil.newHashMap();

    static {
        ATTRIBUTES_KEY_MAP.put("i", UtrSyntaxHighlighter.INTENT);
        ATTRIBUTES_KEY_MAP.put("u", UtrSyntaxHighlighter.WORD);
        ATTRIBUTES_KEY_MAP.put("s", UtrSyntaxHighlighter.SLOT);
        ATTRIBUTES_KEY_MAP.put("sp", UtrSyntaxHighlighter.LITERAL_PHRASE);
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return UtrIcons.FILE;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new UtrSyntaxHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {
            return "<i>My SutrIntentModel</i> <u>This is the utterance with a</u> <s>{Action}</s> <u>and</u> <s>{<sp>literal phrase</sp> | MyLiteral} </s>";


    }

    @NotNull
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return ATTRIBUTES_KEY_MAP;
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        return DESCRIPTORS;
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Utr - Amazon Utterance";
    }

}
