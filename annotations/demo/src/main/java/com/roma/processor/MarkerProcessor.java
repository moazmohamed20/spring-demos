package com.roma.processor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import com.google.auto.service.AutoService;
import com.roma.annotation.Marker;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.roma.annotation.Marker")
public final class MarkerProcessor extends AbstractProcessor {

  private void causeError(String message, Element e) {
    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, e);
  }

  private void causeError(Element subtype, Element supertype, Element method) {
    String message;
    if (subtype == supertype) {
      message = String.format("@Marker target %s declares a method %s", subtype, method);
    } else {
      message = String.format("@Marker target %s has a super interface %s which declares a method %s", subtype, supertype, method);
    }

    causeError(message, subtype);
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

    Elements elementUtils = processingEnv.getElementUtils();
    boolean processMarker = annotations.contains(elementUtils.getTypeElement(Marker.class.getName()));
    if (!processMarker)
      return false;

    for (Element e : roundEnv.getElementsAnnotatedWith(Marker.class)) {
      ElementKind kind = e.getKind();

      if (kind != ElementKind.INTERFACE) {
        causeError(String.format("target of @Marker %s is not an interface", e), e);
        continue;
      }

      if (kind == ElementKind.ANNOTATION_TYPE) {
        causeError(String.format("target of @Marker %s is an annotation", e), e);
        continue;
      }

      ensureNoMethodsDeclared(e, e);
    }

    return true;
  }

  private void ensureNoMethodsDeclared(Element subtype, Element supertype) {
    TypeElement type = (TypeElement) supertype;

    for (Element member : type.getEnclosedElements()) {
      if (member.getKind() != ElementKind.METHOD)
        continue;
      if (member.getModifiers().contains(Modifier.STATIC))
        continue;
      causeError(subtype, supertype, member);
    }

    Types typeUtils = processingEnv.getTypeUtils();
    for (TypeMirror face : type.getInterfaces()) {
      ensureNoMethodsDeclared(subtype, typeUtils.asElement(face));
    }
  }
}