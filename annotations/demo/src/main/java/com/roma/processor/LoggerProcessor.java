package com.roma.processor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
import javax.tools.Diagnostic;
import javax.tools.FileObject;

import com.google.auto.service.AutoService;
import com.roma.annotation.Logger;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.roma.annotation.Logger")
public class LoggerProcessor extends AbstractProcessor {

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    for (Element element : roundEnv.getElementsAnnotatedWith(Logger.class)) {
      if (element.getKind().isClass()) {
        try {
          modifyClass((TypeElement) element);
        } catch (IOException e) {
          processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.toString());
        }
      }
    }
    return true;
  }

  private void modifyClass(TypeElement classElement) throws IOException {
    String packageName = processingEnv.getElementUtils().getPackageOf(classElement).getQualifiedName().toString();
    String className = classElement.getSimpleName().toString();

    // Read the existing class content
    FileObject sourceFile = processingEnv.getFiler().getResource(javax.tools.StandardLocation.SOURCE_PATH, packageName,
        className + ".java");

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(sourceFile.openInputStream()))) {
      String existingClassContent = reader.lines().collect(Collectors.joining("\n"));

      // Find the annotated class and insert the logger field
      String modifiedClassContent = addLoggerField(existingClassContent, className);

      // Write the modified class back to the file
      try (Writer writer = new BufferedWriter(new OutputStreamWriter(sourceFile.openOutputStream()))) {
        writer.write(modifiedClassContent);
      }
    }
  }

  private String addLoggerField(String classContent, String className) {
    String loggerField = "private final java.util.logging.Logger log = java.util.logging.Logger.getLogger(" + className
        + ".class.getName());\n";

    int classIndex = classContent.indexOf("class " + className);
    if (classIndex == -1) {
      return classContent; // Class not found, return original content
    }

    int braceIndex = classContent.indexOf("{", classIndex);
    if (braceIndex == -1) {
      return classContent; // Opening brace not found, return original content
    }

    String beforeBrace = classContent.substring(0, braceIndex + 1);
    String afterBrace = classContent.substring(braceIndex + 1);

    return beforeBrace + "\n" + loggerField + afterBrace;
  }
}
