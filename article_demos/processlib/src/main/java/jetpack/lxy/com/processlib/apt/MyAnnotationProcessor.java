package jetpack.lxy.com.processlib.apt;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import jetpack.lxy.com.processlib.anno.FindViewId;
import jetpack.lxy.com.processlib.anno.TargetClass;

/**
 * @author a
 */
@AutoService(Processor.class)
public class MyAnnotationProcessor extends AbstractProcessor {

    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        elementUtils = processingEnvironment.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        // 添加自定义注解 请务必注意set的添加顺序
        set.add(TargetClass.class.getCanonicalName());
        set.add(FindViewId.class.getCanonicalName());

        return set;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        // 获取所有注解了TargetClass 的elements
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(TargetClass.class);

        for (Element element : elements) {
            // View 的所在 Activity 类
            TypeElement activity = (TypeElement) element;
            // 创建方法 需要引入 javapoet

            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("bind")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(TypeName.VOID)
                    .addParameter(ClassName.get(activity), "activity");
            //  获取单个类中，所有子Element
            List<? extends Element> allMembers = elementUtils.getAllMembers(activity);
            for (Element fieldElement : allMembers) {
                FindViewId bindView = fieldElement.getAnnotation(FindViewId.class);
                if (bindView != null) {
                    // 获取resId
                    int resID = bindView.value();
                    // 添加代码
                    methodBuilder.addStatement(
                            "activity.$L= ($T) activity.findViewById($L)",
                            fieldElement,
                            ClassName.get(fieldElement.asType()),
                            resID
                    );
                }
            }
            MethodSpec methodSpec = methodBuilder.build();
            // 创建类
            TypeSpec typeSpec = TypeSpec.classBuilder("View" + activity.getSimpleName())
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(methodSpec)
                    .build();
            try {
                // 获取包名
                PackageElement packageElement = elementUtils.getPackageOf(activity);
                String packageName = packageElement.getQualifiedName().toString();
                // 创建文件
                JavaFile javaFile = JavaFile.builder(packageName, typeSpec).build();
                javaFile.writeTo(processingEnv.getFiler());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return false;
    }
}
