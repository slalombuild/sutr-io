package com.slalom.aws.avs.sutr;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.impl.PsiFileFactoryImpl;
import com.intellij.testFramework.LightVirtualFile;
import com.slalom.aws.avs.sutr.psi.SutrBody;
import com.slalom.aws.avs.sutr.psi.SutrObject;
import com.slalom.aws.avs.sutr.psi.SutrParamName;
import com.slalom.aws.avs.sutr.psi.SutrUtterance;
import com.slalom.aws.avs.sutr.psi.impl.SutrCustomTypeImpl;
import com.slalom.aws.avs.sutr.psi.impl.SutrSlotImpl;
import com.slalom.aws.avs.sutr.psi.impl.SutrTypeNameImpl;

import java.util.List;

/**
 * Created by Stryder on 1/23/2016.
 */
public class SutrElementFactory {
    public static SutrTypeNameImpl createTypeNameElement(final Project project, final String s) {
        final PsiFile dummyFile = createDummyFile(project, "slotType " + s + "[ foo ]");

        return (SutrTypeNameImpl) ((SutrCustomTypeImpl) dummyFile.getFirstChild()).getTypeName();
    }

    public static PsiFile createDummyFile(Project myProject, String text) {
        final PsiFileFactory factory = PsiFileFactory.getInstance(myProject);
        final String name = "dummy.sutr" + SutrLanguageType.INSTANCE.getDefaultExtension();
        final LightVirtualFile virtualFile = new LightVirtualFile(name, SutrLanguageType.INSTANCE, text);
        final PsiFile psiFile = ((PsiFileFactoryImpl)factory).trySetupPsiForFile(virtualFile, SutrLanguage.INSTANCE, false, true);
        assert psiFile != null;
        return psiFile;
    }

    public static SutrSlotImpl createSlotElement(final Project project, final String s) {

        final String text = "def Foo(){\n {"+s+"}\n} => foo.func";
        final PsiFile dummyFile = createDummyFile(project, text);

        final SutrBody sutrBody = ((SutrObject) dummyFile.getFirstChild()).getBody();
        final List<SutrUtterance> utteranceList = sutrBody.getUtteranceList();

        return (SutrSlotImpl) utteranceList.get(0).getSlotList().get(0);
    }

    public static SutrParamName createParamNameElement(final Project project, final String name) {
        final String text = "def Foo(number "+name+"){\n { foo foo }\n} => foo.func";
        final PsiFile dummyFile = createDummyFile(project, text);
        final SutrObject sutrObject = ((SutrObject) dummyFile.getFirstChild());

        return sutrObject.getSutrParams().getParamList().get(0).getParamName();
    }
}
