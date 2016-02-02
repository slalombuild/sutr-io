package com.slalom.aws.avs.sutr.psi.impl;

import com.intellij.lang.Commenter;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Stryder on 1/23/2016.
 */
public class SutrCommenter implements Commenter {

    @Nullable
    @Override
    public String getLineCommentPrefix() {
        return "#";
    }

    @Nullable
    @Override
    public String getBlockCommentPrefix() {
        return "";
    }

    @Nullable
    @Override
    public String getBlockCommentSuffix() {
        return null;
    }

    @Nullable
    @Override
    public String getCommentedBlockCommentPrefix() {
        return null;
    }

    @Nullable
    @Override
    public String getCommentedBlockCommentSuffix() {
        return null;
    }
}
