package com.slalom.aws.avs.sutr;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SutrColorSettingsPage implements ColorSettingsPage {
    private static final Logger log = Logger.getInstance(SutrColorSettingsPage.class);

    private final Map<String, TextAttributesKey> _keyMap;
    private final AttributesDescriptor[] _descriptors;


    public SutrColorSettingsPage() {
        log.info("building new Attributes Descriptor for the Sutr Color Settings page.");

        List<AttributesDescriptor> descriptors = new ArrayList<>();

        log.info("Creating new Attribute Descriptor [COMMENT]");
        descriptors.add(new AttributesDescriptor("Comment", SutrSyntaxHighlighter.COMMENT));

        log.info("Creating new Attribute Descriptor [KEYWORD]");
        descriptors.add(new AttributesDescriptor("Keyword", SutrSyntaxHighlighter.KEYWORD));

        log.info("Creating new Attribute Descriptor [SUTR_NAME]");
        descriptors.add(new AttributesDescriptor("Sutr Name", SutrSyntaxHighlighter.SUTR_NAME));

        log.info("Creating new Attribute Descriptor [SLOT]");
        descriptors.add(new AttributesDescriptor("Utterance Slot", SutrSyntaxHighlighter.SLOT));

        log.info("Creating new Attribute Descriptor [UTTERANCE_PART]");
        descriptors.add(new AttributesDescriptor("Utterance Part", SutrSyntaxHighlighter.UTTERANCE_PART));

        log.info("Creating new Attribute Descriptor [TYPE_NAME]");
        descriptors.add(new AttributesDescriptor("Type", SutrSyntaxHighlighter.TYPE_NAME));

        log.info("Creating new Attribute Descriptor [CUSTOM_TYPE_ITEM]");
        descriptors.add(new AttributesDescriptor("Custom Type Value", SutrSyntaxHighlighter.CUSTOM_TYPE_ITEM));

        log.info("Creating new Attribute Descriptor [LITERAL_PHRASE]");
        descriptors.add(new AttributesDescriptor("Literal Phrase", SutrSyntaxHighlighter.LITERAL_PHRASE));

        log.info("Creating new Attribute Descriptor [FUNCTION_NAME]");
        descriptors.add(new AttributesDescriptor("Function Reference", SutrSyntaxHighlighter.FUNCTION_NAME));

        log.info("Creating Descriptors array from list.");
        _descriptors = descriptors.toArray(new AttributesDescriptor[descriptors.size()]);

        log.info("Building new Keymap for the Sutr Color Settings page.");

        this._keyMap = ContainerUtil.newHashMap();

        _keyMap.put("c", SutrSyntaxHighlighter.COMMENT);
        _keyMap.put("kw", SutrSyntaxHighlighter.KEYWORD);
        _keyMap.put("sn", SutrSyntaxHighlighter.SUTR_NAME);
        _keyMap.put("us", SutrSyntaxHighlighter.SLOT);
        _keyMap.put("up", SutrSyntaxHighlighter.UTTERANCE_PART);
        _keyMap.put("t", SutrSyntaxHighlighter.TYPE_NAME);
        _keyMap.put("ctv", SutrSyntaxHighlighter.CUSTOM_TYPE_ITEM);
        _keyMap.put("lp", SutrSyntaxHighlighter.LITERAL_PHRASE);
        _keyMap.put("fn", SutrSyntaxHighlighter.FUNCTION_NAME);
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return SutrIcons.FILE;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        log.info("Building new SutrSyntaxHighlighter for Sutr Color settings page.");

        return new SutrSyntaxHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {
        log.info("Returning Sutr Demo Text");

        return "<c># This is a comment</c>\n" +
                "\n" +
                "<kw>type</kw> <t>My_Custom_Type</t> [\n" +
                "    <ctv>Jump</ctv>,\n" +
                "    <ctv>Run</ctv>,\n" +
                "    <ctv>Walk</ctv>,\n" +
                "    <c>#comments can go in declarations</c>\n" +
                "    <ctv>Stand</ctv>\n" +
                "]\n" +
                "\n" +
                "<kw>literal</kw> <t>MyLiteral</t> [\n" +
                "    <lp>Jump and run</lp>,\n" +
                "    <lp>Run</lp>,\n" +
                "    <lp>Walk and do a thing</lp>\n" +
                "]\n" +
                "\n" +
                "<kw>def</kw> <sn>MyIntent</sn>(<t>My_Custom_Type</t> <sn>Action</sn>, <t>MyLiteral</t> <sn>Phrase</sn>, <t>Date</t> <sn>MyDate</sn>){\n" +
                "    <up>Remember to do</up> <us>{Action}</us> <up>or</up> <us>{Phrase}</us> <up>on</up> <us>{Date}</us>\n" +
                "    <up>Remind me to</up> <us>{Action}</us> <up>and</up> <us>{Phrase}</us> <up>on</up> <us>{Date}</us>\n" +
                "} => <fn>MyTarget.Function_Name</fn>";
    }

    @NotNull
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        log.info("Returning additional highlighting tags.");

        if (_keyMap != null) {
            return _keyMap;
        }

        return new HashMap<>();
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {

        log.info("Returning Sutr Attribute descriptors");

        if(_descriptors != null){
            return _descriptors;
        }

        return new AttributesDescriptor[0];
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        log.info("Returning empty Color Descriptor.");

        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Sutr - Slalom Utterance";
    }

}
