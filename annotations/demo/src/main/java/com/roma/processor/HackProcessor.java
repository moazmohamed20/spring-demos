package com.roma.processor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

import com.google.auto.service.AutoService;
import com.roma.annotation.Hack;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.roma.annotation.Hack")
public class HackProcessor extends AbstractProcessor {

  private ProcessingEnvironment env;

  @Override
  public synchronized void init(ProcessingEnvironment pe) {
    this.env = pe;
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (!roundEnv.processingOver()) {

      roundEnv.getElementsAnnotatedWith(Hack.class).forEach(e -> {
        Hack annotation = e.getAnnotation(Hack.class);
        String value = annotation.value();
        env.getMessager().printMessage(Kind.WARNING, "Hacked: " + value, e);
      });

    }
    return true;
  }

}