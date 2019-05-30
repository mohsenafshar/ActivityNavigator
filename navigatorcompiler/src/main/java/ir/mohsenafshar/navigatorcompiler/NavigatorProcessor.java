package ir.mohsenafshar.navigatorcompiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import ir.mohsenafshar.navigatorannotation.BooleanExtra;
import ir.mohsenafshar.navigatorannotation.ByteExtra;
import ir.mohsenafshar.navigatorannotation.CharExtra;
import ir.mohsenafshar.navigatorannotation.DoubleExtra;
import ir.mohsenafshar.navigatorannotation.FloatExtra;
import ir.mohsenafshar.navigatorannotation.IntExtra;
import ir.mohsenafshar.navigatorannotation.LongExtra;
import ir.mohsenafshar.navigatorannotation.Navigate;
import ir.mohsenafshar.navigatorannotation.ShortExtra;
import ir.mohsenafshar.navigatorannotation.StringExtra;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({
        "ir.mohsenafshar.navigatorannotation.Navigate"
})
public class NavigatorProcessor extends AbstractProcessor {

    private static final String METHOD_PREFIX = "start";
    private static final ClassName classIntent = ClassName.get("android.content", "Intent");
    private static final ClassName classContext = ClassName.get("android.content", "Context");
    private static final ClassName classBundle = ClassName.get("android.os", "Bundle");

    private Filer filer;
    private Messager messager;
    private Elements elements;
    private Map<String, String> activitiesWithPackage;
    private Map<String, Container> AnnotationValuesWithActivities;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        elements = processingEnvironment.getElementUtils();
        activitiesWithPackage = new HashMap<>();
        AnnotationValuesWithActivities = new HashMap<>();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        try {
            for (Element element : roundEnvironment.getElementsAnnotatedWith(Navigate.class)) {
                if (element.getKind() != ElementKind.CLASS) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "it's just can applied to CLASS");
                    return true;
                }

                TypeElement typeElement = (TypeElement) element;
                activitiesWithPackage.put(
                        typeElement.getSimpleName().toString(),
                        elements.getPackageOf(typeElement).getQualifiedName().toString());

