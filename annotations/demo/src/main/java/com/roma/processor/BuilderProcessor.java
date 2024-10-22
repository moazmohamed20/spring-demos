package com.roma.processor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import com.google.auto.service.AutoService;
import com.roma.annotation.Builder;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.roma.annotation.Builder")
public class BuilderProcessor extends AbstractProcessor {

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    roundEnv.getElementsAnnotatedWith(Builder.class).forEach(this::generateBuilderFile);
    return true;
  }

  private void generateBuilderFile(Element element) {
    String className = element.getSimpleName().toString();
    String packageName = element.getEnclosingElement().toString();

    String builderName = className + "Builder";
    String builderFullName = packageName + "." + builderName;

    List<? extends Element> fields = element.getEnclosedElements()
        .stream()
        .filter(e -> e.getKind().isField())
        .collect(Collectors.toList());

    try (PrintWriter writer = new PrintWriter(processingEnv.getFiler().createClassFile(builderFullName).openWriter())) {
      writer.println("package " + packageName + ";");
      writer.println();
      writer.println("public class " + builderName + " {");
      writer.println();
      writer.println("  private " + className + " instance = new " + className + "();");
      writer.println("  public " + className + " build() {");
      writer.println("    return instance;");
      writer.println("  }");
      writer.println();
      fields.forEach(f -> {
        String fieldName = f.getSimpleName().toString();
        String fieldType = f.asType().toString();
        writer.println("  public " + builderName + " " + fieldName + "(" + fieldType + " " + fieldName + ") {");
        writer.println("    instance." + fieldName + " = " + fieldName + ";");
        writer.println("    return this;");
        writer.println("  }");
        writer.println();
      });
      writer.println("}");
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("Generating file: " + builderName);
  }

}
