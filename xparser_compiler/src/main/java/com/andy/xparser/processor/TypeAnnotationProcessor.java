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
import com.squareup.javapoet.CodeBlock;
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
    private String mModuleName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        mFiler = processingEnv.getFiler();
        mModuleName = processingEnv.getOptions().get("moduleName");
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

        ParameterizedTypeName inputMapTypeOfGroup = ParameterizedTypeName.get(
                ClassName.get(Map.class),
                ClassName.get(String.class),
                ClassName.get(Type.class)
        );

        ParameterSpec groupParamSpec = ParameterSpec.builder(inputMapTypeOfGroup,
                "wareHouse").build();

        MethodSpec.Builder method = MethodSpec.methodBuilder(Constants.NAME_METHOD_LOAD_DATA)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addStatement("wareHouse.putAll(sMap);")
                .addParameter(groupParamSpec);

        CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();
        codeBlockBuilder.add("sMap = new $T();\n", new TypeToken<HashMap<String, Type>>(){}.getType());

        // collect all Annotation Route, Autowired.
        for (Element element : elements) {
            element.getEnclosedElements();
            VariableElement fieldElement = (VariableElement) element;
            Route route = fieldElement.getEnclosingElement().getAnnotation(Route.class);
            if (route == null) {
                continue;
            }

            String dataName = element.getAnnotation(Autowired.class).name();
            String path = route.path();

            if (fieldElement.asType().getKind().isPrimitive()) {
                codeBlockBuilder.add("sMap.put($S, $T.class);\n",
                        path + Constants.SEPERATOR + dataName,
                        fieldElement.asType());
            } else {
                codeBlockBuilder.add("sMap.put($S, new $T<$T>(){}.getType());\n",
                        path + Constants.SEPERATOR + dataName,
                        TypeToken.class,
                        fieldElement.asType());
            }
        }

        try {
            JavaFile.builder(Constants.NAME_PACKAGE_AUTOWIRED_ROUTE,
                    TypeSpec.classBuilder(
                            Constants.NAME_CLASS_PREFIX +
                                    Constants.SEPERATOR +
                                    mModuleName +
                                    Constants.SEPERATOR +
                                    Constants.NAME_CLASS_SUFFIX)
                            .addMethod(method.build())
                            .addSuperinterface(IDataTypeProvider.class)
                            .addModifiers(Modifier.PUBLIC)
                            .addField(new TypeToken<Map<String, Type>>(){}.getType(), "sMap", Modifier.STATIC)
                            .addStaticBlock(codeBlockBuilder.build())
                            .build())
                    .build().writeTo(mFiler);
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR,
                    String.format(FORMATTER_PREFIX_LOG, e.getMessage()));
            e.printStackTrace();

            throw new RuntimeException(e);
        }

        messager.printMessage(Diagnostic.Kind.NOTE,
                String.format(FORMATTER_PREFIX_LOG, "process success."));

        return false;
    }

}