                Navigate navigate = typeElement.getAnnotation(Navigate.class);
                if (navigate != null) {
                    StringExtra[] stringExtras = navigate.stringExtra();
                    IntExtra[] intExtras = navigate.intExtra();
                    BooleanExtra[] booleanExtras = navigate.booleanExtra();
                    ByteExtra[] byteExtra = navigate.byteExtra();
                    CharExtra[] charExtras = navigate.charExtra();
                    DoubleExtra[] doubleExtra = navigate.doubleExtra();
                    FloatExtra[] floatExtra = navigate.floatExtra();
                    LongExtra[] longExtra = navigate.longExtra();
                    ShortExtra[] shortExtra = navigate.shortExtra();

                    AnnotationValuesWithActivities.put(typeElement.getSimpleName().toString(),
                            new Container(booleanExtras, byteExtra, charExtras, doubleExtra, floatExtra, intExtras, longExtra, shortExtra, stringExtras));
                }
            }

            TypeSpec.Builder navigatorClass = TypeSpec.classBuilder("Navigator")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

            for (Map.Entry<String, String> element : activitiesWithPackage.entrySet()) {
                String activityName = element.getKey();
                String packageName = element.getValue();
                ClassName activityClass = ClassName.get(packageName, activityName);
                Container container = AnnotationValuesWithActivities.get(activityName);

                MethodSpec intentMethodWithoutExtra = MethodSpec
                        .methodBuilder("get" + activityName + "Intent")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(classIntent)
                        .addParameter(classContext, "context")
                        .addStatement("return new $T($L, $L)", classIntent, "context", activityClass + ".class")
                        .build();
                navigatorClass.addMethod(intentMethodWithoutExtra);

                MethodSpec startActivityMethodWithoutExtra = MethodSpec
                        .methodBuilder(METHOD_PREFIX + activityName)
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .addParameter(classContext, "context")
                        .addStatement("$L.startActivity(new $T($L, $L))", "context", classIntent, "context", activityClass + ".class")
                        .build();
                navigatorClass.addMethod(startActivityMethodWithoutExtra);

                MethodSpec.Builder builder = MethodSpec
                        .methodBuilder(METHOD_PREFIX + activityName)
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .addParameter(classContext, "context")
                        .addParameter(classBundle, "bundle")
                        //.addStatement("return new $T($L, $L)", classIntent, "context", activityClass + ".class")
                        .addStatement("$T intent = new $T($L, $L)", classIntent, classIntent, "context", activityClass + ".class")
                        .addStatement("intent.putExtras($L)", "bundle");

                for (BooleanExtra booleanExtra : container.booleanExtras) {
                    String key = booleanExtra.key();
                    Boolean value = booleanExtra.value();
                    if (!key.equals(""))
                        builder.addStatement("intent.putExtra(\"$L\", \"$L\")", key, value);
                }

                for (ByteExtra byteExtra : container.byteExtras) {
                    String key = byteExtra.key();
                    Byte value = byteExtra.value();
                    if (!key.equals(""))
                        builder.addStatement("intent.putExtra(\"$L\", \"$L\")", key, value);
                }

                for (CharExtra charExtra : container.charExtras) {
                    String key = charExtra.key();
                    char value = charExtra.value();
                    if (!key.equals(""))
                        builder.addStatement("intent.putExtra(\"$L\", \"$L\")", key, value);
                }

                for (DoubleExtra doubleExtra : container.doubleExtras) {
                    String key = doubleExtra.key();
                    double value = doubleExtra.value();
                    if (!key.equals(""))
                        builder.addStatement("intent.putExtra(\"$L\", \"$L\")", key, value);
                }

                for (FloatExtra floatExtra : container.floatExtras) {
                    String key = floatExtra.key();
                    float value = floatExtra.value();
                    if (!key.equals(""))
                        builder.addStatement("intent.putExtra(\"$L\", \"$L\")", key, value);
                }

                for (LongExtra longExtra : container.longExtras) {
                    String key = longExtra.key();
                    float value = longExtra.value();
                    if (!key.equals(""))
                        builder.addStatement("intent.putExtra(\"$L\", \"$L\")", key, value);
                }

                for (ShortExtra shortExtra: container.shortExtras) {
                    String key = shortExtra.key();
                    float value = shortExtra.value();
                    if (!key.equals(""))
                        builder.addStatement("intent.putExtra(\"$L\", \"$L\")", key, value);
                }

                for (IntExtra intExtra : container.intExtras) {
                    String key = intExtra.key();
                    int value = intExtra.value();
                    if (!key.equals("") && value != 0)
                        builder.addStatement("intent.putExtra(\"$L\", $L)", key, value);
                }

                for (StringExtra stringExtra : container.stringExtras) {
                    String key = stringExtra.key();
                    String value = stringExtra.value();
                    if (!key.equals("") && !value.equals(""))
                        builder.addStatement("intent.putExtra(\"$L\", \"$L\")", key, value);
                }

                builder.addStatement("$L.startActivity(intent)", "context");
                MethodSpec intentMethod = builder.build();
                navigatorClass.addMethod(intentMethod);
            }


            /*
             * 3- Write generated class to a file
             */
            JavaFile.builder("ir.mohsenafshar.activitynavigator", navigatorClass.build()).build().writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    private class Container {
        BooleanExtra[] booleanExtras;
        ByteExtra[] byteExtras;
        CharExtra[] charExtras;
        DoubleExtra[] doubleExtras;
        FloatExtra[] floatExtras;
        IntExtra[] intExtras;
        LongExtra[] longExtras;
        ShortExtra[] shortExtras;
        StringExtra[] stringExtras;

        public Container(BooleanExtra[] booleanExtras, ByteExtra[] byteExtras, CharExtra[] charExtras, DoubleExtra[] doubleExtras, FloatExtra[] floatExtras, IntExtra[] intExtras, LongExtra[] longExtras, ShortExtra[] shortExtras, StringExtra[] stringExtras) {
            this.booleanExtras = booleanExtras;
            this.byteExtras = byteExtras;
            this.charExtras = charExtras;
            this.doubleExtras = doubleExtras;
            this.floatExtras = floatExtras;
            this.intExtras = intExtras;
            this.longExtras = longExtras;
            this.shortExtras = shortExtras;
            this.stringExtras = stringExtras;
        }
    }

}
