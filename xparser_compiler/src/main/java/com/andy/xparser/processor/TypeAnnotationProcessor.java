package com.andy.xparser.processor;

import com.andy.xparser.model.Constants;
import com.andy.xparser.model.IDataTypeProvider;
import com.google.auto.service.AutoService;

import java.io.IOException;
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

import annotation.Autowired;
import annotation.Route;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

@AutoService(Processor.class)
@SupportedAnnotationTypes("annotation.Autowired")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class TypeAnnotationProcessor extends AbstractProcessor {
    private static final String FORMATTER_PREFIX_LOG = "--XParserCompiler, %s";
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
            messager.printMessage(Diagnostic.Kind.WARNING,
                    String.format(FORMATTER_PREFIX_LOG, "nothing need to process!"));
            return false;
        }

        messager.printMessage(Diagnostic.Kind.NOTE,
                String.format(FORMATTER_PREFIX_LOG, "start to process..."));

        Map<String, SourceModel> javaFileBuilderMap = new HashMap<>();
        // write put
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
            String actFullClassName = classElement.getQualifiedName().toString();
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
                        ClassName.get(Class.class)
                );

                ParameterSpec groupParamSpec = ParameterSpec.builder(inputMapTypeOfGroup,
                        "wareHouse").build();

                sourceModel.method = MethodSpec.methodBuilder(Constants.NAME_METHOD_LOAD_DATA)
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(groupParamSpec);
            }

            sourceModel.method.addStatement("wareHouse.put($S, $T.class)",
                    path + Constants.SEPERATE + dataName,
                    ClassName.get(fieldElement.asType()));

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
                }


            }

        }
        messager.printMessage(Diagnostic.Kind.NOTE,
                String.format(FORMATTER_PREFIX_LOG, "process success."));

        return true;
    }

}
