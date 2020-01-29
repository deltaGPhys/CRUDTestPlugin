import com.intellij.ide.impl.DataManagerImpl;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.impl.EditorComponentImpl;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.ui.ComboBoxCompositeEditor;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

public class TestAction extends AnAction {

    @Override
    public void update(AnActionEvent e) {
        // Using the event, evaluate the context, and enable or disable the action.
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // Using the event, implement an action. For example, create and show a dialog.
        final Project project = e.getProject();
        final DataContext context = e.getDataContext();
        final WeakReference ref = (WeakReference) context.getData("referent");
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        //JavaPsiFacade.findClass()
        JavaPsiFacade facade = JavaPsiFacade.getInstance(project);
        PsiClass psiClass = facade.findClass(psiFile.getName(), GlobalSearchScope.allScope(project));

        System.out.println("psiFile.getName() = " + psiFile.getName());
        Class<?> clazz = null;
        try {
            clazz = Class.forName(com.intellij.psi.util.PsiUtil.getPackageName(psiClass)+"."+psiFile.getName());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            Object date = clazz.newInstance();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }

        boolean isAvailable = project != null;
    }

}