package com.andy.xparser.processor;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import com.andy.xparser.model.Constants;
import com.andy.xparser.model.IDataTypeProvider;
import com.andy.xparser.model.TypeToken;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;


import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

@AutoService(Processor.class)
@SupportedAnnotationTypes("com.alibaba.android.arouter.facade.annotation.Autowired")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class TypeAnnotationProcessor extends AbstractProcessor {
    private static final String FORMATTER_PREFIX_LOG = "--------XParserCompiler, %s";
    private Messager messager;
    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        mFiler = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Autowired.class);
        if (elements == null || elements.size() <= 0) {
            messager.printMessage(Diagnostic.Kind.NOTE,
                    String.format(FORMATTER_PREFIX_LOG, "nothing need to process!"));
            return false;
        }

        messager.printMessage(Diagnostic.Kind.NOTE,
                String.format(FORMATTER_PREFIX_LOG, "start to process..."));

        Map<String, SourceModel> javaFileBuilderMap = new HashMap<>();
        // collect all Annotation Route, Autowired.
        for (Element element : elements) {
            element.getEnclosedElements();
            VariableElement fieldElement = (VariableElement) element;
            TypeElement classElement = (TypeElement) fieldElement.getEnclosingElement();
            Route route = fieldElement.getEnclosingElement().getAnnotation(Route.class);
            if (route == null) {
                continue;
            }

            String dataName = element.getAnnotation(Autowired.class).name();
            String path = route.path();
            String actSimpleName = classElement.getSimpleName().toString();
            String fieldClassName = fieldElement.asType().toString();

            SourceModel sourceModel;
            if (javaFileBuilderMap.containsKey(path)) {
                sourceModel = javaFileBuilderMap.get(path);
            } else {
                sourceModel = new SourceModel();
            }

            sourceModel.actSimpleName = actSimpleName;
            sourceModel.dataName = dataName;
            sourceModel.fieldClassName = fieldClassName;


            if (null == sourceModel.method) {
                ParameterizedTypeName inputMapTypeOfGroup = ParameterizedTypeName.get(
                        ClassName.get(Map.class),
                        ClassName.get(String.class),
                        ClassName.get(Type.class)
                );

                ParameterSpec groupParamSpec = ParameterSpec.builder(inputMapTypeOfGroup,
                        "wareHouse").build();

                sourceModel.method = MethodSpec.methodBuilder(Constants.NAME_METHOD_LOAD_DATA)
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(groupParamSpec);
            }

            if (fieldElement.asType().getKind().isPrimitive()) {
                sourceModel.method.addStatement("wareHouse.put($S, $T.class)",
                        path + Constants.SEPERATE + dataName,
                        fieldElement.asType());
            } else {
                sourceModel.method.addStatement("wareHouse.put($S, new $T<$T>(){}.getType())",
                        path + Constants.SEPERATE + dataName,
                        TypeToken.class,
                        fieldElement.asType());
            }
            javaFileBuilderMap.put(path, sourceModel);
        }

        if (!javaFileBuilderMap.isEmpty()) {
            Set<String> keySet = javaFileBuilderMap.keySet();
            for (String key : keySet) {
                SourceModel sourceModel = javaFileBuilderMap.get(key);
                if (sourceModel == null) {
                    continue;
                }
                try {
                    JavaFile.builder(Constants.NAME_PACKAGE_AUTOWIRED_ROUTE,
                            TypeSpec.classBuilder(
                                    Constants.NAME_CLASS_PREFIX + Constants.SEPERATE
                                            + sourceModel.actSimpleName)
                                    .addSuperinterface(IDataTypeProvider.class)
                                    .addMethod(sourceModel.method.build())
                                    .addModifiers(Modifier.PUBLIC)
                                    .build())
                            .build().writeTo(mFiler);
                } catch (IOException e) {
                    messager.printMessage(Diagnostic.Kind.ERROR,
                            String.format(FORMATTER_PREFIX_LOG, e.getMessage()));
                    e.printStackTrace();

                    throw new RuntimeException(e);
                }


            }

        }
        messager.printMessage(Diagnostic.Kind.NOTE,
                String.format(FORMATTER_PREFIX_LOG, "process success."));

        return false;
    }

}
