package com.slalom.aws.avs.sutr.actions;

import com.slalom.aws.avs.sutr.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Created by stryderc on 1/18/2016.
 */
public class LiteralMapper {

    private final Map<String, List<String>> _literalsMap;

    public LiteralMapper(Map<String, SutrLiteralType> literals) {
        _literalsMap = new HashMap<>();
        for (final Map.Entry<String, SutrLiteralType> stringSutrLiteralTypeEntry : literals.entrySet()) {

            List<String> phrasesList = new ArrayList<>();
            for (final SutrLiteralPhrase sutrLiteralPhrase : stringSutrLiteralTypeEntry.getValue().getLiteralPhrases().getLiteralPhraseList()) {
                phrasesList.add(sutrLiteralPhrase.getText());
            }

            _literalsMap.put(stringSutrLiteralTypeEntry.getKey(), phrasesList);
        }


    }

    public List<String> Map(SutrUtterance utterance, SutrObject sutrObject) {
        final List<SutrParam> sutrParamList = sutrObject.getSutrParams().getSutrParamList();

        List<String> utteranceVariations = new ArrayList<>();

        final List<SutrSlot> slotList = utterance.getSlotList();

        if(slotList.isEmpty()) {
            utteranceVariations.add(utterance.getText().trim());
        } else {
            List<String> seed = new ArrayList<>();
            seed.add(utterance.getText().trim());

            SlotDTO[] keys = buildKeySet(utterance, sutrParamList);

            final Collection<? extends String> variations = parseVariations(seed, _literalsMap, keys, 0);

            utteranceVariations.addAll(variations);
        }

        return utteranceVariations;
    }

    @NotNull
    private SlotDTO[] buildKeySet(SutrUtterance utterance, List<SutrParam> sutrParamList) {
        List<SlotDTO> keySet = new ArrayList<>();

        List<SutrSlot> slots = utterance.getSlotList();

        for (SutrSlot slot : slots) {
            for (SutrParam sutrSutrParam : sutrParamList) {
                if(slot.getSlotName().getText().equals(sutrSutrParam.getParamName().getText())){
                    if(_literalsMap.containsKey(sutrSutrParam.getTypeName().getText())){
                        keySet.add(new SlotDTO(sutrSutrParam.getTypeName().getText(), slot.getText(), slot.getSlotName().getText()));
                    }
                }
            }
        }

        return keySet.toArray(new SlotDTO[keySet.size()]);
    }

    private Collection<? extends String> parseVariations(List<String> seed, Map<String, List<String>> _literalsMap, SlotDTO[] objects, int i) {

        if(i < objects.length){
            List<String> newSeed = new ArrayList<>();
            SlotDTO curKey = objects[i];

            for (String s : seed) {
                for(String value: _literalsMap.get(curKey.typeName)){
                    if(!value.equals("")){
                        newSeed.add(s.replace(curKey.stringName, "{" + value + "|" + curKey.slotName + "}"));
                    }
                }
            }

            return parseVariations(newSeed, _literalsMap, objects, i+1);

        }

        return seed;
    }

    private class SlotDTO {

        public String slotName;
        public String typeName;
        public String stringName;

        public SlotDTO(String typeName, String key, String slotName) {
            this.typeName = typeName;
            this.stringName = key;
            this.slotName = slotName;
        }
    }
}
